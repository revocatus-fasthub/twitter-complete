package tz.co.fasthub.ona.controller;

/**
 * Created by root on 3/27/17.
 */

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.HttpParameter;
import twitter4j.JSONObject;
import twitter4j.TwitterException;
import tz.co.fasthub.ona.domain.Video;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import static org.springframework.http.RequestEntity.post;


public class MediaUploadingController {

    private static final Logger logger = LoggerFactory.getLogger(MediaUploadingController.class);

    private final String CHUNKED_INIT = "INIT";
    private final String CHUNKED_APPEND = "APPEND";
    private final String CHUNKED_FINALIZE = "FINALIZE";
    private final String CHUNKED_STATUS = "STATUS";

    private final int MB = 1024 * 1024; // 1 MByte
    private final int MAX_VIDEO_SIZE = 512 * MB; // 512MB is a constraint  imposed by Twitter for video files
    private final int CHUNK_SIZE = 2 * MB; // max chunk size



    public Video uploadMedia(File image) throws TwitterException {
        checkFileValidity(image);
        return new Video(post(conf.getUploadBaseURL() + "media/upload.json"
                , new HttpParameter("media", image)).asJSONObject());
    }

    
    public Video uploadMedia(String fileName, InputStream image) throws TwitterException {
        return new Video(post(conf.getUploadBaseURL() + "media/upload.json"
                , new HttpParameter("media", fileName, image)).asJSONObject());
    }

    
    public Video uploadMediaChunked(String fileName, InputStream media) throws TwitterException {
        //If the InputStream is remote, this is will download it into memory speeding up the chunked upload process
        byte[] dataBytes = null;
        try {
            dataBytes = IOUtils.toByteArray(media);
            if (dataBytes.length > MAX_VIDEO_SIZE) {
                throw new TwitterException(String.format(Locale.US,
                        "video file can't be longer than: %d MBytes",
                        MAX_VIDEO_SIZE / MB));
            }
        } catch (IOException ioe) {
            throw new TwitterException("Failed to download the file.", ioe);
        }

        try {

            Video uploadedMedia = uploadMediaChunkedInit(dataBytes.length);
            //no need to close ByteArrayInputStream
            ByteArrayInputStream dataInputStream = new ByteArrayInputStream(dataBytes);

            byte[] segmentData = new byte[CHUNK_SIZE];
            int segmentIndex = 0;
            int totalRead = 0;
            int bytesRead = 0;

            while ((bytesRead = dataInputStream.read(segmentData)) > 0) {
                totalRead = totalRead + bytesRead;
                logger.debug("Chunked appened, segment index:" + segmentIndex + " bytes:" + totalRead + "/" + dataBytes.length );
                //no need to close ByteArrayInputStream
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(segmentData, 0 ,bytesRead);
                uploadMediaChunkedAppend(fileName, byteArrayInputStream, segmentIndex, uploadedMedia.getMediaId());
                segmentData = new byte[CHUNK_SIZE];
                segmentIndex++;
            }
            return uploadMediaChunkedFinalize(uploadedMedia.getMediaId());
        } catch (Exception e) {
            throw new TwitterException(e);
        }
    }

    // twurl -H upload.twitter.com "/1.1/media/upload.json" -d
    // "command=INIT&media_type=video/mp4&total_bytes=4430752"

    private Video uploadMediaChunkedInit(long size) throws TwitterException {
        return new Video(post(
                conf.getUploadBaseURL() + "media/upload.json",
                new HttpParameter[] { new HttpParameter("command", CHUNKED_INIT),
                        new HttpParameter("media_type", "video/mp4"),
                        new HttpParameter("media_category", "tweet_video"),
                        new HttpParameter("total_bytes", size) })
                .asJSONObject());
    }

    // twurl -H upload.twitter.com "/1.1/media/upload.json" -d
    // "command=APPEND&media_id=601413451156586496&segment_index=0" --file
    // /path/to/video.mp4 --file-field "media"

    private void uploadMediaChunkedAppend(String fileName, InputStream media, int segmentIndex, long mediaId) throws TwitterException {
        post(conf.getUploadBaseURL() + "media/upload.json", new HttpParameter[] {
                new HttpParameter("command", CHUNKED_APPEND), new HttpParameter("media_id", mediaId),
                new HttpParameter("segment_index", segmentIndex), new HttpParameter("media", fileName, media) });
    }

    // twurl -H upload.twitter.com "/1.1/media/upload.json" -d
    // "command=FINALIZE&media_id=601413451156586496"

    private Video uploadMediaChunkedFinalize(long mediaId) throws TwitterException {
        int tries = 0;
        int maxWait = 10;
        Video uploadedMedia = uploadMediaChunkedFinalize0(mediaId);
        while (tries < maxWait) {
            tries++;
            String state = uploadedMedia.getProcessingState();
            if (state.equals("failed")) {
                throw new TwitterException("Failed to finalize the chuncked upload.");
            }
            if (state.equals("pending") || state.equals("in_progress")) {
                int waitSec = uploadedMedia.getProcessingCheckAfterSecs();
                logger.debug("Chunked finalize, wait for:" + waitSec + " sec");
                try {
                    Thread.sleep(waitSec * 1000);
                } catch (InterruptedException e) {
                    throw new TwitterException("Failed to finalize the chuncked upload.", e);
                }
            }
            if (state.equals("succeeded")) {
                return uploadedMedia;
            }
            uploadedMedia = uploadMediaChunkedStatus(mediaId);
        }
        throw new TwitterException("Failed to finalize the chuncked upload, tried" + maxWait + "times.");
    }

    private Video uploadMediaChunkedFinalize0(long mediaId) throws TwitterException {
        JSONObject json = post(
                conf.getUploadBaseURL() + "media/upload.json",
                new HttpParameter[] {
                        new HttpParameter("command", CHUNKED_FINALIZE),
                        new HttpParameter("media_id", mediaId) })
                .asJSONObject();
        logger.debug("Finalize response:" + json);
        return new Video(json);
    }

    private Video uploadMediaChunkedStatus(long mediaId) throws TwitterException {
        JSONObject json = get(
                conf.getUploadBaseURL() + "media/upload.json",
                new HttpParameter[] {
                        new HttpParameter("command", CHUNKED_STATUS),
                        new HttpParameter("media_id", mediaId) })
                .asJSONObject();
        logger.debug("Status response:" + json);
        return new Video(json);
    }

}

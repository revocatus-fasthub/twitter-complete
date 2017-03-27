package tz.co.fasthub.ona.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Example application that uses OAuth method to acquire access to your account.<br>
 * This application illustrates how to use OAuth method with Twitter4J.<br>
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public  class UpdateStatus {
    /**
     * Usage: java twitter4j.examples.tweets.UpdateStatus [text]
     **/

    private static final Logger log = LoggerFactory.getLogger(TwitterController.class);

    public static void updateStatusTwitter(String postMessage) {

        try {
            Twitter twitter = new TwitterFactory().getInstance();
            try {
                // get request token.
                // this will throw IllegalStateException if access token is already available
                RequestToken requestToken = twitter.getOAuthRequestToken();
                log.info("Got request token.");
                log.info("Request token: " + requestToken.getToken());
                log.info("Request token secret: " + requestToken.getTokenSecret());
                AccessToken accessToken = null;

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                while (null == accessToken) {
                    log.info("Open the following URL and grant access to your account:");
                    log.info(requestToken.getAuthorizationURL());
                    log.info("Enter the PIN(if available) and hit enter after you granted access.[PIN]:");
                    String pin = br.readLine();
                    try {
                        if (pin.length() > 0) {
                            accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                        } else {
                            accessToken = twitter.getOAuthAccessToken(requestToken);
                        }
                    } catch (TwitterException te) {
                        if (401 == te.getStatusCode()) {
                            log.debug("Unable to get the access token.");
                        } else {
                            te.printStackTrace();
                        }
                    }
                }
                log.info("Got access token.");
                log.info("Access token: " + accessToken.getToken());
                log.info("Access token secret: " + accessToken.getTokenSecret());
            } catch (IllegalStateException ie) {
                // access token is already available, or consumer key/secret is not set.
                if (!twitter.getAuthorization().isEnabled()) {
                    log.info("OAuth consumer key/secret is not set.");
                }
            }
            Status status = twitter.updateStatus(postMessage);
            log.info("Successfully updated the status to [" + status.getText() + "].");
        } catch (TwitterException te) {
            te.printStackTrace();
            log.info("Failed to get timeline: " + te.getMessage());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            log.info("Failed to read the system input.");
        }
    }
}
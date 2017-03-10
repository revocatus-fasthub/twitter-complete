package tz.co.fasthub.ona.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import tz.co.fasthub.ona.domain.Video;

/**
 * Created by root on 3/10/17.
 */
public interface VideoRepository extends PagingAndSortingRepository<Video,Long> {
}

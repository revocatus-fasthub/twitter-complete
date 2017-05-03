package tz.co.fasthub.ona.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import tz.co.fasthub.ona.domain.Payload;

/**
 * Created by Naamini on 5/3/17.
 */

public interface PayloadRepository extends PagingAndSortingRepository<Payload,Long> {
}

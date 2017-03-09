package tz.co.fasthub.ona.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tz.co.fasthub.ona.domain.Payload;

/**
 * Created by root on 3/6/17.
 */
public interface TwitterRepository extends JpaRepository<Payload,Long> {

}

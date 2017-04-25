package tz.co.fasthub.ona.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tz.co.fasthub.ona.domain.TwitterTalentAccount;

/**
 * Created by root on 3/31/17.
 */
public interface TwitterTalentRepository extends JpaRepository<TwitterTalentAccount, Long> {

   // TwitterTalentAccount findByUsername(String username);

}

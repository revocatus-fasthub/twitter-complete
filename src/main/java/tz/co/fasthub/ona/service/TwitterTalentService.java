package tz.co.fasthub.ona.service;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tz.co.fasthub.ona.domain.TwitterTalentAccount;

/**
 * Created by root on 3/31/17.
 */
public interface TwitterTalentService {

    TwitterTalentAccount save(TwitterTalentAccount twitterTalentAccount);

    TwitterTalentAccount getTalentById(Long id);

    void deleteTalentById(Long id) throws NotFoundException;

    Page<TwitterTalentAccount> findTalentPage(Pageable pageable);

    TwitterTalentAccount findOne(Long id) throws NotFoundException;

    TwitterTalentAccount findById(Long id);
}
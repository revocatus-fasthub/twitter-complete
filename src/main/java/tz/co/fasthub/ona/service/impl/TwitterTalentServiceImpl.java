package tz.co.fasthub.ona.service.impl;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tz.co.fasthub.ona.domain.TwitterTalentAccount;
import tz.co.fasthub.ona.repository.TwitterTalentRepository;
import tz.co.fasthub.ona.service.TwitterTalentService;

/**
 * Created by root on 3/31/17.
 */
@Service
public class TwitterTalentServiceImpl implements TwitterTalentService {

    private final TwitterTalentRepository twitterTalentRepository;

    @Autowired
    public TwitterTalentServiceImpl(TwitterTalentRepository twitterTalentRepository) {
        this.twitterTalentRepository = twitterTalentRepository;
    }

    @Override
    public TwitterTalentAccount save(TwitterTalentAccount twitterTalentAccount) {
        return twitterTalentRepository.save(twitterTalentAccount);
    }

    @Override
    public TwitterTalentAccount getTalentById(Long id) {
        return twitterTalentRepository.getOne(id);
    }

    @Override
    public void deleteTalentById(Long id) throws NotFoundException {
        twitterTalentRepository.delete(id);
    }

    @Override
    public Page<TwitterTalentAccount> findTalentPage(Pageable pageable) {
        return twitterTalentRepository.findAll(pageable);
    }

    @Override
    public TwitterTalentAccount findOne(Long id) throws NotFoundException {
        return twitterTalentRepository.findOne(id);
    }

    @Override
    public TwitterTalentAccount findById(Long id) {
        return twitterTalentRepository.findOne(id);
    }
}

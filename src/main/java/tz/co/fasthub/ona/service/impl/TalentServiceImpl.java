package tz.co.fasthub.ona.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.stereotype.Service;
import tz.co.fasthub.ona.domain.Talent;
import tz.co.fasthub.ona.repository.TalentRepository;
import tz.co.fasthub.ona.service.TalentService;

import javax.transaction.Transactional;

/**
 * Created by root on 2/10/17.
 */ 
@Service
public class TalentServiceImpl implements TalentService {


    private final TalentRepository talentRepository;

    @Autowired
    public TalentServiceImpl(TalentRepository talentRepository) {
        this.talentRepository = talentRepository;
    }


    @Override
    public Talent getTalentbyId(Long talent_id) {
        return talentRepository.getOne(talent_id);
    }

    @Override
    public Talent getTalentByFname(String fname) {
        return talentRepository.findByFname(fname);
    }

    @Override
    public void deleteTalentById(Long talent_id) {
        talentRepository.delete(talent_id);
    }

    @Override
    public Talent createTalent(Talent talent) {
       return talentRepository.save(talent);
    }

    @Override
    public Page<Talent> findTalentPage(Pageable pageable) {
        return talentRepository.findAll(pageable);
    }

    @Override
    public Talent findOne(Long talent_id) {
        return talentRepository.findOne(talent_id);
    }

    @Override
    public Talent findById(Long talent_id) {
        return talentRepository.findOne(talent_id);
    }
}

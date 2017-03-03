package tz.co.fasthub.ona.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tz.co.fasthub.ona.domain.Talent;
import tz.co.fasthub.ona.repository.TalentRepository;
import tz.co.fasthub.ona.service.TalentService;

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
    public Talent getTalentByFname(String fname) {
        return talentRepository.findByFname(fname);
    }

    @Override
    public void deleteTalentById(Long id) {
        talentRepository.delete(id);
    }

    @Override
    public Talent createTalent(Talent Talent) {
       return talentRepository.save(Talent);
    }

    @Override
    public Page<Talent> findTalentPage(Pageable pageable) {
        return talentRepository.findAll(pageable);
    }
}

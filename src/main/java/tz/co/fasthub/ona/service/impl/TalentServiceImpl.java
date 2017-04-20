package tz.co.fasthub.ona.service.impl;

import tz.co.fasthub.ona.domain.Talent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tz.co.fasthub.ona.repository.TalentRepository;
import tz.co.fasthub.ona.service.TalentService;

/**
 * Talent service implement.
 */
@Service
public class TalentServiceImpl implements TalentService {

    private TalentRepository talentRepository;

    @Autowired
    public void setTalentRepository(TalentRepository talentRepository) {
        this.talentRepository = talentRepository;
    }

    @Override
    public Iterable<Talent> listAllTalent() {
        return talentRepository.findAll();
    }

    @Override
    public Talent getTalentById(Integer id) {
        return talentRepository.findOne(id);
    }

    @Override
    public Talent saveTalent(Talent talent) {
        return talentRepository.save(talent);
    }

    @Override
    public void deleteTalent(Integer id) {
        talentRepository.delete(id);
    }
}

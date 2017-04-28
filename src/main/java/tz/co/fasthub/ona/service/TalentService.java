package tz.co.fasthub.ona.service;

import tz.co.fasthub.ona.domain.Talent;

public interface TalentService {

    Iterable<Talent> listAllTalent();

    Talent getTalentById(Integer id);

    Talent findByTwitterScreenName(String twitterScreenName);

    Talent saveTalent(Talent talent);

    void deleteTalent(Integer id);


}

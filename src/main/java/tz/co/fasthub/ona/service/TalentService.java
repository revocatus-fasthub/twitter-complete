package tz.co.fasthub.ona.service;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.social.oauth1.OAuthToken;
import tz.co.fasthub.ona.domain.Talent;

public interface TalentService {
    Talent getTalentbyId(Long talent_id);
    Talent getTalentByFname(String fname);
     void deleteTalentById(Long id) throws NotFoundException;
     Object createTalent(Talent talent);
     Page<Talent> findTalentPage(Pageable pageable);
     Talent findById(Long talent_id);
     Talent findOne(Long talent_id) throws NotFoundException;
}

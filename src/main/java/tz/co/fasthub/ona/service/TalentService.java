package tz.co.fasthub.ona.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tz.co.fasthub.ona.domain.Talent;

public interface TalentService {
     Talent getTalentByFname(String lname);
     void deleteTalentById(Long id);
     Talent createTalent(Talent talent);
     Page<Talent> findTalentPage(Pageable pageable);
}

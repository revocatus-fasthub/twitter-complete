package tz.co.fasthub.ona.repository;

import tz.co.fasthub.ona.domain.Talent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TalentRepository extends JpaRepository<Talent, Integer> {

    Talent findByTwitterScreenName(String twitterScreenName);

    List<Talent> findAll();

}

package tz.co.fasthub.ona.repository;

import tz.co.fasthub.ona.domain.Talent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TalentRepository extends JpaRepository<Talent, Integer> {

    Talent findByTwitterScreenName(String twitterScreenName);
}

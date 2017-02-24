package tz.co.fasthub.ona.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tz.co.fasthub.ona.domain.Talent;

/**
 * Created by root on 2/10/17.
 */
public interface TalentRepository extends JpaRepository<Talent,Long> {

     Talent findByFname(String fname);
     Talent findByEmail(String email);
}

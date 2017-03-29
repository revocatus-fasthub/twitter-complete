package tz.co.fasthub.ona.domain;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Created by root on 3/3/17.
 */
public class TwitterTalentAccount {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "twitterAccount_id")
    private TwitterTalentAccount twitterTalentAccount;




}

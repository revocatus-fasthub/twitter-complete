package tz.co.fasthub.ona.service;

import tz.co.fasthub.ona.domain.Payload;

/**
 * Created by Naamini on 5/3/17.
 */
public interface PayloadService {

    Payload findOnePayload(Long id);

    void deletePayload(Long id);

}

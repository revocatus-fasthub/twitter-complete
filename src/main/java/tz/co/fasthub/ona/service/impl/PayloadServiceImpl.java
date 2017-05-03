package tz.co.fasthub.ona.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tz.co.fasthub.ona.domain.Payload;
import tz.co.fasthub.ona.repository.PayloadRepository;
import tz.co.fasthub.ona.service.PayloadService;

/**
 * Created by Naamini on 5/3/17.
 */
@Service
public class PayloadServiceImpl implements PayloadService {

    private final PayloadRepository payloadRepository;

    @Autowired
    public PayloadServiceImpl(PayloadRepository payloadRepository) {
        this.payloadRepository = payloadRepository;
    }

    @Override
    public Payload findOnePayload(Long id) {
       return payloadRepository.findOne(id);
    }

    @Override
    public void deletePayload(Long id){
      //  Payload byId = payloadRepository.findOnePayload(id);
        payloadRepository.delete(id);
    }

}

package tz.co.fasthub.ona.repository;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by Naamini on 4/25/17.
 */
public interface UsersConnectionRepository {

    List<String> findUserIdsWithConnection(Connection<?> connection);

    Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds);

    ConnectionRepository createConnectionRepository(String userId);

}

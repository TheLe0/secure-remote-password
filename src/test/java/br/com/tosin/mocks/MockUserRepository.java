package br.com.tosin.mocks;

import br.com.tosin.models.User;
import br.com.tosin.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;

import java.util.HashMap;
import java.util.Map;

@Alternative
@ApplicationScoped
public class MockUserRepository extends UserRepository {

    private final Map<String, User> mockedUsers = new HashMap<>();

    @Override
    public void save(String username, User user) {
        mockedUsers.put(username, user);
    }

    @Override
    public User getByUsername(String username) {
        return mockedUsers.get(username);
    }

    public void clear() {
        mockedUsers.clear();
    }
}

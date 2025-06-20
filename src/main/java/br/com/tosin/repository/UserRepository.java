package br.com.tosin.repository;

import br.com.tosin.models.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private final Map<String, User> users = new HashMap<>();

    public void save(String username, User user) {
        users.put(username, user);
    }

    public User getByUsername(String username) {
        return users.get(username);
    }
}

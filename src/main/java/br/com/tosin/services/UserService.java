package br.com.tosin.services;

import br.com.tosin.models.User;
import br.com.tosin.repository.UserRepository;
import br.com.tosin.utils.SRPUtil;

public class UserService {

    private final UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
    }

    public void register(String username, String password) {

        User user = SRPUtil.encryptUser(username, password);
        userRepository.save(username, user);
        System.out.println("[REGISTERED]");
    }

    public void authenticate(String username, String password) {
        var user = userRepository.getByUsername(username);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        Boolean isValidCredentials =  SRPUtil.validateCredentials(username, password, user);

        if (isValidCredentials) {
            System.out.println("[LOGIN SUCCESSFUL]");
        } else {
            System.out.println("[LOGIN FAILED]");
        }
    }
}

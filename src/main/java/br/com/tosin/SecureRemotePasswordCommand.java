package br.com.tosin;

import br.com.tosin.services.UserService;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "srp", mixinStandardHelpOptions = true)
public class SecureRemotePasswordCommand implements Runnable {

    @Inject
    UserService userService;

    @Parameters(index = "0", paramLabel = "<mode>", description = "Mode: register or login")
    String mode;

    @Parameters(index = "1", paramLabel = "<username>", description = "Username")
    String username;

    @Parameters(index = "2", paramLabel = "<password>", description = "Password")
    String password;

    @Override
    public void run() {
        switch (mode.toLowerCase()) {
            case "register" -> userService.register(username, password);
            case "login" -> userService.authenticate(username, password);
            default -> System.out.println("Invalid mode. Use 'register' or 'login'.");
        }
    }
}

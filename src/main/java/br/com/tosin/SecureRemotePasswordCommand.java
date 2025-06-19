package br.com.tosin;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "srp", mixinStandardHelpOptions = true)
public class SecureRemotePasswordCommand implements Runnable {

    @Parameters(paramLabel = "<username>", description = "Your username.")
    String username;

    @Parameters(paramLabel = "<password>", description = "Your password.")
    String password;

    @Override
    public void run() {
        System.out.printf("Username: %s, Password: %s\n", username, password);
    }

}

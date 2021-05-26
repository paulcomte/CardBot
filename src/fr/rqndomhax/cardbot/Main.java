package fr.rqndomhax.cardbot;

import fr.rqndomhax.cardbot.utils.Setup;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException, InterruptedException {
        new Setup().setup();
    }
}
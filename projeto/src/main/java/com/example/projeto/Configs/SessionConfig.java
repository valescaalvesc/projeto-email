package com.example.projeto.Configs;
import javax.mail.Session;
import java.util.Properties;

public class SessionConfig {
    public static Session session;

    public static Properties getProps(){
        return SessionConfig.session.getProperties();
    };
}

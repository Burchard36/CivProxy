package org.proxy;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.example.InstanceRunner;
import org.slf4j.LoggerFactory;

public class Main {
    public static void main(String[] args) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.getLogger("org.mongodb.driver").setLevel(Level.ERROR);
        new InstanceRunner(Main.class);
    }
}
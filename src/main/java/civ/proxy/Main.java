package civ.proxy;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import civ.proxy.cli.ServerHandler;
import org.example.InstanceRunner;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.getLogger("org.mongodb.driver").setLevel(Level.ERROR);
        new InstanceRunner(Main.class);

        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                if (!line.startsWith("civ")) {
                    throw new RuntimeException("CommandNotFound");
                }
                new CommandLine(new ServerHandler()).execute(removeTheElement(line.split(" "), 0));
            }
        }).start();
    }

    public static String[] removeTheElement(String[] arr, int index) {
        if (arr == null || index < 0
                || index >= arr.length) {

            return arr;
        }

        String[] anotherArray = new String[arr.length - 1];
        for (int i = 0, k = 0; i < arr.length; i++) {
            if (i == index) {
                continue;
            }
            anotherArray[k++] = arr[i];
        }
        return anotherArray;
    }
}
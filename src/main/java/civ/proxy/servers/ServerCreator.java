package civ.proxy.servers;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class ServerCreator {

    public static void createServer(String serverId) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048);
        KeyPair keyPair = keyPairGen.generateKeyPair();
    }

}

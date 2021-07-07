import Models.Server;
import Repository.Repository;

import java.security.SecureRandom;
import java.util.Base64;

public class ServerMain {
    public static void main(String[] args) {
        Repository.getInstance().initialize();
        Server.getInstance().run();
    }
}

import Models.Server;
import Repository.Repository;

import java.util.HashMap;

public class ServerMain {
    public static void main(String[] args) {
        Repository.getInstance().initialize();
        Server.getInstance().run();
    }
}

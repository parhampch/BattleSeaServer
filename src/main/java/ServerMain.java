import Models.Server;
import Repository.Repository;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class ServerMain {
    public static void main(String[] args) {
        HashMap<String, Integer> test = new HashMap<>();
        test.put("1", 1);
        test.put("2", 2);
        test.put("3", 3);
        test.put("4", 2);

        test.entrySet().removeIf(entry -> 2 == entry.getValue());
        System.out.println(test);
        //Repository.getInstance().initialize();
        //
        // Server.getInstance().run();
    }
}

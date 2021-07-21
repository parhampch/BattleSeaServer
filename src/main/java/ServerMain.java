import Models.Server;
import Repository.Repository;

import java.util.HashMap;

public class ServerMain {
    public static void main(String[] args) {
        HashMap<String, String> test1 = new HashMap<>();
        HashMap<String, String> test2 = new HashMap<>();
        test1.put("1", "2");
        test2.put("4", test1.get("1"));
        test1.remove("1");
        System.out.println(test2.get("4"));
        //Repository.getInstance().initialize();
        //Server.getInstance().run();
    }
}

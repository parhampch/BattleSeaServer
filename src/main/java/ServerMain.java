import Models.Server;
import Repository.Repository;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

public class ServerMain {
    public static void main(String[] args) {
        Repository.getInstance().initialize();
        for (int i = 0; i < 6; i++){
            System.out.println(new Gson().toJson(Repository.getInstance().maps[i]));
        }
        for (int i = 0; i < 6; i++){
            System.out.println(new Gson().toJson(Repository.getInstance().maps[i]));
        }
        for (int i = 0; i < 6; i++){
            System.out.println(new Gson().toJson(Repository.getInstance().maps[i]));
        }
        for (int i = 0; i < 6; i++){
            System.out.println(new Gson().toJson(Repository.getInstance().maps[i]));
        }
        for (int i = 0; i < 6; i++){
            System.out.println(new Gson().toJson(Repository.getInstance().maps[i]));
        }
        for (int i = 0; i < 6; i++){
            System.out.println(new Gson().toJson(Repository.getInstance().maps[i]));
        }
        for (int i = 0; i < 6; i++){
            System.out.println(new Gson().toJson(Repository.getInstance().maps[i]));
        }
        for (int i = 0; i < 6; i++){
            System.out.println(new Gson().toJson(Repository.getInstance().maps[i]));
        }
        //Server.getInstance().run();
    }
}

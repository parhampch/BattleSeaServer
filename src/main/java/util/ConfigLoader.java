package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    public static String readProperty(String property){
        Properties prop = new Properties();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/configurations/config.properties");
            prop.load(inputStream);
            return (prop.getProperty(property));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "property not found";
    }
}

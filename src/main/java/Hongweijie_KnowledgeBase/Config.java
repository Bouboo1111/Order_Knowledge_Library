package Hongweijie_KnowledgeBase;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
//配置文件
public class Config {
    private static final Properties properties = new Properties();
    static{

            InputStream in = Config.class.getClassLoader().getResourceAsStream("App.properties");
            if(in != null){
                try {
                    properties.load(in);
                } catch (IOException e) {
                    System.out.println("配置文件加载失败！");
                    setDefaultProperties();
                    e.printStackTrace();// 打印异常信息
                }
            }
            else {
                setDefaultProperties();
            }
    }

    public static void setDefaultProperties(){
        properties.setProperty("data.file.path","notes.json");
        properties.setProperty("app.initialize.time","1000");
        properties.setProperty("data.load.time","2000");
        properties.setProperty("data.save.time","2000");
    }

    public static String getProperty(String key){
        return properties.getProperty(key);
    }

    public static int getIntProperty(String key){
        if (properties.getProperty(key) == null){
            return 0;
        }
        return Integer.parseInt(properties.getProperty(key));
    }
    public static String getProperties(String key){
        try {
            return properties.getProperty(key);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
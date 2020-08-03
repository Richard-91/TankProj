package tank;

import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {
    //主分支不用单例模式
    static Properties props = new Properties();
    static {
        try {
            props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Object get(String key){
        if(props == null) return null;
        return props.get(key);
    }

}

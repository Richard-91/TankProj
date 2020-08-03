package tank;

import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {
    //主分支不用单例模式
//    static Properties props = new Properties();
//    static {
//        try {
//            props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    public static Object get(String key){
//        if(props == null) return null;
//        return props.get(key);
//    }


    //在design_pattern分支里修改
    private static volatile Properties props = null;//要加valatile，防止指令重排序
    private PropertyMgr(){

    }

    public static Properties getPropsInstance(){
        if(props == null){//避免每个线程一上来都拿锁，提高效率
            synchronized (PropertyMgr.class){
                if(props == null){//如果没有这个检查，在第一个检查之后，synchronized之前有两个线程进来，那么第一个线程拿到锁new一个对象之后，第二个线程也会拿锁new一个对象
                    props = new Properties();
                }
            }
        }
        return props;
    }

    public static String get(String key){
        if(props == null) props = getPropsInstance();
        return (String) props.get(key);
    }

}

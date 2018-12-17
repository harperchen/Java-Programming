package dClient.json;
import com.google.gson.Gson;

public class ObjectToJsonUtil {
    private static final Gson googleJson = new Gson();

    /**
     * 获取Gson常量，减少重复创建,在编译后就完成改变量初始化
     * @return
     */
    public static Gson getGoogleJson(){
        return googleJson;
    }

    public static String objectToJson(Object object){
        return googleJson.toJson(object);
    }
}

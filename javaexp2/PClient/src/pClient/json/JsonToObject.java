package pClient.json;

import com.google.gson.Gson;

public class JsonToObject {
    private static final Gson googleJson = new Gson();

    public static Gson getGoogleJson(){
        return googleJson;
    }

    public static <T> Object jsonToObject(String json , Class<T> class1){
        return googleJson.fromJson(json, class1);
    }

}
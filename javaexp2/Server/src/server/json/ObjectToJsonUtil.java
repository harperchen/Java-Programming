package server.json;
import com.google.gson.Gson;

public class ObjectToJsonUtil {
    private static final Gson googleJson = new Gson();

    public static Gson getGoogleJson() {
        return googleJson;
    }

    public static String objectToJson(Object object) {
        return googleJson.toJson(object);
    }
}

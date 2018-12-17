package server.user;
import java.security.*;
import java.util.HashMap;
import java.util.Map;

enum State{
    ON, OFF
}
enum Person {
    Patient, Doctor
}
class User {
    Person person;
    String bh;
    State state;

    public User(Person person, String bh, State state) {
        this.person = person;
        this.bh = bh;
        this.state = state;
    }

    public String getToken() {
        try {
            String input = person + bh;
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(input.getBytes());
            String Token = new String(messageDigest.digest());
            return Token;
        } catch (Exception e) {

        }
        return null;
    }
}


public class Token {
    private static Map<String, User> userMap = new HashMap<String, User>();
    public static void putData(String key, User user){
        userMap.put(key, user);
    }

    public static Boolean isValid(String token) {
        User user = userMap.get(token);
        if ((user != null) && (user.getToken().equals(token))) {
            return true;
        }
        return false;
    }
    public static User getUser(String token){
        return userMap.get(token);
    }

    public static Boolean hasUser(String token, String bh){
        if(userMap.containsKey(token)){
            User user = userMap.get(token);
            if(user.bh.equals(bh)){
                return true;
            }
            return false;
        }
        return false;
    }

    public static User hasUser(String bh, Person person){
        try {
            String input = person + bh;
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(input.getBytes());
            String token = new String(messageDigest.digest());
            if(userMap.containsKey(token)) {
                return userMap.get(token);
            }
        } catch (Exception e) {

        }
        return null;
    }



}

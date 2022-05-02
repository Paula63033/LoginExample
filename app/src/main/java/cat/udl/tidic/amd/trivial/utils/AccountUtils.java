package cat.udl.tidic.amd.trivial.utils;

import android.util.Base64;
import android.util.Patterns;

import java.nio.charset.StandardCharsets;

public class AccountUtils {

    // encodeBase64(email:password)
    public static String getAuthorizationToken(String email, String password){
        String authorizationToken =  email + ":" + password;
        byte[] data = authorizationToken.getBytes(StandardCharsets.UTF_8);
        authorizationToken = Base64.encodeToString(data, Base64.DEFAULT);
        authorizationToken = ("Authentication " + authorizationToken).trim();
        return authorizationToken;
    }

    // email validator
    public static String isEmailValid(String email){
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return "Invalid Email Address";
        }
        return null;
    }

    // password validator
    public static String isPasswordValid(String password) {
        if(password.length() < 8) {
            return "Minimum 8 Character Password";
        }
        if(!password.matches(".*[A-Z].*")) {
            return "Must Contain 1 Upper-case Character";
        }
        if(!password.matches(".*[a-z].*")) {
            return "Must Contain 1 Lower-case Character";
        }
        if(!password.matches(".*[@#$%^&+=].*")) {
            return "Must Contain 1 Special Character (@#$%^&+=)";
        }
        return null;
    }
}

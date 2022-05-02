package cat.udl.tidic.amd.trivial.models;

import android.util.Base64;
import android.util.Patterns;

import com.google.gson.annotations.SerializedName;

import java.nio.charset.StandardCharsets;

public class Account {

    @SerializedName("token")
    private String token;


    public Account(){
        this.token = "";
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @Override
    public String toString() {
        return "Account{" +
                "token='" + token + '\'' +
                '}';
    }
}

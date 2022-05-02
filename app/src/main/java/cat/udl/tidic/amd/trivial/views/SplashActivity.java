package cat.udl.tidic.amd.trivial.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import cat.udl.tidic.amd.trivial.usecases.home.HomeActivity;
import cat.udl.tidic.amd.trivial.usecases.login.LoginActivity;
import cat.udl.tidic.amd.trivial.usecases.login.LoginRouter;
import cat.udl.tidic.amd.trivial.utils.PreferencesProvider;

public class SplashActivity extends AppCompatActivity {

    private final static String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Do stuff onBoarding
        setup();
        data();
    }

    private void setup(){
        PreferencesProvider.init(this);
    }

    private void data(){
        String token = PreferencesProvider.providePreferences().getString("token", "");
        Log.d(TAG, "token: " + token);
        if (token.equals("")) {
            // If device has no token -> go to LoginActivity()
            //startActivity(new Intent(this, LoginActivity.class));
            showLogin();
        } else {
            // If a userToken is stored on sharedPreferences go to MainActivity().
            startActivity(new Intent(this, HomeActivity.class));
        }
        // Close the activity, the user don't need to enter again with back functionality
        finish();
    }

    private void showLogin(){
        LoginRouter loginRouter = new LoginRouter();
        loginRouter.launch(this);
    }

}
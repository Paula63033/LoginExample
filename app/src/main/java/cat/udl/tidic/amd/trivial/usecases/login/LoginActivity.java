package cat.udl.tidic.amd.trivial.usecases.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import cat.udl.tidic.amd.trivial.R;
import cat.udl.tidic.amd.trivial.databinding.ActivityLoginBinding;
import cat.udl.tidic.amd.trivial.models.Result;
import cat.udl.tidic.amd.trivial.usecases.home.HomeActivity;
import cat.udl.tidic.amd.trivial.utils.PreferencesProvider;
import cat.udl.tidic.amd.trivial.utils.UIUtils;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new LoginViewModel();
        initDataBinding();

        loginViewModel.isUserLogged().observe(this, new Observer<Result<String>>() {
            @Override
            public void onChanged(Result<String> tokenResult) {
                loginViewModel.isLogged.postValue(false);
                if (tokenResult.getResult() != null){
                    Log.d(TAG,"Login successful, token obtained.");
                    PreferencesProvider.providePreferences().edit().putString("token",
                            tokenResult.getResult()).commit();
                    Log.d(TAG,"Login successful, add token to SharedPreferences.");
                    goTo(HomeActivity.class);
                }
                else{
                    //Display Error
                    Log.d(TAG,"User not logged, token not obtained.");
                    showLoginError(tokenResult.getError().getMessage());
                }
            }
        });
    }

    private void showLoginError(String errorMessage){
        DialogInterface.OnClickListener positiveAction = (dialogInterface, i) -> dialogInterface.cancel();
        UIUtils.showAlert(this,"Error", errorMessage, "Ok",positiveAction ,null,null, false);
    }


    private void initDataBinding() {
        activityLoginBinding =
                DataBindingUtil.setContentView(this,R.layout.activity_login);
        activityLoginBinding.setLoginViewModel(loginViewModel);
        activityLoginBinding.setLifecycleOwner(this);
    }

    public void goTo(Class _class){
        Intent intent = new Intent(this, _class);
        startActivity(intent);
    }




}
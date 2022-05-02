package cat.udl.tidic.amd.trivial.usecases.home;

import android.content.Context;
import android.content.Intent;

import cat.udl.tidic.amd.trivial.usecases.base.BaseRouter;
import cat.udl.tidic.amd.trivial.usecases.login.LoginActivity;

public class HomeRouter implements BaseRouter {
    @Override
    public Intent intent(Context activity) {
        return new Intent(activity, HomeActivity.class);
    }
}


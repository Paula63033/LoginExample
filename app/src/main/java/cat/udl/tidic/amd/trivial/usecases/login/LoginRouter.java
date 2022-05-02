package cat.udl.tidic.amd.trivial.usecases.login;

import android.content.Context;
import android.content.Intent;

import cat.udl.tidic.amd.trivial.usecases.base.BaseRouter;

public class LoginRouter implements BaseRouter {
    @Override
    public Intent intent(Context activity) {
        return new Intent(activity, LoginActivity.class);
    }
}

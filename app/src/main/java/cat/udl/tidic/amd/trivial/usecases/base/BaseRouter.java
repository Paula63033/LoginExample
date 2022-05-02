package cat.udl.tidic.amd.trivial.usecases.base;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public interface BaseRouter {

    public Intent intent(Context activity);

    public default void launch(Context activity){
        activity.startActivity(intent(activity));
    }

}

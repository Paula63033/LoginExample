package cat.udl.tidic.amd.trivial.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import cat.udl.tidic.amd.trivial.R;


public class UIUtils {


    public static void  showAlert(Context context, String title, String message,
                                  String positive,
                                  DialogInterface.OnClickListener positiveAction,
                                  String negative,
                                  DialogInterface.OnClickListener negativeAction,
                                  Boolean cancelable){

        androidx.appcompat.app.AlertDialog.Builder builder =
                new androidx.appcompat.app.AlertDialog.Builder(context, R.style.CustomDialogTheme);

        builder.setTitle(title);
        builder.setIcon(R.drawable.ic_baseline_warning_24);
        builder.setCancelable(cancelable);
        builder.setMessage(message);
        if (positiveAction != null){
            builder.setPositiveButton(positive, positiveAction);
        }
        if (negativeAction != null) {
            builder.setNegativeButton(negative, negativeAction);
        }

        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();

    }
}

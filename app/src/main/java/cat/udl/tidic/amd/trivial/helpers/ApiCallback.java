package cat.udl.tidic.amd.trivial.helpers;

import android.util.Log;

import com.google.gson.Gson;

import cat.udl.tidic.amd.trivial.models.ApiError;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ApiCallback<T> implements Callback {



    public void onResponse(Call call, Response response) {

        if (response.isSuccessful()){
            onResponseSuccess(call,response);
        }else{
            Log.d("ApiCallback", "onResponseError -> " + response.errorBody());
            Gson gson = new Gson();
            ApiError error = gson.fromJson(response.errorBody().charStream(), ApiError.class);
            onResponseError(call, new Throwable(error.description));
        }


    }
    
    /**
     * Invoked for a received HTTP response with a success code (200-299).
     */
    abstract public void onResponseSuccess(Call<T> call, Response<T> response);

    /**
     * Invoked for a received HTTP response with a failure code (400+).
     */
    abstract public void onResponseError(Call<T> call, Throwable t);
}

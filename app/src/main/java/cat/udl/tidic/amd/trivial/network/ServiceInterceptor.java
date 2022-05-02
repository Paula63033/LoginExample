package cat.udl.tidic.amd.trivial.network;

import androidx.annotation.NonNull;

import java.io.IOException;

import cat.udl.tidic.amd.trivial.utils.PreferencesProvider;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ServiceInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        String token = PreferencesProvider.providePreferences().getString("token","");

        if(request.header("No-Authentication") == null){
            request = request.newBuilder()
                    .addHeader("Authorization", token)
                    .build();
        }
        return chain.proceed(request);
    }
}

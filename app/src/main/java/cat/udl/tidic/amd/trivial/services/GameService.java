package cat.udl.tidic.amd.trivial.services;

import com.google.gson.JsonObject;

import java.util.List;

import cat.udl.tidic.amd.trivial.models.Game;
import cat.udl.tidic.amd.trivial.models.PlayerState;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GameService {

    @POST("/trivial/game/create")
    Call<Game> createGame(@Body JsonObject params);

    @POST("/trivial/game/join/{code}")
    Call<Game> joinGame(@Path("code") String gameCode);

    @POST("/trivial/join/game")
    Call<Game> joinRandomGame();

    @GET("/trivial/game/show/{code}")
    Call<Game> showGame(@Path("code") String gameCode);

    @PUT("/trivial/game/update/status/{code}")
    Call<Void> updateGame(@Path("code") String gameCode,
                          @Body PlayerState playerState);

    @GET("/trivial/game/list")
    Call<List<Game>> getGames();

}

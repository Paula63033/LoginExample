package cat.udl.tidic.amd.trivial.services;

import java.util.List;

import cat.udl.tidic.amd.trivial.models.Player;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface PlayerService {

    @GET("/trivial/player/show")
    Call<Player> getPlayer();

    @GET("/trivial/players/show/{username}")
    Call<Player> getPublicProfile(@Path("username") String username);

    @GET("/trivial/player/list")
    Call<List<Player>> getPlayers();

}

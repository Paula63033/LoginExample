package cat.udl.tidic.amd.trivial.services;

import com.google.gson.JsonObject;

import java.util.List;

import cat.udl.tidic.amd.trivial.models.Game;
import cat.udl.tidic.amd.trivial.models.PlayerState;
import cat.udl.tidic.amd.trivial.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Retrofit;

public class GameServiceImpl implements GameService{

    private  Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

    @Override
    public Call<Game> createGame(JsonObject params) {
        return retrofit.create(GameService.class).createGame(params);
    }

    @Override
    public Call<Game> joinGame(String gameCode) {
        return retrofit.create(GameService.class).joinGame(gameCode);
    }

    @Override
    public Call<Game> joinRandomGame() {
        return retrofit.create(GameService.class).joinRandomGame();
    }

    @Override
    public Call<Game> showGame(String gameCode) {
        return retrofit.create(GameService.class).showGame(gameCode);
    }

    @Override
    public Call<Void> updateGame(String gameCode, PlayerState playerState) {
        return retrofit.create(GameService.class).updateGame(gameCode,playerState);
    }

    @Override
    public Call<List<Game>> getGames() {
        return retrofit.create(GameService.class).getGames();
    }
}

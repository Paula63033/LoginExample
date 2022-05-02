package cat.udl.tidic.amd.trivial.services;

import java.util.List;

import cat.udl.tidic.amd.trivial.models.Player;
import cat.udl.tidic.amd.trivial.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Retrofit;

public class PlayerServiceImpl implements PlayerService {

    private  Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

    @Override
    public Call<Player> getPlayer() {
        return retrofit.create(PlayerService.class).getPlayer();
    }

    @Override
    public Call<Player> getPublicProfile(String username) {
        return retrofit.create(PlayerService.class).getPublicProfile(username);
    }

    @Override
    public Call<List<Player>> getPlayers() {
        return retrofit.create(PlayerService.class).getPlayers();
    }
}

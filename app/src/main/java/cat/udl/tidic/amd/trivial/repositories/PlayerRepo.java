package cat.udl.tidic.amd.trivial.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cat.udl.tidic.amd.trivial.services.PlayerService;
import cat.udl.tidic.amd.trivial.services.PlayerServiceImpl;
import cat.udl.tidic.amd.trivial.helpers.ApiCallback;
import cat.udl.tidic.amd.trivial.models.Player;
import cat.udl.tidic.amd.trivial.models.Result;
import retrofit2.Call;
import retrofit2.Response;

public class PlayerRepo {

    private static final String TAG = "PlayerRepo";

    private final PlayerService playerService;
    private Result<Player> playerResult;
    private MutableLiveData<Result<Player>> playerResultLiveData;

    private Result<List<Player>> playersResult;
    private MutableLiveData<Result<List<Player>>> playersResultLiveData;

    public PlayerRepo() {
        this.playerService = new PlayerServiceImpl();
        this.playerResultLiveData = new MutableLiveData<>();
        this.playersResultLiveData = new MutableLiveData<>();
    }


    public void getPlayers(){

        playerService.getPlayers().enqueue(new ApiCallback<List<Player>>() {
            @Override
            public void onFailure(Call call, Throwable t) {
                playersResult = Result.error(t);
                playersResultLiveData.setValue(playersResult);
            }

            @Override
            public void onResponseSuccess(Call<List<Player>> call, Response<List<Player>> response) {
                playersResult = Result.success(response.body());
                playersResultLiveData.setValue(playersResult);
            }

            @Override
            public void onResponseError(Call<List<Player>> call, Throwable t) {
                playersResult = Result.error(t);
                playersResultLiveData.setValue(playersResult);
            }
        });

    }

    public LiveData<Result<List<Player>>>getLoadedPlayers(){
        return this.playersResultLiveData;
    }


    public void getPlayer() {

        // Uncomment to simulate overload and see the loading
        //new Handler().postDelayed(new Runnable() {
        // public void run() {
        playerService.getPlayer().enqueue(new ApiCallback<Player>() {

            @Override
            public void onFailure(Call call, Throwable t) {
                playerResult = Result.error(t);
                playerResultLiveData.setValue(playerResult);
            }

            @Override
            public void onResponseSuccess(Call<Player> call, Response<Player> response) {
                playerResult = Result.success(response.body());
                playerResultLiveData.setValue(playerResult);
            }

            @Override
            public void onResponseError(Call<Player> call, Throwable t) {
                playerResult = Result.error(t);
                playerResultLiveData.setValue(playerResult);
            }
        });
        // }
        // }, 5000); // 5 seconds
    }

    public LiveData<Result<Player>> getLoadedPlayer(){
        return this.playerResultLiveData;
    }

    public MutableLiveData<Result<Player>> getPublicProfile(String username){

        MutableLiveData<Result<Player>> publicProfile = new MutableLiveData<>();

        playerService.getPublicProfile(username).enqueue(new ApiCallback<Player>() {
            @Override
            public void onFailure(Call call, Throwable t) {

                playerResult = Result.error(t);
                publicProfile.setValue(playerResult);

            }

            @Override
            public void onResponseSuccess(Call<Player> call, Response<Player> response) {
                playerResult = Result.success(response.body());
                publicProfile.setValue(playerResult);
            }

            @Override
            public void onResponseError(Call<Player> call, Throwable t) {
                playerResult = Result.error(t);
                publicProfile.setValue(playerResult);
            }
        });
        return publicProfile;
    }


}
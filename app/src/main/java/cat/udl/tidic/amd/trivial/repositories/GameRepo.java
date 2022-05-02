package cat.udl.tidic.amd.trivial.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import java.util.List;

import cat.udl.tidic.amd.trivial.services.GameService;
import cat.udl.tidic.amd.trivial.services.GameServiceImpl;
import cat.udl.tidic.amd.trivial.helpers.SingleLiveEvent;
import cat.udl.tidic.amd.trivial.helpers.ApiCallback;
import cat.udl.tidic.amd.trivial.models.Game;
import cat.udl.tidic.amd.trivial.models.PlayerState;
import cat.udl.tidic.amd.trivial.models.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameRepo {

    private static String TAG = "GameRepo";
    private static GameRepo gameRepo = null;

    private GameService gameService;

    public MutableLiveData<Game> mGame;
    public MutableLiveData<List<Game>> mGames;
    public MutableLiveData<String> mError;
    public MutableLiveData<Boolean> mMatchUpdated;
    public MutableLiveData<Boolean> mProgressVisible;

    private Result<Game> gameResult;
    Result<List<Game>> gamesResult;
    private final SingleLiveEvent<Result<Game>> gameCreationMutableLiveData;
    private SingleLiveEvent<Result<Game>> joinGameResponse;
    private final MutableLiveData<Result<List<Game>>> gamesMutableLiveData;
    //private MutableLiveData<Result<Game>> gameShowMutableLiveData;

    public GameRepo() {
        this.gameService = new GameServiceImpl();
        this.gameCreationMutableLiveData = new SingleLiveEvent<>();
        this.gamesMutableLiveData = new MutableLiveData<>();
        this.joinGameResponse = new SingleLiveEvent<>();//this.gameShowMutableLiveData = new MutableLiveData<>();

        //Olds
        this.mGame = new MutableLiveData<>();
        this.mGames = new MutableLiveData<>();
        this.mError = new MutableLiveData<>();
        this.mProgressVisible = new MutableLiveData<>();
        this.mMatchUpdated = new MutableLiveData<>();
    }


    // Singleton the repos must shared their LiveData along the app live
    public static GameRepo getInstance() {
        if(gameRepo == null) {
            gameRepo = new GameRepo();
        }
        return gameRepo;
    }

    public void createGame(){
        JsonObject params = new JsonObject();
        gameService.createGame(params).enqueue(new ApiCallback<Game>() {
            @Override
            public void onFailure(Call call, Throwable t) {
                gameResult = Result.error(t);
                gameCreationMutableLiveData.setValue(gameResult);
            }

            @Override
            public void onResponseSuccess(Call<Game> call, Response<Game> response) {
                gameResult = Result.success(response.body());
                gameCreationMutableLiveData.setValue(gameResult);
            }

            @Override
            public void onResponseError(Call<Game> call, Throwable t) {
                gameResult = Result.error(t);
                gameCreationMutableLiveData.setValue(gameResult);
            }
        });
    }

    public LiveData<Result<Game>> getGameCreated(){
        return gameCreationMutableLiveData;
    }


    public void joinGame(String gameCode){

        gameService.joinGame(gameCode).enqueue(new ApiCallback<Game>() {
            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("GameRepo", "joinGame -> onFailure -> " + t.getMessage());
                gameResult = Result.error(t);
                joinGameResponse.setValue(gameResult);
            }

            @Override
            public void onResponseSuccess(Call<Game> call, Response<Game> response) {
                gameResult = Result.success(response.body());
                joinGameResponse.setValue(gameResult);
            }

            @Override
            public void onResponseError(Call<Game> call, Throwable t) {
                Log.d("GameRepo", "joinGame -> onResponseError -> " +gameCode+" -- "+ t.getMessage());
                gameResult = Result.error(t);
                joinGameResponse.setValue(gameResult);
            }
        });
    }

    public void joinGame(){

        gameService.joinRandomGame().enqueue(new ApiCallback<Game>() {
            @Override
            public void onFailure(Call call, Throwable t) {
                gameResult = Result.error(t);
                joinGameResponse.setValue(gameResult);
            }

            @Override
            public void onResponseSuccess(Call<Game> call, Response<Game> response) {
                gameResult = Result.success(response.body());
                joinGameResponse.setValue(gameResult);
            }

            @Override
            public void onResponseError(Call<Game> call, Throwable t) {
                gameResult = Result.error(t);
                joinGameResponse.setValue(gameResult);
            }
        });
    }

    public LiveData<Result<Game>> isPlayerJoined(){
        return joinGameResponse;
    }


    public void getGames(){

        this.gameService.getGames().enqueue(new ApiCallback<List<Game>>() {
            @Override
            public void onFailure(Call call, Throwable t) {
                gamesResult = Result.error(t);
                gamesMutableLiveData.setValue(gamesResult);
            }

            @Override
            public void onResponseSuccess(Call<List<Game>> call, Response<List<Game>> response) {
                gamesResult = Result.success(response.body());
                //@TODO: If games == gameReceived not set -> we don't need to update view
                if (gamesMutableLiveData.getValue() != null) {
                    Log.d("GameRepo","->" + gamesResult.getResult().size());
                    if (!gamesMutableLiveData.getValue().getResult().equals(gamesResult.getResult())) {
                        gamesMutableLiveData.setValue(gamesResult);
                    }
                }else{
                    gamesMutableLiveData.setValue(gamesResult);
                }
            }

            @Override
            public void onResponseError(Call<List<Game>> call, Throwable t) {
                gamesResult = Result.error(t);
                gamesMutableLiveData.setValue(gamesResult);
            }
        });

    }

    public LiveData<Result<List<Game>>> getGameList(){
        return this.gamesMutableLiveData;
    }



    public void updateGame(String gameCode, PlayerState playerState) {

        gameService.updateGame(gameCode,playerState).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                int code = response.code();
                Log.d(TAG, ""+code);
                mMatchUpdated.setValue(true);
                if (code == 200) {
                    Log.d("GameRepo","Updated ok!");
                } else {
                    Log.d(TAG, code + ":" + response.errorBody().toString());
                    mGame.setValue(null);
                    mError.setValue(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, t.getMessage().toString());
                mGame.setValue(null);
                mError.setValue(t.getMessage().toString());
            }
        });
    }

    public void showGame(String gameCode) {
        mProgressVisible.setValue(true);
        gameService.showGame(gameCode).enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                mProgressVisible.setValue(false);
                int code = response.code();
                Log.d(TAG, ""+code);
                if (code == 200) {
                    Game g = response.body();
                    assert g != null;
                    mGame.setValue(g);
                    Log.d(TAG, g.toString());

                } else {
                    Log.d(TAG, code + ":" + response.errorBody().toString());
                    mGame.setValue(null);
                    mError.setValue(response.errorBody().toString());

                }
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                Log.d(TAG, t.getMessage().toString());
                mGame.setValue(null);
                mError.setValue(t.getMessage().toString());

            }
        });
    }


    //@TODO: Destroy
    public void createGameOld() {
        JsonObject params = new JsonObject();
        gameService.createGame(params).enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {

                int code = response.code();
                Log.d(TAG, ""+code);
                if (code == 200) {
                    Game g = response.body();
                    assert g != null;
                    mGame.setValue(g);
                    Log.d(TAG, g.toString());

                } else {
                    Log.d(TAG, code + ":" + response.errorBody().toString());
                    mGame.setValue(null);
                    mError.setValue(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                Log.d(TAG, t.getMessage().toString());
                mGame.setValue(null);
                mError.setValue(t.getMessage().toString());
            }
        });
    }
    public void joinGameOld(String gameCode) {

        gameService.joinGame(gameCode).enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {

                int code = response.code();
                Log.d(TAG, ""+code);
                if (code == 200) {
                    Game g = response.body();
                    assert g != null;
                    mGame.setValue(g);
                    Log.d(TAG, g.toString());

                } else {
                    Log.d(TAG, code + ":" + response.errorBody().toString());
                    mError.setValue(response.errorBody().toString());
                    mGame.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                Log.d(TAG, t.getMessage().toString());
                mError.setValue(t.getMessage().toString());
                mGame.setValue(null);
            }
        });
    }
    public void getGamesOld() {

        gameService.getGames().enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {

                int code = response.code();
                Log.d(TAG, ""+code);
                if (code == 200) {
                    List<Game> games = response.body();
                    mGames.setValue(games);
                } else {
                    Log.d(TAG, code + ":" + response.errorBody().toString());
                    mError.setValue(response.errorBody().toString());
                    mGame.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                Log.d(TAG, t.getMessage().toString());
                mError.setValue(t.getMessage().toString());
                mGame.setValue(null);
            }
        });
    }
}



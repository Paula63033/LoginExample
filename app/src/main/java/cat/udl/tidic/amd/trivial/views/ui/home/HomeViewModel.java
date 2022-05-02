package cat.udl.tidic.amd.trivial.views.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import cat.udl.tidic.amd.trivial.models.Answer;
import cat.udl.tidic.amd.trivial.models.Game;

import cat.udl.tidic.amd.trivial.models.Player;
import cat.udl.tidic.amd.trivial.models.PlayerState;
import cat.udl.tidic.amd.trivial.models.Result;
import cat.udl.tidic.amd.trivial.repositories.GameRepo;
import cat.udl.tidic.amd.trivial.repositories.PlayerRepo;


public class HomeViewModel extends ViewModel {

    private GameRepo gameRepo;

    public HomeViewModel() {
        this.gameRepo = new GameRepo();
    }

    /**
     * Create game action. It calls the async operation in the repository.
     */
    public void createGame(){
        this.gameRepo.createGame();
    }

    /**
     * Observe game creation action.
     * @return Result<Game> with either the game instance or the error.
     */
    public LiveData<Result<Game>> isGameCreated(){
        return this.gameRepo.getGameCreated();
    }


    public void getGames(){
        this.gameRepo.getGames();
    }

    public LiveData<Result<List<Game>>> areGamesLoaded(){
        return this.gameRepo.getGameList();
    }

    /*public LiveData<List<Game>> areGamesLoaded(){
        return this.gameRepo.mGames;
    }*/
    /*public LiveData<Game> isGameCreated(){
        return this.gameRepo.mGame;
    }*/



    public void updateGame(Answer answer, PlayerState playerState){
        playerState.setId_answer(answer.getId());
        this.gameRepo.updateGame(playerState.getCode(),playerState);
    }

    public LiveData<Boolean> isMatchUpdated(){
        return this.gameRepo.mMatchUpdated;
    }


}
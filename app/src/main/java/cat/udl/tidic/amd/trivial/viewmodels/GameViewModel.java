package cat.udl.tidic.amd.trivial.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import cat.udl.tidic.amd.trivial.models.Game;
import cat.udl.tidic.amd.trivial.models.Result;
import cat.udl.tidic.amd.trivial.repositories.GameRepo;

public class GameViewModel extends ViewModel {

    private GameRepo gameRepo;

    public GameViewModel(){
        //Maybe it is interesting to be singleton
        gameRepo = new GameRepo();
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

}

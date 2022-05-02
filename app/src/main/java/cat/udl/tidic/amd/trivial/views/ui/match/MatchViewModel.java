package cat.udl.tidic.amd.trivial.views.ui.match;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import cat.udl.tidic.amd.trivial.models.Game;
import cat.udl.tidic.amd.trivial.repositories.GameRepo;

public class MatchViewModel extends ViewModel {

    private GameRepo gameRepo;

    public MatchViewModel(){
        gameRepo = new GameRepo();
    }

    public void getGame(String code){
        gameRepo.showGame(code);
    }

    public LiveData<Game> isGameLoaded(){
        return gameRepo.mGame;
    }

    public LiveData<Boolean> isProgressVisible(){
        return this.gameRepo.mProgressVisible;
    }

}

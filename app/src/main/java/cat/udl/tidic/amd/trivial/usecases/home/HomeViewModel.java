package cat.udl.tidic.amd.trivial.usecases.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import cat.udl.tidic.amd.trivial.models.Player;
import cat.udl.tidic.amd.trivial.models.Result;
import cat.udl.tidic.amd.trivial.repositories.AccountRepo;
import cat.udl.tidic.amd.trivial.repositories.PlayerRepo;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<Player> currentPlayer;

    // Show and Hide progress bar
    public MutableLiveData<Boolean> isLoaded;

    // Control data and domain
    private PlayerRepo playerRepo;

    public HomeViewModel(){
        this.currentPlayer = new MutableLiveData<>();
        this.isLoaded = new MutableLiveData<>();
        this.playerRepo = new PlayerRepo();
    }

    public MutableLiveData<Player> getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(MutableLiveData<Player> currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void loadPlayer(){
        this.isLoaded.postValue(true);
        this.playerRepo.getPlayer();
    }

    public LiveData<Result<Player>> isPlayerLoaded(){
        return this.playerRepo.getLoadedPlayer();
    }
}

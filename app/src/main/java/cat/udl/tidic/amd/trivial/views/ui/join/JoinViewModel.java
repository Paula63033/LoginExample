package cat.udl.tidic.amd.trivial.views.ui.join;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import cat.udl.tidic.amd.trivial.helpers.SingleLiveEvent;
import cat.udl.tidic.amd.trivial.models.Game;
import cat.udl.tidic.amd.trivial.models.Result;
import cat.udl.tidic.amd.trivial.repositories.GameRepo;

public class JoinViewModel extends ViewModel {

    private static final String TAG = "JoinViewModel";

    /**
     *
     * 2
     *
     * It's because of how Fragment Lifecycle works. When you move to and fro from a fragment onViewCreated() is called again. In onViewCreated you're calling viewModel.getProfileLive() which returns the livedata upto from the repository and observe to it.
     *
     * Since onViewCreated() gets called everytime when you move back to the Fragment so is your call to viewModel.getProfileLive() and in turn the repository gets called again which again triggers the observe method in your Fragment.
     *
     * In order to solve this problem, create a LiveData variable in your ViewModel, set it to the returned Live Data from Repository. In the Fragment observe to the LiveData variable of your ViewModel not the one returned from Repository. That way, your observe method will get triggered on very first time and only when value of your data from repository changes.Ω**/

    public MutableLiveData<String> gameCode;
    private SingleLiveEvent<Result<Game>> isPlayerJoined;
    private GameRepo gameRepo;

    // Show and Hide progress bar
    public MutableLiveData<Boolean> isJoined;


    public JoinViewModel() {
        Log.d(TAG, " [constructor]");
        gameRepo = new GameRepo();
        gameCode = new MutableLiveData<>();
        isJoined = new MutableLiveData<>();
    }

    /*
     * Call the service to send a join petition to the server.
     * Async call -> We activate the progress bar
     */
    public void joinToInvitedGame(){
        String currentGameCode = gameCode.getValue();
        Log.d(TAG, " joinToInvitedGame() --> " + currentGameCode);
        if (currentGameCode != null) {
            isJoined.postValue(true);
            this.gameRepo.joinGame(currentGameCode);
        }
    }

    /*
     * Call the service to get the async result of the join
     * Single shot call (joined or error)
     * If result is not null we get the response -> hide progress bar
     */
   public LiveData<Result<Game>>  isPlayerJoined(){
        if (this.gameRepo.isPlayerJoined() != null){
            isJoined.postValue(false);
        }
        return this.gameRepo.isPlayerJoined();
    }

    public void joinToRandomGame(){
        isJoined.postValue(true);
        this.gameRepo.joinGame();
    }

}
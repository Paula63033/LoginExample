package cat.udl.tidic.amd.trivial.usecases.ranking;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import cat.udl.tidic.amd.trivial.models.Player;
import cat.udl.tidic.amd.trivial.models.Result;
import cat.udl.tidic.amd.trivial.repositories.PlayerRepo;

public class RankingViewModel extends ViewModel {

    private PlayerRepo playerRepo;
    private MutableLiveData<Boolean> loading;

    public RankingViewModel() {
        playerRepo = new PlayerRepo();
        this.loading = new MutableLiveData<>();
    }

    public void loadPlayers(){
        this.loading.setValue(true);
        this.playerRepo.getPlayers();
    }

    public LiveData<Result<List<Player>>> arePlayersLoaded(){
        return this.playerRepo.getLoadedPlayers();
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public void setLoading(Boolean loading) {
        this.loading.setValue(loading);
    }
}
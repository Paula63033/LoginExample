package cat.udl.tidic.amd.trivial.usecases.ranking;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import cat.udl.tidic.amd.trivial.databinding.FragmentRankingBinding;
import cat.udl.tidic.amd.trivial.models.Player;
import cat.udl.tidic.amd.trivial.models.Result;
import cat.udl.tidic.amd.trivial.usecases.ranking.pages.PlayerAdapter;


public class RankingFragment extends Fragment {

    private final static String TAG = "RankingFragment";

    private FragmentRankingBinding binding;
    private RankingViewModel viewModel;
    private PlayerAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewModel =
                new ViewModelProvider(this).get(RankingViewModel.class);

        binding = FragmentRankingBinding.inflate(inflater, container, false);

        setup();
        data();

        return binding.getRoot();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void data(){
        // Laod data from the backend (async operation)
        loadPlayers();
        // Observe change in data obtained from the backend (async operation)
        this.viewModel.arePlayersLoaded().observe(getViewLifecycleOwner(), players -> {
            if (players != null){
                showPlayers(players.getResult());
            }
        });
        // Add the option to refresh information with a Swipe action (move the finger down)
        addSwipeRefresh();
    }

    private void setup(){
        // Recycler View
        Log.d(TAG, "... setup() -> recyclerView");
        RecyclerView playersRecyclerView = binding.playersrecyclerView;
        playersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.adapter = new PlayerAdapter();
        playersRecyclerView.setAdapter(this.adapter);
    }



    private void showPlayers(List<Player> playerList){
        // Reload players to the adapter
        this.adapter.setPlayers(playerList);
        // Notify manual the update
        this.adapter.notifyDataSetChanged();
        // Make progress bar invisible
        viewModel.setLoading(false);
    }

    public void loadPlayers(){
        // Make progress bar visible
        viewModel.setLoading(true);
        // Load player from the backend
        this.viewModel.loadPlayers();
    }

    private void addSwipeRefresh(){
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefreshLayout.setRefreshing(false);
                // Reload players
                loadPlayers();
            }
        });
    }


}
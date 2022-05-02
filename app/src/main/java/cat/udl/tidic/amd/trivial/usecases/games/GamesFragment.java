package cat.udl.tidic.amd.trivial.usecases.games;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import cat.udl.tidic.amd.trivial.R;
import cat.udl.tidic.amd.trivial.adapters.GameAdapter;
import cat.udl.tidic.amd.trivial.databinding.FragmentGamesBinding;
import cat.udl.tidic.amd.trivial.models.Game;
import cat.udl.tidic.amd.trivial.models.Player;
import cat.udl.tidic.amd.trivial.models.PlayerStatus;
import cat.udl.tidic.amd.trivial.views.ui.join.JoinViewModel;
import cat.udl.tidic.amd.trivial.usecases.home.HomeActivity;

public class GamesFragment extends Fragment {

    private static final String TAG = "GamesFragment";

    private FragmentGamesBinding binding;
    private View root;
    private JoinViewModel joinViewModel;
    private GameAdapter adapter;

    private GamesViewModel gamesViewModel;
    private RecyclerView gamesRecylcerView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGamesBinding.inflate(inflater, container, false);

        gamesViewModel = new ViewModelProvider(requireActivity()).get(GamesViewModel.class);
        binding.setViewModel(gamesViewModel);

        joinViewModel = new ViewModelProvider(requireActivity()).get(JoinViewModel.class);

        gamesRecylcerView = binding.recyclerView;
        gamesRecylcerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setGameListAdapter(new ArrayList<Game>());
        root = binding.getRoot();

        //Listeners
        addGameCreationObserver();
        addGamesObserver();
        addSwipeRefresh();
        addGameJoinListener();

        //Get data
        obtainGameList();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        //games = null;
        Log.d(this.getClass().getSimpleName(),"onDestroy()");
    }


    private void addSwipeRefresh(){
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefreshLayout.setRefreshing(false);
                obtainGameList();
            }
        });
    }


    private void addGamesObserver(){
        gamesViewModel.areGamesLoaded().observe(getViewLifecycleOwner(), gameList -> {
            if (gameList.getResult() != null){
                Log.d("HomeFragment","GameList obtained!");
                setGameListAdapter(gameList.getResult());
               // adapter.setGames(gameList.getResult());
            }else {
                //Game not created due to error
                Log.d("HomeFragment","GameList not obtained due to error!");
            }
        });
    }

    public void setGameListAdapter(List<Game> gamesList){
        //@TODO: Adapter must be init in the constructor and set player and list then
        //Log.d("HomeFragment", mainActivityViewModel.getPlayer().toString());
        Player currentPlayer = ((HomeActivity) requireActivity()).getCurrentPlayer();

        adapter = new GameAdapter(gamesList, ((HomeActivity) requireActivity()).getCurrentPlayer());
        gamesRecylcerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new GameAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int id, Game game) {
                Log.d(TAG,"onItemClick() -> "+game.toString());
                Log.d(TAG,"onItemClick() -> "+currentPlayer.toString());
                if (game.getCurrentState(currentPlayer).getStatus() == PlayerStatus.P){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("playerState", game.getCurrentState(currentPlayer));
                    Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_navigation_question,bundle);
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putString("gameCode", game.getCode());
                    Navigation.findNavController(root).navigate(R.id.action_navigation_games_to_matchFragment,bundle);
                }
            }
        });
    }

    private void addGameCreationObserver(){
        gamesViewModel.isGameCreated().observe(getViewLifecycleOwner(), gameResult -> {
            // Game created without errors.
            if (gameResult.getResult() != null){
                Log.d("HomeFragment","Game created!");
                joinToExistingGame(gameResult.getResult().getCode());
            }else {
                //Game not created due to error
                Log.d("HomeFragment","Game not created due to error!");
            }
        });
    }


    // A player that creates a game needs also to join it.
    private void joinToExistingGame(String gameCode){
        Log.d(TAG, "joinToExistingGame() --> " + gameCode);
        joinViewModel.gameCode.setValue(gameCode);
        joinViewModel.joinToInvitedGame();
    }

    private void addGameJoinListener(){
        joinViewModel.isPlayerJoined().observe(getViewLifecycleOwner(), gameResult -> {
            // Player joined without errors.
            if (gameResult.getResult() != null){
                Log.d("HomeFragment","Player joined to game ("+gameResult.getResult().getCode()+")");
                //adapter.addGame(gameResult.getResult());
                obtainGameList();
            }else {
                //Player not joined due to error
                Log.d("HomeFragment","Player not joined due to error!");

            }
        });
    }


    public void obtainGameList(){
        gamesViewModel.getGames();
    }







}
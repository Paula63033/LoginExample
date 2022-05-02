//package cat.udl.tidic.amd.trivial.views.ui.home;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.core.view.MotionEventCompat;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.navigation.ActionOnlyNavDirections;
//import androidx.navigation.NavController;
//import androidx.navigation.Navigation;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import cat.udl.tidic.amd.trivial.R;
//import cat.udl.tidic.amd.trivial.adapters.GameAdapter;
//import cat.udl.tidic.amd.trivial.databinding.FragmentHomeBinding;
//import cat.udl.tidic.amd.trivial.databinding.FragmentJoinBinding;
//import cat.udl.tidic.amd.trivial.models.Game;
//import cat.udl.tidic.amd.trivial.models.GameStatus;
//import cat.udl.tidic.amd.trivial.models.Player;
//import cat.udl.tidic.amd.trivial.models.PlayerStatus;
//import cat.udl.tidic.amd.trivial.models.Result;
//import cat.udl.tidic.amd.trivial.viewmodels.GameViewModel;
//import cat.udl.tidic.amd.trivial.views.MainActivity;
//import cat.udl.tidic.amd.trivial.views.MainActivityViewModel;
//import cat.udl.tidic.amd.trivial.views.ui.join.JoinViewModel;
//
//public class HomeFragment extends Fragment {
//
//    private FragmentHomeBinding binding;
//    //private List<Game> games = null;
//    private View root;
//    private JoinViewModel joinViewModel;
//    private GameAdapter adapter;
//    private HomeViewModel homeViewModel;
//    private MainActivityViewModel mainActivityViewModel;
//    private GameViewModel gameViewModel;
//    private RecyclerView gamesRecylcerView;
//
//    public HomeFragment() {
//
//
//
//    }
//
//
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//
//
//        // Init and construct all the required viewModels
//        mainActivityViewModel = new
//                ViewModelProvider(requireActivity()).get(Ho.class);
//
//        homeViewModel =
//                new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
//
//        joinViewModel = new ViewModelProvider(requireActivity()).get(JoinViewModel.class);
//
//        // DataBinding
//        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        //binding.setLifecycleOwner(this);
//        binding.setHomeViewModel(homeViewModel);
//        binding.setMainActivityViewModel(mainActivityViewModel);
//        gamesRecylcerView = binding.recyclerView;
//        gamesRecylcerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        setGameListAdapter(new ArrayList<Game>());
//        root = binding.getRoot();
//
//        //Listeners
//
//            addGameCreationObserver();
//            addGamesObserver();
//            addSwipeRefresh();
//            addGameJoinListener();
//
//
//        //Get data
//        obtainGameList();
//
//
//        /*
//        homeViewModel.getGames();
//        homeViewModel.areGamesLoaded().observe(getViewLifecycleOwner(), gameList -> {
//            if (games == null){
//                initRecyclerView(gameList);
//                games = gameList;
//                Log.d("HomeFragment" , ""+games.size() );
//            }
//        });*/
//
//
//
//        return root;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//        //games = null;
//        Log.d(this.getClass().getSimpleName(),"onDestroy()");
//    }
//
//
//    private void addSwipeRefresh(){
//        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                binding.swipeRefreshLayout.setRefreshing(false);
//                obtainGameList();
//            }
//        });
//    }
//
//
//    private void addGamesObserver(){
//        homeViewModel.areGamesLoaded().observe(getViewLifecycleOwner(), gameList -> {
//            if (gameList.getResult() != null){
//                Log.d("HomeFragment","GameList obtained!");
//                setGameListAdapter(gameList.getResult());
//               // adapter.setGames(gameList.getResult());
//            }else {
//                //Game not created due to error
//                Log.d("HomeFragment","GameList not obtained due to error!");
//                ((MainActivity) requireActivity())
//                        .popUpAlertDialog(gameList.getError().getMessage());
//            }
//        });
//    }
//
//    public void setGameListAdapter(List<Game> gamesList){
//        //@TODO: Adapter must be init in the constructor and set player and list then
//        //Log.d("HomeFragment", mainActivityViewModel.getPlayer().toString());
//        adapter = new GameAdapter(gamesList, mainActivityViewModel.getPlayer());
//        gamesRecylcerView.setAdapter(adapter);
//        adapter.setOnItemClickListener(new GameAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int id, Game game) {
//                Log.d(this.getClass().getSimpleName(),"onItemClick() -> "+game.toString());
//                if (game.getCurrentState(mainActivityViewModel.getPlayer()).getStatus() == PlayerStatus.P){
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("playerState", game.getCurrentState(mainActivityViewModel.getPlayer()));
//                    Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_navigation_question,bundle);
//                }else{
//                    Bundle bundle = new Bundle();
//                    bundle.putString("gameCode", game.getCode());
//                    Navigation.findNavController(root).navigate(R.id.action_navigation_games_to_matchFragment,bundle);
//                }
//            }
//        });
//    }
//
//    private void addGameCreationObserver(){
//        homeViewModel.isGameCreated().observe(getViewLifecycleOwner(), gameResult -> {
//            // Game created without errors.
//            if (gameResult.getResult() != null){
//                Log.d("HomeFragment","Game created!");
//                joinToExistingGame(gameResult.getResult().getCode());
//            }else {
//                //Game not created due to error
//                Log.d("HomeFragment","Game not created due to error!");
//                ((MainActivity) requireActivity())
//                        .popUpAlertDialog(gameResult.getError().getMessage());
//            }
//        });
//    }
//
//
//    // A player that creates a game needs also to join it.
//    private void joinToExistingGame(String gameCode){
//        Log.d("HomeFragment", "joinToExistingGame();");
//        joinViewModel.gameCode.postValue(gameCode);
//        joinViewModel.joinToInvitedGame();
//    }
//
//    private void addGameJoinListener(){
//        joinViewModel.isPlayerJoined().observe(getViewLifecycleOwner(), gameResult -> {
//            // Player joined without errors.
//            if (gameResult.getResult() != null){
//                Log.d("HomeFragment","Player joined to game ("+gameResult.getResult().getCode()+")");
//                //adapter.addGame(gameResult.getResult());
//                obtainGameList();
//            }else {
//                //Player not joined due to error
//                Log.d("HomeFragment","Player not joined due to error!");
//                ((MainActivity) requireActivity())
//                        .popUpAlertDialog(gameResult.getError().getMessage());
//            }
//        });
//    }
//
//    private void initRecyclerView() {
//        //final RecyclerView list = binding.recyclerView;
//        //adapter = new GameAdapter(gamesList, mainActivityViewModel.getPlayer());
//        //list.setAdapter(adapter);
//
//        //list.setAdapter(adapter);
//    }
//
//    TimerTask getGamesTask = new TimerTask() {
//        @Override
//        public void run() {
//            homeViewModel.getGames();
//        }
//    };
//
//    public void obtainGameList(){
//        //games = null;
//        homeViewModel.getGames();
//    }
//
//
//
//
//
//
//
//}
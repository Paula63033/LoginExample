package cat.udl.tidic.amd.trivial.views.ui.match;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cat.udl.tidic.amd.trivial.adapters.GameAdapter;
import cat.udl.tidic.amd.trivial.adapters.PlayerStateAdapter;
import cat.udl.tidic.amd.trivial.databinding.FragmentMatchBinding;
import cat.udl.tidic.amd.trivial.models.Game;
import cat.udl.tidic.amd.trivial.usecases.home.HomeActivity;


public class MatchFragment extends Fragment {

    private View root;
    private MatchViewModel matchViewModel;

    private PlayerStateAdapter adapter;
    //private HomeViewModel gameViewModel;
    private FragmentMatchBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        matchViewModel = new
                ViewModelProvider(requireActivity()).get(MatchViewModel.class);


        binding = FragmentMatchBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setPlayer(((HomeActivity) requireActivity()).getCurrentPlayer());
        matchViewModel.getGame(getArguments().getString("gameCode"));
        Log.d("MatchFragment",getArguments().getString("gameCode") );
        matchViewModel.isGameLoaded().observe(getViewLifecycleOwner(), new Observer<Game>() {
            @Override
            public void onChanged(Game game) {
                Log.d("MatchFragment", game.toString() );
                binding.setGame(game);
                initRecyclerView(game);
            }
        });

        // Hide back arrow in the actionbar
        ((HomeActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        //binding.setMatchViewModel(matchViewModel);
       root = binding.getRoot();
        return root;

    }

    private void initRecyclerView(Game game) {
        final RecyclerView list = binding.otherPlayersRV;
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PlayerStateAdapter(game.getRivalsState(((HomeActivity) requireActivity()).getCurrentPlayer()));
        list.setAdapter(adapter);
    }

}
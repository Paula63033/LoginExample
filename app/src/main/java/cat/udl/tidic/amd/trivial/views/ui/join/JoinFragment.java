package cat.udl.tidic.amd.trivial.views.ui.join;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.Objects;

import cat.udl.tidic.amd.trivial.R;
import cat.udl.tidic.amd.trivial.databinding.FragmentJoinBinding;
import cat.udl.tidic.amd.trivial.models.Game;
import cat.udl.tidic.amd.trivial.models.Result;

public class JoinFragment extends Fragment {

    private FragmentJoinBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        JoinViewModel joinViewModel =
                new ViewModelProvider(requireActivity()).get(JoinViewModel.class);
        binding = FragmentJoinBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setJoinViewModel(joinViewModel);
        View root = binding.getRoot();


        joinViewModel.isPlayerJoined().observe(getViewLifecycleOwner(), new Observer<Result<Game>>() {
            @Override
            public void onChanged(Result<Game> gameResult) {
                joinViewModel.isJoined.postValue(false);
                if (gameResult.getResult() != null){
                    Log.d("JoinFragment","Player joined to the game -> navigate to HomeFragment");
                    Navigation.findNavController(root).navigate(R.id.action_navigation_join_to_navigation_games);
                }
               else{
                    //Display Error
                    Log.d("JoinFragment","Player not joined to game");

                }
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
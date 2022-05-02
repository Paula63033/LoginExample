package cat.udl.tidic.amd.trivial.usecases.question;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import cat.udl.tidic.amd.trivial.R;
import cat.udl.tidic.amd.trivial.databinding.FragmentQuestionBinding;
import cat.udl.tidic.amd.trivial.models.PlayerState;
import cat.udl.tidic.amd.trivial.views.ui.home.HomeViewModel;

public class QuestionFragment extends Fragment {

    private static final String TAG = "QuestionFragment";

    private FragmentQuestionBinding binding ;
    private PlayerState playerState;
    private QuestionViewModel questionViewModel;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "Binding...");
        binding = FragmentQuestionBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);

        Log.d(TAG, "Get arguments from fragment...");
        playerState = (PlayerState) getArguments().getSerializable("playerState");
        Log.d(TAG, " -> " + playerState.toString());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

        // View Models
        Log.d(TAG, "Defining ViewModels...");
        questionViewModel =
                new ViewModelProvider(requireActivity()).get(QuestionViewModel.class);
        binding.setQuestionViewModel(questionViewModel);

        homeViewModel =
                new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        //Setup
        Log.d(TAG, "Setting data...");
        data();


    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        questionViewModel.userAnswer = new MutableLiveData<>();
    }


    public void data(){
        questionViewModel.showQuestion(playerState.getId_question());
        questionViewModel.isQuestionLoaded().observe(getViewLifecycleOwner(),
                question -> binding.setQuestion(question));

        questionViewModel.userAnswer.observe(getViewLifecycleOwner(), answer -> {
            homeViewModel.updateGame(answer,playerState);
            homeViewModel.isMatchUpdated().observe(getViewLifecycleOwner(),
                    isUpdated -> Navigation.findNavController(
                            binding.getRoot()).navigate(R.id.action_navigation_question_to_navigation_home));
        });

    }

}
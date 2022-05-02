package cat.udl.tidic.amd.trivial.usecases.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import cat.udl.tidic.amd.trivial.R;
import cat.udl.tidic.amd.trivial.databinding.ActivityMainBinding;
import cat.udl.tidic.amd.trivial.models.Player;
import cat.udl.tidic.amd.trivial.utils.PreferencesProvider;


public class HomeActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public HomeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // Content
        setContentView(binding.getRoot());

        // View Model
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Obtain data required to setup view
        data();

    }

    private void data(){
        String serializedPlayer =  PreferencesProvider.providePreferences().getString("player","");
        if (serializedPlayer.equals("")){
            viewModel.loadPlayer();
            viewModel.isPlayerLoaded().observe(this, playerResult -> {
                viewModel.isLoaded.postValue(false);
                PreferencesProvider.providePreferences().edit().putString("player",
                        new Gson().toJson(playerResult.getResult())).commit();
                setup();
            });
            //
        } else {
            setup();
        }
    }

    private void setup(){
        // Bottom view Navigation
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_games, R.id.navigation_join, R.id.navigation_ranking,
                R.id.navigation_question)
                .build();

        final NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_activity_main);
        final NavController navController = navHostFragment.getNavController();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Custom ActionBar -> It requires ActionBar theme
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_foreground);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    public Player getCurrentPlayer(){
        String serializedPlayer =  PreferencesProvider.providePreferences().getString("player","");
        return new Gson().fromJson(serializedPlayer, Player.class);
    }

}
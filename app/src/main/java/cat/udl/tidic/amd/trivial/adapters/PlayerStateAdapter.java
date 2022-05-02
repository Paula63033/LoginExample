package cat.udl.tidic.amd.trivial.adapters;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cat.udl.tidic.amd.trivial.R;
import cat.udl.tidic.amd.trivial.databinding.GameItemBinding;
import cat.udl.tidic.amd.trivial.databinding.GamePlayerTemBinding;
import cat.udl.tidic.amd.trivial.models.PlayerState;

public class PlayerStateAdapter extends RecyclerView.Adapter<PlayerStateAdapter.ViewHolder>
{

    private List<PlayerState> states;
    private LayoutInflater layoutInflater;

    public PlayerStateAdapter(List<PlayerState> states) {
        this.states = states;
    }

    @Override
    public int getItemCount() {
        return states.size();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        final GamePlayerTemBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.game_player_tem,
                        viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setPlayerState(states.get(position));
        //holder.binding.setPlayerImage();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final GamePlayerTemBinding binding;
        public ViewHolder(GamePlayerTemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }



}

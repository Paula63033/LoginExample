package cat.udl.tidic.amd.trivial.usecases.ranking.pages;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cat.udl.tidic.amd.trivial.R;
import cat.udl.tidic.amd.trivial.databinding.PlayerItemRowBinding;
import cat.udl.tidic.amd.trivial.models.Player;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    private List<Player> playerList;
    private LayoutInflater layoutInflater;

    public PlayerAdapter(){
        this.playerList = new ArrayList<>();
    }

    public void setPlayers(List<Player> players){
        this.playerList = players;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        final PlayerItemRowBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.player_item_row,
                        viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setPlayer(playerList.get(position));
        holder.binding.playerRanking.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return this.playerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final PlayerItemRowBinding binding;
        public ViewHolder(PlayerItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

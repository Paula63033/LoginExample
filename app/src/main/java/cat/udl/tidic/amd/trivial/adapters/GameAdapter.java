package cat.udl.tidic.amd.trivial.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cat.udl.tidic.amd.trivial.R;
import cat.udl.tidic.amd.trivial.databinding.GameItemBinding;
import cat.udl.tidic.amd.trivial.models.Game;
import cat.udl.tidic.amd.trivial.models.Player;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder>
{

    private List<Game> games;
    private final Player player;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;

    public GameAdapter(List<Game> games, Player player) {
        this.games = games;
        this.player = player;
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public void addGame(Game game) {
        this.games.add(game);
        notifyItemInserted(this.games.size() - 1);
    }

    public void setGames(List<Game> games) {
        this.games = games;
        notifyItemRangeChanged(0,games.size());
    }

    public interface OnItemClickListener {
        void onItemClick(final int id, final Game game);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        final GameItemBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.game_item,
                        viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setGame(games.get(position));
        holder.binding.setPlayer(player);

        holder.binding.setClicklistener((View.OnClickListener) v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position, games.get(position));
            }
        });

    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final GameItemBinding binding;
        public ViewHolder(GameItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}

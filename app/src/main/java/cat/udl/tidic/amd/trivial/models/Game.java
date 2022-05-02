package cat.udl.tidic.amd.trivial.models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Game  extends BaseObservable {

    @SerializedName("id")
    int id;
    @SerializedName("players")
    List<String> players;
    @SerializedName("code")
    String code;
    @SerializedName("status")
    GameStatus status;
    @SerializedName("max_rounds")
    int max_rounds;


    @SerializedName("playerStatus")
    List<PlayerState> playerStates;

    public Game() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public List<PlayerState> getPlayerStates() {
        return playerStates;
    }

    public void setPlayerStates(List<PlayerState> playerStates) {
        this.playerStates = playerStates;
    }

    public int getMax_rounds() {
        return max_rounds;
    }

    public void setMax_rounds(int max_rounds) {
        this.max_rounds = max_rounds;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", players=" + players +
                ", code='" + code + '\'' +
                ", status=" + status +
                ", max_rounds=" + max_rounds +
                ", playerStates=" + playerStates +
                '}';
    }

    public Boolean isMyTurn(String  username){
        for(int i=0; i<playerStates.size(); i++){
            if (playerStates.get(i).getStatus() == PlayerStatus.P &&
                    username.equals(playerStates.get(i).getUsername())){
                return true;
            }
        }
        return false;
    }

    // The game needs to be Completed
    public Boolean isGameReady(){
        if (this.getStatus() == GameStatus.C){
            return true;
        };
        return false;
    }

    public PlayerState getCurrentState(Player player){
        if (this.playerStates != null) {
            for (PlayerState ps : this.playerStates) {
                if (ps.username.equals(player.username)) {
                    return ps;
                }
            }
        }
        return null;
    }

    public List<PlayerState> getRivalsState(Player player){
        List<PlayerState> rivalsStates = new ArrayList<>();
        if (this.playerStates != null) {
            for (PlayerState ps : this.playerStates) {
                if (! ps.username.equals(player.username)) {
                    rivalsStates.add(ps);
                }
            }
        }
        return rivalsStates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id && max_rounds == game.max_rounds && players.equals(game.players) && code.equals(game.code) && status == game.status && playerStates.equals(game.playerStates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, players, code, status, max_rounds, playerStates);
    }
}

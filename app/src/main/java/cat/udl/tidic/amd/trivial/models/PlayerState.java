package cat.udl.tidic.amd.trivial.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class PlayerState implements Serializable {

    @SerializedName("username")
    String username;
    @SerializedName("status")
    PlayerStatus status;
    @SerializedName("score")
    int score;
    @SerializedName("round")
    int round;
    @SerializedName("id_question")
    int id_question;
    @SerializedName("id_answer")
    int id_answer;
    @SerializedName("code")
    String code;


    public PlayerState() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getId_answer() {
        return id_answer;
    }

    public void setId_answer(int id_answer) {
        this.id_answer = id_answer;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getId_question() {
        return id_question;
    }

    public void setId_question(int id_question) {
        this.id_question = id_question;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "PlayerState{" +
                "username='" + username + '\'' +
                ", status=" + status +
                ", score=" + score +
                ", round=" + round +
                ", id_question=" + id_question +
                ", id_answer=" + id_answer +
                ", code='" + code + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerState that = (PlayerState) o;
        return score == that.score && round == that.round && id_question == that.id_question && id_answer == that.id_answer && username.equals(that.username) && status == that.status && code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, status, score, round, id_question, id_answer, code);
    }
}

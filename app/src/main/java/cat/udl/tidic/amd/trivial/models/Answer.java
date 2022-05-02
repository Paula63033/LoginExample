package cat.udl.tidic.amd.trivial.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Answer implements Serializable {

    @SerializedName("id")
    int id;
    @SerializedName("answer")
    String answer;
    @SerializedName("is_correct")
    Boolean isCorrect;

    public Answer(String answer, Boolean isCorrect) {
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", answer='" + answer + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}

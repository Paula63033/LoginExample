package cat.udl.tidic.amd.trivial.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question {

    @SerializedName("question")
    String question;
    @SerializedName("answers")
    List<Answer> answers;
    @SerializedName("category")
    Category category;

    public Question(String question, List<Answer> answers) {
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

package cat.udl.tidic.amd.trivial.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import cat.udl.tidic.amd.trivial.services.QuestionService;
import cat.udl.tidic.amd.trivial.services.QuestionServiceImpl;
import cat.udl.tidic.amd.trivial.models.Question;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionRepo {

    private static String TAG = "QuestionsRepo";

    private QuestionService questionService;
    public MutableLiveData<Question> mQuestion;

    public QuestionRepo() {
        this.questionService = new QuestionServiceImpl();
        this.mQuestion = new MutableLiveData<>();
    }

    public void showQuestion(int question_id){

        questionService.getQuestion(question_id).enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {

                int code = response.code();
                Log.d(TAG, "getRandomQuestion() -> ha rebut del backend un codi:  " + code);

                if (code == 200) {
                    Question q = response.body();
                    assert q != null;
                    Log.d(TAG, "getRandomQuestion() -> ha rebut la pregunta:  "
                            + q.getQuestion());
                    Log.d(TAG, "getRandomQuestion() -> ha rebut la categoria:  "
                            + q.getCategory().toString());
                    Log.d(TAG, "getRandomQuestion() -> ha rebut les respostes:  "
                            + q.getAnswers());

                    mQuestion.setValue(q);
                }

            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {
                Log.d(TAG,  t.getMessage());
            }
        });

    }


    public void getQuestion() {

        questionService.getRandomQuestion().enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {

                int code = response.code();
                Log.d(TAG, "getRandomQuestion() -> ha rebut del backend un codi:  " + code);

                if (code == 200) {
                    Question q = response.body();
                    assert q != null;
                    Log.d(TAG, "getRandomQuestion() -> ha rebut la pregunta:  "
                            + q.getQuestion());
                    Log.d(TAG, "getRandomQuestion() -> ha rebut la categoria:  "
                            + q.getCategory().toString());
                    Log.d(TAG, "getRandomQuestion() -> ha rebut les respostes:  "
                            + q.getAnswers());

                    mQuestion.setValue(q);
                }

            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {
                Log.d(TAG,  t.getMessage());

            }

        });
    }
}



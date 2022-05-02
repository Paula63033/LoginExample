package cat.udl.tidic.amd.trivial.services;

import cat.udl.tidic.amd.trivial.models.Question;
import cat.udl.tidic.amd.trivial.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Retrofit;

public class QuestionServiceImpl implements QuestionService {

    private  Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

    @Override
    public Call<Question> getRandomQuestion() {
        return retrofit.create(QuestionService.class).getRandomQuestion();
    }

    @Override
    public Call<Question> getQuestion(int question_id) {
        return retrofit.create(QuestionService.class).getQuestion(question_id);
    }
}

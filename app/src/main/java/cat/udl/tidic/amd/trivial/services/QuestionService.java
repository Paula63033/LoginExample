package cat.udl.tidic.amd.trivial.services;

import cat.udl.tidic.amd.trivial.models.Question;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface QuestionService {

    //Get a random question
    @GET("trivial/question")
    Call<Question> getRandomQuestion();

    //Show a question using id.
    @GET("trivial/question/show")
    Call<Question> getQuestion(@Query("id") int question_id);


}

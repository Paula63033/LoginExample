package cat.udl.tidic.amd.trivial.services;

import cat.udl.tidic.amd.trivial.models.Account;
import cat.udl.tidic.amd.trivial.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Retrofit;

public class AccountServiceImpl implements AccountService{

    private Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

    @Override
    public Call<Account> createTokenUser(String authorizationToken) {
        return  retrofit.create(AccountService.class).createTokenUser(authorizationToken);
    }
}

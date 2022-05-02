package cat.udl.tidic.amd.trivial.domain;

import cat.udl.tidic.amd.trivial.repositories.GameRepo;

public class JoinInvitedGameUseCase {

    private GameRepo repository = new GameRepo();

    public void invoke(String gameCode){
        repository.joinGame(gameCode);
    }


}

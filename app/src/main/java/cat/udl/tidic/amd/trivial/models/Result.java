package cat.udl.tidic.amd.trivial.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Result<T> {

    private final T result;
    private final Throwable error;

    private Result(@Nullable T result, @Nullable Throwable error) {
        this.result = result;
        this.error = error;
    }

    @NonNull
    public static <T> Result<T> success(@NonNull T result) {
        return new Result(result, null);
    }

    @NonNull
    public static <T> Result<T> error(@NonNull Throwable error) {

        return new Result(null, error);
    }

    @Nullable
    public T getResult() {
        return result;
    }

    @Nullable
    public Throwable getError() {
        return error;
    }


}
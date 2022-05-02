package cat.udl.tidic.amd.trivial.models;

import androidx.annotation.NonNull;

import cat.udl.tidic.amd.trivial.R;

public enum GameStatus {
    C,
    O,
    F;

    @NonNull
    @Override
    public String toString() {
        switch (this){
            case C: return "Closed";
            case O: return "Opened";
            case F: return "Finished";
            default: return "";
        }
    }

}

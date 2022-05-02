package cat.udl.tidic.amd.trivial.models;

import androidx.annotation.NonNull;

public enum PlayerStatus {
    P,
    W,
    V,
    D;
    
    @NonNull
    @Override
    public String toString() {
        switch (this){
            case P: return "Playing";
            case W: return "Waiting";
            case V: return "Victory";
            case D: return "Defeat";
            default: return "";
        }
    }

}

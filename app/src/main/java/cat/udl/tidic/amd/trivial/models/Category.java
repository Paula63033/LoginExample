package cat.udl.tidic.amd.trivial.models;

import androidx.annotation.NonNull;

import cat.udl.tidic.amd.trivial.R;

public enum Category {
    literature,
    movies,
    science,
    games;

    @NonNull
    @Override
    public String toString() {
        switch (this){
            case literature: return "Literature";
            case movies: return "Movies";
            case science: return "Science";
            case games: return "Games";
            default: return "";
        }
    }

    public int getColor() {
        switch (this){
            case literature: return R.color.trivial_blue;
            case movies: return R.color.trivial_orange;
            case science: return  R.color.trivial_green;
            case games: return  R.color.trivial_pig;
            default: return R.color.black;
        }
    }
}

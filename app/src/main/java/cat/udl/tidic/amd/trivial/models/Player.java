package cat.udl.tidic.amd.trivial.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import cat.udl.tidic.amd.trivial.R;

public class Player extends Account{

    @SerializedName("username")
    String username;
    @SerializedName("id")
    int id;
    @SerializedName("rating")
    int rating;
    @SerializedName("photo")
    private String photo;
    @SerializedName("points")
    private int points;

    public Player() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }

    @Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                ", id=" + id +
                ", rating=" + rating +
                ", photo='" + photo + '\'' +
                ", points=" + points +
                '}';
    }
}

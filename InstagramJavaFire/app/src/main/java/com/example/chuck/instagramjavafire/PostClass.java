package com.example.chuck.instagramjavafire;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostClass extends ArrayAdapter<String> {
    private final ArrayList<String> useremail;
    private final ArrayList<String> userImage;
    private final ArrayList<String> userComment;
    private final Activity activity;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View customView = layoutInflater.inflate(R.layout.custom_view, null, true);
        TextView userEmailText = customView.findViewById(R.id.postuserNameText);
        TextView commentText = customView.findViewById(R.id.postcommentText);
        ImageView postImaj = customView.findViewById(R.id.postimaj);
        userEmailText.setText(useremail.get(position));
        commentText.setText(userComment.get(position));
        Picasso.get().load(userImage.get(position)).into(postImaj);
        return customView;
    }

    public PostClass(ArrayList<String> useremail, ArrayList<String> userImage, ArrayList<String> userComment, Activity activity) {
        super(activity, R.layout.custom_view, useremail);
        this.useremail = useremail;
        this.userImage = userImage;
        this.userComment = userComment;
        this.activity = activity;

    }
}

package com.yousefhaggy.fblamobileapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
// Recycler view adapter for displaying all achievements
public class AchievementRecyclerViewAdapter extends RecyclerView.Adapter<AchievementRecyclerViewAdapter.AchievementViewHolder> {

    public static class AchievementViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView progressTextView;
        TextView descriptionTextView;
        ImageView achievementImageView;

        public AchievementViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.achievementCardView);
            progressTextView = (TextView) itemView.findViewById(R.id.achievementProgress);
            descriptionTextView = (TextView) itemView.findViewById(R.id.achievementDescription);
            achievementImageView = (ImageView) itemView.findViewById(R.id.achievementIcon);
        }
    }

    private List<Achievement> achievementList;

    AchievementRecyclerViewAdapter(List<Achievement> achievementList) {
        this.achievementList = achievementList;
    }

    @NonNull
    @Override
    public AchievementViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.achievement_card, viewGroup, false);
        AchievementViewHolder achievementViewHolder = new AchievementViewHolder(v);
        return achievementViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementViewHolder achievementViewHolder, int i) {
        achievementViewHolder.descriptionTextView.setText(achievementList.get(i).description);
        achievementViewHolder.progressTextView.setText(achievementList.get(i).progress + "/" + achievementList.get(i).max);
       //If achievement is earned, change the locked icon to a green check mark
        if (achievementList.get(i).isEarned) {
            achievementViewHolder.achievementImageView.setImageResource(R.drawable.ic_check_circle_green_24dp);
        } else {
            achievementViewHolder.achievementImageView.setImageResource(R.drawable.ic_lock_black_24dp);


        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return achievementList.size();
    }


}

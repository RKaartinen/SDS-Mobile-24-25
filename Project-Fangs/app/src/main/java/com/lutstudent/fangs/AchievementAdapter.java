package com.lutstudent.fangs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class AchievementAdapter extends RecyclerView.Adapter<AchievementViewHolder> {

    private Context context;
    private ArrayList<Achievement> achievements;

    public AchievementAdapter(Context context, ArrayList<Achievement> achievements) {
        this.context = context;
        this.achievements = achievements;
    }

    @NonNull
    @Override
    public AchievementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.achievement_view, parent, false);
        return new AchievementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementViewHolder holder, int position) {
        Achievement a = achievements.get(position);
        holder.achievementTitle.setText(a.getTitle());
        holder.achievementDesc.setText(a.getDescription());

        int[] images = {
                R.drawable.firstevolve_ach,
                R.drawable.novice_ach,
                R.drawable.highsociety_ach,
                R.drawable.reachingthrone_ach,
                R.drawable.yourcrown_ach,
                R.drawable.learningpolitics_ach,
                R.drawable.judge_ach,
                R.drawable.scholar_ach,
                R.drawable.academicweapon_ach
        };

        if (position < images.length) {
            holder.achievementImage.setImageResource(images[position]);
        }

        if (a.isUnlocked()) {
            holder.itemView.setAlpha(1f);
            holder.achievementTitle.setTextColor(
                    ContextCompat.getColor(context, R.color.gold)
            );
            holder.achievementDesc.setTextColor(
                    ContextCompat.getColor(context, R.color.black)
            );
        } else {
            holder.itemView.setAlpha(0.4f);
            holder.achievementTitle.setTextColor(
                    ContextCompat.getColor(context, R.color.crimson)
            );
            holder.achievementDesc.setTextColor(
                    ContextCompat.getColor(context, R.color.crimson)
            );
        }
    }

    @Override
    public int getItemCount() {
        return achievements.size();
    }
}



package com.lutstudent.fangs;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AchievementViewHolder extends RecyclerView.ViewHolder {

    ImageView achievementImage;

    TextView achievementTitle, achievementDesc;


    public AchievementViewHolder(@NonNull View itemView) {
        super(itemView);
         achievementImage = itemView.findViewById(R.id.imageViewAchievement);
         achievementTitle = itemView.findViewById(R.id.txtAchievementTitle);
         achievementDesc = itemView.findViewById(R.id.txtAchievementDesc);
    }
}

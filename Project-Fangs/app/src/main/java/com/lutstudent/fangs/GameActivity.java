package com.lutstudent.fangs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "FangsGameState";
    private ProgressBar levelProgressBar, thirstProgressBar, authorityProgressBar, wisdomProgressBar;
    private TextView  txtLevel, txtRank, txtThirst, txtAuthority, txtWisdom, txtAuthorityValue, txtWisdomValue;
    private ImageView imgFangs;
    private Button studyBtn, thirstBtn, decisionsBtn, evolveBtn;

    private int thirst = 100;
    private int authorityXP = 0;
    private int wisdomXP = 0;
    private int authorityLevel = 0;
    private int wisdomLevel = 0;
    private int fangsXP = 0;
    private int fangsLevel = 0;

    private final int MAX_LEVEL = 50;
    private final int XP_PER_LEVEL = 200;
    private final int SKILL_XP_PER_LEVEL = 100;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        View root = findViewById(R.id.main);
        root.setVisibility(View.VISIBLE);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        levelProgressBar = findViewById(R.id.pbLevel);
        thirstProgressBar = findViewById(R.id.pbThirst);
        authorityProgressBar = findViewById(R.id.pbAuthority);
        wisdomProgressBar = findViewById(R.id.pbWisdom);

        txtLevel = findViewById(R.id.txtLevelNumber);
        txtRank = findViewById(R.id.txtRank);
        txtThirst = findViewById(R.id.txtThirstValue);
        txtAuthority = findViewById(R.id.txtAuthorityLevel);
        txtWisdom = findViewById(R.id.txtWisdomLevel);
        txtAuthorityValue = findViewById(R.id.txtAuthorityValue);
        txtWisdomValue = findViewById(R.id.txtWisdomValue);

        imgFangs = findViewById(R.id.ImageFangs);

        studyBtn = findViewById(R.id.studyBtn);
        thirstBtn = findViewById(R.id.thirstBtn);
        decisionsBtn = findViewById(R.id.authorityBtn);
        evolveBtn = findViewById(R.id.evolveBtn);

        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        loadGameState();

        studyBtn.setOnClickListener(v -> {
            gainWisdom(25);
        });
        decisionsBtn.setOnClickListener(v -> {
            gainAuthority(25);
        });
        thirstBtn.setOnClickListener(v -> {
            drinkBlood(10);
        });
        evolveBtn.setOnClickListener(v -> {
            attemptEvolve();
        });
        updateUI();
        updateEvolveButton();
    }

    protected void onPause() {
        super.onPause();
        saveGameState();
    }

    private void loadGameState() {
        thirst = prefs.getInt("thirst", 100);
        authorityXP = prefs.getInt("authorityXP", 0);
        wisdomXP = prefs.getInt("wisdomXP", 0);
        authorityLevel = prefs.getInt("authorityLevel", 0);
        wisdomLevel = prefs.getInt("wisdomLevel", 0);
        fangsXP = prefs.getInt("fangsXP", 0);
        fangsLevel = prefs.getInt("fangsLevel", 0);
    }

    private void saveGameState() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("thirst", thirst);
        editor.putInt("authorityXP", authorityXP);
        editor.putInt("wisdomXP", wisdomXP);
        editor.putInt("authorityLevel", authorityLevel);
        editor.putInt("wisdomLevel", wisdomLevel);
        editor.putInt("fangsXP", fangsXP);
        editor.putInt("fangsLevel", fangsLevel);
        editor.apply();
    }

    private void gainWisdom(int xp) {
        if (thirst < 15) { xp /= 4;}
        else if (thirst < 30) { xp /= 2;}

        decreaseThirst(2);
        wisdomXP+= xp;
        fangsXP += xp;
        if (wisdomXP >= SKILL_XP_PER_LEVEL && wisdomLevel < MAX_LEVEL) {
            wisdomXP -= SKILL_XP_PER_LEVEL;
               wisdomLevel++;
        }
        if (wisdomXP > 100) wisdomXP = 100;
        levelUpCheck();
        updateUI();
    }

    private void gainAuthority(int xp) {
        if (thirst < 15) { xp /= 4;}
        else if (thirst < 30) { xp /= 2;}

        decreaseThirst(2);
        authorityXP += xp;
        fangsXP += xp;
        if (authorityXP >= SKILL_XP_PER_LEVEL && authorityLevel < MAX_LEVEL) {
            authorityXP -= SKILL_XP_PER_LEVEL;
            authorityLevel++;
        }
        if (authorityXP > 100) authorityXP = 100;
        levelUpCheck();
        updateUI();
    }

    private void levelUpCheck() {
        while (fangsXP >= XP_PER_LEVEL && fangsLevel < MAX_LEVEL) {
            if (fangsLevel == 9 || fangsLevel == 19 || fangsLevel == 29 || fangsLevel == 39 || fangsLevel == 49) {
                break;
            }
            fangsXP -= XP_PER_LEVEL;
            fangsLevel++;
        }
    }

    private void attemptEvolve() {
        if ((fangsLevel == 9 || fangsLevel == 19 || fangsLevel == 39 )
                || (fangsLevel == 29 && authorityLevel >= 25 && wisdomLevel >= 25)
                || (fangsLevel == 49 && authorityLevel >= 50 && wisdomLevel >= 50)) {
            fangsLevel++;
            fangsXP = 0;
            updateUI();
            updateEvolveButton();
        }
    }

    private void drinkBlood(int amount) {
        thirst += amount;
        if (thirst > 100) thirst = 100;
        updateUI();
    }

    private void decreaseThirst(int amount) {
        thirst -= amount;
        if (thirst < 0) thirst = 0;
    }

    private void updateUI() {
        thirstProgressBar.setProgress(thirst);
        authorityProgressBar.setProgress((authorityXP * 100) / SKILL_XP_PER_LEVEL);
        wisdomProgressBar.setProgress((wisdomXP * 100) / SKILL_XP_PER_LEVEL);

        txtLevel.setText(String.valueOf(fangsLevel));
        txtThirst.setText("Thirst: " + thirst + "/100");
        txtAuthority.setText(String.valueOf(authorityLevel));
        txtWisdom.setText(String.valueOf(wisdomLevel));
        txtAuthorityValue.setText("Authority: " + authorityXP + "/100");
        txtWisdomValue.setText("Wisdom: " + wisdomXP + "/100");

        int progress = (int) ((fangsXP / (float) XP_PER_LEVEL) * 100);
        levelProgressBar.setProgress(progress);

        updateRankAndImage();
        checkAchievement();
        updateEvolveButton();
    }

    private void updateRankAndImage() {
        String title = "Fledgling";
        int imageRes = R.drawable.fledgling;

        if (fangsLevel == 50 && (authorityLevel == 50 && wisdomLevel == 50)) {
            title = "Vampire Monarch";
            imageRes = R.drawable.monarch;
        } else if (fangsLevel >= 40) {
            title = "Vampire Prince";
            imageRes = R.drawable.prince;
        } else if (fangsLevel >= 30 && (authorityLevel >= 25 && wisdomLevel >= 25)) {
            title = "Vampire Noble";
            imageRes = R.drawable.noble;
        } else if (fangsLevel >= 20) {
            title = "Apprentice";
            imageRes = R.drawable.apprentice;
        } else if (fangsLevel >= 10) {
            title = "Primal";
            imageRes = R.drawable.primal;
        }
        txtRank.setText(title);
        imgFangs.setImageResource(imageRes);
    }

    private void updateEvolveButton() {
        boolean canEvolve = false;

        if (fangsLevel == 9 || fangsLevel == 19 || fangsLevel == 39) {
            canEvolve = true;
        } else if (fangsLevel == 29 && (authorityLevel >= 25 && wisdomLevel >= 25)) {
            canEvolve = true;
        } else if (fangsLevel == 49 && (authorityLevel >= 50 && wisdomLevel >= 50)) {
            canEvolve = true;
        }
        evolveBtn.setVisibility(canEvolve ? View.VISIBLE : View.GONE);
        studyBtn.setEnabled((!canEvolve));
        thirstBtn.setEnabled((!canEvolve));
        decisionsBtn.setEnabled((!canEvolve));
    }

    private void checkAchievement() {
        GameManager gameManager = GameManager.getInstance();
        Achievement a;
        a = gameManager.getAchievements().get(0);
        if (!a.isUnlocked() && fangsLevel >= 10) {
            a.unlock();
            Toast.makeText(this, "Achievement '"+a.getTitle()+"' unlocked!", Toast.LENGTH_SHORT).show();
        }
        a = gameManager.getAchievements().get(1);
        if (!a.isUnlocked() && fangsLevel >= 20) {
            a.unlock();
            Toast.makeText(this, "Achievement '"+a.getTitle()+"' unlocked!", Toast.LENGTH_SHORT).show();
        }
        a = gameManager.getAchievements().get(2);
        if (!a.isUnlocked() && fangsLevel >= 30) {
            a.unlock();
            Toast.makeText(this, "Achievement '"+a.getTitle()+"' unlocked!", Toast.LENGTH_SHORT).show();
        }
        a = gameManager.getAchievements().get(3);
        if (!a.isUnlocked() && fangsLevel >= 40) {
            a.unlock();
            Toast.makeText(this, "Achievement '"+a.getTitle()+"' unlocked!", Toast.LENGTH_SHORT).show();
        }
        a = gameManager.getAchievements().get(4);
        if (!a.isUnlocked() && fangsLevel >= 50) {
            a.unlock();
            Toast.makeText(this, "Achievement '"+a.getTitle()+"' unlocked!", Toast.LENGTH_SHORT).show();
        }
        a = gameManager.getAchievements().get(5);
        if (!a.isUnlocked() && authorityLevel >= 25) {
            a.unlock();
            Toast.makeText(this, "Achievement '"+a.getTitle()+"' unlocked!", Toast.LENGTH_SHORT).show();
        }
        a = gameManager.getAchievements().get(6);
        if (!a.isUnlocked() && wisdomLevel >= 25) {
            a.unlock();
            Toast.makeText(this, "Achievement '"+a.getTitle()+"' unlocked!", Toast.LENGTH_SHORT).show();
        }
        a = gameManager.getAchievements().get(7);
        if (!a.isUnlocked() && authorityLevel >= 50) {
            a.unlock();
            Toast.makeText(this, "Achievement '"+a.getTitle()+"' unlocked!", Toast.LENGTH_SHORT).show();
        }
        a = gameManager.getAchievements().get(8);
        if (!a.isUnlocked() && wisdomLevel >= 50) {
            a.unlock();
            Toast.makeText(this, "Achievement '"+a.getTitle()+"' unlocked!", Toast.LENGTH_SHORT).show();
        }
    }
}
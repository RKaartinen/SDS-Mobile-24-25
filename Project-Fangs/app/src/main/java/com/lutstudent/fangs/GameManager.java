package com.lutstudent.fangs;

import java.util.ArrayList;

public class GameManager {
    private static GameManager gameManager = null;
    private ArrayList<Achievement> achievements;

    private GameManager() {
        achievements = new ArrayList<>();
        initAchievements();
    }

    public static GameManager getInstance() {
        if (gameManager == null) {
            gameManager = new GameManager();
        }
        return gameManager;
    }

    public ArrayList<Achievement> getAchievements() {
        return achievements;
    }

    private void initAchievements() {
        achievements.add(new Achievement("First Evolve!", "Reached level 10"));
        achievements.add(new Achievement("Novice!", "Reached level 20"));
        achievements.add(new Achievement("Part of the High Society!", "Reached level 30"));
        achievements.add(new Achievement("Reaching for the Throne", "Reached level 40"));
        achievements.add(new Achievement("The Crown is Yours!", "Reached level 50"));
        achievements.add(new Achievement("Learning the Politics!", "Reached level 25 Authority"));
        achievements.add(new Achievement("Scholar!", "Reached level 25 Wisdom"));
        achievements.add(new Achievement("Judge of them All!", "Reached level 50 Authority"));
        achievements.add(new Achievement("Academic Weapon!", "Reached level 50 Wisdom"));
    }

}

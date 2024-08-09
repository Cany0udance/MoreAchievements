package moreachievements.util;

import basemod.BaseMod;
import com.megacrit.cardcrawl.core.Settings;
import moreachievements.MoreAchievements;

import static com.megacrit.cardcrawl.unlock.UnlockTracker.achievementPref;

public class MoreAchievementUnlocker {
    public static void unlockAchievement(String key) {
        String fullKey = MoreAchievements.makeID(key);
        if (!Settings.isShowBuild && Settings.isStandardRun()) {
            if (!achievementPref.getBoolean(fullKey, false)) {
                achievementPref.putBoolean(fullKey, true);
            }

            achievementPref.flush();
        }
    }
}
package moreachievements.util;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import moreachievements.MoreAchievements;

import static com.megacrit.cardcrawl.unlock.UnlockTracker.achievementPref;

public class MoreAchievementUnlocker {
    public static void unlockAchievement(String key) {
        String fullKey = MoreAchievements.makeID(key);
        if (!Settings.isShowBuild && Settings.isStandardRun()) {
            CardCrawlGame.publisherIntegration.unlockAchievement(fullKey);
            if (!achievementPref.getBoolean(fullKey, false)) {
                achievementPref.putBoolean(fullKey, true);
            }

            achievementPref.flush();
        }
    }
}
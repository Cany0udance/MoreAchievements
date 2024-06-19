package moreachievements.patches.statScreen;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import moreachievements.achievements.MoreAchievementGrid;

@SpirePatch(clz = StatsScreen.class, method = SpirePatch.CLASS)
public class StatsScreenPatch {
    private static MoreAchievementGrid moreAchievements;

    public static MoreAchievementGrid getMoreAchievements() {
        if (moreAchievements == null) {
            moreAchievements = new MoreAchievementGrid();
        }
        return moreAchievements;
    }
}
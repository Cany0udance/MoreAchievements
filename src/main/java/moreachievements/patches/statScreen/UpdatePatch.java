package moreachievements.patches.statScreen;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import moreachievements.MoreAchievements;

@SpirePatch(clz = StatsScreen.class, method = "update")
public class UpdatePatch {
    public static void Postfix(StatsScreen __instance) {
        MoreAchievements.moreAchievementGrid.update();
    }
}
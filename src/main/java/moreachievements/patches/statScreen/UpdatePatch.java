package moreachievements.patches.statScreen;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;

@SpirePatch(clz = StatsScreen.class, method = "update")
public class UpdatePatch {
    public static void Postfix(StatsScreen __instance) {
        StatsScreenPatch.getMoreAchievements().update();
    }
}
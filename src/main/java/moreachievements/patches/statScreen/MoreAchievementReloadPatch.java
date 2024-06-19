package moreachievements.patches.statScreen;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.screens.stats.AchievementItem;
import moreachievements.achievements.MoreAchievementItem;

@SpirePatch(
        clz = AchievementItem.class,
        method = "reloadImg"
)
public class MoreAchievementReloadPatch {
    public MoreAchievementReloadPatch() {
    }

    @SpirePostfixPatch
    public static void Postfix(AchievementItem __instance) {
        if (__instance instanceof MoreAchievementItem) {
            ((MoreAchievementItem)__instance).currentImg = MoreAchievementItem.atlas.findRegion(((MoreAchievementItem)__instance).currentImg.name);
        }

    }
}
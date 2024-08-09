package moreachievements.patches.unlocks;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.monsters.exordium.SlimeBoss;
import moreachievements.util.MoreAchievementUnlocker;

@SpirePatch(
        clz = SlimeBoss.class,
        method = "die"
)
public class Undivided {

    @SpirePostfixPatch
    public static void Postfix(SlimeBoss __instance) {
        MoreAchievementUnlocker.unlockAchievement("UNDIVIDED");
    }
}
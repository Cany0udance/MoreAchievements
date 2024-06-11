package moreachievements.patches.unlocks;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.monsters.city.ShelledParasite;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import moreachievements.util.MoreAchievementUnlocker;

@SpirePatch(
        clz = PlatedArmorPower.class,
        method = "onRemove"
)
public class ShellShock {
    public ShellShock() {

    }

    @SpirePostfixPatch
    public static void Postfix(PlatedArmorPower __instance) {
        if (__instance.owner instanceof ShelledParasite) {
            MoreAchievementUnlocker.unlockAchievement("SHELL_SHOCK");
        }

    }
}
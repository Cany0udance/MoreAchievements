package moreachievements.patches.unlocks;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import moreachievements.util.MoreAchievementUnlocker;

@SpirePatch(clz = EscapeAction.class, method = "<ctor>", paramtypez = {AbstractMonster.class})
public class Mercy {
    private static int cultistsSpared = 0;

    public Mercy() {}

    @SpirePostfixPatch
    public static void Postfix(EscapeAction __instance, AbstractMonster m) {
        if (m instanceof Cultist) {
            ++cultistsSpared;
            if (cultistsSpared == 2) {
                MoreAchievementUnlocker.unlockAchievement("MERCY");
            }
        }
    }
}
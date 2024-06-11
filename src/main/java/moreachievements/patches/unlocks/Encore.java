package moreachievements.patches.unlocks;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.green.GrandFinale;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import moreachievements.util.MoreAchievementUnlocker;
import moreachievements.util.MoreAchievementVariables;

@SpirePatch(
        clz = GrandFinale.class,
        method = "use"
)
public class Encore {
    public Encore() {
    }

    @SpirePostfixPatch
    public static void Postfix(GrandFinale __instance, AbstractPlayer p, AbstractMonster m) {
        MoreAchievementVariables.grandFinalePlays++;
        if (MoreAchievementVariables.grandFinalePlays == 2) {
            MoreAchievementUnlocker.unlockAchievement("ENCORE");
        }
    }
}

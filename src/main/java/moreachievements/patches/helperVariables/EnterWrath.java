package moreachievements.patches.helperVariables;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.WrathStance;
import moreachievements.util.MoreAchievementVariables;

@SpirePatch(
        clz = WrathStance.class,
        method = "onEnterStance"
)
public class EnterWrath {
    public EnterWrath() {
    }

    @SpirePostfixPatch
    public static void Postfix(WrathStance __instance) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p != null && p.chosenClass == AbstractPlayer.PlayerClass.WATCHER) {
            MoreAchievementVariables.wrathEntered = true;
        }

    }
}
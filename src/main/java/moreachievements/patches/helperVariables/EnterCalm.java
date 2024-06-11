package moreachievements.patches.helperVariables;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.CalmStance;
import moreachievements.util.MoreAchievementVariables;

@SpirePatch(
        clz = CalmStance.class,
        method = "onEnterStance"
)
public class EnterCalm {
    public EnterCalm() {
    }

    @SpirePostfixPatch
    public static void Postfix(CalmStance __instance) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p != null && p.chosenClass == AbstractPlayer.PlayerClass.WATCHER) {
            MoreAchievementVariables.calmEntered = true;
        }

    }
}
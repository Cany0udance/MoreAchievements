package moreachievements.patches.unlocks;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.AllCostToHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;
import moreachievements.util.MoreAchievementUnlocker;

@SpirePatch(
        clz = AllCostToHandAction.class,
        method = "update"
)
public class OneForAll {
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(AllCostToHandAction __instance) {
        int initialHandSize = AbstractDungeon.player.hand.size();
        int initialDiscardPileSize = AbstractDungeon.player.discardPile.size();

        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                int cardsRetrieved = AbstractDungeon.player.hand.size() - initialHandSize;
                int cardsRemovedFromDiscard = initialDiscardPileSize - AbstractDungeon.player.discardPile.size();

                if (cardsRetrieved >= 5) {
                    MoreAchievementUnlocker.unlockAchievement("ONE_FOR_ALL");
                }

                this.isDone = true;
            }
        });
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AllCostToHandAction.class, "addToBot");
            return LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
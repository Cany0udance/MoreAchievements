package moreachievements.patches.unlocks;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.watcher.LessonLearnedAction;
import com.megacrit.cardcrawl.monsters.city.BookOfStabbing;
import moreachievements.util.MoreAchievementUnlocker;

@SpirePatch(clz = LessonLearnedAction.class, method = "update")
public class ClassDismissed {
    @SpirePostfixPatch
    public static void Postfix(LessonLearnedAction __instance) {
        if (__instance.isDone && __instance.target != null && __instance.target.id.equals(BookOfStabbing.ID)) {
            if ((__instance.target.isDying || __instance.target.currentHealth <= 0) && !__instance.target.halfDead) {
                MoreAchievementUnlocker.unlockAchievement("CLASS_DISMISSED");
            }
        }
    }
}
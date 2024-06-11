package moreachievements.patches.unlocks;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.Exploder;
import com.megacrit.cardcrawl.powers.TheBombPower;
import moreachievements.util.MoreAchievementUnlocker;

import java.lang.reflect.Field;

@SpirePatch(clz = TheBombPower.class, method = "atEndOfTurn")
public class RoleReversal {
    private static Field damageField;

    static {
        try {
            damageField = TheBombPower.class.getDeclaredField("damage");
            damageField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public RoleReversal() {
    }

    @SpireInsertPatch(rloc = 40)
    public static void Insert(TheBombPower instance, boolean isPlayer) {
        if (instance.amount == 1) {
            try {
                int damage = damageField.getInt(instance);
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (m instanceof Exploder && !m.isDying && !m.halfDead && m.currentHealth > 0) {
                        if (damage >= m.currentHealth) {
                            MoreAchievementUnlocker.unlockAchievement("ROLE_REVERSAL");
                            break;
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
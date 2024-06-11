package moreachievements.patches.unlocks;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.purple.SignatureMove;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Champ;
import moreachievements.util.MoreAchievementUnlocker;

@SpirePatch(clz = SignatureMove.class, method = "use")
public class Overthrown {
    public Overthrown() {
    }

    @SpirePrefixPatch
    public static void Prefix(SignatureMove instance, AbstractPlayer p, AbstractMonster m) {
        if (m != null && m.id.equals(Champ.ID)) {
            BaseMod.logger.info("Champ is the target of Signature Move");
            int damage = instance.damage;
            if (damage >= m.currentHealth) {
                MoreAchievementUnlocker.unlockAchievement("OVERTHROWN");
                BaseMod.logger.info("Achievement unlocked: OVERTHROWN");
            } else {
                BaseMod.logger.info("Signature Move damage is not lethal");
            }
        } else {
            BaseMod.logger.info("Champ is not the target of Signature Move");
        }
    }
}
package moreachievements.patches.unlocks;

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
            int damage = instance.damage;
            if (damage >= m.currentHealth + m.currentBlock) {
                MoreAchievementUnlocker.unlockAchievement("OVERTHROWN");
            }
        }
    }
}
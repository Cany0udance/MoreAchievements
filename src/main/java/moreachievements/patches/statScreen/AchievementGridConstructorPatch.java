package moreachievements.patches.statScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import moreachievements.MoreAchievements;
import moreachievements.achievements.MoreAchievementItem;

@SpirePatch(clz = AchievementGrid.class, method = "<ctor>")
public class AchievementGridConstructorPatch {
    public AchievementGridConstructorPatch() {}

    @SpirePostfixPatch
    public static void Postfix(AchievementGrid instance) {
        MoreAchievementItem.atlas = new TextureAtlas(Gdx.files.internal("moreachievements/images/achievements/MoreAchievements.atlas"));
        loadAchievement(instance, "SHELL_SHOCK", false);
        loadAchievement(instance, "CLASS_DISMISSED", false);
        loadAchievement(instance, "OVERTHROWN", false);
        loadAchievement(instance, "ROLE_REVERSAL", false);
        loadAchievement(instance, "ENCORE", false);
        loadAchievement(instance, "MERCY", false);
        loadAchievement(instance, "COLLECTIONIST", false);
        loadAchievement(instance, "BURDENS", false);
        loadAchievement(instance, "NEW_PERSPECTIVES", false);
        loadAchievement(instance, "BLOODTHIRSTY", false);
        loadAchievement(instance, "SHOWOFF", false);
        loadAchievement(instance, "LIVING_ON_THE_EDGE", false);
        loadAchievement(instance, "SATED", false);
        loadAchievement(instance, "POWERLESS", false);
        loadAchievement(instance, "WORKAROUND", false);
        loadAchievement(instance, "PROTEST", false);
        loadAchievement(instance, "ENERGETIC", false);
        loadAchievement(instance, "NEGATIVE_SYNERGY", false);
        loadAchievement(instance, "BLOOMED", false);
        loadAchievement(instance, "WRATHFUL", false);
        loadAchievement(instance, "ANGER_MANAGEMENT", false);
        loadAchievement(instance, "KEYMASTER", false);
        loadAchievement(instance, "THE_SPIRE_SLEEPS", false);
    }

    private static void loadAchievement(AchievementGrid instance, String id, boolean isHidden) {
        String fullId = MoreAchievements.makeID(id); // Automatically generate the full ID
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(fullId);
        String name = uiStrings.TEXT[0];
        String description = uiStrings.TEXT[1];

        TextureAtlas.AtlasRegion AchievementImageUnlocked = MoreAchievementItem.atlas.findRegion("unlocked/" + id);
        TextureAtlas.AtlasRegion AchievementImageLocked = MoreAchievementItem.atlas.findRegion("locked/" + id);

        instance.items.add(new MoreAchievementItem(name, description, fullId, isHidden, AchievementImageUnlocked, AchievementImageLocked));
    }
}
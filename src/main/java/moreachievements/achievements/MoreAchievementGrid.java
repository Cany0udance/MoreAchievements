package moreachievements.achievements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.stats.AchievementItem;
import moreachievements.MoreAchievements;

import java.util.ArrayList;
import java.util.Iterator;

public class MoreAchievementGrid {
    public ArrayList<MoreAchievementItem> items = new ArrayList<>();
    private static final float SPACING = 200.0F * Settings.scale;
    private static final int ITEMS_PER_ROW = 5;

    public MoreAchievementGrid() {
        MoreAchievementItem.atlas = new TextureAtlas(Gdx.files.internal("moreachievements/images/achievements/MoreAchievements.atlas"));
        loadAchievement("SHELL_SHOCK", false);
        loadAchievement("CLASS_DISMISSED", false);
        loadAchievement("OVERTHROWN", false);
        loadAchievement("ROLE_REVERSAL", false);
        loadAchievement("ENCORE", false);
        loadAchievement("MERCY", false);
        loadAchievement("COLLECTIONIST", false);
        loadAchievement("BURDENS", false);
        loadAchievement("NEW_PERSPECTIVES", false);
        loadAchievement("BLOODTHIRSTY", false);
        loadAchievement("SHOWOFF", false);
        loadAchievement("LIVING_ON_THE_EDGE", false);
        loadAchievement("SATED", false);
        loadAchievement("POWERLESS", false);
        loadAchievement("WORKAROUND", false);
        loadAchievement("PROTEST", false);
        loadAchievement("ENERGETIC", false);
        loadAchievement("NEGATIVE_SYNERGY", false);
        loadAchievement("BLOOMED", false);
        loadAchievement("WRATHFUL", false);
        loadAchievement("ANGER_MANAGEMENT", false);
        loadAchievement("KEYMASTER", false);
        loadAchievement("THE_SPIRE_SLEEPS", false);
    }

    private void loadAchievement(String id, boolean isHidden) {
        String fullId = MoreAchievements.makeID(id);
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(fullId);
        String name = uiStrings.TEXT[0];
        String description = uiStrings.TEXT[1];
        TextureAtlas.AtlasRegion AchievementImageUnlocked = MoreAchievementItem.atlas.findRegion("unlocked/" + id);
        TextureAtlas.AtlasRegion AchievementImageLocked = MoreAchievementItem.atlas.findRegion("locked/" + id);
        items.add(new MoreAchievementItem(name, description, fullId, isHidden, AchievementImageUnlocked, AchievementImageLocked));
    }

    public void render(SpriteBatch sb, float renderY) {
        for (int i = 0; i < items.size(); ++i) {
            items.get(i).render(sb, 560.0F * Settings.scale + (i % ITEMS_PER_ROW) * SPACING, renderY - (i / ITEMS_PER_ROW) * SPACING + 680.0F * Settings.yScale);
        }
    }

    public float calculateHeight() {
        int numRows = (items.size() + ITEMS_PER_ROW - 1) / ITEMS_PER_ROW;
        return numRows * SPACING + 50.0F * Settings.scale;
    }

    public void update() {
        for (MoreAchievementItem item : items) {
            item.update();
        }
    }
}
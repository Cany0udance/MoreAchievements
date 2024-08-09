package moreachievements.achievements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import moreachievements.MoreAchievements;

import java.util.ArrayList;

public class MoreAchievementGrid {
    public ArrayList<MoreAchievementItem> items = new ArrayList<>();
    private static final float SPACING = 200.0F * Settings.scale;
    private static final int ITEMS_PER_ROW = 5;

    public MoreAchievementGrid() {
        MoreAchievementItem.atlas = new TextureAtlas(Gdx.files.internal("moreachievements/images/achievements/MoreAchievements.atlas"));
        loadAchievement("SHELL_SHOCK");
        loadAchievement("CLASS_DISMISSED");
        loadAchievement("OVERTHROWN");
        loadAchievement("ROLE_REVERSAL");
        loadAchievement("UNDIVIDED");
        loadAchievement("ENCORE");
        loadAchievement("MERCY");
        loadAchievement("COLLECTIONIST");
        loadAchievement("BURDENS");
        loadAchievement("NEW_PERSPECTIVES");
        loadAchievement("BLOODTHIRSTY");
        loadAchievement("SHOWOFF");
        loadAchievement("LIVING_ON_THE_EDGE");
        loadAchievement("SATED");
        loadAchievement("POWERLESS");
        loadAchievement("WORKAROUND");
        loadAchievement("PROTEST");
        loadAchievement("ENERGETIC");
        loadAchievement("NEGATIVE_SYNERGY");
        loadAchievement("BLOOMED");
        loadAchievement("ONE_FOR_ALL");
        loadAchievement("WRATHFUL");
        loadAchievement("ANGER_MANAGEMENT");
        loadAchievement("KEYMASTER");
        loadAchievement("THE_SPIRE_SLEEPS");
    }

    private void loadAchievement(String id) {
        String fullId = MoreAchievements.makeID(id);
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(fullId);
        String name = uiStrings.TEXT[0];
        String description = uiStrings.TEXT[1];
        TextureAtlas.AtlasRegion AchievementImageUnlocked = MoreAchievementItem.atlas.findRegion("unlocked/" + id);
        TextureAtlas.AtlasRegion AchievementImageLocked = MoreAchievementItem.atlas.findRegion("locked/" + id);
        items.add(new MoreAchievementItem(name, description, fullId, AchievementImageUnlocked, AchievementImageLocked));
    }

    public void updateAchievementStatus() {
        for (MoreAchievementItem item : items) {
            String achievementKey = item.getKey();
            boolean isUnlocked = UnlockTracker.isAchievementUnlocked(achievementKey);
            item.isUnlocked = isUnlocked;
            item.reloadImg();
        }
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
        updateAchievementStatus();
        for (MoreAchievementItem item : items) {
            item.update();
        }
    }

}
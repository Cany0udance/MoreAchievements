package moreachievements;

import basemod.BaseMod;
import basemod.interfaces.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.MarkOfTheBloom;
import com.megacrit.cardcrawl.relics.RunicPyramid;
import com.megacrit.cardcrawl.relics.SneckoEye;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import moreachievements.achievements.MoreAchievementGrid;
import moreachievements.util.*;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglFileHandle;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static moreachievements.util.MoreAchievementVariables.initializeSaveFields;

@SpireInitializer
public class MoreAchievements implements
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        PostInitializeSubscriber,
        PostDeathSubscriber,
        StartActSubscriber,
        PostDungeonInitializeSubscriber,
        OnStartBattleSubscriber,
        OnPlayerTurnStartSubscriber,
        PostBattleSubscriber {
    public static ModInfo info;
    public static String modID; //Edit your pom.xml to change this
    static { loadModInfo(); }
    private static final String resourcesFolder = checkResourcesPath();
    public static final Logger logger = LogManager.getLogger(modID); //Used to output to the console.
    public static MoreAchievementGrid moreAchievementGrid;

    //This is used to prefix the IDs of various objects like cards and relics,
    //to avoid conflicts between different mods using the same name for things.
    public static String makeID(String id) {
        return modID + ":" + id;
    }

    //This will be called by ModTheSpire because of the @SpireInitializer annotation at the top of the class.
    public static void initialize() {
        new MoreAchievements();
    }

    public MoreAchievements() {
        BaseMod.subscribe(this); //This will make BaseMod trigger all the subscribers at their appropriate times.
        logger.info(modID + " subscribed to BaseMod.");
    }

    @Override
    public void receivePostInitialize() {
        //This loads the image used as an icon in the in-game mods menu.
        Texture badgeTexture = TextureLoader.getTexture(imagePath("badge.png"));
        //Set up the mod information displayed in the in-game mods menu.
        //The information used is taken from your pom.xml file.

        //If you want to set up a config panel, that will be done here.
        //The Mod Badges page has a basic example of this, but setting up config is overall a bit complex.
        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description, null);
        initializeSaveFields();
        moreAchievementGrid = new MoreAchievementGrid();
    }

    @Override
    public void receivePostDungeonInitialize() {
        MoreAchievementVariables.reset();
    }

    @Override
    public void receiveStartAct() {
        if (AbstractDungeon.actNum == 2 && Settings.hasRubyKey && Settings.hasSapphireKey && Settings.hasEmeraldKey) {
            MoreAchievementVariables.exordiumKeys = true;
        }
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        MoreAchievementVariables.grandFinalePlays = 0;
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        MoreAchievementVariables.wrathEntered = false;
        MoreAchievementVariables.calmEntered = false;
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        if ((AbstractDungeon.getCurrRoom()).eliteTrigger) {
            MoreAchievementVariables.elitesDefeated++;
        }

        if (AbstractDungeon.getCurrMapNode().hasEmeraldKey) {
            MoreAchievementVariables.burningElitesDefeated++;
        }

        if (MoreAchievementVariables.wrathEntered == true) {
            MoreAchievementVariables.wrathEnteredFinal = true;
        }

        if (MoreAchievementVariables.calmEntered == true) {
            MoreAchievementVariables.calmEnteredFinal = true;
        }
    }

    @Override
    public void receivePostDeath() {

        // Act 3 wins
        if (AbstractDungeon.player.currentHealth > 0) {

            CardGroup deck = AbstractDungeon.player.masterDeck;
            int curseCount = (int)deck.group.stream().filter((card) -> {
                return card.type == AbstractCard.CardType.CURSE;
            }).count();
            if (curseCount >= 5) {
                MoreAchievementUnlocker.unlockAchievement("BURDENS");
            }

            int powerCount = (int) deck.group.stream().filter(card -> card.type == AbstractCard.CardType.POWER).count();
            if (powerCount == 0) {
                MoreAchievementUnlocker.unlockAchievement("POWERLESS");
            }

            int attackCount = (int) deck.group.stream().filter(card -> card.type == AbstractCard.CardType.ATTACK).count();
            if (attackCount == 0) {
                MoreAchievementUnlocker.unlockAchievement("WORKAROUND");
            }

            boolean allStrikes = true;

            for (AbstractCard card : deck.group) {
                if (card.type == AbstractCard.CardType.ATTACK) {
                    if (!card.hasTag(AbstractCard.CardTags.STRIKE)) {
                        allStrikes = false;
                        break; // Exit as soon as a non-Strike attack is found
                    }
                }
            }

            if (allStrikes) {
                MoreAchievementUnlocker.unlockAchievement("PROTEST");
            }

            if (AbstractDungeon.player.masterDeck.size() >= 50) {
                MoreAchievementUnlocker.unlockAchievement("COLLECTIONIST");
            }

            if (MoreAchievementVariables.elitesDefeated >= 12) {
                MoreAchievementUnlocker.unlockAchievement("BLOODTHIRSTY");
            }

            if (AbstractDungeon.player.energy.energyMaster >= 6) {
                MoreAchievementUnlocker.unlockAchievement("ENERGETIC");
            }

            if (AbstractDungeon.player.maxHealth <= 24) {
                MoreAchievementUnlocker.unlockAchievement("LIVING_ON_THE_EDGE");
            }

            if (AbstractDungeon.player.maxHealth >= 150) {
                MoreAchievementUnlocker.unlockAchievement("SATED");
            }

            if (MoreAchievementVariables.burningElitesDefeated >= 3) {
                MoreAchievementUnlocker.unlockAchievement("SHOWOFF");
            }

            if (AbstractDungeon.player.hasRelic(RunicPyramid.ID) && AbstractDungeon.player.hasRelic(SneckoEye.ID)) {
                MoreAchievementUnlocker.unlockAchievement("NEGATIVE_SYNERGY");
            }

            if (AbstractDungeon.player.hasRelic(MarkOfTheBloom.ID)) {
                MoreAchievementUnlocker.unlockAchievement("BLOOMED");
            }

            AbstractPlayer p = AbstractDungeon.player;
            int greenCount = 0;
            int redCount = 0;
            int blueCount = 0;
            int purpleCount = 0;
            int colorlessCount = 0;
            Iterator var7 = p.masterDeck.group.iterator();

            while(var7.hasNext()) {
                AbstractCard c = (AbstractCard)var7.next();
                switch (c.color) {
                    case GREEN:
                        ++greenCount;
                        break;
                    case RED:
                        ++redCount;
                        break;
                    case BLUE:
                        ++blueCount;
                        break;
                    case PURPLE:
                        ++purpleCount;
                        break;
                    case COLORLESS:
                        ++colorlessCount;
                }
            }

            if (greenCount >= 3 && redCount >= 3 && blueCount >= 3 && purpleCount >= 3 && colorlessCount >= 3) {
                MoreAchievementUnlocker.unlockAchievement("NEW_PERSPECTIVES");
            }

            if (AbstractDungeon.player.chosenClass == AbstractPlayer.PlayerClass.WATCHER && !MoreAchievementVariables.wrathEnteredFinal) {
                MoreAchievementUnlocker.unlockAchievement("ANGER_MANAGEMENT");
            }

            if (AbstractDungeon.player.chosenClass == AbstractPlayer.PlayerClass.WATCHER && !MoreAchievementVariables.calmEnteredFinal) {
                MoreAchievementUnlocker.unlockAchievement("WRATHFUL");
            }

            // Act 4 wins
            if (AbstractDungeon.actNum == 4) {

                if (AbstractDungeon.ascensionLevel == 20) {
                    MoreAchievementUnlocker.unlockAchievement("THE_SPIRE_SLEEPS");
                }

                if (MoreAchievementVariables.exordiumKeys == true) {
                    MoreAchievementUnlocker.unlockAchievement("KEYMASTER");
                }

            }

        }
    }

    /*----------Localization----------*/

    //This is used to load the appropriate localization files based on language.
    private static String getLangString()
    {
        return Settings.language.name().toLowerCase();
    }
    private static final String defaultLanguage = "eng";

    public static final Map<String, KeywordInfo> keywords = new HashMap<>();

    @Override
    public void receiveEditStrings() {
        /*
            First, load the default localization.
            Then, if the current language is different, attempt to load localization for that language.
            This results in the default localization being used for anything that might be missing.
            The same process is used to load keywords slightly below.
        */
        loadLocalization(defaultLanguage); //no exception catching for default localization; you better have at least one that works.
        if (!defaultLanguage.equals(getLangString())) {
            try {
                loadLocalization(getLangString());
            }
            catch (GdxRuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadLocalization(String lang) {
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                localizationPath(lang, "PowerStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class,
                localizationPath(lang, "UIStrings.json"));
    }

    @Override
    public void receiveEditKeywords()
    {
        Gson gson = new Gson();
        String json = Gdx.files.internal(localizationPath(defaultLanguage, "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        KeywordInfo[] keywords = gson.fromJson(json, KeywordInfo[].class);
        for (KeywordInfo keyword : keywords) {
            keyword.prep();
            registerKeyword(keyword);
        }

        if (!defaultLanguage.equals(getLangString())) {
            try
            {
                json = Gdx.files.internal(localizationPath(getLangString(), "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
                keywords = gson.fromJson(json, KeywordInfo[].class);
                for (KeywordInfo keyword : keywords) {
                    keyword.prep();
                    registerKeyword(keyword);
                }
            }
            catch (Exception e)
            {
                logger.warn(modID + " does not support " + getLangString() + " keywords.");
            }
        }
    }

    private void registerKeyword(KeywordInfo info) {
        BaseMod.addKeyword(modID.toLowerCase(), info.PROPER_NAME, info.NAMES, info.DESCRIPTION);
        if (!info.ID.isEmpty())
        {
            keywords.put(info.ID, info);
        }
    }

    //These methods are used to generate the correct filepaths to various parts of the resources folder.
    public static String localizationPath(String lang, String file) {
        return resourcesFolder + "/localization/" + lang + "/" + file;
    }

    public static String imagePath(String file) {
        return resourcesFolder + "/images/" + file;
    }
    public static String characterPath(String file) {
        return resourcesFolder + "/images/character/" + file;
    }
    public static String powerPath(String file) {
        return resourcesFolder + "/images/powers/" + file;
    }
    public static String relicPath(String file) {
        return resourcesFolder + "/images/relics/" + file;
    }

    /**
     * Checks the expected resources path based on the package name.
     */
    private static String checkResourcesPath() {
        String name = MoreAchievements.class.getName(); //getPackage can be iffy with patching, so class name is used instead.
        int separator = name.indexOf('.');
        if (separator > 0)
            name = name.substring(0, separator);

        FileHandle resources = new LwjglFileHandle(name, Files.FileType.Internal);
        if (resources.child("images").exists() && resources.child("localization").exists()) {
            return name;
        }

        throw new RuntimeException("\n\tFailed to find resources folder; expected it to be named \"" + name + "\"." +
                " Either make sure the folder under resources has the same name as your mod's package, or change the line\n" +
                "\t\"private static final String resourcesFolder = checkResourcesPath();\"\n" +
                "\tat the top of the " + MoreAchievements.class.getSimpleName() + " java file.");
    }

    /**
     * This determines the mod's ID based on information stored by ModTheSpire.
     */
    private static void loadModInfo() {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo)->{
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(), Collections.emptySet());
            return initializers.contains(MoreAchievements.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
            modID = info.ID;
        }
        else {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }
    }
}

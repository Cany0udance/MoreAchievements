package moreachievements.util;

import basemod.BaseMod;
import basemod.abstracts.CustomSavable;

public class MoreAchievementVariables {
    public static int elitesDefeated = 0;
    public static int burningElitesDefeated = 0;
    public static int grandFinalePlays = 0;
    public static boolean exordiumKeys = false;
    public static boolean calmEntered = false;
    public static boolean calmEnteredFinal = false;
    public static boolean wrathEntered = false;
    public static boolean wrathEnteredFinal = false;

    private static final String INT_SAVE_ID = "more_achievements_int";
    private static final String BOOL_SAVE_ID = "more_achievements_bool";

    public static void initializeSaveFields() {
        BaseMod.addSaveField(INT_SAVE_ID, new CustomSavable<int[]>() {
            @Override
            public int[] onSave() {
                return new int[]{elitesDefeated, burningElitesDefeated};
            }
            @Override
            public void onLoad(int[] ints) {
                if (ints != null) {
                    elitesDefeated = ints[0];
                    burningElitesDefeated = ints[1];
                }
            }
        });

        BaseMod.addSaveField(BOOL_SAVE_ID, new CustomSavable<boolean[]>() {
            @Override
            public boolean[] onSave() {
                return new boolean[]{exordiumKeys, calmEnteredFinal, wrathEnteredFinal};
            }
            @Override
            public void onLoad(boolean[] bools) {
                if (bools != null) {
                    exordiumKeys = bools[0];
                    calmEnteredFinal = bools[1];
                    wrathEnteredFinal = bools[2];
                }
            }
        });
    }

    public static void reset() {
        elitesDefeated = 0;
        burningElitesDefeated = 0;
        exordiumKeys = false;
        calmEnteredFinal = false;
        wrathEnteredFinal = false;
    }
}
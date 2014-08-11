package iguanaman.iguanatweakstconstruct.reference;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import iguanaman.iguanatweakstconstruct.leveling.RandomBonuses;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.util.*;

public class Config {
    private Configuration configfile;

	// leveling
    public static int maxToolLevel;
    public static int xpRequiredToolsPercentage;
    public static int xpRequiredWeaponsPercentage;
    public static float xpPerLevelMultiplier;
    public static boolean showTooltipXP;
    public static boolean showMinimalTooltipXP;
	public static boolean detailedXpTooltip;
	public static boolean toolLeveling;
	public static boolean toolLevelingExtraModifiers;
    public static int[] toolModifiersAtLevels;
	public static boolean toolLevelingRandomBonuses;
    public static boolean randomBonusesAreUseful;
    public static boolean randomBonusesAreRandom;

    // random bonuses deactivation
    public static Set<RandomBonuses.Modifier> deactivatedModifiers = new HashSet<RandomBonuses.Modifier>();

	// pick boost
	public static boolean pickaxeBoostRequired;
	public static boolean mobHeadPickaxeBoost;
    public static boolean mobHeadRequiresModifier;
	public static boolean levelingPickaxeBoost;
	public static int levelingPickaxeBoostXpPercentage;
    public static float xpPerBoostLevelMultiplier;

    // Harvest Leveling
    public static boolean nerfBronze;
    public static int durabilityPercentage;
    public static int miningSpeedPercentage;

    // part replacement
    public static boolean removeMobHeadOnPartReplacement;
    public static int partReplacementXpPenality;
    public static int partReplacementBoostXpPenality;

	// heads
	public static int baseHeadDropChance;
	public static int beheadingHeadDropChance;

    // tweaks
    public static boolean nerfVanillaTools;
    public static boolean nerfVanillaHoes;
    public static boolean nerfVanillaSwords;
    public static boolean nerfVanillaBows;
    public static boolean removeFlintDrop;
    public static boolean addFlintRecipe;
    public static int recipeGravelPerFlint;
    public static boolean disableStoneTools;
    public static boolean castsBurnMaterial;
    public static boolean easyToolRepair;
    public static boolean allowPartReuse;
    public static boolean removeStoneTorchRecipe;
    public static boolean moreExpensiveSilkyCloth;
    public static boolean moreExpensiveSilkyJewel;
    public static boolean moreModifiersForFlux;
    public static int maxToolRepairs;
    //public static float repairAmountMultiplier;

    // allowed tools that should not be nerfed
    public static List<String> allowedTools = new LinkedList<String>();
    public static Set<String> allowedModTools = new HashSet<String>();

    // debug
    public static boolean showDebugXP;
	public static boolean logHarvestLevelChanges;
	public static boolean logMiningLevelChanges;
    public static boolean logToolMaterialChanges;
    public static boolean logBonusExtraChance;


    public void init(File file) {
        configfile = new Configuration(file);
        configfile.load();

        sync();
    }

    public void sync()
    {
        final String CATEGORY_Leveling = "ToolLeveling";
        final String CATEGORY_Bonuses = "RandomBonuses";
        final String CATEGORY_PickLeveling = "PickLeveling";
        final String CATEGORY_HarvestLevels = "HarvestLevelTweaks";
        final String CATEGORY_PartReplacement = "PartReplacement";
        final String CATEGORY_Heads = "MobHeads";
        final String CATEGORY_Tweaks = "Tweaks";
        final String CATEGORY_AllowedTools = "AllowedTools";
        final String CATEGORY_Debug = "Debug";

		/** Leveling **/
		configfile.setCategoryComment(CATEGORY_Leveling, "Leveling Module: Setup the leveling system how you like it");

        // modifying leveling behaviour
        xpRequiredToolsPercentage   = configfile.getInt("xpRequiredToolsPercentage", CATEGORY_Leveling, 100, 1, 999, "Change the XP required to level up tools in % (higher = more xp needed)");
        xpRequiredWeaponsPercentage = configfile.getInt("xpRequiredWeaponsPercentage", CATEGORY_Leveling, 100, 1, 999, "Change the XP required to level up weapons in % (higher = more xp needed)");
        xpPerLevelMultiplier        = configfile.getFloat("xpPerLevelMultiplier", CATEGORY_Leveling, 1.15f, 1.0f, 9.99f, "Exponential multiplier for required xp per level");

        // tooltip things
        showTooltipXP        = configfile.getBoolean("showTooltipXP", CATEGORY_Leveling, true, "Current XP is shown when hovering over a tool");
        detailedXpTooltip    = configfile.getBoolean("detailedXpTooltip", CATEGORY_Leveling, true, "XP tooltip shows numbers, in addition to percentage");
        showMinimalTooltipXP = configfile.getBoolean("showMinimalTooltipXP", CATEGORY_Leveling, false, "Current XP% is shown after the level");

        // levelup behaviour
        maxToolLevel               = configfile.getInt("maxToolLevel", CATEGORY_Leveling, 6, 1, 99, "");
        toolLeveling               = configfile.getBoolean("toolLeveling", CATEGORY_Leveling, true, "Can your skill with tools 'level up' as you use them?");
        toolLevelingExtraModifiers = configfile.getBoolean("ExtraModifiers", CATEGORY_Leveling, true, "Removes modifiers on new tools and gives them through leveling (requires 'toolLeveling=true')");
		toolLevelingRandomBonuses  = configfile.getBoolean("RandomBonuses", CATEGORY_Leveling, true, "Gives a random bonus every level, if false and levelling is on modifiers are given at levels 2 and 4 (requires 'toolLeveling=true')");
        toolModifiersAtLevels      = configfile.get(CATEGORY_Leveling, "ModifiersAtLevels", new int[]{2,4,6}, "Adds an extra modifier on these levleups if 'toolLevelingExtraModifiers' is enabled").getIntList();
        randomBonusesAreUseful     = configfile.getBoolean("UsefulBonuses", CATEGORY_Leveling, true, "Disables less-useful modifiers on levelups. Like a sword with silktouch, or a pickaxe with beheading.");
        randomBonusesAreRandom     = configfile.getBoolean("CompletelyRandomBonuses", CATEGORY_Leveling, false, "Each modifier is equally likely on levelup. Disables useful bonuses.");

        /** Random Bonuses **/
        configfile.setCategoryComment(CATEGORY_Bonuses, "Leveling Module: Allows to completely deactivate specific modifiers on levelup.");

        for(RandomBonuses.Modifier mod : RandomBonuses.Modifier.values()) {
            // we use this way of obtaining the values because it doesn't create the empty lines and comments
            // otherwise this blows up the config. a lot.
            Property allowed = configfile.get(CATEGORY_Bonuses, String.format("allow%s", mod.toString()), true);
            if(!allowed.getBoolean())
                deactivatedModifiers.add(mod);
        }

        /** Pickaxe Boosting **/
        configfile.setCategoryComment(CATEGORY_PickLeveling, "Leveling Module: Allows pickaxes to gain a mining level with enough XP. Should be used with the HarvestLevel Module.");

        // pick boosting behaviour
        pickaxeBoostRequired    = configfile.getBoolean("pickaxeBoostRequired", CATEGORY_PickLeveling, true, "Every Pickaxes Mining Level is reduced by 1 and needs a mining levelup (separate from tool level) or, if enabled, a mob head modifier to advance");
        levelingPickaxeBoost    = configfile.getBoolean("allowLevelingBoost", CATEGORY_PickLeveling, true, "Pickaxes gain Mining Xp. A pickaxes mining level can be boosted through gaining XP. Should be used with pickaxeBoostRequired, otherwise tools will be able to mine higher than normally.");
        mobHeadPickaxeBoost     = configfile.getBoolean("addMobHeadBoost", CATEGORY_PickLeveling, true, "Mob heads can be used to boost a pickaxe's mining xp (REQUIRES allowLevelBoost)");
        mobHeadRequiresModifier = configfile.getBoolean("mobHeadBoostNeedsModifier", CATEGORY_PickLeveling, false, "Mob head boosting requires a free modifier");

        levelingPickaxeBoostXpPercentage = configfile.getInt("xpRequiredPickBoostPercentage", CATEGORY_PickLeveling, 100, 1, 999, "Change the percentage of XP required to boost a pick (i.e. 200 means 2x normal boost xp required)");
        xpPerBoostLevelMultiplier        = configfile.getFloat("xpPerBoostLevelMultiplier", CATEGORY_Leveling, 1.12f, 1.0f, 9.99f, "Exponential multiplier for required boost xp per level");

        /** HarvestLevel Module **/
        configfile.setCategoryComment(CATEGORY_HarvestLevels, "Harvest Level Tweak Module: Introduces a slower mining level progression.");

        // bronze levels
        nerfBronze = configfile.getBoolean("nerfBronze", CATEGORY_HarvestLevels, false, "Reduces the mining level of bronze by 1. This means bronze can not be used to harvest obsidian. ATTENTION: ONLY USE IF YOU HAVE A WAY OF GETTING STEEL (or something with equivalent mining level)");

        // Tool durability/speed changes
        durabilityPercentage  = configfile.getInt("durabilityPercentage", CATEGORY_HarvestLevels, 80, 1, 999, "Change durability of all tool materials (in percent)");
        miningSpeedPercentage = configfile.getInt("miningSpeedPercentage", CATEGORY_HarvestLevels, 100, 1, 999, "Change mining speed of all tool materials (in percent)");

        /** PartReplacement Module **/
        removeMobHeadOnPartReplacement = configfile.getBoolean("removeMobHead", CATEGORY_PartReplacement, true, "Removes the Mob Head Modifier on Tool-Head replacement, allowing it to be reapplied. Sholud be used with PickBoostXpPenality.");
        partReplacementXpPenality      = configfile.getInt("XpPenality", CATEGORY_PartReplacement, 0, 0, 100, "How much of the current XP% shall be removed when replacing parts (So if you had 50%, and penality is 10% it'll remove 5% xp, resulting in 45%). Does not remove Skill Levels.");
        partReplacementBoostXpPenality = configfile.getInt("PickBoostXpPenality", CATEGORY_PartReplacement, 5, 0, 100, "How much of the current XP% to the next mining level shall be removed when replacing the pickaxe head. Useful to remove the mining level boost on part replacement.");

        /** MobHeads **/
        configfile.setCategoryComment(CATEGORY_Heads, "Mob Head Module: Adds additional Mob heads and drops");

        // drop behaviour
        baseHeadDropChance      = configfile.getInt("baseDropChange", CATEGORY_Heads, 5, 1, 100, "Base percentage for a head to drop");
        beheadingHeadDropChance = configfile.getInt("beheadingDropChange", CATEGORY_Heads, 2, 1, 100, "Percentage added to base percentage per level of Beheading modifier");


        /** Vanilla/TConstruct Tweaks **/
        configfile.setCategoryComment(CATEGORY_Tweaks, "Tweak Module: Tweaks to vanilla Minecraft and Tinker's Construct");

        nerfVanillaTools  = configfile.getBoolean("disableRegularTools", CATEGORY_Tweaks, true, "Makes all non-TConstruct tools mine nothing");
        nerfVanillaHoes   = configfile.getBoolean("disableRegularHoes", CATEGORY_Tweaks, false, "Makes all non-TConstruct hoes to not be able to hoe ground. Use the Mattock.");
        nerfVanillaSwords = configfile.getBoolean("disableRegularSwords", CATEGORY_Tweaks, false, "Makes all non-TConstruct swords useless. Like whacking enemies with a stick.");
        nerfVanillaBows   = configfile.getBoolean("disableRegularBows", CATEGORY_Tweaks, false, "Makes all non-TConstruct bows useless. You suddenly forgot how to use a bow.");

        // gravel/flint tweaks
        removeFlintDrop = configfile.getBoolean("removeFlintDrop", CATEGORY_Tweaks, true, "Removes the random chance of getting flint from gravel");
        addFlintRecipe = configfile.getBoolean("addFlintRecipe", CATEGORY_Tweaks, true, "Adds a shapeless recipe to get flint from gravel");
        recipeGravelPerFlint = configfile.getInt("gravelPerFlint", CATEGORY_Tweaks, 3, 1, 9, "How many gravel are required to craft one Flint");

        // ticon tweaks
        disableStoneTools = configfile.getBoolean("disableStoneTools", CATEGORY_Tweaks, true, "Stone Tools can only be used to create casts, but no tools");
        castsBurnMaterial = configfile.getBoolean("castingBurnsMaterial", CATEGORY_Tweaks, true, "Creating a metal cast burns up the material that was used to create it");
        easyToolRepair    = configfile.getBoolean("easyToolRepair", CATEGORY_Tweaks, true, "Allows to repair your tool in a crafting grid, without tool station");
        allowPartReuse    = configfile.getBoolean("allowPartReuse", CATEGORY_Tweaks, true, "Allows toolparts to be used as material in the Part Builder. Like, turn a Pick head into a Shovel head.!");

        // stuff
        removeStoneTorchRecipe  = configfile.getBoolean("removeStoneTorchRecipe", CATEGORY_Tweaks, false, "Removes the recipe for Tinker's Construct's stone torch");
        moreExpensiveSilkyCloth = configfile.getBoolean("moreExpensiveSilkyCloth", CATEGORY_Tweaks, true, "Silky Cloth needs gold ingots, instead of nuggets");
        moreExpensiveSilkyJewel = configfile.getBoolean("moreExpensiveSilkyJewel", CATEGORY_Tweaks, false, "Silky Jewel needs an emerald block, instead of one emerald");
        moreModifiersForFlux    = configfile.getBoolean("moreModifiersForFlux", CATEGORY_Tweaks, true, "Flux modifier requires 2 Modifiers. Because that stuff is broken.");

        // repair
        maxToolRepairs = configfile.getInt("repairsLimit", CATEGORY_Tweaks, -1, -1, 999, "Limits the amount how often a tool can be repaired. -1 means unlimited repairs, like normally.");
        //repairAmountMultiplier = configfile.getFloat("repairAmountMultiplier", CATEGORY_Tweaks, 1.0f, 0.01f, 9.99f, "A factor that is multiplied onto the amount a tool is repaired. (0.5 = half durability restored per repair, 2.0 = twice as much durability restored per repair)");

        /** Allowed tools for nerfed vanilla tools **/
        configfile.setCategoryComment(CATEGORY_AllowedTools, "Tweak Module: This category allows you to specify which tools ARE STILL USABLE if the option to disable non-TConstsruct tools is enabled.");
        {
            String[] axes =    configfile.getStringList("axes", CATEGORY_AllowedTools, defaultAllowedAxes, "Axes that shall remain useful");
            String[] picks =   configfile.getStringList("pickaxes", CATEGORY_AllowedTools, defaultAllowedPicks, "Pickaxes that shall remain useful");
            String[] shovels = configfile.getStringList("shovels", CATEGORY_AllowedTools, defaultAllowedShovel, "Shovels that shall remain useful");
            String[] swords =  configfile.getStringList("swords", CATEGORY_AllowedTools, defaultAllowedSwords, "Swords that shall remain useful");
            String[] bows   =  configfile.getStringList("bows", CATEGORY_AllowedTools, defaultAllowedBows, "bows that shall remain useful");
            String[] hoes =    configfile.getStringList("hoes", CATEGORY_AllowedTools, defaultAllowedHoes, "Hoes that shall remain useful");
            String[] other =   configfile.getStringList("unspecified", CATEGORY_AllowedTools, defaultAllowedOther, "Other tools. I'll be honest, the category doesn't matter, they're just for readability :P");

            allowedModTools.addAll(Arrays.asList(configfile.getStringList("mods", CATEGORY_AllowedTools, defaultAllowMod, "Here you can enter a mod-id to whitelist ALL itesm of this mod.")));

            allowedTools.addAll(Arrays.asList(picks));
            allowedTools.addAll(Arrays.asList(axes));
            allowedTools.addAll(Arrays.asList(shovels));
            allowedTools.addAll(Arrays.asList(swords));
            allowedTools.addAll(Arrays.asList(bows));
            allowedTools.addAll(Arrays.asList(hoes));
            allowedTools.addAll(Arrays.asList(other));
        }


        /** Debug **/
        configfile.setCategoryComment(CATEGORY_Debug, "Stuff to give you/me more information");

        showDebugXP = configfile.getBoolean("showDebugXP", CATEGORY_Debug, false, "Current Tool/Pick XP is shown as debug (F3) text");

        logHarvestLevelChanges = configfile.getBoolean("logBlockHarvestLevelChange", CATEGORY_Debug, true, "Logs when the harvest level of a block is changed");
        logMiningLevelChanges  = configfile.getBoolean("logToolMiningLevelChange", CATEGORY_Debug, true, "Logs when the mining level of a (non-tinker) tool is changed");
        logToolMaterialChanges = configfile.getBoolean("logTinkerMaterialChange", CATEGORY_Debug, true, "Logs when the mining level of a tinkers tool material is changed");
        logBonusExtraChance    = configfile.getBoolean("logBonusExtraChance", CATEGORY_Debug, true, "Logs how much the extra-chance from doing stuff you had when getting a random bonus on levelup.");


		configfile.save();
	}

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if(event.modID.equals(Reference.MOD_ID))
            sync();
    }


    private static String[] defaultAllowedPicks = new String[]{"Botania:terraPick", "Botania:glassPick", "Steamcraft:steamDrill"};
    private static String[] defaultAllowedAxes = new String[]{"Steamcraft:steamAxe"};
    private static String[] defaultAllowedShovel = new String[]{"Steamcraft:steamShovel"};
    private static String[] defaultAllowedHoes = new String[]{};
    private static String[] defaultAllowedSwords = new String[]{"Botania:terraSword", "Botania:enderDagger"};
    private static String[] defaultAllowedBows = new String[]{};
    private static String[] defaultAllowedOther = new String[]{"ThermalExpansion:tool.battleWrenchInvar", "ThermalExpansion:tool.sickleInvar"};
    private static String[] defaultAllowMod = new String[]{"RedstoneArsenal", "ExtraUtilities", "witchery", "AWWayofTime"};
}

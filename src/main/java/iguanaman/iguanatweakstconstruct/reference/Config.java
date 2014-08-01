package iguanaman.iguanatweakstconstruct.reference;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Config {
    private Configuration configfile;

	// leveling
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
    //public static boolean randomBonusesAreUseful;
    public static boolean randomBonusesAreRandom;

	// pick boost
	public static boolean pickaxeBoostRequired;
	public static boolean mobHeadPickaxeBoost;
    public static boolean mobHeadRequiresModifier;
	public static boolean levelingPickaxeBoost;
	public static int levelingPickaxeBoostXpPercentage;

    // Harvest Leveling
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
    public static boolean removeFlintDrop;
    public static boolean addFlintRecipe;
    public static int recipeGravelPerFlint;
    public static boolean disableStoneTools;
    public static boolean removeStoneTorchRecipe;
    public static boolean toolsNeverDespawn;

    // debug
    public static boolean showDebugXP;
	public static boolean logHarvestLevelChanges;
	public static boolean logMiningLevelChanges;
    public static boolean logToolMaterialChanges;


    public void init(File file) {
        configfile = new Configuration(file);
        configfile.load();

        sync();
    }

    public void sync()
    {
        final String CATEGORY_Leveling = "ToolLeveling";
        final String CATEGORY_PickLeveling = "PickLeveling";
        final String CATEGORY_HarvestLevels = "HarvestLevelTweaks";
        final String CATEGORY_PartReplacement = "PartReplacement";
        final String CATEGORY_Heads = "MobHeads";
        final String CATEGORY_Tweaks = "Tweaks";
        final String CATEGORY_Debug = "Debug";

		/** Leveling **/
		configfile.setCategoryComment(CATEGORY_Leveling, "Leveling Module: Setup the leveling system how you like it");

        // modifying leveling behaviour
        xpRequiredToolsPercentage   = configfile.getInt("xpRequiredToolsPercentage", CATEGORY_Leveling, 100, 1, 999, "Change the XP required to level up tools in % (higher = more xp needed)");
        xpRequiredWeaponsPercentage = configfile.getInt("xpRequiredWeaponsPercentage", CATEGORY_Leveling, 100, 1, 999, "Change the XP required to level up weapons in % (higher = more xp needed)");
        xpPerLevelMultiplier        = configfile.getFloat("xpPerLevelMultiplier", CATEGORY_Leveling, 1.15f, 0.01f, 9.99f, "Exponential multiplier for required xp per level");

        // tooltip things
        showTooltipXP        = configfile.getBoolean("showTooltipXP", CATEGORY_Leveling, true, "Current XP is shown when hovering over a tool");
        detailedXpTooltip    = configfile.getBoolean("detailedXpTooltip", CATEGORY_Leveling, true, "XP tooltip shows numbers, in addition to percentage");
        showMinimalTooltipXP = configfile.getBoolean("showMinimalTooltipXP", CATEGORY_Leveling, false, "Current XP% is shown after the level");

        // levelup behaviour
        toolLeveling               = configfile.getBoolean("toolLeveling", CATEGORY_Leveling, true, "Can your skill with tools 'level up' as you use them?");
        toolLevelingExtraModifiers = configfile.getBoolean("ExtraModifiers", CATEGORY_Leveling, true, "Removes modifiers on new tools and gives them through leveling (requires 'toolLeveling=true')");
		toolLevelingRandomBonuses  = configfile.getBoolean("RandomBonuses", CATEGORY_Leveling, true, "Gives a random bonus every level, if false and levelling is on modifiers are given at levels 2 and 4 (requires 'toolLeveling=true')");
        toolModifiersAtLevels      = configfile.get(CATEGORY_Leveling, "ModifiersAtLevels", new int[]{2,4,6}, "Adds an extra modifier on these levleups if 'toolLevelingExtraModifiers' is enabled").getIntList();
        //randomBonusesAreUseful     = configfile.getBoolean("UsefulBonuses", CATEGORY_Leveling, true, "Increases chance of getting a useful modifier for the tool drastically (compared to completely random)");
        randomBonusesAreRandom     = configfile.getBoolean("CompletelyRandomBonuses", CATEGORY_Leveling, false, "Each modifier is equally likely on levelup. Disables useful bonuses.");


        /** Pickaxe Boosting **/
        configfile.setCategoryComment(CATEGORY_PickLeveling, "Leveling Module: Allows pickaxes to gain a mining level with enough XP. Should be used with the HarvestLevel Module.");

        // pick boosting behaviour
        pickaxeBoostRequired    = configfile.getBoolean("pickaxeBoostRequired", CATEGORY_PickLeveling, false, "Every Pickaxes Mining Level is reduced by 1 and needs a mining levelup (separate from tool level) or, if enabled, a mob head modifier to advance");
        levelingPickaxeBoost    = configfile.getBoolean("allowLevelingBoost", CATEGORY_PickLeveling, true, "Pickaxes gain Mining Xp. A pickaxes mining level can be boosted through gaining XP");
        mobHeadPickaxeBoost     = configfile.getBoolean("addMobHeadBoost", CATEGORY_PickLeveling, true, "Mob heads can be used to boost a pickaxe's mining xp (REQUIRES allowLevelBoost)");
        mobHeadRequiresModifier = configfile.getBoolean("mobHeadBoostNeedsModifier", CATEGORY_PickLeveling, false, "Mob head boosting requires a free modifier");

        levelingPickaxeBoostXpPercentage = configfile.getInt("xpRequiredPickBoostPercentage", CATEGORY_PickLeveling, 100, 1, 999, "Change the percentage of XP required to boost a pick (i.e. 200 means 2x normal boost xp required)");

        /** HarvestLevel Module **/
        configfile.setCategoryComment(CATEGORY_HarvestLevels, "Harvest Level Tweak Module: Introduces a slower mining level progression.");

        // Tool durability/speed changes
        durabilityPercentage  = configfile.getInt("durabilityPercentage", CATEGORY_HarvestLevels, 80, 1, 999, "Change durability of all tool materials (in percent)");
        miningSpeedPercentage = configfile.getInt("miningSpeedPercentage", CATEGORY_HarvestLevels, 100, 1, 999, "Change mining speed of all tool materials (in percent)");

        /** PartReplacement Module **/
        removeMobHeadOnPartReplacement = configfile.getBoolean("removeMobHead", CATEGORY_PartReplacement, false, "Removes the Mob Head Modifier on Tool-Head replacement, allowing it to be reapplied. Sholud be used with PickBoostXpPenality.");
        partReplacementXpPenality      = configfile.getInt("XpPenality", CATEGORY_PartReplacement, 0, 0, 100, "How much of the current XP% shall be removed when replacing parts (So if you had 50%, and penality is 10% it'll remove 5% xp, resulting in 45%). Does not remove Skill Levels.");
        partReplacementBoostXpPenality = configfile.getInt("PickBoostXpPenality", CATEGORY_PartReplacement, 5, 0, 100, "How much of the current XP% to the next mining level shall be removed when replacing the pickaxe head. Useful to remove the mining level boost on part replacement.");

        /** MobHeads **/
        // todo: implement
        configfile.setCategoryComment(CATEGORY_Heads, "Mob Head Module: Adds additional Mob heads and drops");

        // drop behaviour
        baseHeadDropChance      = configfile.getInt("baseDropChange", CATEGORY_Heads, 5, 1, 100, "Base percentage for a head to drop");
        beheadingHeadDropChance = configfile.getInt("beheadingDropChange", CATEGORY_Heads, 2, 1, 100, "Percentage added to base percentage per level of Beheading modifier");


        /** Vanilla/TConstruct Tweaks **/
        configfile.setCategoryComment(CATEGORY_Tweaks, "Tweak Module: Tweaks to vanilla Minecraft and Tinker's Construct");

        // gravel/flint tweaks
        removeFlintDrop = configfile.getBoolean("removeFlintDrop", CATEGORY_Tweaks, true, "Removes the random chance of getting flint from gravel");
        addFlintRecipe = configfile.getBoolean("addFlintRecipe", CATEGORY_Tweaks, true, "Adds a shapeless recipe to get flint from gravel");
        recipeGravelPerFlint = configfile.getInt("gravelPerFlint", CATEGORY_Tweaks, 4, 1, 9, "How many gravel are required to craft one Flint");

        // ticon tweaks
        disableStoneTools = configfile.getBoolean("disablestoneTools", CATEGORY_Tweaks, true, "Stone Tools can only be used to create casts, but no tools");

        // stuff
        removeStoneTorchRecipe = configfile.getBoolean("removeStoneTorchRecipe", CATEGORY_Tweaks, false, "Removes the recipe for Tinker's Construct's stone torch");
        toolsNeverDespawn      = configfile.getBoolean("toolsNeverDespawn", CATEGORY_Tweaks, true, "Causes Tinker's tools to never despawn");


        /**  Debug **/
        configfile.setCategoryComment(CATEGORY_Debug, "Stuff to give you/me more information");

        showDebugXP = configfile.getBoolean("showDebugXP", CATEGORY_Debug, false, "Current Tool/Pick XP is shown as debug (F3) text");

        logHarvestLevelChanges = configfile.getBoolean("logBlockHarvestLevelChange", CATEGORY_Debug, true, "Logs when the harvest level of a block is changed");
        logMiningLevelChanges  = configfile.getBoolean("logToolMiningLevelChange", CATEGORY_Debug, true, "Logs when the mining level of a (non-tinker) tool is changed");
        logToolMaterialChanges = configfile.getBoolean("logTinkerMaterialChange", CATEGORY_Debug, true, "Logs when the mining level of a tinkers tool material is changed");


        /** not implemented anymore **/
/*

		// repairs
		ConfigCategory repairsCategory = configfile.getCategory("repairs");
		repairsCategory.setComment("Changes to tool repairing");

		Property repairCostScalingProperty = configfile.get("repairs", "repairCostScaling", false);
		repairCostScalingProperty.comment = "Repairs are less effective the more a tool is repaired";
		repairCostScaling = repairCostScalingProperty.getBoolean(false);

		Property repairLimitActiveProperty = configfile.get("repairs", "repairLimitActive", false);
		repairLimitActiveProperty.comment = "Number of times TC tools can be repaired is limited";
		repairLimitActive = repairLimitActiveProperty.getBoolean(false);

		Property repairLimitProperty = configfile.get("repairs", "repairLimit", 25);
		repairLimitProperty.comment = "Number of times TC tools can be repaired (only if 'repairLimitActive' is true) (set to 0 to disable repairs)";
		repairLimit = Math.max(repairLimitProperty.getInt(25), 0);
		repairLimitProperty.set(repairLimit);

		Property repairScalingModifierProperty = configfile.get("repairs", "repairScalingModifier", 5);
		repairScalingModifierProperty.comment = "Repair cost doubles after this many repairs (only if 'repairCostScaling' is true)";
		repairScalingModifier = Math.max(repairScalingModifierProperty.getInt(5), 1);
		repairScalingModifierProperty.set(repairScalingModifier);

		Property repairCostPercentageProperty = configfile.get("repairs", "repairCostPercentage", 100);
		repairCostPercentageProperty.comment = "Increase or decrease repair costs (higher = more expensive)";
		repairCostPercentage = Math.max(repairCostPercentageProperty.getInt(100), 1);
		repairCostPercentageProperty.set(repairCostPercentage);




		// crafting
		ConfigCategory craftingCategory = configfile.getCategory("crafting");
		craftingCategory.setComment("Allow Tinkers crafting to be done in a normal crafting window");

		Property easyBlankPatternRecipeProperty = configfile.get("crafting", "easyBlankPatternRecipe", true);
		easyBlankPatternRecipeProperty.comment = "Allows blank patterns to be crafted with 4 sticks in a square";
		easyBlankPatternRecipe = easyBlankPatternRecipeProperty.getBoolean(true);

		Property easyPartCraftingProperty = configfile.get("crafting", "easyPartCrafting", true);
		easyPartCraftingProperty.comment = "Allows you to make tool parts in a normal crafting window";
		easyPartCrafting = easyPartCraftingProperty.getBoolean(true);

		Property easyPatternCraftingProperty = configfile.get("crafting", "easyPatternCrafting", true);
		easyPatternCraftingProperty.comment = "Allows you to rotate the the tier 1 patterns in a normal crafting window";
		easyPatternCrafting = easyPatternCraftingProperty.getBoolean(true);

		Property easyToolCreationProperty = configfile.get("crafting", "easyToolCreation", true);
		easyToolCreationProperty.comment = "Allows you create tinkers tools in a normal crafting window";
		easyToolCreation = easyToolCreationProperty.getBoolean(true);

		Property easyToolModificationProperty = configfile.get("crafting", "easyToolModification", true);
		easyToolModificationProperty.comment = "Allows you add modifications to tools in a normal crafting window";
		easyToolModification = easyToolModificationProperty.getBoolean(true);


		// modifiers
		ConfigCategory modifiersCategory = configfile.getCategory("modifiers");
		modifiersCategory.setComment("Options relating to tool modifiers");

		Property addCleanModifierProperty = configfile.get("modifiers", "addCleanModifier", true);
		addCleanModifierProperty.comment = "Silky Cloth can be used to remove all modifiers from a tool (currently safe but not working)";
		addCleanModifier = addCleanModifierProperty.getBoolean(true);

		Property moreExpensiveSilkyClothProperty = configfile.get("modifiers", "moreExpensiveSilkyCloth", true);
		moreExpensiveSilkyClothProperty.comment = "Silky Cloth needs gold ingots, instead of nuggets";
		moreExpensiveSilkyCloth = moreExpensiveSilkyClothProperty.getBoolean(true);

		Property moreExpensiveSilkyJewelProperty = configfile.get("modifiers", "moreExpensiveSilkyJewel", true);
		moreExpensiveSilkyJewelProperty.comment = "Silky Jewel needs emerald block, instead of one emerald";
		moreExpensiveSilkyJewel = moreExpensiveSilkyJewelProperty.getBoolean(true);

		Property mossRepairSpeedProperty = configfile.get("modifiers", "mossRepairSpeed", 3);
		mossRepairSpeedProperty.comment = "Rate tools with moss repair (TC default 3)";
		mossRepairSpeed = Math.max(mossRepairSpeedProperty.getInt(3), 0);
		mossRepairSpeedProperty.set(mossRepairSpeed);

		Property redstoneEffectProperty = configfile.get("modifiers", "redstoneEffect", 4);
		redstoneEffectProperty.comment = "Amount each piece of redstone increases mining speed (tinkers default is 8)";
		redstoneEffect = Math.max(redstoneEffectProperty.getInt(4), 1);
		redstoneEffectProperty.set(redstoneEffect);



		//restrictions
		ConfigCategory restrictionsCategory = configfile.getCategory("restrictions");
		restrictionsCategory.setComment("See config section of mod thread for list of pattern ids");

		Property allowStoneToolsProperty = configfile.get("restrictions", "allowStoneTools", false);
		allowStoneToolsProperty.comment = "Allow certain stone tools to be built (if equivalent flint tool can also be made, the stone version is allowed)";
		allowStoneTools = allowStoneToolsProperty.getBoolean(false);

		Property restrictedWoodPartsProperty = configfile.get("restrictions", "restrictedWoodParts", new int[] {2,4,5,6,7,10,13,14,15,16,17,18,19,20,21,22,23,24});
		restrictedWoodPartsProperty.comment = "Pattern ids to restrict for wood parts";
		for (int i : restrictedWoodPartsProperty.getIntList()) restrictedWoodParts.add(i);

		Property restrictedStonePartsProperty = configfile.get("restrictions", "restrictedStoneParts", new int[] {});
		restrictedStonePartsProperty.comment = "Pattern ids to restrict for stone parts";
		for (int i : restrictedStonePartsProperty.getIntList()) restrictedStoneParts.add(i);

		Property restrictedFlintPartsProperty = configfile.get("restrictions", "restrictedFlintParts", new int[] {1,5,6,7,8,9,10,11,14,15,16,17,18,19,20,21,22,23,24});
		restrictedFlintPartsProperty.comment = "Pattern ids to restrict for flint parts";
		for (int i : restrictedFlintPartsProperty.getIntList()) restrictedFlintParts.add(i);

		Property restrictedBonePartsProperty = configfile.get("restrictions", "restrictedBoneParts", new int[] {2,5,6,7,9,10,11,13,14,15,16,17,18,19,20,21,22,23,24});
		restrictedBonePartsProperty.comment = "Pattern ids to restrict for bone parts";
		for (int i : restrictedBonePartsProperty.getIntList()) restrictedBoneParts.add(i);

		Property restrictedPaperPartsProperty = configfile.get("restrictions", "restrictedPaperParts", new int[] {2,3,4,5,6,7,8,10,11,12,13,14,15,16,17,18,19,20,21,22,23,25});
		restrictedPaperPartsProperty.comment = "Pattern ids to restrict for paper parts";
		for (int i : restrictedPaperPartsProperty.getIntList()) restrictedPaperParts.add(i);

		Property restrictedSlimePartsProperty = configfile.get("restrictions", "restrictedSlimeParts", new int[] {2,3,4,5,6,7,8,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25});
		restrictedSlimePartsProperty.comment = "Pattern ids to restrict for slime parts";
		for (int i : restrictedSlimePartsProperty.getIntList()) restrictedSlimeParts.add(i);

		Property restrictedCactusPartsProperty = configfile.get("restrictions", "restrictedCactusParts", new int[] {2,3,4,5,6,7,8,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25});
		restrictedCactusPartsProperty.comment = "Pattern ids to restrict for cactus parts";
		for (int i : restrictedCactusPartsProperty.getIntList()) restrictedCactusParts.add(i);

*/

		configfile.save();
	}

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if(event.modID.equals(Reference.MOD_ID))
            sync();
    }

}

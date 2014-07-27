package iguanaman.iguanatweakstconstruct.old;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class IguanaConfig {

	// Item Ids
	/*
	public static int clayBucketLavaId;
	public static int clayBucketMilkId;
	public static int clayBucketWaterId;
	public static int clayBucketFiredId;
	public static int clayBucketUnfiredId;
	public static int clayBucketsId;
	*/

	// leveling
	public static boolean detailedXpTooltip;
	public static boolean toolLeveling;
	public static boolean toolLevelingExtraModifiers;
    public static int[] toolModifiersAtLevels;
	public static boolean toolLevelingRandomBonuses;
	public static boolean showTooltipXP;
    public static boolean showMinimalTooltipXP;
	public static boolean showDebugXP;
	public static int xpRequiredToolsPercentage;
	public static int xpRequiredWeaponsPercentage;
	public static double xpPerLevelMultiplier;

	// pick boost
	public static boolean pickaxeBoostRequired;
	public static boolean mobHeadPickaxeBoost;
	public static boolean levelingPickaxeBoost;
	public static int levelingPickaxeBoostXpPercentage;

	// repairs
	public static boolean repairCostScaling;
	public static boolean repairLimitActive;
	public static int repairScalingModifier;
	public static int repairLimit;
	public static int repairCostPercentage;

	// heads
	public static int baseHeadDropChance;
	public static int beheadingHeadDropChance;

	//crafting
	public static boolean easyBlankPatternRecipe;
	public static boolean easyPartCrafting;
	public static boolean easyPatternCrafting;
	public static boolean easyToolModification;
	public static boolean easyToolCreation;

	//patterns
	/*
    public static int patternCostToolRod;
    public static int patternCostPickaxeHead;
    public static int patternCostShovelHead;
    public static int patternCostHatchetHead;
    public static int patternCostSwordBlade;
    public static int patternCostWideGuard;
    public static int patternCostHandGuard;
    public static int patternCostCrossbar;
    public static int patternCostBinding;
    public static int patternCostFrypanHead;
    public static int patternCostSignHead;
    public static int patternCostKnifeBlade;
    public static int patternCostChiselHead;
    public static int patternCostToughRod;
    public static int patternCostToughBinding;
    public static int patternCostHeavyPlate;
    public static int patternCostBroadAxeHead;
    public static int patternCostScytheBlade;
    public static int patternCostExcavatorHead;
    public static int patternCostLargeSwordBlade;
    public static int patternCostHammerHead;
    public static int patternCostFullGuard;
    public static int patternCostBowstring;
    public static int patternCostFletching;
    public static int patternCostArrowHead;
	 */

	// modifiers
	public static boolean addCleanModifier;
	public static boolean moreExpensiveSilkyCloth;
	public static boolean moreExpensiveSilkyJewel;
	public static int mossRepairSpeed;
	public static int redstoneEffect;

	//debug
	public static boolean logHarvestLevelChanges;
	public static boolean logMiningLevelChanges;

	//other
	public static boolean toolsNeverDespawn;
	public static boolean partTooltips;
	public static boolean partReplacement;
	public static boolean cobaltArmor;
	public static boolean removeStoneTorchRecipe;
	public static boolean removeFlintDrop;
	public static boolean addFlintRecipe;
	public static int durabilityPercentage;
	public static int miningSpeedPercentage;

	// Harvest Levels
	public static List<String> harvestLevel0Ids = new ArrayList<String>();
	public static List<String> harvestLevel1Ids = new ArrayList<String>();
	public static List<String> harvestLevel2Ids = new ArrayList<String>();
	public static List<String> harvestLevel3Ids = new ArrayList<String>();
	public static List<String> harvestLevel4Ids = new ArrayList<String>();
	public static List<String> harvestLevel5Ids = new ArrayList<String>();
	public static List<String> harvestLevel6Ids = new ArrayList<String>();
	public static List<String> harvestLevel7Ids = new ArrayList<String>();

	// Pickaxe mining level overrides
	public static HashMap<Integer, Integer> pickaxeOverrides = new HashMap<Integer, Integer>();

	//Restrictions
	public static boolean allowStoneTools;
	public static List<Integer> restrictedWoodParts = new ArrayList<Integer>();
	public static List<Integer> restrictedStoneParts = new ArrayList<Integer>();
	public static List<Integer> restrictedFlintParts = new ArrayList<Integer>();
	public static List<Integer> restrictedBoneParts = new ArrayList<Integer>();
	public static List<Integer> restrictedPaperParts = new ArrayList<Integer>();
	public static List<Integer> restrictedSlimeParts = new ArrayList<Integer>();
	public static List<Integer> restrictedCactusParts = new ArrayList<Integer>();

	public static void init(File file)
	{
		Configuration config = new Configuration(file);
		config.load();

		// leveling
		ConfigCategory levelingCategory = config.getCategory("leveling");
		levelingCategory.setComment("Setup the leveling system how you like it");

		Property detailedXpTooltipProperty = config.get("leveling", "detailedXpTooltip", false);
		detailedXpTooltipProperty.comment = "XP tooltip shows numbers, in addition to percentage";
		detailedXpTooltip = detailedXpTooltipProperty.getBoolean(false);

		Property toolLevelingProperty = config.get("leveling", "toolLeveling", true);
		toolLevelingProperty.comment = "Can your skill with tools 'level up' as you use them?";
		toolLeveling = toolLevelingProperty.getBoolean(true);

		Property toolLevelingExtraModifiersProperty = config.get("leveling", "toolLevelingExtraModifiers", true);
		toolLevelingExtraModifiersProperty.comment = "Removes modifiers on new tools and gives them through leveling (requires 'toolLeveling=true')";
		toolLevelingExtraModifiers = toolLevelingExtraModifiersProperty.getBoolean(true);

        Property toolModifiersAtLevelsProperty = config.get("leveling", "toolModifiersAtLevels", new int[]{2,4,6});
        toolModifiersAtLevelsProperty.comment = "Adds an extra modifier on these levleups if 'toolLevelingExtraModifiers' is enabled";
        toolModifiersAtLevels = toolModifiersAtLevelsProperty.getIntList();

        Property toolLevelingRandomBonusesProperty = config.get("leveling", "toolLevelingRandomBonuses", true);
		toolLevelingRandomBonusesProperty.comment = "Gives a random bonus every level, if false and levelling is on modifiers are given at levels 2 and 4 (requires 'toolLeveling=true')";
		toolLevelingRandomBonuses = toolLevelingRandomBonusesProperty.getBoolean(true);

		Property showTooltipXPProperty = config.get("leveling", "showTooltipXP", true);
		showTooltipXPProperty.comment = "Current XP is shown when hovering over a tool (requires 'toolLeveling=true')";
		showTooltipXP = showTooltipXPProperty.getBoolean(true);

        Property showMinimalTooltipXPProperty = config.get("leveling", "showMinimalTooltipXP", false);
        showMinimalTooltipXPProperty.comment = "Current XP% is shown after the level (requires 'toolLeveling=true')";
        showMinimalTooltipXP = showMinimalTooltipXPProperty.getBoolean(true);

		Property showDebugXPProperty = config.get("leveling", "showDebugXP", false);
		showDebugXPProperty.comment = "Current XP is shown as debug (F3) text (requires 'toolLeveling=true')";
		showDebugXP = showDebugXPProperty.getBoolean(false);

		Property xpRequiredToolsPercentageProperty = config.get("leveling", "xpRequiredToolsPercentage", 100);
		xpRequiredToolsPercentageProperty.comment = "Change the XP required to level up tools (higher=more) (requires 'toolLeveling' to be true)";
		xpRequiredToolsPercentage = Math.max(xpRequiredToolsPercentageProperty.getInt(100), 1);
		xpRequiredToolsPercentageProperty.set(xpRequiredToolsPercentage);

		Property xpRequiredWeaponsPercentageProperty = config.get("leveling", "xpRequiredWeaponsPercentage", 100);
		xpRequiredWeaponsPercentageProperty.comment = "Change the XP required to level up weapons (higher=more) (requires 'toolLeveling' to be true)";
		xpRequiredWeaponsPercentage = Math.max(xpRequiredWeaponsPercentageProperty.getInt(100), 1);
		xpRequiredWeaponsPercentageProperty.set(xpRequiredWeaponsPercentage);

		Property xpPerLevelMultiplierProperty = config.get("leveling", "xpPerLevelMultiplier", 1.35d);
		xpPerLevelMultiplierProperty.comment = "Exponential multiplier on required XP per level";
		xpPerLevelMultiplier = Math.max(xpPerLevelMultiplierProperty.getDouble(1.35d), 1d);
		xpPerLevelMultiplierProperty.set(xpPerLevelMultiplier);


		// pick leveling
		ConfigCategory pickboostingCategory = config.getCategory("pickboosting");
		pickboostingCategory.setComment("Options to configure to pickaxe mining level boost mechanics");

		Property pickaxeBoostRequiredProperty = config.get("pickboosting", "pickaxeBoostRequired", true);
		pickaxeBoostRequiredProperty.comment = "Pickaxes only mine upto their head material level and need a mob head modifier OR leveling boost to advance";
		pickaxeBoostRequired = pickaxeBoostRequiredProperty.getBoolean(true);

		Property mobHeadPickaxeBoostProperty = config.get("pickboosting", "mobHeadPickaxeBoost", true);
		mobHeadPickaxeBoostProperty.comment = "Mob heads can be used to boost a pickaxe's mining level";
		mobHeadPickaxeBoost = mobHeadPickaxeBoostProperty.getBoolean(true);

		Property levelingPickaxeBoostProperty = config.get("pickboosting", "levelingPickaxeBoost", true);
		levelingPickaxeBoostProperty.comment = "A pickaxes mining level can be boosted through gaining XP";
		levelingPickaxeBoost = levelingPickaxeBoostProperty.getBoolean(true);

		Property levelingPickaxeBoostXpPercentageProperty = config.get("pickboosting", "levelingPickaxeBoostXpPercentage", 100);
		levelingPickaxeBoostXpPercentageProperty.comment = "Change the percentage of XP required to boost a pick (i.e. 200 means 2x normal boost xp required)";
		levelingPickaxeBoostXpPercentage = Math.max(levelingPickaxeBoostXpPercentageProperty.getInt(100), 1);
		levelingPickaxeBoostXpPercentageProperty.set(levelingPickaxeBoostXpPercentage);


		// repairs
		ConfigCategory repairsCategory = config.getCategory("repairs");
		repairsCategory.setComment("Changes to tool repairing");

		Property repairCostScalingProperty = config.get("repairs", "repairCostScaling", false);
		repairCostScalingProperty.comment = "Repairs are less effective the more a tool is repaired";
		repairCostScaling = repairCostScalingProperty.getBoolean(false);

		Property repairLimitActiveProperty = config.get("repairs", "repairLimitActive", false);
		repairLimitActiveProperty.comment = "Number of times TC tools can be repaired is limited";
		repairLimitActive = repairLimitActiveProperty.getBoolean(false);

		Property repairLimitProperty = config.get("repairs", "repairLimit", 25);
		repairLimitProperty.comment = "Number of times TC tools can be repaired (only if 'repairLimitActive' is true) (set to 0 to disable repairs)";
		repairLimit = Math.max(repairLimitProperty.getInt(25), 0);
		repairLimitProperty.set(repairLimit);

		Property repairScalingModifierProperty = config.get("repairs", "repairScalingModifier", 5);
		repairScalingModifierProperty.comment = "Repair cost doubles after this many repairs (only if 'repairCostScaling' is true)";
		repairScalingModifier = Math.max(repairScalingModifierProperty.getInt(5), 1);
		repairScalingModifierProperty.set(repairScalingModifier);

		Property repairCostPercentageProperty = config.get("repairs", "repairCostPercentage", 100);
		repairCostPercentageProperty.comment = "Increase or decrease repair costs (higher = more expensive)";
		repairCostPercentage = Math.max(repairCostPercentageProperty.getInt(100), 1);
		repairCostPercentageProperty.set(repairCostPercentage);


		// heads
		ConfigCategory headsCategory = config.getCategory("heads");
		headsCategory.setComment("Configure the mob head modifiers and drops here");

		Property baseHeadDropChanceProperty = config.get("heads", "baseHeadDropChance", 5);
		baseHeadDropChanceProperty.comment = "Base percentage chance for a head to drop (only if 'pickaxeHeads' is true)";
		baseHeadDropChance = Math.max(baseHeadDropChanceProperty.getInt(5), 0);
		baseHeadDropChanceProperty.set(baseHeadDropChance);

		Property beheadingHeadDropChanceProperty = config.get("heads", "beheadingHeadDropChance", 2);
		beheadingHeadDropChanceProperty.comment = "Percentage chance for a head to drop for each level of beheading (only if 'pickaxeHeads' is true)";
		beheadingHeadDropChance = Math.max(beheadingHeadDropChanceProperty.getInt(2), 0);
		beheadingHeadDropChanceProperty.set(beheadingHeadDropChance);


		// crafting
		ConfigCategory craftingCategory = config.getCategory("crafting");
		craftingCategory.setComment("Allow Tinkers crafting to be done in a normal crafting window");

		Property easyBlankPatternRecipeProperty = config.get("crafting", "easyBlankPatternRecipe", true);
		easyBlankPatternRecipeProperty.comment = "Allows blank patterns to be crafted with 4 sticks in a square";
		easyBlankPatternRecipe = easyBlankPatternRecipeProperty.getBoolean(true);

		Property easyPartCraftingProperty = config.get("crafting", "easyPartCrafting", true);
		easyPartCraftingProperty.comment = "Allows you to make tool parts in a normal crafting window";
		easyPartCrafting = easyPartCraftingProperty.getBoolean(true);

		Property easyPatternCraftingProperty = config.get("crafting", "easyPatternCrafting", true);
		easyPatternCraftingProperty.comment = "Allows you to rotate the the tier 1 patterns in a normal crafting window";
		easyPatternCrafting = easyPatternCraftingProperty.getBoolean(true);

		Property easyToolCreationProperty = config.get("crafting", "easyToolCreation", true);
		easyToolCreationProperty.comment = "Allows you create tinkers tools in a normal crafting window";
		easyToolCreation = easyToolCreationProperty.getBoolean(true);

		Property easyToolModificationProperty = config.get("crafting", "easyToolModification", true);
		easyToolModificationProperty.comment = "Allows you add modifications to tools in a normal crafting window";
		easyToolModification = easyToolModificationProperty.getBoolean(true);


		// patterns
		/*
		ConfigCategory patternsCategory = config.getCategory("patterns");
		patternsCategory.setComment("Set the material cost (in half ingots) required for each pattern type");

        Property patternCostToolRodProperty = config.get("patterns", "patternCostToolRod", 1);
        patternCostToolRodProperty.comment = "TCon default is 1";
        patternCostToolRod = Math.max(patternCostToolRodProperty.getInt(1), 1);
        patternCostToolRodProperty.set(patternCostToolRod);

        Property patternCostPickaxeHeadProperty = config.get("patterns", "patternCostPickaxeHead", 6);
        patternCostPickaxeHeadProperty.comment = "TCon default is 2";
        patternCostPickaxeHead = Math.max(patternCostPickaxeHeadProperty.getInt(6), 1);
        patternCostPickaxeHeadProperty.set(patternCostPickaxeHead);

        Property patternCostShovelHeadProperty = config.get("patterns", "patternCostShovelHead", 2);
        patternCostShovelHeadProperty.comment = "TCon default is 2";
        patternCostShovelHead = Math.max(patternCostShovelHeadProperty.getInt(2), 1);
        patternCostShovelHeadProperty.set(patternCostShovelHead);

        Property patternCostHatchetHeadProperty = config.get("patterns", "patternCostHatchetHead", 4);
        patternCostHatchetHeadProperty.comment = "TCon default is 2";
        patternCostHatchetHead = Math.max(patternCostHatchetHeadProperty.getInt(4), 1);
        patternCostHatchetHeadProperty.set(patternCostHatchetHead);

        Property patternCostSwordBladeProperty = config.get("patterns", "patternCostSwordBlade", 4);
        patternCostSwordBladeProperty.comment = "TCon default is 2";
        patternCostSwordBlade = Math.max(patternCostSwordBladeProperty.getInt(4), 1);
        patternCostSwordBladeProperty.set(patternCostSwordBlade);

        Property patternCostWideGuardProperty = config.get("patterns", "patternCostWideGuard", 1);
        patternCostWideGuardProperty.comment = "TCon default is 1";
        patternCostWideGuard = Math.max(patternCostWideGuardProperty.getInt(1), 1);
        patternCostWideGuardProperty.set(patternCostWideGuard);

        Property patternCostHandGuardProperty = config.get("patterns", "patternCostHandGuard", 1);
        patternCostHandGuardProperty.comment = "TCon default is 1";
        patternCostHandGuard = Math.max(patternCostHandGuardProperty.getInt(1), 1);
        patternCostHandGuardProperty.set(patternCostHandGuard);

        Property patternCostCrossbarProperty = config.get("patterns", "patternCostCrossbar", 1);
        patternCostCrossbarProperty.comment = "TCon default is 1";
        patternCostCrossbar = Math.max(patternCostCrossbarProperty.getInt(1), 1);
        patternCostCrossbarProperty.set(patternCostCrossbar);

        Property patternCostBindingProperty = config.get("patterns", "patternCostBinding", 1);
        patternCostBindingProperty.comment = "TCon default is 1";
        patternCostBinding = Math.max(patternCostBindingProperty.getInt(1), 1);
        patternCostBindingProperty.set(patternCostBinding);

        Property patternCostFrypanHeadProperty = config.get("patterns", "patternCostFrypanHead", 6);
        patternCostFrypanHeadProperty.comment = "TCon default is 2";
        patternCostFrypanHead = Math.max(patternCostFrypanHeadProperty.getInt(6), 1);
        patternCostFrypanHeadProperty.set(patternCostFrypanHead);

        Property patternCostSignHeadProperty = config.get("patterns", "patternCostSignHead", 6);
        patternCostSignHeadProperty.comment = "TCon default is 2";
        patternCostSignHead = Math.max(patternCostSignHeadProperty.getInt(6), 1);
        patternCostSignHeadProperty.set(patternCostSignHead);

        Property patternCostKnifeBladeProperty = config.get("patterns", "patternCostKnifeBlade", 1);
        patternCostKnifeBladeProperty.comment = "TCon default is 1";
        patternCostKnifeBlade = Math.max(patternCostKnifeBladeProperty.getInt(1), 1);
        patternCostKnifeBladeProperty.set(patternCostKnifeBlade);

        Property patternCostChiselHeadProperty = config.get("patterns", "patternCostChiselHead", 1);
        patternCostChiselHeadProperty.comment = "TCon default is 1";
        patternCostChiselHead = Math.max(patternCostChiselHeadProperty.getInt(1), 1);
        patternCostChiselHeadProperty.set(patternCostChiselHead);

        Property patternCostToughRodProperty = config.get("patterns", "patternCostToughRod", 6);
        patternCostToughRodProperty.comment = "TCon default is 6";
        patternCostToughRod = Math.max(patternCostToughRodProperty.getInt(6), 1);
        patternCostToughRodProperty.set(patternCostToughRod);

        Property patternCostToughBindingProperty = config.get("patterns", "patternCostToughBinding", 6);
        patternCostToughBindingProperty.comment = "TCon default is 6";
        patternCostToughBinding = Math.max(patternCostToughBindingProperty.getInt(6), 1);
        patternCostToughBindingProperty.set(patternCostToughBinding);

        Property patternCostHeavyPlateProperty = config.get("patterns", "patternCostHeavyPlate", 16);
        patternCostHeavyPlateProperty.comment = "TCon default is 16";
        patternCostHeavyPlate = Math.max(patternCostHeavyPlateProperty.getInt(16), 1);
        patternCostHeavyPlateProperty.set(patternCostHeavyPlate);

        Property patternCostBroadAxeHeadProperty = config.get("patterns", "patternCostBroadAxeHead", 16);
        patternCostBroadAxeHeadProperty.comment = "TCon default is 16";
        patternCostBroadAxeHead = Math.max(patternCostBroadAxeHeadProperty.getInt(16), 1);
        patternCostBroadAxeHeadProperty.set(patternCostBroadAxeHead);

        Property patternCostScytheBladeProperty = config.get("patterns", "patternCostScytheBlade", 16);
        patternCostScytheBladeProperty.comment = "TCon default is 16";
        patternCostScytheBlade = Math.max(patternCostScytheBladeProperty.getInt(16), 1);
        patternCostScytheBladeProperty.set(patternCostScytheBlade);

        Property patternCostExcavatorHeadProperty = config.get("patterns", "patternCostExcavatorHead", 16);
        patternCostExcavatorHeadProperty.comment = "TCon default is 16";
        patternCostExcavatorHead = Math.max(patternCostExcavatorHeadProperty.getInt(16), 1);
        patternCostExcavatorHeadProperty.set(patternCostExcavatorHead);

        Property patternCostLargeSwordBladeProperty = config.get("patterns", "patternCostLargeSwordBlade", 16);
        patternCostLargeSwordBladeProperty.comment = "TCon default is 16";
        patternCostLargeSwordBlade = Math.max(patternCostLargeSwordBladeProperty.getInt(16), 1);
        patternCostLargeSwordBladeProperty.set(patternCostLargeSwordBlade);

        Property patternCostHammerHeadProperty = config.get("patterns", "patternCostHammerHead", 16);
        patternCostHammerHeadProperty.comment = "TCon default is 16";
        patternCostHammerHead = Math.max(patternCostHammerHeadProperty.getInt(16), 1);
        patternCostHammerHeadProperty.set(patternCostHammerHead);

        Property patternCostFullGuardProperty = config.get("patterns", "patternCostFullGuard", 6);
        patternCostFullGuardProperty.comment = "TCon default is 6";
        patternCostFullGuard = Math.max(patternCostFullGuardProperty.getInt(6), 1);
        patternCostFullGuardProperty.set(patternCostFullGuard);

        Property patternCostBowstringProperty = config.get("patterns", "patternCostBowstring", 6);
        patternCostBowstringProperty.comment = "TCon default is 6";
        patternCostBowstring = Math.max(patternCostBowstringProperty.getInt(6), 1);
        patternCostBowstringProperty.set(patternCostBowstring);

        Property patternCostFletchingProperty = config.get("patterns", "patternCostFletching", 2);
        patternCostFletchingProperty.comment = "TCon default is 2";
        patternCostFletching = Math.max(patternCostFletchingProperty.getInt(2), 1);
        patternCostFletchingProperty.set(patternCostFletching);

        Property patternCostArrowHeadProperty = config.get("patterns", "patternCostArrowHead", 2);
        patternCostArrowHeadProperty.comment = "TCon default is 2";
        patternCostArrowHead = Math.max(patternCostArrowHeadProperty.getInt(2), 1);
        patternCostArrowHeadProperty.set(patternCostArrowHead);
		 */


		// modifiers
		ConfigCategory modifiersCategory = config.getCategory("modifiers");
		modifiersCategory.setComment("Options relating to tool modifiers");

		Property addCleanModifierProperty = config.get("modifiers", "addCleanModifier", true);
		addCleanModifierProperty.comment = "Silky Cloth can be used to remove all modifiers from a tool (currently safe but not working)";
		addCleanModifier = addCleanModifierProperty.getBoolean(true);

		Property moreExpensiveSilkyClothProperty = config.get("modifiers", "moreExpensiveSilkyCloth", true);
		moreExpensiveSilkyClothProperty.comment = "Silky Cloth needs gold ingots, instead of nuggets";
		moreExpensiveSilkyCloth = moreExpensiveSilkyClothProperty.getBoolean(true);

		Property moreExpensiveSilkyJewelProperty = config.get("modifiers", "moreExpensiveSilkyJewel", true);
		moreExpensiveSilkyJewelProperty.comment = "Silky Jewel needs emerald block, instead of one emerald";
		moreExpensiveSilkyJewel = moreExpensiveSilkyJewelProperty.getBoolean(true);

		Property mossRepairSpeedProperty = config.get("modifiers", "mossRepairSpeed", 3);
		mossRepairSpeedProperty.comment = "Rate tools with moss repair (TC default 3)";
		mossRepairSpeed = Math.max(mossRepairSpeedProperty.getInt(3), 0);
		mossRepairSpeedProperty.set(mossRepairSpeed);

		Property redstoneEffectProperty = config.get("modifiers", "redstoneEffect", 4);
		redstoneEffectProperty.comment = "Amount each piece of redstone increases mining speed (tinkers default is 8)";
		redstoneEffect = Math.max(redstoneEffectProperty.getInt(4), 1);
		redstoneEffectProperty.set(redstoneEffect);


		// debug
		ConfigCategory debugCategory = config.getCategory("debug");
		debugCategory.setComment("Debugging options");

		Property logHarvestLevelChangesProperty = config.get("debug", "logHarvestLevelChanges", false);
		logHarvestLevelChangesProperty.comment = "Outputs to the log when the harvest level of a block is changed";
		logHarvestLevelChanges = logHarvestLevelChangesProperty.getBoolean(false);

		Property logMiningLevelChangesProperty = config.get("debug", "logMiningLevelChanges", false);
		logMiningLevelChangesProperty.comment = "Outputs to the log when the mining level of a tool is changed";
		logMiningLevelChanges = logMiningLevelChangesProperty.getBoolean(false);


		// other
		ConfigCategory otherCategory = config.getCategory("other");
		otherCategory.setComment("Random stuff to configure here");

		Property cobaltArmorProperty = config.get("other", "cobaltArmor", false);
		cobaltArmorProperty.comment = "Changes diamond armor to cobalt armor (more expensive recipe)";
		cobaltArmor = cobaltArmorProperty.getBoolean(false);

		Property partTooltipsProperty = config.get("other", "partTooltips", true);
		partTooltipsProperty.comment = "Shows information about tool parts in the mouseover tooltip";
		partTooltips = partTooltipsProperty.getBoolean(true);

		Property partReplacementProperty = config.get("other", "partReplacement", true);
		partReplacementProperty.comment = "Can you replace parts of existing tools?";
		partReplacement = partReplacementProperty.getBoolean(true);

		Property removeFlintDropProperty = config.get("other", "removeFlintDrop", true);
		removeFlintDropProperty.comment = "Removes the random chance of getting flint from gravel";
		removeFlintDrop = removeFlintDropProperty.getBoolean(true);

		Property addFlintRecipeProperty = config.get("other", "addFlintRecipe", true);
		addFlintRecipeProperty.comment = "Adds a shapeless recipe to get flint from 4 gravel blocks";
		addFlintRecipe = addFlintRecipeProperty.getBoolean(true);

		Property removeStoneTorchRecipeProperty = config.get("other", "removeStoneTorchRecipe", true);
		removeStoneTorchRecipeProperty.comment = "Removes the recipe for Tinker's Construct's stone torch";
		removeStoneTorchRecipe = removeStoneTorchRecipeProperty.getBoolean(true);

		Property durabilityPercentageProperty = config.get("other", "durabilityPercentage", 50);
		durabilityPercentageProperty.comment = "Change durability of all materials here (higher = tougher)";
		durabilityPercentage = Math.max(durabilityPercentageProperty.getInt(50), 1);
		durabilityPercentageProperty.set(durabilityPercentage);

		Property miningSpeedPercentageProperty = config.get("other", "miningSpeedPercentage", 100);
		miningSpeedPercentageProperty.comment = "Change mining speed of all materials here (higher = faster)";
		miningSpeedPercentage = Math.max(miningSpeedPercentageProperty.getInt(100), 1);
		miningSpeedPercentageProperty.set(miningSpeedPercentage);

		Property toolsNeverDespawnProperty = config.get("other", "toolsNeverDespawn", true);
		toolsNeverDespawnProperty.comment = "Do Tinker's tools on the ground never despawn?";
		toolsNeverDespawn = toolsNeverDespawnProperty.getBoolean(true);


		//restrictions
		ConfigCategory restrictionsCategory = config.getCategory("restrictions");
		restrictionsCategory.setComment("See config section of mod thread for list of pattern ids");

		Property allowStoneToolsProperty = config.get("restrictions", "allowStoneTools", false);
		allowStoneToolsProperty.comment = "Allow certain stone tools to be built (if equivalent flint tool can also be made, the stone version is allowed)";
		allowStoneTools = allowStoneToolsProperty.getBoolean(false);

		Property restrictedWoodPartsProperty = config.get("restrictions", "restrictedWoodParts", new int[] {2,4,5,6,7,10,13,14,15,16,17,18,19,20,21,22,23,24});
		restrictedWoodPartsProperty.comment = "Pattern ids to restrict for wood parts";
		for (int i : restrictedWoodPartsProperty.getIntList()) restrictedWoodParts.add(i);

		Property restrictedStonePartsProperty = config.get("restrictions", "restrictedStoneParts", new int[] {});
		restrictedStonePartsProperty.comment = "Pattern ids to restrict for stone parts";
		for (int i : restrictedStonePartsProperty.getIntList()) restrictedStoneParts.add(i);

		Property restrictedFlintPartsProperty = config.get("restrictions", "restrictedFlintParts", new int[] {1,5,6,7,8,9,10,11,14,15,16,17,18,19,20,21,22,23,24});
		restrictedFlintPartsProperty.comment = "Pattern ids to restrict for flint parts";
		for (int i : restrictedFlintPartsProperty.getIntList()) restrictedFlintParts.add(i);

		Property restrictedBonePartsProperty = config.get("restrictions", "restrictedBoneParts", new int[] {2,5,6,7,9,10,11,13,14,15,16,17,18,19,20,21,22,23,24});
		restrictedBonePartsProperty.comment = "Pattern ids to restrict for bone parts";
		for (int i : restrictedBonePartsProperty.getIntList()) restrictedBoneParts.add(i);

		Property restrictedPaperPartsProperty = config.get("restrictions", "restrictedPaperParts", new int[] {2,3,4,5,6,7,8,10,11,12,13,14,15,16,17,18,19,20,21,22,23,25});
		restrictedPaperPartsProperty.comment = "Pattern ids to restrict for paper parts";
		for (int i : restrictedPaperPartsProperty.getIntList()) restrictedPaperParts.add(i);

		Property restrictedSlimePartsProperty = config.get("restrictions", "restrictedSlimeParts", new int[] {2,3,4,5,6,7,8,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25});
		restrictedSlimePartsProperty.comment = "Pattern ids to restrict for slime parts";
		for (int i : restrictedSlimePartsProperty.getIntList()) restrictedSlimeParts.add(i);

		Property restrictedCactusPartsProperty = config.get("restrictions", "restrictedCactusParts", new int[] {2,3,4,5,6,7,8,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25});
		restrictedCactusPartsProperty.comment = "Pattern ids to restrict for cactus parts";
		for (int i : restrictedCactusPartsProperty.getIntList()) restrictedCactusParts.add(i);


		// harvest ids
		ConfigCategory harvestidsCategory = config.getCategory("harvestids");
		harvestidsCategory.setComment("Set harvest levels of blocks here (most should be modified by default, so check if needed first)");

		Property harvestLevel0IdsProperty = config.get("harvestids", "harvestLevel0Ids", new String[] {});
		harvestLevel0IdsProperty.comment = "Block ids (each on seperate line) for blocks to be set to harvest level 0 (stone pick+)";
		for (String i : harvestLevel0IdsProperty.getStringList()) harvestLevel0Ids.add(i);

		Property harvestLevel1IdsProperty = config.get("harvestids", "harvestLevel1Ids", new String[] {});
		harvestLevel1IdsProperty.comment = "Block ids (each on seperate line) for blocks to be set to harvest level 1 (flint pick+)";
		for (String i : harvestLevel1IdsProperty.getStringList()) harvestLevel1Ids.add(i);

		Property harvestLevel2IdsProperty = config.get("harvestids", "harvestLevel2Ids", new String[] {});
		harvestLevel2IdsProperty.comment = "Block ids (each on seperate line) for blocks to be set to harvest level 2 (copper pick+)";
		for (String i : harvestLevel2IdsProperty.getStringList()) harvestLevel2Ids.add(i);

		Property harvestLevel3IdsProperty = config.get("harvestids", "harvestLevel3Ids", new String[] {});
		harvestLevel3IdsProperty.comment = "Block ids (each on seperate line) for blocks to be set to harvest level 3 (iron pick+)";
		for (String i : harvestLevel3IdsProperty.getStringList()) harvestLevel3Ids.add(i);

		Property harvestLevel4IdsProperty = config.get("harvestids", "harvestLevel4Ids", new String[] {});
		harvestLevel4IdsProperty.comment = "Block ids (each on seperate line) for blocks to be set to harvest level 4 (bronze pick+)";
		for (String i : harvestLevel4IdsProperty.getStringList()) harvestLevel4Ids.add(i);

		Property harvestLevel5IdsProperty = config.get("harvestids", "harvestLevel5Ids", new String[] {});
		harvestLevel5IdsProperty.comment = "Block ids (each on seperate line) for blocks to be set to harvest level 5 (alumite pick+)";
		for (String i : harvestLevel5IdsProperty.getStringList()) harvestLevel5Ids.add(i);

		Property harvestLevel6IdsProperty = config.get("harvestids", "harvestLevel6Ids", new String[] {});
		harvestLevel6IdsProperty.comment = "Block ids (each on seperate line) for blocks to be set to harvest level 6 (ardite pick+)";
		for (String i : harvestLevel6IdsProperty.getStringList()) harvestLevel6Ids.add(i);

		Property harvestLevel7IdsProperty = config.get("harvestids", "harvestLevel7Ids", new String[] {});
		harvestLevel7IdsProperty.comment = "Block ids (each on seperate line) for blocks to be set to harvest level 7 (cobalt pick+)";
		for (String i : harvestLevel7IdsProperty.getStringList()) harvestLevel7Ids.add(i);


		// pickaxe mining level overrides
		ConfigCategory pickaxeoverridesCategory = config.getCategory("pickaxeoverrides");
		pickaxeoverridesCategory.setComment("Normally the mod changes the mining levels of all pickaxes to be in line with the new system, override that for specific picks here");

		Property pickaxeOverridesProperty = config.get("pickaxeoverrides", "pickaxeOverrides", new String[] {});
		// TODO: Find a better way to implement pickaxeOveride
		/*pickaxeOverridesProperty.comment = "Format <itemID>:<miningLevel> (Each on a separate line)";
		for (String i : pickaxeOverridesProperty.getStringList())
		{
			String[] splt = i.split(":");
			pickaxeOverrides.put(Integer.parseInt(splt[0]), Integer.parseInt(splt[1]));
		}*/


		config.save();
	}

}

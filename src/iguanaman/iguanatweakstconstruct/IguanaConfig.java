package iguanaman.iguanatweakstconstruct;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class IguanaConfig {

	// Item Ids
    public static int clayBucketLavaId;
    public static int clayBucketMilkId;
    public static int clayBucketWaterId;
    public static int clayBucketFiredId;
    public static int clayBucketUnfiredId;
    public static int clayBucketsId;
    public static int easterEggId1;
    public static int easterEggId2;
    
    // leveling
	public static boolean toolLeveling;
	public static boolean toolLevelingExtraModifiers;
	public static boolean toolLevelingRandomBonuses;
	public static boolean pickaxeLevelingBoost;
	public static boolean showTooltipXP;
	public static boolean showDebugXP;
    public static int miningBoostLevel;
    public static int toolLevelingRatePercentage;
    public static int weaponLevelingRatePercentage;
	
	// repairs
	public static boolean repairCostScaling;
	public static boolean repairLimitActive;
    public static int repairScalingModifier;
    public static int repairLimit;
    public static int repairCostPercentage;
	
	// heads
	public static boolean mobHeadModifiers;
    public static int baseHeadDropChance;
    public static int beheadingHeadDropChance;
    
    //crafting
    public static boolean easyBlankPatternRecipe;
    public static boolean easyPartCrafting;
    public static boolean easyPatternCrafting;
    public static boolean easyToolModification;
    public static boolean easyToolCreation;
	
	//other
	public static boolean partReplacement;
	public static boolean pickaxeBoostRequired;
	public static boolean cobaltArmor;
	public static boolean moreExpensiveSilkTouch;
	public static boolean moreExpensiveElectric;
    public static boolean removeStoneTorchRecipe;
    public static boolean removeFlintDrop;
    public static boolean addFlintRecipe;
    public static int mossRepairSpeed;
    public static int durabilityPercentage;
    public static int miningSpeedPercentage;
    public static int redstoneEffect;
    
    // Harvest Levels
    public static List<String> harvestLevel0Ids = new ArrayList<String>();
    public static List<String> harvestLevel1Ids = new ArrayList<String>();
    public static List<String> harvestLevel2Ids = new ArrayList<String>();
    public static List<String> harvestLevel3Ids = new ArrayList<String>();
    public static List<String> harvestLevel4Ids = new ArrayList<String>();
    public static List<String> harvestLevel5Ids = new ArrayList<String>();
    public static List<String> harvestLevel6Ids = new ArrayList<String>();
    public static List<String> harvestLevel7Ids = new ArrayList<String>();
    
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
        
        // items
        Property clayBucketUnfiredIdProperty = config.getItem("clayBucketUnfiredId", 25710);
        clayBucketUnfiredIdProperty.comment = "Item ID for the unfired clay bucket";
		clayBucketUnfiredId = clayBucketUnfiredIdProperty.getInt(25710);

        Property clayBucketFiredIdProperty = config.getItem("clayBucketFiredId", 25711);
        clayBucketFiredIdProperty.comment = "Item ID for the fired clay bucket";
		clayBucketFiredId = clayBucketFiredIdProperty.getInt(25711);

        Property clayBucketWaterIdProperty = config.getItem("clayBucketWaterId", 25712);
        clayBucketWaterIdProperty.comment = "Item ID for the water filled clay bucket";
        clayBucketWaterId = clayBucketWaterIdProperty.getInt(25712);

        Property clayBucketLavaIdProperty = config.getItem("clayBucketLavaId", 25713);
        clayBucketLavaIdProperty.comment = "Item ID for the lava filled clay bucket";
        clayBucketLavaId = clayBucketLavaIdProperty.getInt(25713);

        Property clayBucketsIdProperty = config.getItem("clayBucketsId", 25714);
        clayBucketsIdProperty.comment = "Item ID for the filled clay buckets";
        clayBucketsId = clayBucketsIdProperty.getInt(25714);

        Property clayBucketMilkIdProperty = config.getItem("clayBucketMilkId", 25715);
        clayBucketMilkIdProperty.comment = "Item ID for the milk filled clay bucket";
        clayBucketMilkId = clayBucketMilkIdProperty.getInt(25715);

        Property easterEggId1Property = config.getItem("easterEggId1", 25716);
        easterEggId1Property.comment = "Item ID for the easter egg item 1";
        easterEggId1 = easterEggId1Property.getInt(25716);

        Property easterEggId2Property = config.getItem("easterEggId2", 25717);
        easterEggId2Property.comment = "Item ID for the easter egg item 2";
        easterEggId2 = easterEggId2Property.getInt(25717);
        
        
        
        // leveling
		ConfigCategory levelingCategory = config.getCategory("leveling");
		levelingCategory.setComment("Setup the leveling system how you like it");

        Property toolLevelingProperty = config.get("leveling", "toolLeveling", true);
        toolLevelingProperty.comment = "Can your skill with tools 'level up' as you use them?";
        toolLeveling = toolLevelingProperty.getBoolean(true);
        
        Property toolLevelingExtraModifiersProperty = config.get("leveling", "toolLevelingExtraModifiers", true);
        toolLevelingExtraModifiersProperty.comment = "Removes modifiers on new tools and gives them through leveling (requires 'toolLeveling=true')";
        toolLevelingExtraModifiers = toolLevelingExtraModifiersProperty.getBoolean(true);
        
        Property toolLevelingRandomBonusesProperty = config.get("leveling", "toolLevelingRandomBonuses", true);
        toolLevelingRandomBonusesProperty.comment = "Gives a random bonus every level, if false and levelling is on modifiers are given at levels 2 and 4 (requires 'toolLeveling=true')";
        toolLevelingRandomBonuses = toolLevelingRandomBonusesProperty.getBoolean(true);
        
        Property pickaxeLevelingBoostProperty = config.get("leveling", "pickaxeLevelingBoost", true);
        pickaxeLevelingBoostProperty.comment = "Seperate pickaxe leveling system which gives a mining level boost";
        pickaxeLevelingBoost = pickaxeLevelingBoostProperty.getBoolean(true);
        
        Property showTooltipXPProperty = config.get("leveling", "showTooltipXP", true);
        showTooltipXPProperty.comment = "Current XP is shown when hovering over a tool (requires 'toolLeveling=true')";
        showTooltipXP = showTooltipXPProperty.getBoolean(true);
        
        Property showDebugXPProperty = config.get("leveling", "showDebugXP", false);
        showDebugXPProperty.comment = "Current XP is shown as debug (F3) text (requires 'toolLeveling=true')";
        showDebugXP = showDebugXPProperty.getBoolean(false);
		
        Property miningBoostLevelProperty = config.get("leveling", "miningBoostLevel", 2);
        miningBoostLevelProperty.comment = "Levels of XP required to get mining boost modifier on a pick (requires 'toolLevelingPickaxeBoost=true')";
        miningBoostLevel = Math.max(miningBoostLevelProperty.getInt(2), 1);
        miningBoostLevelProperty.set(miningBoostLevel);
		
        Property toolLevelingRatePercentageProperty = config.get("leveling", "toolLevelingRatePercentage", 100);
        toolLevelingRatePercentageProperty.comment = "Rate at which tools gain XP (higher=faster) (requires 'toolLeveling=true')";
        toolLevelingRatePercentage = Math.max(toolLevelingRatePercentageProperty.getInt(100), 1);
        toolLevelingRatePercentageProperty.set(toolLevelingRatePercentage);
		
        Property weaponLevelingRatePercentageProperty = config.get("leveling", "weaponLevelingRatePercentage", 100);
        weaponLevelingRatePercentageProperty.comment = "Rate at which tools gain XP (higher=faster) (requires 'toolLeveling=true')";
        weaponLevelingRatePercentage = Math.max(weaponLevelingRatePercentageProperty.getInt(100), 1);
        weaponLevelingRatePercentageProperty.set(weaponLevelingRatePercentage);
        
        
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
        
        Property pickaxeHeadsProperty = config.get("heads", "pickaxeHeads", true);
        pickaxeHeadsProperty.comment = "Adds mob head drops that can boost pickaxes and nerfs cleaver";
        mobHeadModifiers = pickaxeHeadsProperty.getBoolean(true);
		
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

        Property easyBlankPatternRecipeProperty = config.get("other", "easyBlankPatternRecipe", true);
        easyBlankPatternRecipeProperty.comment = "Allows blank patterns to be crafted with 4 sticks in a square";
        easyBlankPatternRecipe = easyBlankPatternRecipeProperty.getBoolean(true);

        Property easyPartCraftingProperty = config.get("other", "easyPartCrafting", true);
        easyPartCraftingProperty.comment = "Allows you to rotate the the tier 1 patterns in a normal crafting window";
        easyPartCrafting = easyPartCraftingProperty.getBoolean(true);

        Property easyPatternCraftingProperty = config.get("other", "easyPatternCrafting", true);
        easyPatternCraftingProperty.comment = "Allows you to rotate the the tier 1 patterns in a normal crafting window";
        easyPatternCrafting = easyPatternCraftingProperty.getBoolean(true);

        Property easyToolCreationProperty = config.get("other", "easyToolCreation", true);
        easyToolCreationProperty.comment = "Allows you create tinkers tools in a normal crafting window";
        easyToolCreation = easyToolCreationProperty.getBoolean(true);

        Property easyToolModificationProperty = config.get("other", "easyToolModification", true);
        easyToolModificationProperty.comment = "Allows you add modifications to tools in a normal crafting window";
        easyToolModification = easyToolModificationProperty.getBoolean(true);
        
        
        // other
		ConfigCategory otherCategory = config.getCategory("other");
		otherCategory.setComment("Random stuff to configure here");

        Property cobaltArmorProperty = config.get("other", "cobaltArmor", false);
        cobaltArmorProperty.comment = "Changes diamond armor to cobalt armor (more expensive recipe)";
        cobaltArmor = cobaltArmorProperty.getBoolean(false);
        
        Property pickaxeBoostRequiredProperty = config.get("other", "pickaxeBoostRequired", true);
        pickaxeBoostRequiredProperty.comment = "Pickaxes only mine upto their head material level and need a mob head modifier OR leveling boost to advance";
        pickaxeBoostRequired = pickaxeBoostRequiredProperty.getBoolean(true);
        
        Property partReplacementProperty = config.get("other", "partReplacement", true);
        partReplacementProperty.comment = "Can you replace parts of existing tools? (If true, paper/thaumium doesn't give extra modifiers)";
        partReplacement = partReplacementProperty.getBoolean(true);
		
        Property moreExpensiveSilkTouchProperty = config.get("other", "moreExpensiveSilkTouch", true);
        moreExpensiveSilkTouchProperty.comment = "Silky Cloth needs gold ingots and Silky Jewels needs an emerald block";
        moreExpensiveSilkTouch = moreExpensiveSilkTouchProperty.getBoolean(true);
		
        Property moreExpensiveElectricProperty = config.get("other", "moreExpensiveElectric", true);
        moreExpensiveElectricProperty.comment = "Electric modifier requires 2 modifiers slots instead of 1";
        moreExpensiveElectric = moreExpensiveElectricProperty.getBoolean(true);
		
        Property removeFlintDropProperty = config.get("other", "removeFlintDrop", true);
        removeFlintDropProperty.comment = "Removes the random chance of getting flint from gravel";
        removeFlintDrop = removeFlintDropProperty.getBoolean(true);
		
        Property addFlintRecipeProperty = config.get("other", "addFlintRecipe", true);
        addFlintRecipeProperty.comment = "Adds a shapeless recipe to get flint from 4 gravel blocks";
        addFlintRecipe = addFlintRecipeProperty.getBoolean(true);
		
        Property removeStoneTorchRecipeProperty = config.get("other", "removeStoneTorchRecipe", true);
        removeStoneTorchRecipeProperty.comment = "Removes the recipe for Tinker's Construct's stone torch";
        removeStoneTorchRecipe = removeStoneTorchRecipeProperty.getBoolean(true);
		
        Property mossRepairSpeedProperty = config.get("other", "mossRepairSpeed", 3);
        mossRepairSpeedProperty.comment = "Rate tools with moss repair (TC default 3)";
        mossRepairSpeed = Math.max(mossRepairSpeedProperty.getInt(3), 0);
        mossRepairSpeedProperty.set(mossRepairSpeed);
		
        Property durabilityPercentageProperty = config.get("other", "durabilityPercentage", 100);
        durabilityPercentageProperty.comment = "Change durability of all materials here (higher = tougher)";
        durabilityPercentage = Math.max(durabilityPercentageProperty.getInt(100), 1);
        durabilityPercentageProperty.set(durabilityPercentage);
		
        Property miningSpeedPercentageProperty = config.get("other", "miningSpeedPercentage", 100);
        miningSpeedPercentageProperty.comment = "Change mining speed of all materials here (higher = faster)";
        miningSpeedPercentage = Math.max(miningSpeedPercentageProperty.getInt(100), 1);
        miningSpeedPercentageProperty.set(miningSpeedPercentage);
		
        Property redstoneEffectProperty = config.get("other", "redstoneEffect", 4);
        redstoneEffectProperty.comment = "Amount each piece of redstone increases mining speed (tinkers default is 8)";
        redstoneEffect = Math.max(redstoneEffectProperty.getInt(4), 1);
        redstoneEffectProperty.set(redstoneEffect);
        
        
        //restrictions
		ConfigCategory restrictionsCategory = config.getCategory("restrictions");
		restrictionsCategory.setComment("See config section of mod thread for list of pattern ids");
		
        Property allowStoneToolsProperty = config.get("restrictions", "allowStoneTools", false);
        allowStoneToolsProperty.comment = "Allow stone tools to be built in the tool station";
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
		
        Property restrictedPaperPartsProperty = config.get("restrictions", "restrictedPaperParts", new int[] {2,3,4,5,6,7,8,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25});
        restrictedBonePartsProperty.comment = "Pattern ids to restrict for paper parts";
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
        harvestLevel1IdsProperty.comment = "Block ids (each on seperate line) for blocks to be set to harvest level 1 (flString pick+)";
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
        
        
        config.save();
	}

}

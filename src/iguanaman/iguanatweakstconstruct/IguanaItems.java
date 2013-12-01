package iguanaman.iguanatweakstconstruct;

import tconstruct.common.TContent;
import tconstruct.items.MetalPattern;
import tconstruct.items.Pattern;
import tconstruct.util.config.PHConstruct;
import iguanaman.iguanatweakstconstruct.items.ClayBucket;
import iguanaman.iguanatweakstconstruct.items.ClayBucketFilled;
import iguanaman.iguanatweakstconstruct.items.ClayBucketMilk;
import iguanaman.iguanatweakstconstruct.items.IguanaItemSkull;
import iguanaman.iguanatweakstconstruct.items.IguanaMetalPattern;
import iguanaman.iguanatweakstconstruct.items.IguanaPattern;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.potion.Potion;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class IguanaItems {

    public static Item clayBucketLava;
    public static Item clayBucketWater;
    public static Item clayBucketMilk;
    public static Item clayBucketFired;
    public static Item clayBucketUnfired;
    public static Item clayBuckets;
    public static Item twinkie1;
    public static Item twinkie2;
    
	public static void init()
	{

		//CLAY BUCKETS
    	IguanaLog.log("Adding clay buckets");
        clayBucketUnfired = new Item(IguanaConfig.clayBucketUnfiredId).setUnlocalizedName("iguanatweakstconstruct:clayBucketUnfired").setTextureName("iguanatweakstconstruct:clayBucketUnfired").setMaxStackSize(16).setCreativeTab(CreativeTabs.tabMisc);
        clayBucketFired = new ClayBucket(IguanaConfig.clayBucketFiredId, 0).setUnlocalizedName("iguanatweakstconstruct:clayBucketFired").setTextureName("iguanatweakstconstruct:clayBucketFired").setMaxStackSize(16);
        clayBucketWater = new ClayBucket(IguanaConfig.clayBucketWaterId, Block.waterMoving.blockID).setUnlocalizedName("iguanatweakstconstruct:clayBucketWater").setTextureName("iguanatweakstconstruct:clayBucketWater").setContainerItem(clayBucketFired);
        clayBucketLava = new ClayBucket(IguanaConfig.clayBucketLavaId, Block.lavaMoving.blockID).setUnlocalizedName("iguanatweakstconstruct:clayBucketLava").setTextureName("iguanatweakstconstruct:clayBucketLava");
        clayBucketMilk = new ClayBucketMilk(IguanaConfig.clayBucketMilkId).setUnlocalizedName("iguanatweakstconstruct:clayBucketMilk").setTextureName("iguanatweakstconstruct:clayBucketMilk").setContainerItem(clayBucketFired);
        clayBuckets = new ClayBucketFilled(IguanaConfig.clayBucketsId);
        
        LanguageRegistry.addName(clayBucketUnfired, "Unfired Clay Bucket");
        LanguageRegistry.addName(clayBucketFired, "Clay Bucket");
        LanguageRegistry.addName(clayBucketWater, "Clay Bucket (Water)");
        LanguageRegistry.addName(clayBucketLava, "Clay Bucket (Lava)");
        LanguageRegistry.addName(clayBucketMilk, "Clay Bucket (Milk)");
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(clayBucketUnfired), "c c", " c ", 'c', new ItemStack(Item.clay)));
		GameRegistry.addSmelting(clayBucketUnfired.itemID, new ItemStack(clayBucketFired), 0.0F);
		OreDictionary.registerOre("listAllmilk", clayBucketMilk);

		ItemStack emptyClayBucket = new ItemStack(clayBucketFired);
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.WATER, new ItemStack(clayBucketWater), emptyClayBucket);
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.LAVA, new ItemStack(clayBucketLava), emptyClayBucket);
		
		
		//SKULL ITEM
		IguanaLog.log("Adding skull item");
        Item.itemsList[Item.skull.itemID] = null;
        Item.skull = (new IguanaItemSkull(141)).setUnlocalizedName("skull").setTextureName("skull");
        LanguageRegistry.addName(new ItemStack(Item.skull, 1, 5), "Enderman Head");
        LanguageRegistry.addName(new ItemStack(Item.skull, 1, 6), "Zombie Pigman Head");
        LanguageRegistry.addName(new ItemStack(Item.skull, 1, 7), "Blaze Head");
		
		
		//COBALT ARMOR
		if (IguanaConfig.cobaltArmor)
		{
			IguanaLog.log("Changing diamond armor to cobalt armor");
			
	        LanguageRegistry.addName(new ItemStack(Item.helmetDiamond), "Cobalt Helmet");
	        LanguageRegistry.addName(new ItemStack(Item.plateDiamond), "Cobalt Chestplate");
	        LanguageRegistry.addName(new ItemStack(Item.bootsDiamond), "Cobalt Boots");
	        LanguageRegistry.addName(new ItemStack(Item.legsDiamond), "Cobalt Leggings");
	        RecipeRemover.removeAnyRecipe(new ItemStack(Item.helmetDiamond));
	        RecipeRemover.removeAnyRecipe(new ItemStack(Item.plateDiamond));
	        RecipeRemover.removeAnyRecipe(new ItemStack(Item.bootsDiamond));
	        RecipeRemover.removeAnyRecipe(new ItemStack(Item.legsDiamond));
	        String[][] recipePatterns = new String[][] {{"XXX", "X X"}, {"X X", "XXX", "XXX"}, {"XXX", "X X", "X X"}, {"X X", "X X"}};
	        Object[] recipeItems = new Object[] {Item.helmetDiamond, Item.plateDiamond, Item.legsDiamond, Item.bootsDiamond};
	
	        for (int j = 0; j < recipeItems.length; ++j)
	        {
	            Item item = (Item)recipeItems[j];
	            CraftingManager.getInstance().addRecipe(new ItemStack(item), new Object[] {recipePatterns[j], 'X', new ItemStack(TContent.materials, 1, 3)});
	        }
		}
		
		
		// NEW TCON PATTERNS
		/*
		Item.itemsList[TContent.woodPattern.itemID] = null;
		Item.itemsList[TContent.metalPattern.itemID] = null;
		TContent.woodPattern = new IguanaPattern(PHConstruct.woodPattern, "pattern_", "materials/").setUnlocalizedName("tconstruct.Pattern");
		TContent.metalPattern = new IguanaMetalPattern(PHConstruct.metalPattern, "cast_", "materials/").setUnlocalizedName("tconstruct.MetalPattern");
        */
		
		
        //EASTER EGGS
        if (Item.itemsList[IguanaConfig.easterEggId1] == null && Item.itemsList[IguanaConfig.easterEggId2] == null)
        {
        	IguanaLog.log("Adding easter eggs");
            twinkie1 = new ItemFood(IguanaConfig.easterEggId1, 0, false).setAlwaysEdible().setPotionEffect(Potion.moveSpeed.id, 5, 5, 1.0F).setUnlocalizedName("iguanatweakstconstruct:twinkie1").setTextureName("iguanatweakstconstruct:twinkie1");
            LanguageRegistry.addName(twinkie1, "Materialize127 Twinkie");
            twinkie2 = new ItemFood(IguanaConfig.easterEggId2, 0, false).setAlwaysEdible().setPotionEffect(Potion.invisibility.id, 30, 1, 1.0F).setUnlocalizedName("iguanatweakstconstruct:twinkie2").setTextureName("iguanatweakstconstruct:twinkie2");
            LanguageRegistry.addName(twinkie2, "VoxKnight Twinkie");
        }
        
        
	}
	
}

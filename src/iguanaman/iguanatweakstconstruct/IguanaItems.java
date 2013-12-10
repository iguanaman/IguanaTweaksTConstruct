package iguanaman.iguanatweakstconstruct;

import tconstruct.common.TContent;
import tconstruct.items.Bowstring;
import tconstruct.items.Fletching;
import tconstruct.items.MetalPattern;
import tconstruct.items.Pattern;
import tconstruct.items.ToolPart;
import tconstruct.items.ToolPartHidden;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.crafting.ToolBuilder;
import tconstruct.library.util.IPattern;
import tconstruct.util.config.PHConstruct;
import iguanaman.iguanatweakstconstruct.items.ClayBucket;
import iguanaman.iguanatweakstconstruct.items.ClayBucketFilled;
import iguanaman.iguanatweakstconstruct.items.ClayBucketMilk;
import iguanaman.iguanatweakstconstruct.items.IguanaItemSkull;
import iguanaman.iguanatweakstconstruct.items.IguanaMetalPattern;
import iguanaman.iguanatweakstconstruct.items.IguanaPattern;
import iguanaman.iguanatweakstconstruct.items.IguanaToolPart;
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
		
		
		//TCON PARTS
        TContent.toolRod = new IguanaToolPart(PHConstruct.toolRod, "_rod", "ToolRod").setUnlocalizedName("tconstruct.ToolRod");
        TContent.pickaxeHead = new IguanaToolPart(PHConstruct.pickaxeHead, "_pickaxe_head", "PickHead").setUnlocalizedName("tconstruct.PickaxeHead");
        TContent.shovelHead = new IguanaToolPart(PHConstruct.shovelHead, "_shovel_head", "ShovelHead").setUnlocalizedName("tconstruct.ShovelHead");
        TContent.hatchetHead = new IguanaToolPart(PHConstruct.axeHead, "_axe_head", "AxeHead").setUnlocalizedName("tconstruct.AxeHead");
        TContent.binding = new IguanaToolPart(PHConstruct.binding, "_binding", "Binding").setUnlocalizedName("tconstruct.Binding");
        TContent.toughBinding = new IguanaToolPart(PHConstruct.toughBinding, "_toughbind", "ToughBind").setUnlocalizedName("tconstruct.ThickBinding");
        TContent.toughRod = new IguanaToolPart(PHConstruct.toughRod, "_toughrod", "ToughRod").setUnlocalizedName("tconstruct.ThickRod");
        TContent.largePlate = new IguanaToolPart(PHConstruct.largePlate, "_largeplate", "LargePlate").setUnlocalizedName("tconstruct.LargePlate");

        TContent.swordBlade = new IguanaToolPart(PHConstruct.swordBlade, "_sword_blade", "SwordBlade").setUnlocalizedName("tconstruct.SwordBlade");
        TContent.wideGuard = new IguanaToolPart(PHConstruct.largeGuard, "_large_guard", "LargeGuard").setUnlocalizedName("tconstruct.LargeGuard");
        TContent.handGuard = new IguanaToolPart(PHConstruct.medGuard, "_medium_guard", "MediumGuard").setUnlocalizedName("tconstruct.MediumGuard");
        TContent.crossbar = new IguanaToolPart(PHConstruct.crossbar, "_crossbar", "Crossbar").setUnlocalizedName("tconstruct.Crossbar");
        TContent.knifeBlade = new IguanaToolPart(PHConstruct.knifeBlade, "_knife_blade", "KnifeBlade").setUnlocalizedName("tconstruct.KnifeBlade");
        TContent.fullGuard = new IguanaToolPart(PHConstruct.fullGuard, "_full_guard", "FullGuard").setUnlocalizedName("tconstruct.FullGuard");

        TContent.frypanHead = new IguanaToolPart(PHConstruct.frypanHead, "_frypan_head", "FrypanHead").setUnlocalizedName("tconstruct.FrypanHead");
        TContent.signHead = new IguanaToolPart(PHConstruct.signHead, "_battlesign_head", "SignHead").setUnlocalizedName("tconstruct.SignHead");
        TContent.chiselHead = new IguanaToolPart(PHConstruct.chiselHead, "_chisel_head", "ChiselHead").setUnlocalizedName("tconstruct.ChiselHead");

        TContent.scytheBlade = new IguanaToolPart(PHConstruct.scytheBlade, "_scythe_head", "ScytheHead").setUnlocalizedName("tconstruct.ScytheBlade");
        TContent.broadAxeHead = new IguanaToolPart(PHConstruct.lumberHead, "_lumberaxe_head", "LumberHead").setUnlocalizedName("tconstruct.LumberHead");
        TContent.excavatorHead = new IguanaToolPart(PHConstruct.excavatorHead, "_excavator_head", "ExcavatorHead").setUnlocalizedName("tconstruct.ExcavatorHead");
        TContent.largeSwordBlade = new IguanaToolPart(PHConstruct.largeSwordBlade, "_large_sword_blade", "LargeSwordBlade").setUnlocalizedName("tconstruct.LargeSwordBlade");
        TContent.hammerHead = new IguanaToolPart(PHConstruct.hammerHead, "_hammer_head", "HammerHead").setUnlocalizedName("tconstruct.HammerHead");

        TContent.arrowhead = new IguanaToolPart(PHConstruct.arrowhead, "_arrowhead", "ArrowHead").setUnlocalizedName("tconstruct.Arrowhead");
        

        // RE-ADD TOOL RECIPES
        ToolBuilder tb = ToolBuilder.instance;
        tb.addNormalToolRecipe(TContent.pickaxe, TContent.pickaxeHead, TContent.toolRod, TContent.binding);
        tb.addNormalToolRecipe(TContent.broadsword, TContent.swordBlade, TContent.toolRod, TContent.wideGuard);
        tb.addNormalToolRecipe(TContent.hatchet, TContent.hatchetHead, TContent.toolRod);
        tb.addNormalToolRecipe(TContent.shovel, TContent.shovelHead, TContent.toolRod);
        tb.addNormalToolRecipe(TContent.longsword, TContent.swordBlade, TContent.toolRod, TContent.handGuard);
        tb.addNormalToolRecipe(TContent.rapier, TContent.swordBlade, TContent.toolRod, TContent.crossbar);
        tb.addNormalToolRecipe(TContent.frypan, TContent.frypanHead, TContent.toolRod);
        tb.addNormalToolRecipe(TContent.battlesign, TContent.signHead, TContent.toolRod);
        tb.addNormalToolRecipe(TContent.mattock, TContent.hatchetHead, TContent.toolRod, TContent.shovelHead);
        tb.addNormalToolRecipe(TContent.dagger, TContent.knifeBlade, TContent.toolRod, TContent.crossbar);
        tb.addNormalToolRecipe(TContent.cutlass, TContent.swordBlade, TContent.toolRod, TContent.fullGuard);
        tb.addNormalToolRecipe(TContent.chisel, TContent.chiselHead, TContent.toolRod);

        tb.addNormalToolRecipe(TContent.scythe, TContent.scytheBlade, TContent.toughRod, TContent.toughBinding, TContent.toughRod);
        tb.addNormalToolRecipe(TContent.lumberaxe, TContent.broadAxeHead, TContent.toughRod, TContent.largePlate, TContent.toughBinding);
        tb.addNormalToolRecipe(TContent.cleaver, TContent.largeSwordBlade, TContent.toughRod, TContent.largePlate, TContent.toughRod);
        tb.addNormalToolRecipe(TContent.excavator, TContent.excavatorHead, TContent.toughRod, TContent.largePlate, TContent.toughBinding);
        tb.addNormalToolRecipe(TContent.hammer, TContent.hammerHead, TContent.toughRod, TContent.largePlate, TContent.largePlate);
        tb.addNormalToolRecipe(TContent.battleaxe, TContent.broadAxeHead, TContent.toughRod, TContent.broadAxeHead, TContent.toughBinding);
		
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
		Item.itemsList[TContent.woodPattern.itemID] = null;
		//Item.itemsList[TContent.metalPattern.itemID] = null;
		TContent.woodPattern = new IguanaPattern(PHConstruct.woodPattern, "pattern_", "materials/").setUnlocalizedName("tconstruct.Pattern");
		//TContent.metalPattern = new IguanaMetalPattern(PHConstruct.metalPattern, "cast_", "materials/").setUnlocalizedName("tconstruct.MetalPattern");
        PatternBuilder.instance.addToolPattern((IPattern) TContent.woodPattern);
        
        
	}
	
}

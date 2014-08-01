package iguanaman.iguanatweakstconstruct.old;

import iguanaman.iguanatweakstconstruct.reference.*;
import iguanaman.iguanatweakstconstruct.util.Log;
import iguanaman.iguanatweakstconstruct.old.items.ClayBucket;
import iguanaman.iguanatweakstconstruct.old.items.ClayBucketFilled;
import iguanaman.iguanatweakstconstruct.old.items.ClayBucketMilk;
import iguanaman.iguanatweakstconstruct.old.items.IguanaPattern;
import iguanaman.iguanatweakstconstruct.old.items.IguanaToolPart;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.crafting.ToolBuilder;
import tconstruct.library.util.IPattern;
import tconstruct.tools.BowRecipe;
import tconstruct.tools.TinkerTools;
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
		Log.info("Adding clay buckets");
		clayBucketUnfired = new Item().setUnlocalizedName("iguanatweakstconstruct:clayBucketUnfired").setTextureName("iguanatweakstconstruct:clayBucketUnfired").setMaxStackSize(16).setCreativeTab(CreativeTabs.tabMisc);
		clayBucketFired = new ClayBucket(Blocks.air).setUnlocalizedName("iguanatweakstconstruct:clayBucketFired").setTextureName("iguanatweakstconstruct:clayBucketFired").setMaxStackSize(16);
		clayBucketWater = new ClayBucket(Blocks.flowing_water).setUnlocalizedName("iguanatweakstconstruct:clayBucketWater").setTextureName("iguanatweakstconstruct:clayBucketWater").setContainerItem(clayBucketFired);
		clayBucketLava = new ClayBucket(Blocks.flowing_lava).setUnlocalizedName("iguanatweakstconstruct:clayBucketLava").setTextureName("iguanatweakstconstruct:clayBucketLava");
		clayBucketMilk = new ClayBucketMilk().setUnlocalizedName("iguanatweakstconstruct:clayBucketMilk").setTextureName("iguanatweakstconstruct:clayBucketMilk").setContainerItem(clayBucketFired);
		clayBuckets = new ClayBucketFilled(Blocks.air);

		LanguageRegistry.addName(clayBucketUnfired, "Unfired Clay Bucket");
		LanguageRegistry.addName(clayBucketFired, "Clay Bucket");
		LanguageRegistry.addName(clayBucketWater, "Clay Bucket (Water)");
		LanguageRegistry.addName(clayBucketLava, "Clay Bucket (Lava)");
		LanguageRegistry.addName(clayBucketMilk, "Clay Bucket (Milk)");
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(clayBucketUnfired), "c c", " c ", 'c', new ItemStack(Items.clay_ball)));
		GameRegistry.addSmelting(clayBucketUnfired, new ItemStack(clayBucketFired), 0.0F);
		OreDictionary.registerOre("listAllmilk", clayBucketMilk);

		ItemStack emptyClayBucket = new ItemStack(clayBucketFired);
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.WATER, new ItemStack(clayBucketWater), emptyClayBucket);
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.LAVA, new ItemStack(clayBucketLava), emptyClayBucket);


		//TCON PARTS
		TinkerTools.toolRod = new IguanaToolPart("_rod", "ToolRod").setUnlocalizedName("tconstruct.ToolRod");
		TinkerTools.pickaxeHead = new IguanaToolPart("_pickaxe_head", "PickHead").setUnlocalizedName("tconstruct.PickaxeHead");
		TinkerTools.shovelHead = new IguanaToolPart("_shovel_head", "ShovelHead").setUnlocalizedName("tconstruct.ShovelHead");
		TinkerTools.hatchetHead = new IguanaToolPart("_axe_head", "AxeHead").setUnlocalizedName("tconstruct.AxeHead");
		TinkerTools.binding = new IguanaToolPart("_binding", "Binding").setUnlocalizedName("tconstruct.Binding");
		TinkerTools.toughBinding = new IguanaToolPart("_toughbind", "ToughBind").setUnlocalizedName("tconstruct.ThickBinding");
		TinkerTools.toughRod = new IguanaToolPart("_toughrod", "ToughRod").setUnlocalizedName("tconstruct.ThickRod");
		TinkerTools.largePlate = new IguanaToolPart("_largeplate", "LargePlate").setUnlocalizedName("tconstruct.LargePlate");

		TinkerTools.swordBlade = new IguanaToolPart("_sword_blade", "SwordBlade").setUnlocalizedName("tconstruct.SwordBlade");
		TinkerTools.wideGuard = new IguanaToolPart("_large_guard", "LargeGuard").setUnlocalizedName("tconstruct.LargeGuard");
		TinkerTools.handGuard = new IguanaToolPart("_medium_guard", "MediumGuard").setUnlocalizedName("tconstruct.MediumGuard");
		TinkerTools.crossbar = new IguanaToolPart("_crossbar", "Crossbar").setUnlocalizedName("tconstruct.Crossbar");
		TinkerTools.knifeBlade = new IguanaToolPart("_knife_blade", "KnifeBlade").setUnlocalizedName("tconstruct.KnifeBlade");
		TinkerTools.fullGuard = new IguanaToolPart("_full_guard", "FullGuard").setUnlocalizedName("tconstruct.FullGuard");

		TinkerTools.frypanHead = new IguanaToolPart("_frypan_head", "FrypanHead").setUnlocalizedName("tconstruct.FrypanHead");
		TinkerTools.signHead = new IguanaToolPart("_battlesign_head", "SignHead").setUnlocalizedName("tconstruct.SignHead");
		TinkerTools.chiselHead = new IguanaToolPart("_chisel_head", "ChiselHead").setUnlocalizedName("tconstruct.ChiselHead");

		TinkerTools.scytheBlade = new IguanaToolPart("_scythe_head", "ScytheHead").setUnlocalizedName("tconstruct.ScytheBlade");
		TinkerTools.broadAxeHead = new IguanaToolPart("_lumberaxe_head", "LumberHead").setUnlocalizedName("tconstruct.LumberHead");
		TinkerTools.excavatorHead = new IguanaToolPart("_excavator_head", "ExcavatorHead").setUnlocalizedName("tconstruct.ExcavatorHead");
		TinkerTools.largeSwordBlade = new IguanaToolPart("_large_sword_blade", "LargeSwordBlade").setUnlocalizedName("tconstruct.LargeSwordBlade");
		TinkerTools.hammerHead = new IguanaToolPart("_hammer_head", "HammerHead").setUnlocalizedName("tconstruct.HammerHead");

		TinkerTools.arrowhead = new IguanaToolPart("_arrowhead", "ArrowHead").setUnlocalizedName("tconstruct.Arrowhead");


		// RE-ADD TOOL RECIPES
		ToolBuilder tb = ToolBuilder.instance;
		ToolBuilder.addNormalToolRecipe(TinkerTools.pickaxe, TinkerTools.pickaxeHead, TinkerTools.toolRod, TinkerTools.binding);
		ToolBuilder.addNormalToolRecipe(TinkerTools.broadsword, TinkerTools.swordBlade, TinkerTools.toolRod, TinkerTools.wideGuard);
		ToolBuilder.addNormalToolRecipe(TinkerTools.hatchet, TinkerTools.hatchetHead, TinkerTools.toolRod);
		ToolBuilder.addNormalToolRecipe(TinkerTools.shovel, TinkerTools.shovelHead, TinkerTools.toolRod);
		ToolBuilder.addNormalToolRecipe(TinkerTools.longsword, TinkerTools.swordBlade, TinkerTools.toolRod, TinkerTools.handGuard);
		ToolBuilder.addNormalToolRecipe(TinkerTools.rapier, TinkerTools.swordBlade, TinkerTools.toolRod, TinkerTools.crossbar);
		ToolBuilder.addNormalToolRecipe(TinkerTools.frypan, TinkerTools.frypanHead, TinkerTools.toolRod);
		ToolBuilder.addNormalToolRecipe(TinkerTools.battlesign, TinkerTools.signHead, TinkerTools.toolRod);
		ToolBuilder.addNormalToolRecipe(TinkerTools.mattock, TinkerTools.hatchetHead, TinkerTools.toolRod, TinkerTools.shovelHead);
		ToolBuilder.addNormalToolRecipe(TinkerTools.dagger, TinkerTools.knifeBlade, TinkerTools.toolRod, TinkerTools.crossbar);
		ToolBuilder.addNormalToolRecipe(TinkerTools.cutlass, TinkerTools.swordBlade, TinkerTools.toolRod, TinkerTools.fullGuard);
		ToolBuilder.addNormalToolRecipe(TinkerTools.chisel, TinkerTools.chiselHead, TinkerTools.toolRod);

		ToolBuilder.addNormalToolRecipe(TinkerTools.scythe, TinkerTools.scytheBlade, TinkerTools.toughRod, TinkerTools.toughBinding, TinkerTools.toughRod);
		ToolBuilder.addNormalToolRecipe(TinkerTools.lumberaxe, TinkerTools.broadAxeHead, TinkerTools.toughRod, TinkerTools.largePlate, TinkerTools.toughBinding);
		ToolBuilder.addNormalToolRecipe(TinkerTools.cleaver, TinkerTools.largeSwordBlade, TinkerTools.toughRod, TinkerTools.largePlate, TinkerTools.toughRod);
		ToolBuilder.addNormalToolRecipe(TinkerTools.excavator, TinkerTools.excavatorHead, TinkerTools.toughRod, TinkerTools.largePlate, TinkerTools.toughBinding);
		ToolBuilder.addNormalToolRecipe(TinkerTools.hammer, TinkerTools.hammerHead, TinkerTools.toughRod, TinkerTools.largePlate, TinkerTools.largePlate);
		ToolBuilder.addNormalToolRecipe(TinkerTools.battleaxe, TinkerTools.broadAxeHead, TinkerTools.toughRod, TinkerTools.broadAxeHead, TinkerTools.toughBinding);

		ToolBuilder.addNormalToolRecipe(TinkerTools.arrow, TinkerTools.arrowhead, TinkerTools.toolRod, TinkerTools.fletching);
		ToolBuilder.addCustomToolRecipe(new BowRecipe(TinkerTools.toolRod, TinkerTools.bowstring, TinkerTools.toolRod, TinkerTools.shortbow));

		//SKULL ITEM
		Log.info("Adding skullItem item");
		// TODO: find a way to replace vanilla skullItem
		/*Item.itemsList[Item.skullItem.itemID] = null;
		Item.skullItem = new IguanaItemSkull(141).setUnlocalizedName("skullItem").setTextureName("skullItem");
		LanguageRegistry.addName(new ItemStack(Item.skullItem, 1, 5), "Enderman Head");
		LanguageRegistry.addName(new ItemStack(Item.skullItem, 1, 6), "Zombie Pigman Head");
		LanguageRegistry.addName(new ItemStack(Item.skullItem, 1, 7), "Blaze Head");*/


		//COBALT ARMOR
		if (IguanaConfig.cobaltArmor)
		{
			Log.info("Changing diamond armor to cobalt armor");

			// TODO: Implement Cobalt Armor
			/*LanguageRegistry.addName(new ItemStack(Item.helmetDiamond), "Cobalt Helmet");
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
			}*/
		}


		// NEW TCON PATTERNS
		TinkerTools.woodPattern = null;
		//Item.itemsList[TContent.metalPattern.itemID] = null;
		TinkerTools.woodPattern = new IguanaPattern("pattern_", "materials/").setUnlocalizedName("tconstruct.Pattern");
		//TContent.metalPattern = new IguanaMetalPattern(PHConstruct.metalPattern, "cast_", "materials/").setUnlocalizedName("tconstruct.MetalPattern");
		PatternBuilder.instance.addToolPattern((IPattern) TinkerTools.woodPattern);

	}

}

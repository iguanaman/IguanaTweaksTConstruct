package iguanaman.iguanatweakstconstruct.old.tweaks;

import iguanaman.iguanatweakstconstruct.reference.Config;
import iguanaman.iguanatweakstconstruct.util.Log;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ChestGenHooks;

public class RemoveVanillaTools {

	public static void init()
	{
		//Remove vanilla tools
		Log.info("Removing vanilla tool recipes");

		//Wood
		if (Config.restrictedWoodParts.contains(1) || Config.restrictedWoodParts.contains(2) || Config.restrictedWoodParts.contains(9))
			RemoveVanillaTool(Items.wooden_pickaxe);

		if (Config.restrictedWoodParts.contains(1) || Config.restrictedWoodParts.contains(3))
			RemoveVanillaTool(Items.wooden_shovel);

		if (Config.restrictedWoodParts.contains(1) || Config.restrictedWoodParts.contains(4))
			RemoveVanillaTool(Items.wooden_axe);

		if (Config.restrictedWoodParts.contains(1) || Config.restrictedWoodParts.contains(5) || Config.restrictedWoodParts.contains(6))
			RemoveVanillaTool(Items.wooden_sword);

		//Stone
		if (!Config.allowStoneTools || Config.restrictedWoodParts.contains(1)
				|| Config.restrictedFlintParts.contains(2) || Config.restrictedWoodParts.contains(9))
			RemoveVanillaTool(Items.stone_pickaxe);

		if (!Config.allowStoneTools || Config.restrictedWoodParts.contains(1) || Config.restrictedFlintParts.contains(3))
			RemoveVanillaTool(Items.stone_shovel);

		if (!Config.allowStoneTools || Config.restrictedWoodParts.contains(1) || Config.restrictedFlintParts.contains(4))
			RemoveVanillaTool(Items.stone_axe);

		if (!Config.allowStoneTools || Config.restrictedWoodParts.contains(1) || Config.restrictedFlintParts.contains(5) || Config.restrictedWoodParts.contains(6))
			RemoveVanillaTool(Items.stone_sword);
	}

	public static void RemoveVanillaTool(Item item)
	{
		RecipeRemover.removeAnyRecipe(new ItemStack(item));
		ChestGenHooks.getInfo(ChestGenHooks.BONUS_CHEST).removeItem(new ItemStack(item));
	}
}

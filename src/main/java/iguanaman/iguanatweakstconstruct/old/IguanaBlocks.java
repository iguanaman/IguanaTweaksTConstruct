package iguanaman.iguanatweakstconstruct.old;

import cpw.mods.fml.common.registry.GameRegistry;
import iguanaman.iguanatweakstconstruct.old.blocks.IguanaToolForgeBlock;
import iguanaman.iguanatweakstconstruct.old.blocks.IguanaToolStationBlock;
import iguanaman.iguanatweakstconstruct.util.Log;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import tconstruct.common.itemblocks.MetadataItemBlock;
import tconstruct.tools.TinkerTools;
import tconstruct.tools.itemblocks.ToolStationItemBlock;

public class IguanaBlocks {

	public static Block gravelNew;
	public static Block newSkullBlock;
	public static Block iguanaToolStationWood;

	public static void init()
	{

		//TOOL STATION + FORGE
		Log.info("Modifying GUIs");

		TinkerTools.toolStationWood = null;
		TinkerTools.toolStationWood = new IguanaToolStationBlock(Material.wood).setBlockName("ToolStation");
		GameRegistry.registerBlock(TinkerTools.toolStationWood, ToolStationItemBlock.class, "ToolStationBlock");

		TinkerTools.toolForge = null;
		TinkerTools.toolForge = new IguanaToolForgeBlock(Material.iron).setBlockName("ToolForge");
		GameRegistry.registerBlock(TinkerTools.toolForge, MetadataItemBlock.class, "ToolForgeBlock");

		//TinkerWorld.oreGravel = null;
		//TinkerWorld.oreGravel = new IguanaGravelOre().setBlockName("tconstruct.gravelore");
		//GameRegistry.registerBlock(TinkerWorld.oreGravel, GravelOreItem.class, "GravelOre");


		//SKULLS
		Log.info("Adding skullItem blocks");
		// TODO: Find a way to change vanilla skulls
		/*Blocks.skullItem = null;
		newSkullBlock = new IguanaBlockSkull().setHardness(1.0F).setStepSound(Block.soundTypeSand).setBlockName("skullItem").setBlockTextureName("skullItem");
		Blocks.skullItem = newSkullBlock;
		GameRegistry.registerBlock(newSkullBlock, "Skull");
		GameRegistry.registerTileEntity(IguanaTileEntitySkull.class, "SkullEntity");*/


	}

}

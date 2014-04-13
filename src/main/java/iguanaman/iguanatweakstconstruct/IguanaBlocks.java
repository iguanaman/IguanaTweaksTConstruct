package iguanaman.iguanatweakstconstruct;

import iguanaman.iguanatweakstconstruct.blocks.IguanaBlockSkull;
import iguanaman.iguanatweakstconstruct.blocks.IguanaGravelOre;
import iguanaman.iguanatweakstconstruct.blocks.IguanaTileEntitySkull;
import iguanaman.iguanatweakstconstruct.blocks.IguanaToolForgeBlock;
import iguanaman.iguanatweakstconstruct.blocks.IguanaToolStationBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import tconstruct.common.TContent;
import tconstruct.util.config.PHConstruct;
import cpw.mods.fml.common.registry.GameRegistry;

public class IguanaBlocks {

	public static Block gravelNew;
	public static Block newSkullBlock;
	public static Block iguanaToolStationWood;

	public static void init()
	{

		//TOOL STATION + FORGE
		IguanaLog.log("Modifying GUIs");

		Block.blocksList[TContent.toolStationWood.blockID] = null;
		TContent.toolStationWood = new IguanaToolStationBlock(PHConstruct.woodStation, Material.wood).setUnlocalizedName("ToolStation");

		Block.blocksList[TContent.toolForge.blockID] = null;
		TContent.toolForge = new IguanaToolForgeBlock(PHConstruct.toolForge, Material.iron).setUnlocalizedName("ToolForge");

		Block.blocksList[TContent.oreGravel.blockID] = null;
		TContent.oreGravel = new IguanaGravelOre(PHConstruct.oreGravel).setUnlocalizedName("GravelOre").setUnlocalizedName("tconstruct.gravelore");


		//SKULLS
		IguanaLog.log("Adding skull blocks");
		Block.blocksList[Block.skull.blockID] = null;
		newSkullBlock = new IguanaBlockSkull(144).setHardness(1.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("skull").setTextureName("skull");
		GameRegistry.registerBlock(newSkullBlock, "Skull");
		GameRegistry.registerTileEntity(IguanaTileEntitySkull.class, "SkullEntity");


	}

}

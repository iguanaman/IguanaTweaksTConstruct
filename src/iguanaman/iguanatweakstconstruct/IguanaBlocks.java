package iguanaman.iguanatweakstconstruct;

import iguanaman.iguanatweakstconstruct.blocks.IguanaBlockSkull;
import iguanaman.iguanatweakstconstruct.blocks.IguanaTileEntitySkull;
import iguanaman.iguanatweakstconstruct.blocks.IguanaToolForgeBlock;
import iguanaman.iguanatweakstconstruct.blocks.IguanaToolStationBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.MinecraftForge;
import tconstruct.common.TContent;
import tconstruct.util.config.PHConstruct;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

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
        
        
        //SKULLS
    	IguanaLog.log("Adding skull blocks");
        Block.blocksList[Block.skull.blockID] = null;
        newSkullBlock = (new IguanaBlockSkull(144)).setHardness(1.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("skull").setTextureName("skull");
        GameRegistry.registerBlock(newSkullBlock, "Skull");
        GameRegistry.registerTileEntity(IguanaTileEntitySkull.class, "SkullEntity");
        
        
	}
	
}

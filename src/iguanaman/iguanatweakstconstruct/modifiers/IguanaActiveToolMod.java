package iguanaman.iguanatweakstconstruct.modifiers;

import java.util.Arrays;
import java.util.List;

import iguanaman.iguanatweakstconstruct.IguanaLevelingLogic;
import iguanaman.iguanatweakstconstruct.IguanaLog;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import tconstruct.common.TContent;
import tconstruct.items.tools.*;
import tconstruct.library.ActiveToolMod;
import tconstruct.library.tools.ToolCore;
import tconstruct.library.tools.ToolMod;
import tconstruct.modifiers.ModAntiSpider;
import tconstruct.modifiers.ModAttack;
import tconstruct.modifiers.ModBlaze;
import tconstruct.modifiers.ModDurability;
import tconstruct.modifiers.ModInteger;
import tconstruct.modifiers.ModPiston;
import tconstruct.modifiers.ModRedstone;
import tconstruct.modifiers.ModReinforced;
import tconstruct.modifiers.ModSmite;
import tconstruct.library.tools.HarvestTool;
import tconstruct.library.tools.Weapon;

public class IguanaActiveToolMod extends ActiveToolMod {

    static List<Material> materialBlacklist = Arrays.asList(
    		Material.leaves, Material.vine, Material.circuits, 
    		Material.glass, Material.piston, Material.snow
    		);
    
    /* Harvesting */
    @Override
    public boolean beforeBlockBreak (ToolCore tool, ItemStack stack, int x, int y, int z, EntityLivingBase entity)
    {
    	if (!(entity instanceof EntityPlayer)) return false;

        int bID = entity.worldObj.getBlockId(x, y, z);
        int meta = entity.worldObj.getBlockMetadata(x, y, z);
        Block block = Block.blocksList[bID];
    	
        if (block == null || materialBlacklist.contains(block.blockMaterial)) return false;

		NBTTagCompound tags = stack.getTagCompound().getCompoundTag("InfiTool");	

        int miningSpeed = tags.hasKey("MiningSpeed") ? tags.getInteger("MiningSpeed"): -1;
        int miningSpeed2 = tags.hasKey("MiningSpeed2") ? tags.getInteger("MiningSpeed2"): -1;
        int miningSpeedHandle = tags.hasKey("MiningSpeedHandle") ? tags.getInteger("MiningSpeedHandle"): -1;
        int miningSpeedExtra = tags.hasKey("MiningSpeedExtra") ? tags.getInteger("MiningSpeedExtra"): -1;

        if (miningSpeed > 0) tags.setInteger("MiningSpeed", miningSpeed * 2);
        if (miningSpeed2 > 0) tags.setInteger("MiningSpeed2", miningSpeed2 * 2);
        if (miningSpeedHandle > 0) tags.setInteger("MiningSpeedHandle", miningSpeedHandle * 2);
        if (miningSpeedExtra > 0) tags.setInteger("MiningSpeedExtra", miningSpeedExtra * 2);

    	//IguanaLog.log(tool.canHarvestBlock(block) + " " + Float.toString(tool.getStrVsBlock(stack, block, meta)));
        
        if (tool.canHarvestBlock(block) && tool.getStrVsBlock(stack, block, meta) > 1f) 
        {
        	//IguanaLog.log("xp added");
        	IguanaLevelingLogic.addXP(stack, (EntityPlayer)entity, 1L);
        }

        if (miningSpeed > 0) tags.setInteger("MiningSpeed", miningSpeed);
        if (miningSpeed2 > 0) tags.setInteger("MiningSpeed2", miningSpeed2);
        if (miningSpeedHandle > 0) tags.setInteger("MiningSpeedHandle", miningSpeedHandle);
        if (miningSpeedExtra > 0) tags.setInteger("MiningSpeedExtra", miningSpeedExtra);
        
    	return false;
    }

}

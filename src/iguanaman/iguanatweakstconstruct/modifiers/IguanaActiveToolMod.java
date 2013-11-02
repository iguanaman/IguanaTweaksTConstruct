package iguanaman.iguanatweakstconstruct.modifiers;

import iguanaman.iguanatweakstconstruct.IguanaLevelingLogic;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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

	
    /* Harvesting */
    @Override
    public boolean beforeBlockBreak (ToolCore tool, ItemStack stack, int x, int y, int z, EntityLivingBase entity)
    {
    	if (!(entity instanceof EntityPlayer))
    		return false;
    	
    	String[] toolTypes = IguanaLevelingLogic.getHarvestType(tool);
    	
    	if (toolTypes == null)
    		return false;
    	
		EntityPlayer player = (EntityPlayer)entity;

        NBTTagCompound tags = stack.getTagCompound().getCompoundTag("InfiTool");
        World world = entity.worldObj;
        int bID = entity.worldObj.getBlockId(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        Block block = Block.blocksList[bID];
        if (block == null || bID < 1 || bID > 4095 || block instanceof BlockLeaves)
            return false;
        
        boolean allow = false;
        
        for (String toolType : toolTypes)
        {
        	int hlvl = MinecraftForge.getBlockHarvestLevel(block, meta, toolType);

            if (hlvl >= 0)
            {
            	allow = true;
            	break;
            }
        }
        
        IguanaLevelingLogic.addXP(stack, player, 1L);
        
    	return false;
    }
    
}

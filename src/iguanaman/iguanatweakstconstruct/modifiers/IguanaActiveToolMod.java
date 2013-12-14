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
        
        if (tool.canHarvestBlock(block) && tool.getStrVsBlock(stack, block, meta) > 1f) 
        {
        	//IguanaLog.log("xp added");
        	IguanaLevelingLogic.addXP(stack, (EntityPlayer)entity, 1L);
        }
        
    	return false;
    }

}

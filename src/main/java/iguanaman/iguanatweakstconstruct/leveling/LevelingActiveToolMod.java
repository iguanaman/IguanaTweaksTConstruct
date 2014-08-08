package iguanaman.iguanatweakstconstruct.leveling;

import iguanaman.iguanatweakstconstruct.IguanaTweaksTConstruct;
import iguanaman.iguanatweakstconstruct.util.Log;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.library.ActiveToolMod;
import tconstruct.library.tools.HarvestTool;
import tconstruct.library.tools.ToolCore;

import java.util.Arrays;
import java.util.List;

// This class is responsible for actually getting XP when mining!
public class LevelingActiveToolMod extends ActiveToolMod {
    // TODO: make this customizable?
    static List<Material> materialBlacklist = Arrays.asList(
            Material.leaves, Material.vine, Material.circuits,
            Material.glass, Material.piston, Material.snow
    );

    @Override
    public boolean beforeBlockBreak(ToolCore tool, ItemStack stack, int x, int y, int z, EntityLivingBase entity) {
        if (!(entity instanceof EntityPlayer)) return false;
        // nope, you don't use an autonomous activator!
        if(entity instanceof FakePlayer) return false;
        // why are you breaking this block with that tool! It's not a harvest tool derp!
        if(!(tool instanceof HarvestTool)) return false;

        Block block = entity.worldObj.getBlock(x, y, z);
        int meta = entity.worldObj.getBlockMetadata(x, y, z);

        if (block == null || materialBlacklist.contains(block.blockMaterial)) return false;

        NBTTagCompound tags = stack.getTagCompound().getCompoundTag("InfiTool");
        HarvestTool harvestTool = (HarvestTool) tool;

        boolean harvestable = false,
                effective = false,
                strong = false;

        harvestable = harvestTool.canHarvestBlock(block, stack);

        Float strength = harvestTool.calculateStrength(tags, block, meta);
        strong = strength >= 1.0f;
        effective = harvestTool.isEffective(block.getMaterial());

        boolean blockWithDrops = block.quantityDropped(IguanaTweaksTConstruct.random) > 1;
        boolean blockIsOre = false;
        // look for an oredict entry that suggests that the block is an ore
        // todo: might actually be worth it caching this stuff
        ItemStack blockStack = new ItemStack(Item.getItemFromBlock(block), 1, meta);
        for(int id : OreDictionary.getOreIDs(blockStack))
            if(OreDictionary.getOreName(id).startsWith("ore"))
            {
                blockIsOre = true;
                break;
            }

        // only give xp if the use makes sense
        if(harvestable && effective && strong) {
            int xp = 1;
            // bonus xp for mining ores!
            if(blockWithDrops || blockIsOre)
                xp++;
            LevelingLogic.addXP(stack, (EntityPlayer) entity, xp);
        }

        return false;
    }

    @Override
    public boolean afterBlockBreak() {
        Log.info("afterBlockBreak is implemented. Somebody tell me that so I can update xp giving.");
        return super.afterBlockBreak();
    }
}

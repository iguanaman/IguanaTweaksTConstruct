package iguanaman.iguanatweakstconstruct.leveling;

import cpw.mods.fml.common.FMLLog;
import iguanaman.iguanatweakstconstruct.IguanaLevelingLogic;
import iguanaman.iguanatweakstconstruct.util.IguanaLog;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.library.ActiveToolMod;
import tconstruct.library.tools.HarvestTool;
import tconstruct.library.tools.ToolCore;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class LevelingActiveToolMod extends ActiveToolMod {
    private static Method ToolStrength;
    private static Method EffectiveMaterials;
    static {
        try {
            ToolStrength = HarvestTool.class.getDeclaredMethod("calculateStrength", NBTTagCompound.class, Block.class, int.class);
            ToolStrength.setAccessible(true);
            EffectiveMaterials = HarvestTool.class.getDeclaredMethod("getEffectiveMaterials");
            EffectiveMaterials.setAccessible(true);
        } catch (NoSuchMethodException e) {
            IguanaLog.error(e);
            e.printStackTrace();
        }
    }

    // TODO: make this customizable?
    static List<Material> materialBlacklist = Arrays.asList(
            Material.leaves, Material.vine, Material.circuits,
            Material.glass, Material.piston, Material.snow
    );

    @Override
    public boolean beforeBlockBreak(ToolCore tool, ItemStack stack, int x, int y, int z, EntityLivingBase entity) {
        if (!(entity instanceof EntityPlayer)) return false;
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
        try {
            harvestable = harvestTool.canHarvestBlock(block, stack);

            Float strength = (Float)ToolStrength.invoke(harvestTool, tags, block, meta);
            strong = strength >= 1.0f;
            Material[] materials = (Material[])EffectiveMaterials.invoke(harvestTool);
            for(Material m : materials)
                if(m == block.getMaterial())
                    effective = true;
        } catch (IllegalAccessException e) {
            IguanaLog.error(e);
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            IguanaLog.error(e);
            e.printStackTrace();
        }

        IguanaLog.trace("Tool is [harvestable: '" + harvestable + "', effective: '" + effective + "', strong: '" + strong + "']");

        // only give xp if the use makes sense
        if(harvestable && effective && strong)
            // TODO: maybe give xp depending on WHAT block was mined? (xp determined by hardness, if it was an ore, etc.)
            IguanaLevelingLogic.addXP(stack, (EntityPlayer) entity, 1);

        return false;
    }

    @Override
    public boolean afterBlockBreak() {
        IguanaLog.debug("afterBlockBreak is implemented. Somebody tell me that so I can update xp giving.");
        return super.afterBlockBreak();
    }
}

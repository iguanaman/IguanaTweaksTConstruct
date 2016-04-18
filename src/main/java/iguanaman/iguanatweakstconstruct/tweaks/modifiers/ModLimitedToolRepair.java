package iguanaman.iguanatweakstconstruct.tweaks.modifiers;

import iguanaman.iguanatweakstconstruct.reference.Config;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.modifiers.tools.ModToolRepair;

public class ModLimitedToolRepair extends ModToolRepair {
    @Override
    protected boolean canModify(ItemStack tool, ItemStack[] input) {
        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
        int repairCount = tags.getInteger("RepairCount");
        if(repairCount >= Config.maxToolRepairs)
            return false;

        return super.canModify(tool, input);
    }


}

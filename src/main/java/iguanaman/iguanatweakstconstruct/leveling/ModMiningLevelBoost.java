package iguanaman.iguanatweakstconstruct.leveling;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import tconstruct.library.modifier.ItemModifier;
import tconstruct.modifiers.tools.ModBoolean;

public class ModMiningLevelBoost extends ModBoolean {
    private int maxLvl = 0;

    public ModMiningLevelBoost(ItemStack[] recipe, int effect, int maxLvl) {
        super(recipe, effect, "Mining Level Boost", EnumChatFormatting.GREEN.toString(), recipe[0].getDisplayName());

        this.maxLvl = maxLvl;
    }

    @Override
    protected boolean canModify(ItemStack input, ItemStack[] recipe) {
        NBTTagCompound tags = input.getTagCompound().getCompoundTag("InfiTool");

        // modifiers left and not yet modified?
        if(!super.canModify(input, recipe))
            return false;

        // got required harvest level?
        int hlvl = tags.getInteger("HarvestLevel");
        if(hlvl < maxLvl && hlvl > 0)
            return true;

        return false;
    }

    @Override
    public void modify(ItemStack[] input, ItemStack tool) {
        LevelingLogic.levelUpMiningLevel(tool, null, false);

        super.modify(input, tool);
    }
}

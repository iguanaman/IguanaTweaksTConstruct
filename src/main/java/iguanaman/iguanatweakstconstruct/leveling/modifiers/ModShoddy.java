package iguanaman.iguanatweakstconstruct.leveling.modifiers;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import tconstruct.modifiers.tools.ModBoolean;

// Shoddy handles Stonebound and Jagged. Jagged simply is negative Stonebound.
public class ModShoddy extends ModBoolean {
    public static ModShoddy ModJagged = new ModShoddy("BonusJagged", EnumChatFormatting.RED.toString(), StatCollector.translateToLocal("materialtraits.jagged"), -0.5f);
    public static ModShoddy ModStonebound = new ModShoddy("BonusStonebound", EnumChatFormatting.AQUA.toString(), StatCollector.translateToLocal("materialtraits.stonebound"), 0.5f);

    protected float change;

    public ModShoddy(String tag, String c, String tip, float change) {
        super(new ItemStack[0], 0, tag, c, tip);

        this.change = change;
    }

    @Override
    protected boolean canModify(ItemStack tool, ItemStack[] input) {
        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");

        // cannot apply jagged if we have stonebound
        if(tags.getFloat("Shoddy") > 0.0f && this.key.equals(ModJagged.key))
            return false;

        // cannot apply stonebound if jagged
        if(tags.getFloat("Shoddy") < 0.0f && this.key.equals(ModStonebound.key))
            return false;

        return true;
    }


    @Override
    public void modify(ItemStack[] input, ItemStack tool) {
        super.modify(input, tool);

        // go make stonebound/jagged...y
        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
        tags.setFloat("Shoddy", tags.getFloat("Shoddy") + change);
    }

    // no visual effect :(
    @Override
    public void addMatchingEffect(ItemStack input) {
    }
}

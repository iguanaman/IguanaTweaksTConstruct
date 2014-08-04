package iguanaman.iguanatweakstconstruct.debug;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import iguanaman.iguanatweakstconstruct.util.HarvestLevels;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class DebugTooltipHandler {
    @SubscribeEvent
    public void onItemToolTip(ItemTooltipEvent event) {
        if (event.entityPlayer == null)
            return;
        if(event.itemStack == null)
            return;

        Block block = Block.getBlockFromItem(event.itemStack.getItem());
        if(block == null || block== Blocks.air)
            return;
        int meta = event.itemStack.getItemDamage();
        if(!"pickaxe".equals(block.getHarvestTool(meta)))
            return;

        int hlvl = block.getHarvestLevel(meta);
        if(hlvl >= 0)
            event.toolTip.add(EnumChatFormatting.GOLD + "Harvest Level required: " + HarvestLevels.getHarvestLevelName(hlvl));
        else
            event.toolTip.add(EnumChatFormatting.GOLD + "Harvest Level required: Unknown");
    }
}

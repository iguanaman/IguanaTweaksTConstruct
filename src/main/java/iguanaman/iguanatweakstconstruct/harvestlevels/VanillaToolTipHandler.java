package iguanaman.iguanatweakstconstruct.harvestlevels;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import iguanaman.iguanatweakstconstruct.leveling.LevelingTooltips;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemTool;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import tconstruct.library.tools.ToolCore;

public class VanillaToolTipHandler {

    @SubscribeEvent
    public void onItemToolTip(ItemTooltipEvent event) {
        if (event.entityPlayer == null)
            return;

        if(!(event.itemStack.getItem() instanceof ItemTool))
            return;

        // we're only interested in stuff that's basically a pickaxe
        int hlvl = event.itemStack.getItem().getHarvestLevel(event.itemStack, "pickaxe");
        if (hlvl >= 0)
            event.toolTip.add(1, LevelingTooltips.getMiningLevelTooltip(hlvl));

        // well.. let's check the other things too /o\
        /* disabled because it'll probably cause more confusion than help..
        hlvl = event.itemStack.getItem().getHarvestLevel(event.itemStack, "shovel");
        if (hlvl >= 0)
            event.toolTip.add(1, LevelingTooltips.getMiningLevelTooltip(hlvl));

        hlvl = event.itemStack.getItem().getHarvestLevel(event.itemStack, "axe");
        if (hlvl >= 0)
            event.toolTip.add(1, LevelingTooltips.getMiningLevelTooltip(hlvl));
        */
    }
}

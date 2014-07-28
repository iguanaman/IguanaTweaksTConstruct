package iguanaman.iguanatweakstconstruct.harvestlevels;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import iguanaman.iguanatweakstconstruct.leveling.LevelingTooltips;
import net.minecraft.item.ItemPickaxe;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import tconstruct.library.tools.ToolCore;

public class VanillaToolTipHandler {

    @SubscribeEvent
    public void onItemToolTip(ItemTooltipEvent event) {
        if (event.entityPlayer == null)
            return;

        // we're only interested in stuff that's basically a pickaxe
        int hlvl = event.itemStack.getItem().getHarvestLevel(event.itemStack, "pickaxe");
        if (hlvl < 0)
            return;

        event.toolTip.add(1, LevelingTooltips.getMiningLevelTooltip(hlvl));
    }
}

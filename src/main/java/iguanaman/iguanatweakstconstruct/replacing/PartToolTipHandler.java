package iguanaman.iguanatweakstconstruct.replacing;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import iguanaman.iguanatweakstconstruct.reference.Config;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.ToolCore;
import tconstruct.tools.items.ToolPart;

public class PartToolTipHandler {

    @SubscribeEvent
    public void onItemToolTip(ItemTooltipEvent event) {
        if(event.entityPlayer == null)
            return;

        // we're only interested if it's a tool part
        if(!(event.itemStack.getItem() instanceof ToolPart))
            return;

        ItemStack stack = event.itemStack;
        ToolPart part = (ToolPart)stack.getItem();

        // stone part?
        if(Config.disableStoneTools && TConstructRegistry.getMaterial(part.getMaterialID(stack)) == TConstructRegistry.getMaterial("Stone"))
        {
            event.toolTip.add(1, "");
            event.toolTip.add(2, "\u00a74Can only be used to make casts,");
            event.toolTip.add(3, "\u00a74cannot be used to make a tool");
            return;
        }

        // regular parts
        String ability = TConstructRegistry.getMaterial(part.getMaterialID(stack)).ability();
        // paper or thaumium?
        if(ability.equals(StatCollector.translateToLocal("materialtraits.writable")) ||
           ability.equals(StatCollector.translateToLocal("materialtraits.thaumic"))) {
            event.toolTip.add(1, "");
            event.toolTip.add(2, "\u00a74Cannot be replaced once added,");
            event.toolTip.add(3, "\u00a74unless a modifier is available");
        }
        else {
            event.toolTip.add(1, "");
            event.toolTip.add(2, "\u00a76Parts can be replaced");
        }
    }
}

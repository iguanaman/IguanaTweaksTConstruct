package iguanaman.iguanatweakstconstruct.replacing;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import iguanaman.iguanatweakstconstruct.reference.Config;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.ToolCore;
import tconstruct.library.util.IToolPart;
import tconstruct.tools.items.ToolPart;

public class PartToolTipHandler {

    @SubscribeEvent
    public void onItemToolTip(ItemTooltipEvent event) {
        if(event.entityPlayer == null)
            return;

        // we're only interested if it's a tool part
        if(!(event.itemStack.getItem() instanceof IToolPart))
            return;

        // we abuse the fact that the result is not used by anything else.
        // Some other tooltip handlers already added a different tooltip, so this part is not replaceable
        if(event.getResult() == Event.Result.DENY)
            return;

        ItemStack stack = event.itemStack;
        IToolPart part = (IToolPart)stack.getItem();

        String ability = TConstructRegistry.getMaterial(part.getMaterialID(stack)).ability();
        // paper or thaumium?
        if(ability.equals(StatCollector.translateToLocal("materialtraits.writable")) ||
           ability.equals(StatCollector.translateToLocal("materialtraits.thaumic"))) {
            event.toolTip.add(1, "");
            event.toolTip.add(2, EnumChatFormatting.RED + StatCollector.translateToLocal("tooltip.part.needsmodifier1"));
            event.toolTip.add(3, EnumChatFormatting.RED + StatCollector.translateToLocal("tooltip.part.needsmodifier2"));
        }
        else {
            event.toolTip.add(1, "");
            event.toolTip.add(2, EnumChatFormatting.GOLD + StatCollector.translateToLocal("tooltip.part.replaceable"));
        }
    }
}

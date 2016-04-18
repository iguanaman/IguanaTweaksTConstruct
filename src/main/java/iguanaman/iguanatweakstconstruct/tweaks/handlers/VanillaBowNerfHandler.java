package iguanaman.iguanatweakstconstruct.tweaks.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import iguanaman.iguanatweakstconstruct.leveling.handlers.LevelingToolTipHandler;
import iguanaman.iguanatweakstconstruct.tweaks.IguanaTweaks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import java.util.ListIterator;

public class VanillaBowNerfHandler {
    @SubscribeEvent
    public void onArrowNock(ArrowNockEvent event)
    {
        if(event.entityPlayer == null)
            return;

        if(event.result == null)
            return;

        if(isUselessBow(event.result.getItem()))
            event.setCanceled(true);
    }

    @SubscribeEvent
    public void onItemToolTip(ItemTooltipEvent event) {
        if (event.entityPlayer == null)
            return;

        if(isUselessBow(event.itemStack.getItem())) {
            event.toolTip.add(EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("tooltip.uselessBow1"));
            event.toolTip.add(EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("tooltip.uselessTool2"));
        }
    }

    public static boolean isUselessBow(Item item)
    {
        if(item == null)
            return false;

        if(IguanaTweaks.toolWhitelist.contains(item))
            return false;

        if(item instanceof ItemBow)
            return true;

        return false;
    }
}

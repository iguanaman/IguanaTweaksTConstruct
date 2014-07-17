package iguanaman.iguanatweakstconstruct.leveling.handlers;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import iguanaman.iguanatweakstconstruct.leveling.LevelingLogic;
import iguanaman.iguanatweakstconstruct.leveling.LevelingTooltips;
import iguanaman.iguanatweakstconstruct.reference.IguanaConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import org.lwjgl.input.Keyboard;
import tconstruct.items.tools.Hammer;
import tconstruct.items.tools.Pickaxe;
import tconstruct.library.tools.ToolCore;

import java.util.List;
import java.util.ListIterator;

public class ToolTipHandler {
    // the prefix used for "+ X attack damage". Thanks Tic Tooltips ;)
    private static String plusPrefix = "\u00A79+";

    @SubscribeEvent
    public void onItemToolTip(ItemTooltipEvent event) {
        if(event.entityPlayer == null)
            return;

        // we're only interested in tinker tools, obviously
        if(!(event.itemStack.getItem() instanceof ToolCore))
            return;

        // don't display tooltip when CTRL is held (also tic tooltips compatibility)
        if(ctrlHeld())
            return;

        ItemStack stack = event.itemStack;
        // find spot to insert our tooltip data
        ListIterator<String> inserter = findInsertSpot(event.toolTip);
        // does the user hold shift?
        boolean advanced = shiftHeld();
        // only allow advanced (xp) tooltip if config option is set
        advanced &= IguanaConfig.showTooltipXP;



        ToolCore tool = (ToolCore)event.itemStack.getItem();
        NBTTagCompound tags = stack.getTagCompound().getCompoundTag(tool.getBaseTagName()); // tinker tags
        boolean hasMiningLevel = tool instanceof Pickaxe || tool instanceof Hammer;

        // add mining level if applicable
        if(hasMiningLevel)
        {
            int hLevel = tags.getInteger("HarvestLevel");
            String mLvl = LevelingTooltips.getMiningLevelTooltip(hLevel);
            // is the pick applicable for mining level boosting? if yes display xp
            if(LevelingLogic.canBoostMiningLevel(hLevel)) {
                // add minimal xp if config option is set
                if (!advanced && IguanaConfig.showMinimalTooltipXP && !LevelingLogic.isBoosted(tags))
                    mLvl += " (" + LevelingTooltips.getBoostXpString(stack, tags, false) + ")";
                inserter.add(mLvl);

                // advanced mining level boost progress info
                if (advanced && IguanaConfig.levelingPickaxeBoost && IguanaConfig.showTooltipXP) {
                    if (LevelingLogic.isBoosted(tags))
                        inserter.add(LevelingTooltips.getBoostedTooltip());
                    else
                        inserter.add(LevelingTooltips.getBoostXpToolTip(stack, tags));
                }
            }
            else inserter.add(mLvl);
        }

        // add skill level
        int level = LevelingLogic.getLevel(tags);
        String lvl = LevelingTooltips.getLevelTooltip(level);
        if(!advanced && IguanaConfig.showMinimalTooltipXP && !LevelingLogic.isMaxLevel(tags))
            lvl += " (" + LevelingTooltips.getXpString(stack, tags, false) + ")";
        inserter.add(lvl);

        // skill level progress
        if(advanced && IguanaConfig.showTooltipXP && !LevelingLogic.isMaxLevel(tags))
            inserter.add(LevelingTooltips.getXpToolTip(stack, tags));

        // since we added at least one line we'll add an empty spacing line at the end
        inserter.add("");

        // add info that you can hold shift for more details
        if(!advanced && IguanaConfig.showTooltipXP && !Loader.isModLoaded("TiCTooltips")) // don't display if TicToolTips is installed
            event.toolTip.add(EnumChatFormatting.GRAY.toString() + EnumChatFormatting.ITALIC.toString() + "Hold SHIFT for XP");
    }

    private ListIterator<String> findInsertSpot(List<String> tooltip)
    {
        ListIterator<String> iterator = tooltip.listIterator();

        // progress to the end, check if there's a "+ damage" stuff
        while(iterator.hasNext())
        {
            String str = iterator.next();
            if (str.isEmpty() || str.startsWith(plusPrefix)) {
                iterator.previous();
                break;
            }
            else if(Loader.isModLoaded("TiCTooltips") && str.contains("Shift")) {
                iterator.previous();
                break;
            }
        }

        //  iterator.previous();

        // we're either directly before the "+ damage" or at the end now

        return iterator;
    }

    // all hail ticTooltips for that information ;)
    private boolean shiftHeld()
    {
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    }

    private boolean ctrlHeld()
    {

        // prioritize CONTROL, but allow OPTION as well on Mac (note: GuiScreen's isCtrlKeyDown only checks for the OPTION key on Mac)
        boolean isCtrlKeyDown = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
        if (!isCtrlKeyDown && Minecraft.isRunningOnMac)
            isCtrlKeyDown = Keyboard.isKeyDown(Keyboard.KEY_LMETA) || Keyboard.isKeyDown(Keyboard.KEY_RMETA);

        return isCtrlKeyDown;
    }
}

package iguanaman.iguanatweakstconstruct.leveling;

import iguanaman.iguanatweakstconstruct.reference.Config;
import iguanaman.iguanatweakstconstruct.util.HarvestLevels;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

// utility class for constructing tooltip
public abstract class LevelingTooltips {

    /**
     * Returns only the XP progress. Standard is %.
     * @param detailed if true the xp-numbers will be returned too. "X/Y (Z%)"
     * @return
     */
    public static String getXpString(ItemStack tool, NBTTagCompound tags, boolean detailed) { return getXpString(tool, tags, detailed, false); }
    /**
     * Returns only the XP progress for the mining level. Standard is %.
     * @param detailed if true the xp-numbers will be returned too. "X/Y (Z%)"
     * @return
     */
    public static String getBoostXpString(ItemStack tool, NBTTagCompound tags, boolean detailed) { return getXpString(tool, tags, detailed, true); }

    public static String getXpToolTip(ItemStack tool, NBTTagCompound tags) { return getXpToolTip(tool, tags, false); }
    public static String getBoostXpToolTip(ItemStack tool, NBTTagCompound tags) { return getXpToolTip(tool, tags, true); }

    /**
    * Returns the XP tooltip for the ToolTip.
    * @param boostXp If true, the xp for the mining level boost will be returned instead of the xp for the next tool level.
    */
    private static String getXpToolTip(ItemStack tool, NBTTagCompound tags, boolean boostXp)
    {
        String prefix = StatCollector.translateToLocal(boostXp ? "tooltip.level.miningxp" : "tooltip.level.skillxp");
        return prefix + ": " + getXpString(tool, tags, Config.detailedXpTooltip, boostXp);
    }

    /**
     * Returns only the xp part of the xp tooltip.
     * @param detailed if true the xp-numbers will be returned too.
     * @param boostXp If true, the xp for the mining level boost will be returned instead of the xp for the next tool level.
     * @return
     */
    private static String getXpString(ItemStack tool, NBTTagCompound tags, boolean detailed, boolean boostXp)
    {
        if (tags == null) tags = tool.getTagCompound().getCompoundTag("InfiTool");

        int requiredXp = LevelingLogic.getRequiredXp(tool, tags, boostXp);
        long currentXp = boostXp ? LevelingLogic.getBoostXp(tags) : LevelingLogic.getXp(tags);
        float xpPercentage = (float)currentXp / (float)requiredXp * 100f;
        String xpPercentageString = String.format("%.2f", xpPercentage) + "%";

        if(detailed)
            return Long.toString(currentXp) + " / " + Integer.toString(requiredXp) + " (" + xpPercentageString + ")";
        else
            return xpPercentageString;
    }

    public static String getLevelTooltip(int level)
    {
        String str;
        if(!StatCollector.canTranslate("tooltip.level.skill." + level))
            str = "Unknown";
        else
            str = StatCollector.translateToLocal("tooltip.level.skill." + level);

        return String.format("%s: %s%s", StatCollector.translateToLocal("tooltip.level.skilllevel"), getLevelColor(level), str);
    }

    public static String getLevelColor(int level)
    {
        switch (level)
        {
            case 1: return "\u00a74";
            case 2: return "\u00a76";
            case 3: return "\u00a7e";
            case 4: return "\u00a72";
            case 5: return "\u00a73";
            case 6: return "\u00a7d";
            default: return "";
        }
    }

    public static String getMiningLevelTooltip(int hLevel)
    {
        return String.format("%s: %s", StatCollector.translateToLocal("tooltip.level.mininglevel"), HarvestLevels.getHarvestLevelName(hLevel));
    }

    public static String getBoostedTooltip()
    {
        return String.format("%s: \u00a76%s", StatCollector.translateToLocal("tooltip.level.miningxp"), StatCollector.translateToLocal("tooltip.level.boosted"));
    }

    public static String getInfoString(String base, EnumChatFormatting baseColor, String info, EnumChatFormatting infoColor)
    {
        return getInfoString(base, baseColor, info, infoColor.toString());
    }

    public static String getInfoString(String base, EnumChatFormatting baseColor, String info, String infoColor)
    {
        return String.format("%s%s %s(%s%s%s)", baseColor, base, baseColor, infoColor, info, baseColor);
    }
}

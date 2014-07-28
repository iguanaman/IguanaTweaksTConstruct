package iguanaman.iguanatweakstconstruct.leveling;

import iguanaman.iguanatweakstconstruct.reference.Config;
import iguanaman.iguanatweakstconstruct.util.HarvestLevels;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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
        String prefix = boostXp ? "Mining XP: " : "Skill XP: ";
        return prefix + getXpString(tool, tags, Config.detailedXpTooltip, boostXp);
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
        switch (level)
        {
        case 1: return "Skill Level: \u00a74Clumsy";
        case 2: return "Skill Level: \u00a76Comfortable";
        case 3: return "Skill Level: \u00a7eAccustomed";
        case 4: return "Skill Level: \u00a72Adept";
        case 5: return "Skill Level: \u00a73Expert";
        case 6: return "Skill Level: \u00a7dMaster";
        default: return "";
        }
    }

    public static String getMiningLevelTooltip(int hLevel)
    {
        return "Mining Level: " + HarvestLevels.getHarvestLevelName(hLevel);
    }

    public static String getBoostedTooltip()
    {
        return "Mining XP: \u00a76Boosted";
    }
}

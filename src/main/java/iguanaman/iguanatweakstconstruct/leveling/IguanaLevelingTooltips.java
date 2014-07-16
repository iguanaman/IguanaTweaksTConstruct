package iguanaman.iguanatweakstconstruct.leveling;

import iguanaman.iguanatweakstconstruct.IguanaTweaksTConstruct;
import iguanaman.iguanatweakstconstruct.reference.IguanaConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

// utility class for constructing tooltip
public abstract class IguanaLevelingTooltips {

    public static String getXpString(ItemStack tool)
    {
        return getXpString(tool, null);
    }

    public static String getXpString(ItemStack tool, boolean boostXp)
    {
        return getXpToolTip(tool, null, boostXp);
    }

    public static String getXpString(ItemStack tool, NBTTagCompound tags)
    {
        return getXpToolTip(tool, tags, false);
    }

    /**
* Returns the XP string for the ToolTip.
* @param boostXp If true, the xp for the mining level boost will be returned instead of the xp for the next tool level.
*/
    public static String getXpToolTip(ItemStack tool, NBTTagCompound tags, boolean boostXp)
    {
        if (tags == null) tags = tool.getTagCompound().getCompoundTag("InfiTool");

        int requiredXp = IguanaLevelingLogic.getRequiredXp(tool, tags, boostXp);
        long currentXp = boostXp ? tags.getLong(IguanaLevelingLogic.TAG_BOOST_EXP) : tags.getLong(IguanaLevelingLogic.TAG_EXP);
        float xpPercentage = (float)currentXp / (float)requiredXp * 100f;
        String xpPercentageString = String.format("%.2f", xpPercentage) + "%";

        String prefix = boostXp ? "Mining XP: " : "Skill XP: ";

        if (IguanaConfig.detailedXpTooltip)
            return prefix + Long.toString(currentXp) + " / " + Integer.toString(requiredXp) + " (" + xpPercentageString + ")";
        else
            return prefix + xpPercentageString;
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
        return "Mining Level: " + IguanaTweaksTConstruct.getHarvestLevelName(hLevel);
    }

    public static String getBoostedTooltip()
    {
        return "\u00a76Boosted";
    }
}

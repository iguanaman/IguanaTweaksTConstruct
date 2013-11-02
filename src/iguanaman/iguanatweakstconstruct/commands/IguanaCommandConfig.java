package iguanaman.iguanatweakstconstruct.commands;

import iguanaman.iguanatweakstconstruct.IguanaConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class IguanaCommandConfig extends CommandBase {

	@Override
	public String getCommandName() {
		return "igtweakstconstruct";
	}
	
    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		boolean worked = false;
		if (astring.length == 2)
		{
			String setting = astring[0];
			String value = astring[1];
			if (setting.equalsIgnoreCase("showTooltipXP"))
			{
				IguanaConfig.showTooltipXP = Boolean.parseBoolean(value);
				worked = true;
			}
			else if (setting.equalsIgnoreCase("showDebugXP"))
			{
				IguanaConfig.showDebugXP = Boolean.parseBoolean(value);
				worked = true;
			}
			else if (setting.equalsIgnoreCase("repairCostScaling"))
			{
				IguanaConfig.repairCostScaling = Boolean.parseBoolean(value);
				worked = true;
			}
			else if (setting.equalsIgnoreCase("repairLimitActive"))
			{
				IguanaConfig.repairLimitActive = Boolean.parseBoolean(value);
				worked = true;
			}
			else if (setting.equalsIgnoreCase("miningBoostLevel") && isInteger(value))
			{
				IguanaConfig.miningBoostLevel = Integer.parseInt(value);
				worked = true;
			}
			else if (setting.equalsIgnoreCase("baseHeadDropChance") && isInteger(value))
			{
				IguanaConfig.baseHeadDropChance = Integer.parseInt(value);
				worked = true;
			}
			else if (setting.equalsIgnoreCase("beheadingHeadDropChance") && isInteger(value))
			{
				IguanaConfig.beheadingHeadDropChance = Integer.parseInt(value);
				worked = true;
			}
			else if (setting.equalsIgnoreCase("repairScalingModifier") && isInteger(value))
			{
				IguanaConfig.repairScalingModifier = Integer.parseInt(value);
				worked = true;
			}
			else if (setting.equalsIgnoreCase("repairLimit") && isInteger(value))
			{
				IguanaConfig.repairLimit = Integer.parseInt(value);
				worked = true;
			}
			else if (setting.equalsIgnoreCase("repairCostPercentage") && isInteger(value))
			{
				IguanaConfig.repairCostPercentage = Integer.parseInt(value);
				worked = true;
			}
			else if (setting.equalsIgnoreCase("toolLevelingRatePercentage") && isInteger(value))
			{
				IguanaConfig.toolLevelingRatePercentage = Integer.parseInt(value);
				worked = true;
			}
			else if (setting.equalsIgnoreCase("weaponLevelingRatePercentage") && isInteger(value))
			{
				IguanaConfig.weaponLevelingRatePercentage = Integer.parseInt(value);
				worked = true;
			}
			
			if (worked) notifyAdmins(icommandsender, 0, "Set '" + setting + "' to '" + value + "'", new Object[0]);
		}

		if (!worked) throw new WrongUsageException("/" + getCommandName() + " <settingname> <value>", new Object[0]);			
	}


    /**
     * Parses an int from the given sring with a specified minimum.
     */
    public static int parseIntWithMinMax(ICommandSender par0ICommandSender, String par1Str, int min, int max)
    {
        return parseIntBounded(par0ICommandSender, par1Str, min, max);
    }
    
    public static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        }
        // only got here if we didn't return false
        return true;
    }

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "/" + getCommandName() + " <settingname> <value>";
	}

}

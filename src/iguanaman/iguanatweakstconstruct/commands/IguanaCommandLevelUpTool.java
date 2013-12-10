package iguanaman.iguanatweakstconstruct.commands;

import iguanaman.iguanatweakstconstruct.IguanaLevelingLogic;
import iguanaman.iguanatweakstconstruct.IguanaLog;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.AbilityHelper;
import tconstruct.library.tools.ToolCore;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class IguanaCommandLevelUpTool extends CommandBase {

	@Override
	public String getCommandName() {
		return "leveluptool";
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
        EntityPlayerMP entityplayermp = astring.length >= 1 ? getPlayer(icommandsender, astring[0]) : getCommandSenderAsPlayer(icommandsender);
        ItemStack equipped = entityplayermp.getCurrentEquippedItem();
		if (equipped != null && equipped.getItem() instanceof ToolCore)
		{
			NBTTagCompound tags = equipped.getTagCompound().getCompoundTag("InfiTool");
			if (tags.hasKey("ToolLevel"))
			{
		    	int level = tags.getInteger("ToolLevel");
				int hLevel = tags.hasKey("HarvestLevel") ? hLevel = tags.getInteger("HarvestLevel") : -1;
				
				if ((level >= 1 && level <= 5) || (hLevel >= TConstructRegistry.getMaterial("Copper").harvestLevel() || hLevel < TConstructRegistry.getMaterial("Manyullyn").harvestLevel()))
				{
			    	Long toolXP = tags.hasKey("ToolEXP") ? tags.getLong("ToolEXP") : -1;
			    	Long headXP = tags.hasKey("HeadEXP") ? tags.getLong("HeadEXP") : -1;
			    	long requiredToolXP = (long)IguanaLevelingLogic.getRequiredXp(equipped, tags) - toolXP;
			    	long requiredHeadXP = tags.hasKey("HeadEXP") && hLevel >= TConstructRegistry.getMaterial("Copper").harvestLevel() && hLevel < TConstructRegistry.getMaterial("Manyullyn").harvestLevel() ? (long)IguanaLevelingLogic.getRequiredXp(equipped, tags) - headXP : -1;
			    	
			    	if (requiredHeadXP < requiredToolXP && requiredHeadXP > 0)
			    		IguanaLevelingLogic.updateXP(equipped, entityplayermp, toolXP + requiredHeadXP, headXP + requiredHeadXP);
			    	else
			    		IguanaLevelingLogic.updateXP(equipped, entityplayermp, toolXP + requiredToolXP, headXP + requiredToolXP);

		            if (entityplayermp != icommandsender)
					{
						notifyAdmins(icommandsender, 1, "Leveled up %s's tool", new Object[]{entityplayermp.getEntityName()});
					}
					else
					{
						notifyAdmins(icommandsender, 1, "Leveled up their own tool", new Object[]{});
					}
				}
				else
				{
		        	throw new WrongUsageException("Players tool is already max level", new Object[0]);
				}
			}
			else
			{
	        	throw new WrongUsageException("Player must have a levelable Tinker's Construct tool in hand", new Object[0]);
			}
		}
		else
		{
        	throw new WrongUsageException("Player must have a Tinker's Construct tool in hand", new Object[0]);
		}
	}


    /**
     * Parses an int from the given sring with a specified minimum.
     */
    public static int parseIntWithMinMax(ICommandSender par0ICommandSender, String par1Str, int min, int max)
    {
        return parseIntBounded(par0ICommandSender, par1Str, min, max);
    }

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return null;
	}

}

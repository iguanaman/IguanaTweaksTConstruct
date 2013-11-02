package iguanaman.iguanatweakstconstruct.commands;

import iguanaman.iguanatweakstconstruct.IguanaLevelingLogic;
import tconstruct.library.tools.AbilityHelper;
import tconstruct.library.tools.ToolCore;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class IguanaCommandToolXP extends CommandBase {

	@Override
	public String getCommandName() {
		return "toolxp";
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
		if (astring.length > 0)
		{
	        EntityPlayerMP entityplayermp = astring.length >= 2 ? getPlayer(icommandsender, astring[0]) : getCommandSenderAsPlayer(icommandsender);
	        int xp = astring.length >= 2 ? parseIntWithMinMax(icommandsender, astring[1], 0, Integer.MAX_VALUE) : parseIntWithMinMax(icommandsender, astring[0], 0, Integer.MAX_VALUE);
	        ItemStack equipped = entityplayermp.getCurrentEquippedItem();
			if (equipped != null)
			{
				if (equipped.getItem() instanceof ToolCore)
				{
					IguanaLevelingLogic.addXP(equipped, entityplayermp, xp);
					notifyAdmins(icommandsender, 1, "Set " + entityplayermp.getEntityName() + "'s tool xp to " + xp, new Object[0]);

				}
				else
				{
		        	throw new WrongUsageException("Player must have a Tinker's Construct tool in hand", new Object[0]);
				}
			}
			else
			{
	        	throw new WrongUsageException("Player must have a Tinker's Construct tool in hand", new Object[0]);
			}
		}
		else
		{
            throw new WrongUsageException("toolxp [player] <value>", new Object[0]);
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
		// TODO Auto-generated method stub
		return null;
	}

}

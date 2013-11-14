package iguanaman.iguanatweakstconstruct.commands;

import iguanaman.iguanatweakstconstruct.IguanaLevelingLogic;
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
			if (tags.hasKey("ToolEXP"))
			{
				Long xp = tags.getLong("ToolEXP");
				Long toAdd = IguanaLevelingLogic.getRequiredXp(equipped, false, tags) - xp;
				if (tags.hasKey("HeadEXP") && !tags.hasKey("HarvestLevelModified"))
				{
					Long xpHead = tags.getLong("HeadEXP");
					Long toAddHead = IguanaLevelingLogic.getRequiredXp(equipped, true, tags) - xpHead;
					if (toAddHead < toAdd) toAdd = toAddHead;
				}
				if (toAdd > 0L)
				{
					IguanaLevelingLogic.addXP(equipped, entityplayermp, toAdd);
					if (astring == null)
					{
						notifyAdmins(icommandsender, 1, entityplayermp.username + " leveled up their tool", new Object[0]);
					}
					else
					{
						notifyAdmins(icommandsender, 1, getPlayer(icommandsender, astring[0]).username + " leveled up " + entityplayermp.username + "'s tool", new Object[0]);
					}
				}
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
		// TODO Auto-generated method stub
		return null;
	}

}

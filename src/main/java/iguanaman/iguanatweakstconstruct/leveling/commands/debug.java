package iguanaman.iguanatweakstconstruct.leveling.commands;

import iguanaman.iguanatweakstconstruct.util.Log;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.library.tools.ToolCore;

public class debug extends CommandBase {

    @Override
    public String getCommandName() {
        return "shownbt";
    }

    /**
     * Return the required permission level for this command.
     */
    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    @Override
    public void processCommand(ICommandSender icommandsender, String[] astring) {
        EntityPlayerMP entityplayermp = astring.length >= 1 ? getPlayer(icommandsender, astring[0]) : getCommandSenderAsPlayer(icommandsender);
        ItemStack equipped = entityplayermp.getCurrentEquippedItem();
        if (equipped != null && equipped.getItem() instanceof ToolCore) {
            NBTTagCompound tags = equipped.getTagCompound().getCompoundTag("InfiTool");
            int tipNum = 0;
            while (true) {
                tipNum++;
                String tip = "Tooltip" + tipNum;
                String modTip = "ModifierTip" + tipNum;

                boolean found = false;
                if (tags.hasKey(tip)) {
                    Log.info(tip + ": " + tags.getString(tip));
                    found = true;
                }
                if (tags.hasKey(modTip)) {
                    Log.info(modTip + ": " + tags.getString(tip));
                    found = true;
                }

                if (!found)
                    break;
            }
        }
    }


    @Override
    public String getCommandUsage(ICommandSender icommandsender) {
        return null;
    }

    @Override
    public int compareTo (Object arg0)
    {
        return 0;
    }

}

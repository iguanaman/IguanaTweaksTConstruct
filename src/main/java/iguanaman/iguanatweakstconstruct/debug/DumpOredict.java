package iguanaman.iguanatweakstconstruct.debug;

import iguanaman.iguanatweakstconstruct.util.Log;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.oredict.OreDictionary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class DumpOredict extends CommandBase {

    @Override
    public String getCommandName() {
        return "dumpOredict";
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
        File file = new File("dumpOreDict.txt");
        try {
            PrintWriter pw = new PrintWriter(file);
            for(String ore : OreDictionary.getOreNames())
                pw.println(ore);

            pw.close();

            icommandsender.addChatMessage(new ChatComponentText("Dumped oredict names to " + file.getName()));
        } catch (FileNotFoundException e) {
            Log.error("Couldn't create file " + file.getName());
            icommandsender.addChatMessage(new ChatComponentText("Couldn't create file " + file.getName()));
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
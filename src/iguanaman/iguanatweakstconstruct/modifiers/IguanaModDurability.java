package iguanaman.iguanatweakstconstruct.modifiers;

import iguanaman.iguanatweakstconstruct.IguanaConfig;
import iguanaman.iguanatweakstconstruct.IguanaTweaksTConstruct;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.items.tools.Hammer;
import tconstruct.items.tools.Pickaxe;
import tconstruct.library.tools.ToolMod;
import tconstruct.modifiers.ModDurability;
import tconstruct.util.PHConstruct;

public class IguanaModDurability extends ToolMod {

    String tooltipName;
    String color;
    int durability;
    float modifier;
    int miningLevel;

    public IguanaModDurability(ItemStack[] items, int effect, int dur, float mod, int level, String k, String tip, String c)
    {
        super(items, effect, k);
        durability = dur;
        modifier = mod;
        miningLevel = level;
        tooltipName = tip;
        color = c;
    }

    @Override
    protected boolean canModify (ItemStack tool, ItemStack[] input)
    {
        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
        if (tags.hasKey(key) || tags.getBoolean("HarvestLevelModified")) return false;
        
        if (IguanaConfig.pickaxeHeads) 
        {
            int mLevel = tags.getInteger("HarvestLevel");
            if (mLevel > 1) return true;
            else return false;
        }
        
        return super.canModify(tool, input);
    }

    @Override
    public void modify (ItemStack[] input, ItemStack tool)
    {
        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");

        if (PHConstruct.miningLevelIncrease || IguanaConfig.pickaxeHeads)
        {
            int mLevel = tags.getInteger("HarvestLevel");
            if (mLevel < miningLevel) tags.setInteger("HarvestLevel", mLevel + 1);
            tags.setBoolean("HarvestLevelModified", true);
            
            overwriteToolTip(tool);
        }

        if (!IguanaConfig.pickaxeHeads)
        {
            int base = tags.getInteger("BaseDurability");
            int bonus = tags.getInteger("BonusDurability");
            float modDur = tags.getFloat("ModDurability");

            bonus += durability;
            modDur += modifier;

            int total = (int) ((base + bonus) * (modDur + 1f));
            if (total <= 0)
                total = 1;

            tags.setInteger("TotalDurability", total);
            tags.setInteger("BonusDurability", bonus);
            tags.setFloat("ModDurability", modDur);

            int modifiers = tags.getInteger("Modifiers");
            modifiers -= 1;
            tags.setInteger("Modifiers", modifiers);
        }

        tags.setBoolean(key, true);
        String modTip = color + key;
        addToolTip(tool, tooltipName, modTip);
    }
    
    @Override
    public boolean matches (ItemStack[] input, ItemStack tool)
    {
    	boolean result = super.matches(input, tool);
    	
    	if (result == true && IguanaConfig.pickaxeHeads)
    	{	
	    	if (tool.getItem() != null)
	    	{
	    		if (!(tool.getItem() instanceof Pickaxe))
	    			return false;
	    	}
	    	
	        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
	        int harvestLevel = tags.getInteger("HarvestLevel");
	        if (harvestLevel == 0 || harvestLevel >= miningLevel)
	        	return false;
    	}
        
    	return result;
    }
    
    public void overwriteToolTip(ItemStack tool)
    {
        NBTTagCompound toolTag = tool.getTagCompound().getCompoundTag("InfiTool");

    	//tooltip lists
    	List<String> tips = new ArrayList<String>();
    	List<String> modifierTips = new ArrayList<String>();

    	tips.add(IguanaTweaksTConstruct.getHarvestLevelName(toolTag.getInteger("HarvestLevel")));
    	modifierTips.add("");
    	
    	
    	//get and remove tooltips
        int tipNum = 0;
        while (true)
        {
            String tip = "Tooltip" + ++tipNum;
            if (toolTag.hasKey(tip))
            {
            	String tipString = toolTag.getString(tip);
            	if (!tipString.startsWith("Head XP:") && !tipString.startsWith("Mining Level:"))
            	{
                    tips.add(toolTag.getString(tip));
                    modifierTips.add(toolTag.getString("ModifierTip" + tipNum));
            	}
                toolTag.removeTag(tip);
                toolTag.removeTag("ModifierTip" + tipNum);
            }
            else break;
        }
        
        //write tips
        for (int i = 1; i <= tips.size(); ++i)
        {
        	toolTag.setString("Tooltip" + i, tips.get(i - 1));
        	toolTag.setString("ModifierTip" + i, modifierTips.get(i - 1));
        }
        
    }

}

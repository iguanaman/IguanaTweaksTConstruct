package iguanaman.iguanatweakstconstruct;

import iguanaman.iguanatweakstconstruct.modifiers.IguanaModAttack;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModBlaze;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModLapis;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModPiston;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModRedstone;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tconstruct.items.tools.Battleaxe;
import tconstruct.items.tools.Excavator;
import tconstruct.items.tools.Hammer;
import tconstruct.items.tools.Hatchet;
import tconstruct.items.tools.LumberAxe;
import tconstruct.items.tools.Mattock;
import tconstruct.items.tools.Pickaxe;
import tconstruct.items.tools.Scythe;
import tconstruct.items.tools.Shortbow;
import tconstruct.items.tools.Shovel;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.HarvestTool;
import tconstruct.library.tools.ToolCore;
import tconstruct.library.tools.ToolMod;
import tconstruct.library.tools.Weapon;
import tconstruct.modifiers.ModAntiSpider;
import tconstruct.modifiers.ModInteger;
import tconstruct.modifiers.ModReinforced;
import tconstruct.modifiers.ModSmite;

public class IguanaLevelingLogic {

	public static String getXpString(ItemStack tool, boolean debug)
	{
		return getXpString(tool, debug, null);
	}

	public static String getXpString(ItemStack tool, boolean debug, boolean pick)
	{
		return getXpString(tool, debug, null, pick);
	}

	public static String getXpString(ItemStack tool, boolean debug, NBTTagCompound tags)
	{
		return getXpString(tool, debug, tags, false);	
	}
	
	public static String getXpString(ItemStack tool, boolean debug, NBTTagCompound tags, boolean pick)
	{
		if (tags == null) tags = tool.getTagCompound().getCompoundTag("InfiTool");		
		
		int requiredXp = getRequiredXp(tool, tags, pick);
		long currentXp = pick ? tags.getLong("HeadEXP") : tags.getLong("ToolEXP");
		float xpPercentage = ((float)currentXp / (float)requiredXp) * 100f;
		String xpPercentageString = String.format("%.2f", xpPercentage) + "%"; 
		
		String prefix = pick ? "Boost XP: " : "XP: ";
		
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
			default: return null;
		}
	}
	
	public static void updateXP(ItemStack tool, EntityPlayer player, long toolXP, long headXP)
	{
        
        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
        
        if (!tags.hasKey("ToolLevel")) return;
        
    	int level = tags.getInteger("ToolLevel");
		int hLevel = tags.hasKey("HarvestLevel") ? hLevel = tags.getInteger("HarvestLevel") : -1;

        World world = player.worldObj;
        
        boolean leveled = false;
        boolean pickLeveled = false;

    	if (tags.hasKey("ToolEXP") && level >= 1 && level <= 5 && toolXP >= 0) 
    	{
    		tags.setLong("ToolEXP", toolXP);

        	//CHECK FOR LEVEL UP
    	    if (toolXP >= getRequiredXp(tool, tags))
    	    {
    	    	LevelUpTool(tool, player);
    	    	leveled = true;
    	    }
    	}
    	
    	if (IguanaConfig.levelingPickaxeBoost && tags.hasKey("HeadEXP") && !tags.hasKey("HarvestLevelModified"))
    	{
    		if (hLevel >= TConstructRegistry.getMaterial("Copper").harvestLevel() && ((!IguanaConfig.pickaxeBoostRequired && hLevel < 6 || IguanaConfig.pickaxeBoostRequired && hLevel < 7)))
    		{
        		tags.setLong("HeadEXP", headXP);
        		
            	//CHECK FOR PICK LEVEL UP
        	    if (headXP >= getRequiredXp(tool, true))
        	    {
        	    	LevelUpPick(tool, player, leveled);
        	    	pickLeveled = true;
        	    }
    		}
    	}	
    	
    	//Recheck level
    	level = tags.getInteger("ToolLevel");
		if (tags.hasKey("HarvestLevel")) hLevel = tags.getInteger("HarvestLevel");

    	//tooltip lists
    	List<String> tips = new ArrayList<String>();
    	List<String> modifierTips = new ArrayList<String>();

        //add mining level tooltip
    	if (tool.getItem() instanceof Pickaxe || tool.getItem() instanceof Hammer)
        {
            tips.add("Mining Level: " + IguanaTweaksTConstruct.getHarvestLevelName(hLevel));
            modifierTips.add("");
        }
    	
    	tips.add(getLevelTooltip(level));
    	modifierTips.add("");

        if (IguanaConfig.showTooltipXP)
        {
        	if (level <= 5)
        	{
            	tips.add(getXpString(tool, false, false));
            	modifierTips.add("");
        	}
        	
        	if (IguanaConfig.levelingPickaxeBoost)
        	{
	        	if (hLevel >= TConstructRegistry.getMaterial("Copper").harvestLevel() && hLevel < TConstructRegistry.getMaterial("Manyullyn").harvestLevel() 
	        			&& !tags.hasKey("HarvestLevelModified") 
	        			&& (tool.getItem() instanceof Pickaxe || tool.getItem() instanceof Hammer))
	        	{
	            	tips.add(getXpString(tool, false, true));
	            	modifierTips.add("");
	        	}
        	}
        }
    	
    	//get and remove tooltips
        int tipNum = 0;
        while (true)
        {
            String tip = "Tooltip" + ++tipNum;
            if (tags.hasKey(tip))
            {
            	String tipString = tags.getString(tip);
            	if (!tipString.startsWith("XP:") && !tipString.startsWith("Head XP:") && !tipString.startsWith("Boost XP:") 
            			&& !tipString.startsWith("Skill Level:") && !tipString.startsWith("Mining Level:")
            			 && !tipString.startsWith("Requires boost"))
            	{
                    tips.add(tipString);
                    modifierTips.add(tags.getString("ModifierTip" + tipNum));
            	}
                tags.removeTag(tip);
                tags.removeTag("ModifierTip" + tipNum);
            }
            else break;
        }
        
        if (pickLeveled)
        {
            tips.add("\u00a7fMining Level Boost");
            modifierTips.add("");
        }
        
        //write tips
        for (int i = 1; i <= tips.size(); ++i)
        {
        	if (tips.get(i - 1) != null)
        	{
	        	tags.setString("Tooltip" + i, tips.get(i - 1));
	        	if (modifierTips.get(i - 1) != null)
	        		tags.setString("ModifierTip" + i, modifierTips.get(i - 1));
	        	else
	        		tags.setString("ModifierTip" + i, "");
        	}
        }
	}
	
    public static void addXP(ItemStack tool, EntityPlayer player, long xp)
    {
        if (player.capabilities.isCreativeMode) return;
        
        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");

    	Long toolXP = tags.hasKey("ToolEXP") ? tags.getLong("ToolEXP") + xp : -1;
    	Long headXP = tags.hasKey("HeadEXP") ? tags.getLong("HeadEXP") + xp : -1;
    	
    	updateXP(tool, player, toolXP, headXP);
    }
    
    public static int getRequiredXp(ItemStack tool)
    {
    	return getRequiredXp(tool, null);
    }
    
    public static int getRequiredXp(ItemStack tool, boolean pick)
    {
    	return getRequiredXp(tool, null, pick);
    }
    
    public static int getRequiredXp(ItemStack tool, NBTTagCompound tags)
    {
    	return getRequiredXp(tool, tags, false);
    }
    
    public static int getRequiredXp(ItemStack tool, NBTTagCompound tags, boolean pick)
    {
        if (tags == null) tags = tool.getTagCompound().getCompoundTag("InfiTool");
        
        float base = 600;
        
        if (tool.getItem() instanceof Weapon || tool.getItem() instanceof Shortbow)
        {
        	if (tool.getItem() instanceof Scythe) base *= 1.5f;
        	base *= ((float)IguanaConfig.xpRequiredWeaponsPercentage / 100f);
        }
        else
        {
	        int miningSpeed = tags.getInteger("MiningSpeed");
	        int divider = 1;
	        if (tags.hasKey("MiningSpeed2"))
	        {
	        	miningSpeed += tags.getInteger("MiningSpeed2");
	        	divider += 1;
	        }
	        if (tags.hasKey("MiningSpeedHandle"))
	        {
	        	miningSpeed += tags.getInteger("MiningSpeedHandle");
	        	divider += 1;
	        }
	        if (tags.hasKey("MiningSpeedExtra"))
	        {
	        	miningSpeed += tags.getInteger("MiningSpeedExtra");
	        	divider += 1;
	        }
			
	        base = ((float)miningSpeed / (float)divider) / 2f;
	        
	        // tier 2 tools
	        if (tool.getItem() instanceof Hammer || tool.getItem() instanceof Excavator || tool.getItem() instanceof LumberAxe)
	        {
	        	base *= 6f;
	        }
			
			if (tool.getItem() instanceof Shovel)
			{
				base *= 2f;
			}
			
			if (tool.getItem() instanceof Mattock)
			{
				base *= 3f;
			}
	        
        	base *= ((float)IguanaConfig.xpRequiredToolsPercentage / 100f);
        }
        
        if (pick) 
        {
        	base *= ((float)IguanaConfig.levelingPickaxeBoostXpPercentage / 100f);
        }
        else
        {
        	int level = tags.getInteger("ToolLevel");
        	if (level >= 1) base *= Math.pow(IguanaConfig.xpPerLevelMultiplier, level - 1);
        }

        return Math.round(base);
    }
    
    public static void LevelUpTool(ItemStack stack, EntityPlayer player)
    {
        NBTTagCompound tags = stack.getTagCompound().getCompoundTag("InfiTool");

        World world = player.worldObj;
    	int level = tags.getInteger("ToolLevel");
    	tags.setInteger("ToolLevel", ++level);
    	
        boolean isTool = false;
    	if (stack.getItem() instanceof HarvestTool)
    		isTool = true;
        	
        updateXP(stack, player, 0l, -1l);
    
    	if (!world.isRemote)
    	{
            player.worldObj.playSoundAtEntity(player, "iguanatweakstconstruct:chime", 1.0F, 1.0F);
            
            switch (level)
            {
        	case 2: player.addChatMessage("\u00a73You begin to feel comfortable handling the " + stack.getDisplayName()); break;
        	case 3: player.addChatMessage("\u00a73You are now accustomed to the weight of the " + stack.getDisplayName()); break;
        	case 4: player.addChatMessage("\u00a73You have become adept at handling the " + stack.getDisplayName()); break;
        	case 5: player.addChatMessage("\u00a73You are now an expert at using the " + stack.getDisplayName() + "\u00a73!"); break;
        	case 6: player.addChatMessage("\u00a73You have mastered the " + stack.getDisplayName() + "\u00a73!"); break;
            }

            if (!IguanaConfig.toolLevelingRandomBonuses || (level % 2 == 0 && IguanaConfig.toolLevelingExtraModifiers))
            	player.addChatMessage("\u00a79You notice room for improvement (+1 modifier).");
    	}
    	
        int currentModifiers = tags.getInteger("Modifiers");
        if (!IguanaConfig.toolLevelingRandomBonuses || (level % 2 == 0 && IguanaConfig.toolLevelingExtraModifiers))
        	tags.setInteger("Modifiers", ++currentModifiers);
        
        if (IguanaConfig.toolLevelingRandomBonuses)
        {
            tags.setInteger("Modifiers", currentModifiers + 1);
        	for (int i = 1; i <= 10; ++i) 
        		if (tryModify(player, stack, world.rand.nextInt(10), isTool)) break;

            tags.setInteger("Modifiers", currentModifiers);
        }
        
    }
    
    public static void LevelUpPick(ItemStack stack, EntityPlayer player, boolean leveled)
    {
        NBTTagCompound tags = stack.getTagCompound().getCompoundTag("InfiTool");

        World world = player.worldObj;
    	int level = tags.getInteger("ToolLevel");
    	
    	updateXP(stack, player, -1L, 0L);

    	if (!world.isRemote)
    	{
    		if (leveled)
        		player.addChatMessage("\u00a79Suddenly, a flash of light shines from the tip of the pickaxe (+1 mining level)");
    		else
        		player.addChatMessage("\u00a73Suddenly, a flash of light shines from the tip of your " + stack.getDisplayName() + "\u00a73 (+1 mining level)");
    	}
        
        tags.setBoolean("HarvestLevelModified", true);
        tags.setInteger("HarvestLevel", tags.getInteger("HarvestLevel") + 1);
    }
    
    private static boolean tryModify(EntityPlayer player, ItemStack stack, int rnd, boolean isTool)
    {
    	ToolMod mod = null;
    	Item item = stack.getItem();

        ItemStack[] nullItemStack = new ItemStack[] {};
        if (rnd < 1)
        {
            mod = new ModInteger(nullItemStack, 4, "Moss", IguanaConfig.mossRepairSpeed, "\u00a72", "Auto-Repair");
        	if (!player.worldObj.isRemote)
        		player.addChatMessage("\u00a79It seems to have accumulated a patch of moss (+1 repair)");
        }
        else if (rnd < 2 && ((!isTool && !(item instanceof Shortbow)) || (isTool && (item instanceof Pickaxe || item instanceof Hammer))))
        {
        	mod = new IguanaModLapis(nullItemStack, 10, 100);
			if (((IguanaModLapis)mod).canModify(stack, nullItemStack)) {
	        	if (!player.worldObj.isRemote)
	        		player.addChatMessage("\u00a79Perhaps holding on to it will bring you luck? (+100 luck)");
			} else return false;
        }
        else if (rnd < 6 && (isTool || item instanceof Shortbow))
        {
        	mod = new IguanaModRedstone(nullItemStack, 2, 50);
			if (((IguanaModRedstone)mod).canModify(stack, nullItemStack, true)) {
				if (!player.worldObj.isRemote)
		        	player.addChatMessage("\u00a79You spin it around with a flourish (+1 haste)");
			} else return false;
        }
        else if (rnd < 3 && (!isTool && !(item instanceof Shortbow)))
        {
        	mod = new IguanaModAttack("Quartz", nullItemStack, 11, 30);
			if (((IguanaModAttack)mod).canModify(stack, nullItemStack, true)) {
	        	if (!player.worldObj.isRemote)
	        		player.addChatMessage("\u00a79You take the time to sharpen the dull edges of the blade (+1 attack)");
			} else return false;
        }
        else if (rnd < 4 && (!isTool && !(item instanceof Shortbow)))
        {
        	mod = new ModInteger(nullItemStack, 13, "Beheading", 1, "\u00a7d", "Beheading");
        	if (!player.worldObj.isRemote)
        		player.addChatMessage("\u00a79You could take someones head off with that! (+1 beheading)");
        }
        else if (rnd < 5 && (!isTool && !(item instanceof Shortbow)))
        {
        	mod = new IguanaModBlaze(nullItemStack, 7, 25);
			if (((IguanaModBlaze)mod).canModify(stack, nullItemStack)) {
	        	if (!player.worldObj.isRemote)
	        		player.addChatMessage("\u00a79It starts to feels more hot to the touch (+1 fire aspect)");
			} else return false;
        }
        else if (rnd < 6 && (!isTool && !(item instanceof Shortbow)))
        {
        	mod = new ModInteger(nullItemStack, 8, "Necrotic", 1, "\u00a78", "Life Steal");
        	if (!player.worldObj.isRemote)
        		player.addChatMessage("\u00a79It shudders with a strange energy (+1 life steal)");
        }
        else if (rnd < 7 && (!isTool && !(item instanceof Shortbow)))
        {
        	mod = new ModSmite("Smite", nullItemStack, 14, 36);
        	if (!player.worldObj.isRemote)
        		player.addChatMessage("\u00a79It begins to radiate a slight glow (+1 smite)");
        }
        else if (rnd < 8 && (!isTool && !(item instanceof Shortbow)))
        {
        	mod = new ModAntiSpider("Anti-Spider", nullItemStack, 15, 4);
        	if (!player.worldObj.isRemote)
        		player.addChatMessage("\u00a79A strange odor emanates from the weapon (+1 bane of arthropods)");
        }
        else if (rnd < 9 && !isTool)
        {
        	mod = new IguanaModPiston(nullItemStack, 3, 10);
			if (((IguanaModPiston)mod).canModify(stack, nullItemStack)) {
	        	if (!player.worldObj.isRemote)
	        		player.addChatMessage("\u00a79Feeling more confident, you can more easily keep your assailants at bay (+1 knockback)");
			} else return false;
        }
        else if (rnd < 10)
        {
        	mod = new ModReinforced(nullItemStack, 16, 1);
        	if (!player.worldObj.isRemote)
        		player.addChatMessage("\u00a79Fixing up the wear and tear should make it last a little longer (+1 reinforced)");
        }
		
		if (mod == null) return false;

		mod.addMatchingEffect(stack);
		mod.modify(nullItemStack, stack);
		return true;
    }
    
    public static String[] getHarvestType(ToolCore tool)
    {
    	if (tool instanceof Battleaxe || tool instanceof Hatchet || tool instanceof LumberAxe)
    		return new String[] {"axe"};
    	else if (tool instanceof Excavator || tool instanceof Shovel)
    		return new String[] {"shovel"};
    	else if (tool instanceof Hammer || tool instanceof Pickaxe)
    		return new String[] {"pickaxe"};
    	else if (tool instanceof Mattock)
    		return new String[] {"shovel", "axe"};
    	else
    		return null;
    }
}

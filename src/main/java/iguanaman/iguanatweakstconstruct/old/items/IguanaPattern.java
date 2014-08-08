package iguanaman.iguanatweakstconstruct.old.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.tools.items.Pattern;

import java.util.List;

public class IguanaPattern extends Pattern {

	public IguanaPattern(String patternType, String folder) {
		super(patternType, folder);
	}

	public IguanaPattern(int id, String[] names, String[] patternTypes,
			String folder) {
		super(names, patternTypes, folder);
	}

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);

		PatternBuilder pb = PatternBuilder.instance;

		par3List.add("Valid materials:");

		if (par1ItemStack.getItemDamage() == 23)
			par3List.add("String");
		else if (par1ItemStack.getItemDamage() == 24)
		{
			par3List.add("Feather");
			par3List.add("Leaf");
			par3List.add("Slime");
		} else
			for (tconstruct.library.tools.ToolMaterial material : TConstructRegistry.toolMaterials.values())
				if (!par3List.contains(material.displayName))
				{
					ItemStack shard = pb.getShardFromSet(material.name());
					if (shard != null)
					{
						shard.stackSize = getPatternCost(par1ItemStack);
						if (pb.getToolPart(shard, par1ItemStack, null) != null)
							if (par3List.size() < 7 || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
								par3List.add(material.displayName);
							else
							{
								par3List.add("\u00A7o<Hold SHIFT for more>");
								break;
							}
					}
				}
	}

	/*
    @Override
    public int getPatternCost (ItemStack pattern)
    {
        switch (pattern.getItemDamage())
        {
        case 0:
            return 2;
        case 1:
            return IguanaConfig.patternCostToolRod;
        case 2:
            return IguanaConfig.patternCostPickaxeHead;
        case 3:
            return IguanaConfig.patternCostShovelHead;
        case 4:
            return IguanaConfig.patternCostHatchetHead;
        case 5:
            return IguanaConfig.patternCostSwordBlade;
        case 6:
            return IguanaConfig.patternCostWideGuard;
        case 7:
            return IguanaConfig.patternCostHandGuard;
        case 8:
            return IguanaConfig.patternCostCrossbar;
        case 9:
            return IguanaConfig.patternCostBinding;
        case 10:
            return IguanaConfig.patternCostFrypanHead;
        case 11:
            return IguanaConfig.patternCostSignHead;
        case 12:
            return IguanaConfig.patternCostKnifeBlade;
        case 13:
            return IguanaConfig.patternCostChiselHead;
        case 14:
            return IguanaConfig.patternCostToughRod;
        case 15:
            return IguanaConfig.patternCostToughBinding;
        case 16:
            return IguanaConfig.patternCostHeavyPlate;
        case 17:
            return IguanaConfig.patternCostBroadAxeHead;
        case 18:
            return IguanaConfig.patternCostScytheBlade;
        case 19:
            return IguanaConfig.patternCostExcavatorHead;
        case 20:
            return IguanaConfig.patternCostLargeSwordBlade;
        case 21:
            return IguanaConfig.patternCostHammerHead;
        case 22:
            return IguanaConfig.patternCostFullGuard;
        case 23:
            return IguanaConfig.patternCostBowstring;
        case 24:
            return IguanaConfig.patternCostFletching;
        case 25:
            return IguanaConfig.patternCostArrowHead;
        default:
            return 0;
        }
    }
	 */

}

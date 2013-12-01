package iguanaman.iguanatweakstconstruct.items;

import iguanaman.iguanatweakstconstruct.IguanaConfig;
import net.minecraft.item.ItemStack;
import tconstruct.items.Pattern;

public class IguanaPattern extends Pattern {

	public IguanaPattern(int id, String patternType, String folder) {
		super(id, patternType, folder);
	}

	public IguanaPattern(int id, String[] names, String[] patternTypes,
			String folder) {
		super(id, names, patternTypes, folder);
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

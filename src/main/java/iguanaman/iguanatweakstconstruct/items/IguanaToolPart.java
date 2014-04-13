package iguanaman.iguanatweakstconstruct.items;

import iguanaman.iguanatweakstconstruct.IguanaConfig;
import iguanaman.iguanatweakstconstruct.IguanaTweaksTConstruct;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import tconstruct.items.ToolPart;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.ToolMaterial;

public class IguanaToolPart extends ToolPart {

	public IguanaToolPart(int id, String textureType, String name) {
		super(id, textureType, name);
	}

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		ToolMaterial material = TConstructRegistry.getMaterial(par1ItemStack.getItemDamage());
		if (!IguanaConfig.allowStoneTools && material.materialName.equals("Stone"))
		{
			par3List.add("\u00a74Can only be used to make casts,");
			par3List.add("\u00a74cannot be used to make a tool");
		}
		else
		{

			if (!material.ability.equals("")) par3List.add(material.style() + material.ability);

			if (partName.equals("PickHead") || partName.equals("HammerHead") || partName.equals("ShovelHead")
					|| partName.equals("ExcavatorHead"))
				par3List.add("Mining Level: " + IguanaTweaksTConstruct.getHarvestLevelName(material.harvestLevel));

			if (
					partName.equals("PickHead") || partName.equals("HammerHead") || partName.equals("ShovelHead") || partName.equals("ExcavatorHead")
					|| partName.equals("AxeHead") || partName.equals("LumberHead") || partName.equals("SwordBlade") || partName.equals("KnifeBlade")
					|| partName.equals("FrypanHead") || partName.equals("SignHead") || partName.equals("ScytheHead") || partName.equals("LargeSwordBlade")
					|| partName.equals("ArrowHead")
					)
				par3List.add("Damage: " + material.attack);

			if (partName.equals("ToolRod") || partName.equals("ToughRod"))
				par3List.add("Handle Modifier: " + Float.toString(material.handleModifier));
			else if (!partName.equals("Binding"))
			{
				par3List.add("Speed: " + material.miningspeed);
				par3List.add("Durability: " + material.durability);
			}

			if (IguanaConfig.partReplacement)
				if (material.ability.equals("Writable") || material.ability.equals("Thaumic"))
				{
					par3List.add("\u00a74Cannot be replaced once added,");
					par3List.add("\u00a74unless a modifier is available");
				} else
					par3List.add("\u00a76Parts can be replaced");
		}
	}

}

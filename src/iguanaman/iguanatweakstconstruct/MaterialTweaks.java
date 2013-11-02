package iguanaman.iguanatweakstconstruct;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import tconstruct.common.TContent;
import tconstruct.library.TConstructRegistry;
import cpw.mods.fml.common.FMLLog;

public class MaterialTweaks {

	public static void init()
	{
		
		// TINKERS
    	IguanaLog.log("Modifying TConstruct materials");
    	
		float durMod = (float)IguanaConfig.durabilityPercentage / 100F;
		float speedMod = (float)IguanaConfig.miningSpeedPercentage / 100F;
		
		//FMLLog.warning("speedmod" + Float.toString(speedMod) + " iron" + Math.round(500f * speedMod));
		
        TConstructRegistry.instance.toolMaterials.remove(0);
        TConstructRegistry.addToolMaterial(0, "Wood", "Wooden ", 0, Math.round(50F * durMod), Math.round(100f * speedMod), 0, 1.0F, 0, 0f, "\u00A7e", "");

        TConstructRegistry.instance.toolMaterials.remove(4);
        TConstructRegistry.addToolMaterial(4, "Cactus", 0, Math.round(25F * durMod), Math.round(50f * speedMod), 1, 1.0F, 0, -1f, "\u00A72", "Jagged");

        TConstructRegistry.instance.toolMaterials.remove(8);
        TConstructRegistry.addToolMaterial(8, "Slime", 0, Math.round(500f * durMod), Math.round(50f * speedMod), 0, 2.0F, 0, 0f, "\u00A7a", "");
        
        TConstructRegistry.instance.toolMaterials.remove(17);
        TConstructRegistry.addToolMaterial(17, "BlueSlime", "Slime ", 0, Math.round(500f * durMod), Math.round(50f * speedMod), 0, 1.5F, 0, 0f, "\u00A7b", "");

        TConstructRegistry.instance.toolMaterials.remove(9);
        TConstructRegistry.addToolMaterial(9, "Paper", 0, Math.round(25F * durMod), Math.round(50f * speedMod), 0, 0.3F, 0, 0f, "\u00A7f", "Writable");
        
        TConstructRegistry.instance.toolMaterials.remove(1);
        TConstructRegistry.addToolMaterial(1, "Stone", 0, Math.round(75F * durMod), Math.round(200f * speedMod), 1, 0.5F, 0, 1f, "", "Stonebound");
		
        TConstructRegistry.instance.toolMaterials.remove(3);
        TConstructRegistry.addToolMaterial(3, "Flint", 1, Math.round(100F * durMod), Math.round(300f * speedMod), 1, 0.5F, 0, 0f, "\u00A78", "");

        TConstructRegistry.instance.toolMaterials.remove(5);
        TConstructRegistry.addToolMaterial(5, "Bone", 1, Math.round(100f * durMod), Math.round(300f * speedMod), 1, 1.0F, 0, 0f, "\u00A7e", "");
        
        TConstructRegistry.instance.toolMaterials.remove(7);
        TConstructRegistry.addToolMaterial(7, "Netherrack", 0, Math.round(300f * durMod), Math.round(500f * speedMod), 3, 1.2F, 0, 1f, "\u00A74", "Stonebound");
        
        TConstructRegistry.instance.toolMaterials.remove(13);
        TConstructRegistry.addToolMaterial(13, "Copper", 2, Math.round(200f * durMod), Math.round(400f * speedMod), 1, 1.15F, 0, 0f, "\u00A7c", "");
        
        TConstructRegistry.instance.toolMaterials.remove(2);
        TConstructRegistry.addToolMaterial(2, "Iron", 3, Math.round(250f * durMod), Math.round(500f * speedMod), 2, 1.3F, 1, 0f, "\u00A7f", "");
        
        TConstructRegistry.instance.toolMaterials.remove(14);
        TConstructRegistry.addToolMaterial(14, "Bronze", 4, Math.round(350f * durMod), Math.round(600f * speedMod), 2, 1.3F, 1, 0f, "\u00A76", "");
   
        if (TContent.thaumcraftAvailable)
        {
            TConstructRegistry.instance.toolMaterials.remove(31);
            TConstructRegistry.addToolMaterial(31, "Thaumium", 3, Math.round(200f * durMod), Math.round(600f * speedMod), 2, 1.3F, 0, 0f, "\u00A75", "Thaumic");
        }

        TConstructRegistry.instance.toolMaterials.remove(16);
        TConstructRegistry.addToolMaterial(16, "Steel", 4, Math.round(400f * durMod), Math.round(600f * speedMod), 2, 1.3F, 2, 0f, "\u00A7f", "");

        TConstructRegistry.instance.toolMaterials.remove(6);
        TConstructRegistry.addToolMaterial(6, "Obsidian", 5, Math.round(450f * durMod), Math.round(700f * speedMod), 2, 0.8F, 3, 0f, "\u00A78", "");

        TConstructRegistry.instance.toolMaterials.remove(15);
        TConstructRegistry.addToolMaterial(15, "Alumite", 5, Math.round(550f * durMod), Math.round(800f * speedMod), 3, 1.3F, 2, 0f, "\u00A7d", "");

        TConstructRegistry.instance.toolMaterials.remove(11);
        TConstructRegistry.addToolMaterial(11, "Ardite", 6, Math.round(700f * durMod), Math.round(850f * speedMod), 3, 2.0F, 0, 2f, "\u00A74", "Stonebound");

        TConstructRegistry.instance.toolMaterials.remove(10);
        TConstructRegistry.addToolMaterial(10, "Cobalt", 7, Math.round(950f * durMod), Math.round(900f * speedMod), 3, 1.75F, 2, 0f, "\u00A73", "");

        TConstructRegistry.instance.toolMaterials.remove(12);
        TConstructRegistry.addToolMaterial(12, "Manyullyn", 8, Math.round(1200f * durMod), Math.round(1000f * speedMod), 4, 2.5F, 0, 0f, "\u00A75", "");
        
		
        // VANILLA
    	IguanaLog.log("Modifying harvest levels of vanilla tools");
        MinecraftForge.setToolClass(Item.pickaxeWood,   "pickaxe", TConstructRegistry.getMaterial("Wood").harvestLevel());
        MinecraftForge.setToolClass(Item.axeWood,   "axe", TConstructRegistry.getMaterial("Wood").harvestLevel());
        MinecraftForge.setToolClass(Item.shovelWood,   "shovel", TConstructRegistry.getMaterial("Wood").harvestLevel());
        MinecraftForge.setToolClass(Item.pickaxeGold,   "pickaxe", TConstructRegistry.getMaterial("Wood").harvestLevel());
        MinecraftForge.setToolClass(Item.axeGold,   "axe", TConstructRegistry.getMaterial("Wood").harvestLevel());
        MinecraftForge.setToolClass(Item.shovelGold,   "shovel", TConstructRegistry.getMaterial("Wood").harvestLevel());
        MinecraftForge.setToolClass(Item.pickaxeStone,   "pickaxe", TConstructRegistry.getMaterial("Stone").harvestLevel());
        MinecraftForge.setToolClass(Item.axeStone,   "axe", TConstructRegistry.getMaterial("Stone").harvestLevel());
        MinecraftForge.setToolClass(Item.shovelStone,   "shovel", TConstructRegistry.getMaterial("Stone").harvestLevel());
        MinecraftForge.setToolClass(Item.pickaxeIron,   "pickaxe", TConstructRegistry.getMaterial("Iron").harvestLevel());
        MinecraftForge.setToolClass(Item.axeIron,   "axe", TConstructRegistry.getMaterial("Iron").harvestLevel());
        MinecraftForge.setToolClass(Item.shovelIron,   "shovel", TConstructRegistry.getMaterial("Iron").harvestLevel());
        MinecraftForge.setToolClass(Item.pickaxeDiamond, "pickaxe", TConstructRegistry.getMaterial("Obsidian").harvestLevel());
        MinecraftForge.setToolClass(Item.axeDiamond, "axe", TConstructRegistry.getMaterial("Obsidian").harvestLevel());
        MinecraftForge.setToolClass(Item.shovelDiamond, "shovel", TConstructRegistry.getMaterial("Obsidian").harvestLevel());
	}
	
}

package iguanaman.iguanatweakstconstruct.old.tweaks;

import iguanaman.iguanatweakstconstruct.reference.Config;
import iguanaman.iguanatweakstconstruct.util.Log;
import tconstruct.library.TConstructRegistry;
import tconstruct.tools.TinkerTools;
import cpw.mods.fml.common.Loader;

public class MaterialTweaks {

	public static void init()
	{

		// TINKERS
		Log.info("Modifying TConstruct materials");

		float durMod = Config.durabilityPercentage / 100F;
		float speedMod = Config.miningSpeedPercentage / 100F;

		TConstructRegistry.toolMaterials.remove(0);
		TConstructRegistry.addToolMaterial(0, "Wood", "Wooden ", 0, Math.round(59F * durMod), Math.round(150f * speedMod), 1, 1.0F, 0, 0f, "\u00A7e", "");

		TConstructRegistry.toolMaterials.remove(4);
		TConstructRegistry.addToolMaterial(4, "Cactus", 0, Math.round(150F * durMod), Math.round(500f * speedMod), 2, 1.0F, 0, -1f, "\u00A72", "Jagged");

		TConstructRegistry.toolMaterials.remove(8);
		TConstructRegistry.addToolMaterial(8, "Slime", 0, Math.round(500f * durMod), Math.round(100f * speedMod), 1, 2.0F, 0, 0f, "\u00A7a", "");

		TConstructRegistry.toolMaterials.remove(17);
		TConstructRegistry.addToolMaterial(17, "BlueSlime", "Slime ", 0, Math.round(1200f * durMod), Math.round(100f * speedMod), 1, 1.5F, 0, 0f, "\u00A7b", "");

		TConstructRegistry.toolMaterials.remove(9);
		TConstructRegistry.addToolMaterial(9, "Paper", 0, Math.round(30F * durMod), Math.round(100f * speedMod), 1, 0.3F, 0, 0f, "\u00A7f", "Writable");

		TConstructRegistry.toolMaterials.remove(1);
		TConstructRegistry.addToolMaterial(1, "Stone", 0, Math.round(131F * durMod), Math.round(300f * speedMod), 2, 0.5F, 0, 1f, "", "Stonebound");

		TConstructRegistry.toolMaterials.remove(3);
		TConstructRegistry.addToolMaterial(3, "Flint", 1, Math.round(171F * durMod), Math.round(400f * speedMod), 2, 0.5F, 0, 0f, "\u00A78", "");

		TConstructRegistry.toolMaterials.remove(5);
		TConstructRegistry.addToolMaterial(5, "Bone", 1, Math.round(200f * durMod), Math.round(400f * speedMod), 2, 1.0F, 0, 0f, "\u00A7e", "");

		TConstructRegistry.toolMaterials.remove(7);
		TConstructRegistry.addToolMaterial(7, "Netherrack", 0, Math.round(131f * durMod), Math.round(400f * speedMod), 3, 1.2F, 0, 1f, "\u00A74", "Stonebound");

		TConstructRegistry.toolMaterials.remove(13);
		TConstructRegistry.addToolMaterial(13, "Copper", 2, Math.round(180f * durMod), Math.round(500f * speedMod), 2, 1.15F, 0, 0f, "\u00A7c", "");

		TConstructRegistry.toolMaterials.remove(2);
		TConstructRegistry.addToolMaterial(2, "Iron", 3, Math.round(250f * durMod), Math.round(600f * speedMod), 3, 1.3F, 1, 0f, "\u00A7f", "");

		TConstructRegistry.toolMaterials.remove(14);
		TConstructRegistry.addToolMaterial(14, "Bronze", 4, Math.round(350f * durMod), Math.round(700f * speedMod), 3, 1.3F, 1, 0f, "\u00A76", "");

		if (TinkerTools.thaumcraftAvailable)
		{
			TConstructRegistry.toolMaterials.remove(31);
			TConstructRegistry.addToolMaterial(31, "Thaumium", 3, Math.round(200f * durMod), Math.round(600f * speedMod), 3, 1.3F, 0, 0f, "\u00A75", "Thaumic");
		}

		TConstructRegistry.toolMaterials.remove(16);
		TConstructRegistry.addToolMaterial(16, "Steel", 4, Math.round(400f * durMod), Math.round(800f * speedMod), 3, 1.3F, 2, 0f, "\u00A7f", "");

		TConstructRegistry.toolMaterials.remove(18);
		TConstructRegistry.addToolMaterial(18, "PigIron", "Pig Iron ", 4, Math.round(250F * durMod), Math.round(600F * speedMod), 3, 1.3F, 1, 0f, "\u00A7c", "Tasty");

		TConstructRegistry.toolMaterials.remove(6);
		TConstructRegistry.addToolMaterial(6, "Obsidian", 5, Math.round(89f * durMod), Math.round(700f * speedMod), 3, 0.8F, 3, 0f, "\u00A78", "");

		TConstructRegistry.toolMaterials.remove(15);
		TConstructRegistry.addToolMaterial(15, "Alumite", 5, Math.round(550f * durMod), Math.round(800f * speedMod), 4, 1.3F, 2, 0f, "\u00A7d", "");

		TConstructRegistry.toolMaterials.remove(11);
		TConstructRegistry.addToolMaterial(11, "Ardite", 6, Math.round(600f * durMod), Math.round(800f * speedMod), 4, 2.0F, 0, 2f, "\u00A74", "Stonebound");

		TConstructRegistry.toolMaterials.remove(10);
		TConstructRegistry.addToolMaterial(10, "Cobalt", 7, Math.round(800f * durMod), Math.round(1100f * speedMod), 4, 1.75F, 2, 0f, "\u00A73", "");

		TConstructRegistry.toolMaterials.remove(12);
		TConstructRegistry.addToolMaterial(12, "Manyullyn", 8, Math.round(1200f * durMod), Math.round(900f * speedMod), 5, 2.5F, 0, 0f, "\u00A75", "");


		//Extra TiC / Metallurgy materials
		if (Loader.isModLoaded("ExtraTiC") && Loader.isModLoaded("Metallurgy3Base"))
		{
			// Stone level
			TConstructRegistry.toolMaterials.remove(103);
			TConstructRegistry.addToolMaterial(103, "Brass", 0, Math.round(15F * durMod), Math.round(1200F * speedMod), 2, 0.2F, 0, 0.0F, "", "");

			TConstructRegistry.toolMaterials.remove(104);
			TConstructRegistry.addToolMaterial(104, "Electrum", 0, Math.round(100F * durMod), Math.round(2000F * speedMod), 2, 0.75F, 0, 0.0F, "", "");

			TConstructRegistry.toolMaterials.remove(106);
			TConstructRegistry.addToolMaterial(106, "Silver", 0, Math.round(25F * durMod), Math.round(1600F * speedMod), 2, 0.2F, 0, 0.0F, "", "");

			TConstructRegistry.toolMaterials.remove(109);
			TConstructRegistry.addToolMaterial(109, "Ignatius", 0, Math.round(200F * durMod), Math.round(400F * speedMod), 3, 1.0F, 0, 0.0F, "", "");

			TConstructRegistry.toolMaterials.remove(114);
			TConstructRegistry.addToolMaterial(114, "Shadow Iron", 0, Math.round(300F * durMod), Math.round(400F * speedMod), 3, 1.3F, 1, 0.0F, "", "");

			TConstructRegistry.toolMaterials.remove(129);
			TConstructRegistry.addToolMaterial(129, "Prometheum", 0, Math.round(200F * durMod), Math.round(400F * speedMod), 2, 1.0F, 0, 0.0F, "", "");

			// Iron Level
			TConstructRegistry.toolMaterials.remove(105);
			TConstructRegistry.addToolMaterial(105, "Platinum", 3, Math.round(100F * durMod), Math.round(2400F * speedMod), 2, 0.75F, 0, 0.0F, "", "");

			TConstructRegistry.toolMaterials.remove(115);
			TConstructRegistry.addToolMaterial(115, "Shadow Steel", 3, Math.round(400F * durMod), Math.round(600F * speedMod), 4, 1.3F, 2, 0.0F, "", "");

			TConstructRegistry.toolMaterials.remove(121);
			TConstructRegistry.addToolMaterial(121, "Black Steel", 3, Math.round(500F * durMod), Math.round(800F * speedMod), 3, 1.3F, 2, 0.0F, "§9", "");

			TConstructRegistry.toolMaterials.remove(124);
			TConstructRegistry.addToolMaterial(124, "Deep Iron", 3, Math.round(250F * durMod), Math.round(600F * speedMod), 3, 1.3F, 1, 0.0F, "§9", "");

			// Bronze Level
			TConstructRegistry.toolMaterials.remove(100);
			TConstructRegistry.addToolMaterial(100, "Angmallen", 4, Math.round(300F * durMod), Math.round(800F * speedMod), 3, 1.0F, 0, 0.0F, "", "");

			TConstructRegistry.toolMaterials.remove(102);
			TConstructRegistry.addToolMaterial(102, "Hepatizon", 4, Math.round(300F * durMod), Math.round(800F * speedMod), 2, 1.2F, 0, 0.0F, "", "");

			TConstructRegistry.toolMaterials.remove(108);
			TConstructRegistry.addToolMaterial(108, "Ceruclase", 4, Math.round(500F * durMod), Math.round(700F * speedMod), 4, 1.4F, 0, 0.0F, "", "");

			TConstructRegistry.toolMaterials.remove(112);
			TConstructRegistry.addToolMaterial(112, "Midasium", 4, Math.round(100F * durMod), Math.round(1000F * speedMod), 4, 1.0F, 0, 0.0F, "", "");

			TConstructRegistry.toolMaterials.remove(117);
			TConstructRegistry.addToolMaterial(117, "Vyroxeres", 4, Math.round(300F * durMod), Math.round(700F * speedMod), 4, 1.3F, 0, 0.0F, "", "");

			TConstructRegistry.toolMaterials.remove(128);
			TConstructRegistry.addToolMaterial(128, "Oureclase", 4, Math.round(750F * durMod), Math.round(800F * speedMod), 3, 1.3F, 0, 0.0F, "", "");

			TConstructRegistry.toolMaterials.remove(133);
			TConstructRegistry.addToolMaterial(133, "Eximite", 4, Math.round(1000F * durMod), Math.round(800F * speedMod), 4, 1.3F, 0, 0.0F, "", "");

			// Obsidian Level
			TConstructRegistry.toolMaterials.remove(101);
			TConstructRegistry.addToolMaterial(101, "Damascus Steel", 5, Math.round(500F * durMod), Math.round(600F * speedMod), 3, 1.3F, 2, 0.0F, "", "");

			TConstructRegistry.toolMaterials.remove(107);
			TConstructRegistry.addToolMaterial(107, "Amordrine", 5, Math.round(1300F * durMod), Math.round(1400F * speedMod), 4, 1.8F, 0, 0.0F, "", "");

			TConstructRegistry.toolMaterials.remove(110);
			TConstructRegistry.addToolMaterial(110, "Inolashite", 5, Math.round(900F * durMod), Math.round(800F * speedMod), 4, 1.7F, 0, 0.0F, "", "");

			TConstructRegistry.toolMaterials.remove(111);
			TConstructRegistry.addToolMaterial(111, "Kalendrite", 5, Math.round(1000F * durMod), Math.round(800F * speedMod), 4, 1.75F, 0, 0.0F, "", "");

			TConstructRegistry.toolMaterials.remove(119);
			TConstructRegistry.addToolMaterial(119, "Astral Silver", 5, Math.round(35F * durMod), Math.round(1200F * speedMod), 2, 0.35F, 0, 0.0F, "", "");

			TConstructRegistry.toolMaterials.remove(122);
			TConstructRegistry.addToolMaterial(122, "Carmot", 5, Math.round(50F * durMod), Math.round(1200F * speedMod), 2, 0.35F, 0, 0.0F, "", "");

			TConstructRegistry.toolMaterials.remove(125);
			TConstructRegistry.addToolMaterial(125, "Haderoth", 5, Math.round(1250F * durMod), Math.round(1200F * speedMod), 4, 2.3F, 0, 0.0F, "", "");

			TConstructRegistry.toolMaterials.remove(126);
			TConstructRegistry.addToolMaterial(126, "Mithril", 5, Math.round(1000F * durMod), Math.round(900F * speedMod), 4, 1.5F, 0, 0.0F, "", "");

			TConstructRegistry.toolMaterials.remove(130);
			TConstructRegistry.addToolMaterial(130, "Quicksilver", 5, Math.round(1100F * durMod), Math.round(1400F * speedMod), 4, 1.8F, 0, 0.0F, "", "");

			TConstructRegistry.toolMaterials.remove(132);
			TConstructRegistry.addToolMaterial(132, "Desichalkosr", 5, Math.round(1800F * durMod), Math.round(1000F * speedMod), 5, 2.75F, 0, 0.0F, "", "");

			//Ardite Level
			TConstructRegistry.toolMaterials.remove(116);
			TConstructRegistry.addToolMaterial(116, "Vulcanite", 6, Math.round(1500F * durMod), Math.round(1000F * speedMod), 4, 2.0F, 0, 0.0F, "", "");

			TConstructRegistry.toolMaterials.remove(123);
			TConstructRegistry.addToolMaterial(123, "Celenegil", 6, Math.round(1600F * durMod), Math.round(1400F * speedMod), 4, 2.5F, 0, 0.0F, "", "");

			TConstructRegistry.toolMaterials.remove(127);
			TConstructRegistry.addToolMaterial(127, "Orichalcum", 6, Math.round(1350F * durMod), Math.round(900F * speedMod), 4, 2.5F, 0, 0.0F, "", "");

			// Cobalt Level
			TConstructRegistry.toolMaterials.remove(113);
			TConstructRegistry.addToolMaterial(113, "Sanguinite", 7, Math.round(1750F * durMod), Math.round(1200F * speedMod), 5, 2.3F, 0, 0.0F, "", "");

			TConstructRegistry.toolMaterials.remove(118);
			TConstructRegistry.addToolMaterial(118, "Adamantine", 7, Math.round(1550F * durMod), Math.round(1000F * speedMod), 5, 2.75F, 2, 0.0F, "§4", "");

			TConstructRegistry.toolMaterials.remove(120);
			TConstructRegistry.addToolMaterial(120, "Atlarus", 7, Math.round(1750F * durMod), Math.round(1000F * speedMod), 5, 2.5F, 0, 0.0F, "", "");

			//Manyullyn Level
			TConstructRegistry.toolMaterials.remove(131);
			TConstructRegistry.addToolMaterial(131, "Tartarite", 8, Math.round(3000F * durMod), Math.round(1400F * speedMod), 6, 3.0F, 0, 0.0F, "", "");

		}
	}

}

package iguanaman.iguanatweakstconstruct.old.gui;

import iguanaman.iguanatweakstconstruct.util.HarvestLevels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import tconstruct.client.gui.NewContainerGui;
import tconstruct.smeltery.inventory.ActiveContainer;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.tools.ToolMaterial;
import tconstruct.tools.inventory.PartCrafterChestContainer;
import tconstruct.tools.logic.PartBuilderLogic;

public class IguanaPartCrafterGui extends NewContainerGui
{
	PartBuilderLogic logic;
	String title, otherTitle = "";
	boolean drawChestPart;
	boolean hasTop, hasBottom;
	ItemStack topMaterial, bottomMaterial;
	ToolMaterial topEnum, bottomEnum;

	public IguanaPartCrafterGui(InventoryPlayer inventoryplayer, PartBuilderLogic partlogic, World world, int x, int y, int z)
	{
		super((ActiveContainer) partlogic.getGuiContainer(inventoryplayer, world, x, y, z));
		logic = partlogic;
		drawChestPart = container instanceof PartCrafterChestContainer;

		title = "\u00A7nTool Part Crafting";
	}

	@Override
	protected void drawGuiContainerForegroundLayer (int par1, int par2)
	{
		fontRendererObj.drawString(StatCollector.translateToLocal("crafters.PartBuilder"), 6, 6, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
		if (drawChestPart)
			fontRendererObj.drawString(StatCollector.translateToLocal("inventory.PatternChest"), -108, ySize - 148, 4210752);

		drawMaterialInformation();
	}

	void drawDefaultInformation ()
	{
		title = "\u00A7nTool Part Building";
		drawCenteredString(fontRendererObj, title, xSize + 63, 8, 16777215);
		fontRendererObj.drawSplitString("Place a pattern and a material on the left to get started.", xSize + 8, 24, 115, 16777215);
	}

	void drawMaterialInformation ()
	{
		ItemStack top = logic.getStackInSlot(2);
		//ItemStack topResult = logic.getStackInSlot(4);
		ItemStack bottom = logic.getStackInSlot(3);
		//ItemStack bottomResult = logic.getStackInSlot(6);
		if (topMaterial != top)
		{
			topMaterial = top;
			int topID = PatternBuilder.instance.getPartID(top);

			if (topID != Short.MAX_VALUE)// && topResult != null)
			{
				topEnum = TConstructRegistry.getMaterial(topID);
				hasTop = true;
				title = "\u00A7n" + topEnum.name();
			}
			else
				hasTop = false;
		}

		if (bottomMaterial != bottom)
		{
			bottomMaterial = bottom;
			int bottomID = PatternBuilder.instance.getPartID(bottom);

			if (bottomID != Short.MAX_VALUE)// && bottomResult != null)
			{
				bottomEnum = TConstructRegistry.getMaterial(bottomID);
				hasBottom = true;
				otherTitle = "\u00A7n" + bottomEnum.name();
			}
			else
				hasBottom = false;
		}

		int offset = 8;
		if (hasTop)
		{
			drawCenteredString(fontRendererObj, title, xSize + 63, offset, 16777215);
			fontRendererObj.drawString("Base Durability: " + topEnum.durability(), xSize + 8, offset + 16, 16777215);
			fontRendererObj.drawString("Handle Modifier: " + topEnum.handleDurability() + "x", xSize + 8, offset + 27, 16777215);
			fontRendererObj.drawString("Mining Speed: " + topEnum.toolSpeed() / 100f, xSize + 8, offset + 38, 16777215);
			fontRendererObj.drawString("Mining Level: " + getHarvestLevelName(topEnum.harvestLevel()), xSize + 8, offset + 49, 16777215);

			int attack = topEnum.attack();
			String heart = attack == 2 ? " Heart" : " Hearts";
			if (attack % 2 == 0)
				fontRendererObj.drawString("Attack: " + attack / 2 + heart, xSize + 8, offset + 60, 0xffffff);
			else
				fontRendererObj.drawString("Attack: " + attack / 2f + heart, xSize + 8, offset + 60, 0xffffff);
		}

		offset = 90;
		if (hasBottom)
		{
			drawCenteredString(fontRendererObj, otherTitle, xSize + 63, offset, 16777215);
			fontRendererObj.drawString("Base Durability: " + bottomEnum.durability(), xSize + 8, offset + 16, 16777215);
			fontRendererObj.drawString("Handle Modifier: " + bottomEnum.handleDurability() + "x", xSize + 8, offset + 27, 16777215);
			fontRendererObj.drawString("Mining Speed: " + bottomEnum.toolSpeed() / 100f, xSize + 8, offset + 38, 16777215);
			fontRendererObj.drawString("Mining Level: " + getHarvestLevelName(bottomEnum.harvestLevel()), xSize + 8, offset + 49, 16777215);
			int attack = bottomEnum.attack();
			String heart = attack == 2 ? " Heart" : " Hearts";
			if (attack % 2 == 0)
				fontRendererObj.drawString("Attack: " + attack / 2 + heart, xSize + 8, offset + 60, 0xffffff);
			else
				fontRendererObj.drawString("Attack: " + attack / 2f + heart, xSize + 8, offset + 60, 0xffffff);
		}

		if (!hasTop && !hasBottom)
			drawDefaultInformation();
	}

	public static String getHarvestLevelName (int num)
	{
		return HarvestLevels.getHarvestLevelName(num);
	}

	private static final ResourceLocation background = new ResourceLocation("tinker", "textures/gui/toolparts.png");
	private static final ResourceLocation minichest = new ResourceLocation("tinker", "textures/gui/patternchestmini.png");
	private static final ResourceLocation description = new ResourceLocation("tinker", "textures/gui/description.png");

	@Override
	protected void drawGuiContainerBackgroundLayer (float par1, int par2, int par3)
	{
		// Draw the background
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(background);
		int cornerX = (width - xSize) / 2;
		int cornerY = (height - ySize) / 2;
		drawTexturedModalRect(cornerX, cornerY, 0, 0, xSize, ySize);

		// Draw Slots
		drawTexturedModalRect(cornerX + 39, cornerY + 26, 0, 166, 98, 36);
		if (!logic.isStackInSlot(0))
			drawTexturedModalRect(cornerX + 39, cornerY + 26, 176, 0, 18, 18);
		if (!logic.isStackInSlot(2))
			drawTexturedModalRect(cornerX + 57, cornerY + 26, 176, 18, 18, 18);
		if (!logic.isStackInSlot(1))
			drawTexturedModalRect(cornerX + 39, cornerY + 44, 176, 0, 18, 18);
		if (!logic.isStackInSlot(3))
			drawTexturedModalRect(cornerX + 57, cornerY + 44, 176, 36, 18, 18);

		// Draw chest
		if (drawChestPart)
		{
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			mc.getTextureManager().bindTexture(minichest);
			drawTexturedModalRect(cornerX - 116, cornerY + 11, 0, 0, xSize, ySize);
		}

		// Draw description
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(description);
		cornerX = (width + xSize) / 2;
		cornerY = (height - ySize) / 2;
		drawTexturedModalRect(cornerX, cornerY, 126, 0, 126, ySize);
	}

	@Override
	protected void mouseClicked (int mouseX, int mouseY, int clickNum)
	{
		boolean flag = clickNum == mc.gameSettings.keyBindPickBlock.getKeyCode() + 100;
		Slot slot = getSlotAtPosition(mouseX, mouseY);
		long l = Minecraft.getSystemTime();
		field_94074_J = field_94072_H == slot && l - field_94070_G < 250L && field_94073_I == clickNum;
		field_94068_E = false;

		int offsetLeft = drawChestPart ? 108 : 0;

		if (clickNum == 0 || clickNum == 1 || flag)
		{
			int i1 = guiLeft;
			int j1 = guiTop;
			boolean flag1 = mouseX < i1 - offsetLeft || mouseY < j1 || mouseX >= i1 + xSize || mouseY >= j1 + ySize;
			int k1 = -1;

			if (slot != null)
				k1 = slot.slotNumber;

			if (flag1)
				k1 = -999;

			if (mc.gameSettings.touchscreen && flag1 && mc.thePlayer.inventory.getItemStack() == null)
			{
				mc.displayGuiScreen((GuiScreen) null);
				return;
			}

			if (k1 != -1)
				if (mc.gameSettings.touchscreen)
				{
					if (slot != null && slot.getHasStack())
					{
						clickedSlot = slot;
						draggedStack = null;
						isRightMouseClick = clickNum == 1;
					} else
						clickedSlot = null;
				}
				else if (!field_94076_q)
					if (mc.thePlayer.inventory.getItemStack() == null)
					{
						if (clickNum == mc.gameSettings.keyBindPickBlock.getKeyCode() + 100)
							handleMouseClick(slot, k1, clickNum, 3);
						else
						{
							boolean flag2 = k1 != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
							byte b0 = 0;

							if (flag2)
							{
								field_94075_K = slot != null && slot.getHasStack() ? slot.getStack() : null;
								b0 = 1;
							}
							else if (k1 == -999)
								b0 = 4;

							handleMouseClick(slot, k1, clickNum, b0);
						}

						field_94068_E = true;
					}
					else
					{
						field_94076_q = true;
						field_94067_D = clickNum;
						field_94077_p.clear();

						if (clickNum == 0)
							field_94071_C = 0;
						else if (clickNum == 1)
							field_94071_C = 1;
					}
		}

		field_94072_H = slot;
		field_94070_G = l;
		field_94073_I = clickNum;
	}
}

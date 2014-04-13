package iguanaman.iguanatweakstconstruct.modifiers;

import iguanaman.iguanatweakstconstruct.IguanaLog;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.common.TContent;
import tconstruct.library.crafting.ToolBuilder;
import tconstruct.library.tools.ToolCore;
import tconstruct.library.tools.ToolMod;

public class IguanaModClean extends ToolMod {

	public IguanaModClean() {
		super(new ItemStack[] { new ItemStack(TContent.materials, 1, 25) }, 0, "");
	}

	public IguanaModClean(ItemStack[] items, int effect, String dataKey) {
		super(items, effect, dataKey);
	}

	@Override
	protected boolean canModify (ItemStack tool, ItemStack[] input) { return true; }

	@Override
	public void modify(ItemStack[] input, ItemStack tool) {

		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
		ToolCore toolClass = (ToolCore)tool.getItem();

		//get old tool part materials
		int oldHead = tags.getInteger("Head");
		int oldHandle = tags.getInteger("Handle");
		int oldAccessory = tags.getInteger("Accessory");
		int oldExtra = tags.getInteger("Extra");

		//get current item parts
		ItemStack headStack = new ItemStack(toolClass.getHeadItem(), 1, oldHead);
		ItemStack handleStack = new ItemStack(toolClass.getHandleItem(), 1, oldHandle);
		ItemStack accessoryStack = null;
		if (toolClass.getAccessoryItem() != null) accessoryStack = new ItemStack(toolClass.getAccessoryItem(), 1, oldAccessory);
		ItemStack extraStack = null;
		if (toolClass.getExtraItem() != null) extraStack = new ItemStack(toolClass.getExtraItem(), 1, oldExtra);

		// Get tool name
		String name = "";
		if (tags.hasKey("display"))
		{
			NBTTagCompound displayTags = tags.getCompoundTag("display");
			if (displayTags.hasKey("Name")) name = displayTags.getString("Name");
		}

		// Create blank tool
		IguanaLog.log("replacing tool");
		tool = ToolBuilder.instance.buildTool(headStack, handleStack, accessoryStack, extraStack, name);
	}

	@Override
	public void addMatchingEffect (ItemStack tool) {}

}

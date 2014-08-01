package iguanaman.iguanatweakstconstruct.mobheads.renderers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import iguanaman.iguanatweakstconstruct.mobheads.IguanaMobHeads;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;
import org.lwjgl.opengl.GL11;

public class RenderPlayerHandler {
    @SubscribeEvent
    public void renderSkullHelmet(RenderPlayerEvent.SetArmorModel event)
    {
        ItemStack itemStack = event.entityPlayer.inventory.armorItemInSlot(3);
        if(itemStack == null || itemStack.getItem() != IguanaMobHeads.skullItem)
            return;

        GL11.glPushMatrix();
        event.renderer.modelBipedMain.bipedHead.postRender(0.0625F);
        float f1 = 1.0625F;
        GL11.glScalef(f1, -f1, -f1);
        IguanaTileEntitySkullRenderer.renderer.renderSkull(-0.5f, 0.0f, -0.5f, 180.0f, 1, itemStack.getItemDamage());
        //TileEntitySkullRenderer.field_147536_b.func_152674_a(-0.5F, 0.0F, -0.5F, 1, 00.0F, 0, null);
        GL11.glPopMatrix();
    }
}

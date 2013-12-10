package iguanaman.iguanatweakstconstruct.renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;

public class IguanaSkullRenderer extends TileEntitySkullRenderer {

	public IguanaSkullRenderer() {}
	
	@Override
    public void func_82393_a(float par1, float par2, float par3, int par4, float par5, int par6, String par7Str)
    {
        ModelSkeletonHead modelskeletonhead = this.field_82396_c;

        switch (par6)
        {
            case 0:
            default:
                this.bindTexture(field_110642_c);
                break;
            case 1:
                this.bindTexture(field_110640_d);
                break;
            case 2:
                this.bindTexture(field_110641_e);
                modelskeletonhead = this.field_82395_d;
                break;
            case 3:
                ResourceLocation resourcelocation = AbstractClientPlayer.locationStevePng;

                if (par7Str != null && par7Str.length() > 0)
                {
                    resourcelocation = AbstractClientPlayer.getLocationSkull(par7Str);
                    AbstractClientPlayer.getDownloadImageSkin(resourcelocation, par7Str);
                }

                this.bindTexture(resourcelocation);
                break;
            case 4:
                this.bindTexture(field_110639_f);
                break;
            case 5:
                this.bindTexture(new ResourceLocation("textures/entity/enderman/enderman.png"));
                break;
            case 6:
                this.bindTexture(new ResourceLocation("textures/entity/zombie_pigman.png"));
                break;
            case 7:
                this.bindTexture(new ResourceLocation("textures/entity/blaze.png"));
                break;
        }

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);

        if (par4 != 1)
        {
            switch (par4)
            {
                case 2:
                    GL11.glTranslatef(par1 + 0.5F, par2 + 0.25F, par3 + 0.74F);
                    break;
                case 3:
                    GL11.glTranslatef(par1 + 0.5F, par2 + 0.25F, par3 + 0.26F);
                    par5 = 180.0F;
                    break;
                case 4:
                    GL11.glTranslatef(par1 + 0.74F, par2 + 0.25F, par3 + 0.5F);
                    par5 = 270.0F;
                    break;
                case 5:
                default:
                    GL11.glTranslatef(par1 + 0.26F, par2 + 0.25F, par3 + 0.5F);
                    par5 = 90.0F;
            }
        }
        else
        {
            GL11.glTranslatef(par1 + 0.5F, par2, par3 + 0.5F);
        }

        float f4 = 0.0625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        modelskeletonhead.render((Entity)null, 0.0F, 0.0F, 0.0F, par5, 0.0F, f4);
        GL11.glPopMatrix();
    }

}

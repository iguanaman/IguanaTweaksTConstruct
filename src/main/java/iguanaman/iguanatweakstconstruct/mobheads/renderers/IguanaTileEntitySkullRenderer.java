package iguanaman.iguanatweakstconstruct.mobheads.renderers;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.Map;

public class IguanaTileEntitySkullRenderer extends TileEntitySpecialRenderer {
    public static IguanaTileEntitySkullRenderer renderer = new IguanaTileEntitySkullRenderer();

    private ModelSkeletonHead modelSkull = new ModelSkeletonHead(0,0,64,32); // standard skull model
    private ModelSkeletonHead modelZombie = new ModelSkeletonHead(0,0,64,64); // zombie skull model

    private ModelHeadwear modelEnderManJaw = new ModelHeadwear(0,16,64,32);

    private ModelEnderManHead modelEnderManHead = new ModelEnderManHead();

    private ResourceLocation[] textures = new ResourceLocation[] {
            new ResourceLocation("textures/entity/enderman/enderman.png"),
            new ResourceLocation("textures/entity/zombie_pigman.png"),
            new ResourceLocation("textures/entity/blaze.png"),
            // modsupport: Thermal Expansion
            new ResourceLocation("thermalfoundation","textures/entity/Blizz.png")
    };

    private ResourceLocation enderManEyes = new ResourceLocation("textures/entity/enderman/enderman_eyes.png");

    public IguanaTileEntitySkullRenderer()
    {
        this.func_147497_a(TileEntityRendererDispatcher.instance);
    }



    @Override
    public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {
        if(!(entity instanceof TileEntitySkull))
            return;
        TileEntitySkull entitySkull = (TileEntitySkull)entity;

        float r = (entitySkull.func_145906_b() * 360) / 16.0F;
        renderSkull((float)x, (float)y, (float)z, r, entitySkull.getBlockMetadata(), entitySkull.func_145904_a());
    }

    public void renderSkull(float x, float y, float z, float r, int sidePlacement, int meta)
    {
        ModelBase model = modelSkull;

        // chose model
        if(meta == 0)
            model = modelEnderManHead;
        if(meta == 1)
            model = modelZombie;

        // chose texture
        this.bindTexture(textures[meta]);


        // debug
        //model = new ModelSkeletonHead(0,16,64,32);
        //this.bindTexture(new ResourceLocation("textures/entity/enderman/enderman_eyes.png"));

        // begin rendering
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);


        if (sidePlacement != 1)
        {
            switch (sidePlacement)
            {
                case 2:
                    GL11.glTranslatef(x + 0.5F, y + 0.25F, z + 0.74F);
                    break;
                case 3:
                    GL11.glTranslatef(x + 0.5F, y + 0.25F, z + 0.26F);
                    r = 180.0F;
                    break;
                case 4:
                    GL11.glTranslatef(x + 0.74F, y + 0.25F, z + 0.5F);
                    r = 270.0F;
                    break;
                case 5:
                default:
                    GL11.glTranslatef(x + 0.26F, y + 0.25F, z + 0.5F);
                    r = 90.0F;
            }
        }
        else
        {
            GL11.glTranslatef(x + 0.5F, y, z + 0.5F);
        }


        float f4 = 0.0625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        GL11.glEnable(GL11.GL_ALPHA_TEST);

        model.render(null, 0.0f, 0.0f, 0.0f, r, 0.0f, f4);

        // also render endermanstuff!
        if(meta == 0)
        {
            //modelEnderManJaw.render(null, 0.0f, 0.0f, 0.0f, r, 0.0f, f4);
            this.bindTexture(enderManEyes);
            model.render(null, 0.0f, 0.0f, 0.0f, r, 0.0f, f4);
        }

        GL11.glPopMatrix();
    }
}

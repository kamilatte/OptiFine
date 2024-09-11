package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.IllusionerRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterIllager;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterIllusioner extends ModelAdapterIllager {
  public ModelAdapterIllusioner() {
    super(EntityType.ILLUSIONER, "illusioner", 0.5F, new String[] { "illusion_illager" });
  }
  
  public Model makeModel() {
    IllagerModel model = new IllagerModel(bakeModelLayer(ModelLayers.ILLUSIONER));
    (model.getHat()).visible = true;
    return (Model)model;
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    IllusionerRenderer render = new IllusionerRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}

package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.WitchModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.WitchRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterVillager;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterWitch extends ModelAdapterVillager {
  public ModelAdapterWitch() {
    super(EntityType.WITCH, "witch", 0.5F);
  }
  
  public Model makeModel() {
    return (Model)new WitchModel(bakeModelLayer(ModelLayers.WITCH));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof WitchModel))
      return null; 
    WitchModel modelWitch = (WitchModel)model;
    if (modelPart.equals("mole"))
      return modelWitch.root().getChildModelDeep("mole"); 
    return super.getModelRenderer((Model)modelWitch, modelPart);
  }
  
  public String[] getModelRendererNames() {
    String[] names = super.getModelRendererNames();
    names = (String[])Config.addObjectToArray((Object[])names, "mole");
    return names;
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    WitchRenderer render = new WitchRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}

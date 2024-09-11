package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EndCrystalRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.EnderCrystalModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterEnderCrystal extends ModelAdapter {
  public ModelAdapterEnderCrystal() {
    this("end_crystal");
  }
  
  protected ModelAdapterEnderCrystal(String name) {
    super(EntityType.END_CRYSTAL, name, 0.5F);
  }
  
  public Model makeModel() {
    return (Model)new EnderCrystalModel();
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof EnderCrystalModel))
      return null; 
    EnderCrystalModel modelEnderCrystal = (EnderCrystalModel)model;
    if (modelPart.equals("cube"))
      return modelEnderCrystal.cube; 
    if (modelPart.equals("glass"))
      return modelEnderCrystal.glass; 
    if (modelPart.equals("base"))
      return modelEnderCrystal.base; 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "cube", "glass", "base" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    EntityRenderer renderObj = rendererCache.get(EntityType.END_CRYSTAL, index, () -> new EndCrystalRenderer(renderManager.getContext()));
    if (!(renderObj instanceof EndCrystalRenderer)) {
      Config.warn("Not an instance of RenderEnderCrystal: " + String.valueOf(renderObj));
      return null;
    } 
    EndCrystalRenderer render = (EndCrystalRenderer)renderObj;
    if (!(modelBase instanceof EnderCrystalModel)) {
      Config.warn("Not a EnderCrystalModel model: " + String.valueOf(modelBase));
      return null;
    } 
    EnderCrystalModel enderCrystalModel = (EnderCrystalModel)modelBase;
    render = enderCrystalModel.updateRenderer(render);
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}

package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.GuardianModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.GuardianRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.util.StrUtils;

public class ModelAdapterGuardian extends ModelAdapter {
  public ModelAdapterGuardian() {
    super(EntityType.GUARDIAN, "guardian", 0.5F);
  }
  
  public ModelAdapterGuardian(EntityType entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public Model makeModel() {
    return (Model)new GuardianModel(bakeModelLayer(ModelLayers.GUARDIAN));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof GuardianModel))
      return null; 
    GuardianModel modelGuardian = (GuardianModel)model;
    if (modelPart.equals("body"))
      return modelGuardian.root().getChildModelDeep("head"); 
    if (modelPart.equals("eye"))
      return modelGuardian.root().getChildModelDeep("eye"); 
    String PREFIX_SPINE = "spine";
    if (modelPart.startsWith(PREFIX_SPINE)) {
      String numStr = StrUtils.removePrefix(modelPart, PREFIX_SPINE);
      int index = Config.parseInt(numStr, -1);
      int indexPart = index - 1;
      return modelGuardian.root().getChildModelDeep("spike" + indexPart);
    } 
    String PREFIX_TAIL = "tail";
    if (modelPart.startsWith(PREFIX_TAIL)) {
      String numStr = StrUtils.removePrefix(modelPart, PREFIX_TAIL);
      int index = Config.parseInt(numStr, -1);
      int indexPart = index - 1;
      return modelGuardian.root().getChildModelDeep("tail" + indexPart);
    } 
    if (modelPart.equals("root"))
      return modelGuardian.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { 
        "body", "eye", "spine1", "spine2", "spine3", "spine4", "spine5", "spine6", "spine7", "spine8", 
        "spine9", "spine10", "spine11", "spine12", "tail1", "tail2", "tail3", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    GuardianRenderer render = new GuardianRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}

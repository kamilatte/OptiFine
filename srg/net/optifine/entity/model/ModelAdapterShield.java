package srg.net.optifine.entity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.entity.model.VirtualEntityRenderer;
import net.optifine.reflect.Reflector;
import net.optifine.util.Either;

public class ModelAdapterShield extends ModelAdapter {
  public ModelAdapterShield() {
    super((Either)null, "shield", 0.0F, null);
  }
  
  public Model makeModel() {
    return (Model)new ShieldModel(bakeModelLayer(ModelLayers.SHIELD));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof ShieldModel))
      return null; 
    ShieldModel modelShield = (ShieldModel)model;
    if (modelPart.equals("plate"))
      return (ModelPart)Reflector.ModelShield_ModelRenderers.getValue(modelShield, 1); 
    if (modelPart.equals("handle"))
      return (ModelPart)Reflector.ModelShield_ModelRenderers.getValue(modelShield, 2); 
    if (modelPart.equals("root"))
      return (ModelPart)Reflector.ModelShield_ModelRenderers.getValue(modelShield, 0); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "plate", "handle", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    return (IEntityRenderer)new VirtualEntityRenderer(modelBase);
  }
}

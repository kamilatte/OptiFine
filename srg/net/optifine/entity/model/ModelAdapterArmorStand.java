package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmorStandModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterBiped;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterArmorStand extends ModelAdapterBiped {
  public ModelAdapterArmorStand() {
    super(EntityType.ARMOR_STAND, "armor_stand", 0.0F);
  }
  
  public Model makeModel() {
    return (Model)new ArmorStandModel(bakeModelLayer(ModelLayers.ARMOR_STAND));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof ArmorStandModel))
      return null; 
    ArmorStandModel modelArmorStand = (ArmorStandModel)model;
    if (modelPart.equals("right"))
      return (ModelPart)Reflector.getFieldValue(modelArmorStand, Reflector.ModelArmorStand_ModelRenderers, 0); 
    if (modelPart.equals("left"))
      return (ModelPart)Reflector.getFieldValue(modelArmorStand, Reflector.ModelArmorStand_ModelRenderers, 1); 
    if (modelPart.equals("waist"))
      return (ModelPart)Reflector.getFieldValue(modelArmorStand, Reflector.ModelArmorStand_ModelRenderers, 2); 
    if (modelPart.equals("base"))
      return (ModelPart)Reflector.getFieldValue(modelArmorStand, Reflector.ModelArmorStand_ModelRenderers, 3); 
    return super.getModelRenderer((Model)modelArmorStand, modelPart);
  }
  
  public String[] getModelRendererNames() {
    String[] names = super.getModelRendererNames();
    names = (String[])Config.addObjectsToArray((Object[])names, (Object[])new String[] { "right", "left", "waist", "base" });
    return names;
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    ArmorStandRenderer render = new ArmorStandRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}

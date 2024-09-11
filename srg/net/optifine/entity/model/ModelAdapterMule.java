package srg.net.optifine.entity.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ChestedHorseModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.ChestedHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterHorse;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterMule extends ModelAdapterHorse {
  public ModelAdapterMule() {
    super(EntityType.MULE, "mule", 0.75F);
  }
  
  public Model makeModel() {
    return (Model)new ChestedHorseModel(bakeModelLayer(ModelLayers.MULE));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof ChestedHorseModel))
      return null; 
    ChestedHorseModel modelHorseChests = (ChestedHorseModel)model;
    if (modelPart.equals("left_chest"))
      return (ModelPart)Reflector.ModelHorseChests_ModelRenderers.getValue(modelHorseChests, 0); 
    if (modelPart.equals("right_chest"))
      return (ModelPart)Reflector.ModelHorseChests_ModelRenderers.getValue(modelHorseChests, 1); 
    return super.getModelRenderer(model, modelPart);
  }
  
  public String[] getModelRendererNames() {
    List<String> list = new ArrayList<>(Arrays.asList(super.getModelRendererNames()));
    list.add("left_chest");
    list.add("right_chest");
    return list.<String>toArray(new String[list.size()]);
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    ChestedHorseRenderer render = new ChestedHorseRenderer(renderManager.getContext(), 0.92F, ModelLayers.MULE);
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}

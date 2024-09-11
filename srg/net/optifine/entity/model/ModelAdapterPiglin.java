package srg.net.optifine.entity.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PiglinModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.PiglinRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterPlayer;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterPiglin extends ModelAdapterPlayer {
  public ModelAdapterPiglin() {
    super(EntityType.PIGLIN, "piglin", 0.5F);
  }
  
  protected ModelAdapterPiglin(EntityType type, String name, float shadowSize) {
    super(type, name, shadowSize);
  }
  
  public Model makeModel() {
    return (Model)new PiglinModel(bakeModelLayer(ModelLayers.PIGLIN));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (model instanceof PiglinModel) {
      PiglinModel piglinModel = (PiglinModel)model;
      if (Reflector.ModelPiglin_ModelRenderers.exists()) {
        if (modelPart.equals("left_ear"))
          return piglinModel.head.getChild("left_ear"); 
        if (modelPart.equals("right_ear"))
          return piglinModel.head.getChild("right_ear"); 
      } 
    } 
    return super.getModelRenderer(model, modelPart);
  }
  
  public String[] getModelRendererNames() {
    List<String> names = new ArrayList<>(Arrays.asList(super.getModelRendererNames()));
    names.add("left_ear");
    names.add("right_ear");
    return names.<String>toArray(new String[names.size()]);
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    PiglinRenderer render = new PiglinRenderer(renderManager.getContext(), ModelLayers.PIGLIN, ModelLayers.PIGLIN_INNER_ARMOR, ModelLayers.PIGLIN_OUTER_ARMOR, false);
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}

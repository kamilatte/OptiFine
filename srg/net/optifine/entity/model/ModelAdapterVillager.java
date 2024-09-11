package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterVillager extends ModelAdapter {
  public ModelAdapterVillager() {
    super(EntityType.VILLAGER, "villager", 0.5F);
  }
  
  protected ModelAdapterVillager(EntityType type, String name, float shadowSize) {
    super(type, name, shadowSize);
  }
  
  public Model makeModel() {
    return (Model)new VillagerModel(bakeModelLayer(ModelLayers.VILLAGER));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof VillagerModel))
      return null; 
    VillagerModel modelVillager = (VillagerModel)model;
    if (modelPart.equals("head"))
      return modelVillager.root().getChildModelDeep("head"); 
    if (modelPart.equals("headwear"))
      return modelVillager.root().getChildModelDeep("hat"); 
    if (modelPart.equals("headwear2"))
      return modelVillager.root().getChildModelDeep("hat_rim"); 
    if (modelPart.equals("body"))
      return modelVillager.root().getChildModelDeep("body"); 
    if (modelPart.equals("bodywear"))
      return modelVillager.root().getChildModelDeep("jacket"); 
    if (modelPart.equals("arms"))
      return modelVillager.root().getChildModelDeep("arms"); 
    if (modelPart.equals("right_leg"))
      return modelVillager.root().getChildModelDeep("right_leg"); 
    if (modelPart.equals("left_leg"))
      return modelVillager.root().getChildModelDeep("left_leg"); 
    if (modelPart.equals("nose"))
      return modelVillager.root().getChildModelDeep("nose"); 
    if (modelPart.equals("root"))
      return modelVillager.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "headwear", "headwear2", "body", "bodywear", "arms", "right_leg", "left_leg", "nose", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    ReloadableResourceManager resourceManager = (ReloadableResourceManager)Minecraft.getInstance().getResourceManager();
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    VillagerRenderer render = new VillagerRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}

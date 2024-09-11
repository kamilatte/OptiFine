package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ZombieVillagerRenderer;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterBiped;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterZombieVillager extends ModelAdapterBiped {
  public ModelAdapterZombieVillager() {
    super(EntityType.ZOMBIE_VILLAGER, "zombie_villager", 0.5F);
  }
  
  public Model makeModel() {
    return (Model)new ZombieVillagerModel(bakeModelLayer(ModelLayers.ZOMBIE_VILLAGER));
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    ReloadableResourceManager resourceManager = (ReloadableResourceManager)Minecraft.getInstance().getResourceManager();
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    ZombieVillagerRenderer render = new ZombieVillagerRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}

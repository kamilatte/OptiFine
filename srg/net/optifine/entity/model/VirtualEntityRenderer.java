package srg.net.optifine.entity.model;

import net.minecraft.client.model.Model;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.util.Either;

public class VirtualEntityRenderer implements IEntityRenderer {
  private Model model;
  
  private Either<EntityType, BlockEntityType> type;
  
  private ResourceLocation locationTextureCustom;
  
  public VirtualEntityRenderer(Model model) {
    this.model = model;
  }
  
  public Model getModel() {
    return this.model;
  }
  
  public Either<EntityType, BlockEntityType> getType() {
    return this.type;
  }
  
  public void setType(Either<EntityType, BlockEntityType> type) {
    this.type = type;
  }
  
  public ResourceLocation getLocationTextureCustom() {
    return this.locationTextureCustom;
  }
  
  public void setLocationTextureCustom(ResourceLocation locationTextureCustom) {
    this.locationTextureCustom = locationTextureCustom;
  }
}

package srg.net.optifine.entity.model;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.RendererCache;
import net.optifine.util.Either;

public abstract class ModelAdapter {
  private Either<EntityType, BlockEntityType> type;
  
  private String name;
  
  private float shadowSize;
  
  private String[] aliases;
  
  public ModelAdapter(EntityType entityType, String name, float shadowSize) {
    this(Either.makeLeft(entityType), name, shadowSize, null);
  }
  
  public ModelAdapter(EntityType entityType, String name, float shadowSize, String[] aliases) {
    this(Either.makeLeft(entityType), name, shadowSize, aliases);
  }
  
  public ModelAdapter(BlockEntityType tileEntityType, String name, float shadowSize) {
    this(Either.makeRight(tileEntityType), name, shadowSize, null);
  }
  
  public ModelAdapter(BlockEntityType tileEntityType, String name, float shadowSize, String[] aliases) {
    this(Either.makeRight(tileEntityType), name, shadowSize, aliases);
  }
  
  public ModelAdapter(Either<EntityType, BlockEntityType> type, String name, float shadowSize, String[] aliases) {
    this.type = type;
    this.name = name;
    this.shadowSize = shadowSize;
    this.aliases = aliases;
  }
  
  public Either<EntityType, BlockEntityType> getType() {
    return this.type;
  }
  
  public String getName() {
    return this.name;
  }
  
  public String[] getAliases() {
    return this.aliases;
  }
  
  public float getShadowSize() {
    return this.shadowSize;
  }
  
  public abstract Model makeModel();
  
  public abstract ModelPart getModelRenderer(Model paramModel, String paramString);
  
  public abstract String[] getModelRendererNames();
  
  public abstract IEntityRenderer makeEntityRender(Model paramModel, float paramFloat, RendererCache paramRendererCache, int paramInt);
  
  public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
    return false;
  }
  
  public ModelPart[] getModelRenderers(Model model) {
    String[] names = getModelRendererNames();
    List<ModelPart> list = new ArrayList<>();
    for (int i = 0; i < names.length; i++) {
      String name = names[i];
      ModelPart mr = getModelRenderer(model, name);
      if (mr != null)
        list.add(mr); 
    } 
    ModelPart[] mrs = list.<ModelPart>toArray(new ModelPart[list.size()]);
    return mrs;
  }
  
  public static ModelPart bakeModelLayer(ModelLayerLocation loc) {
    return Minecraft.getInstance().getEntityRenderDispatcher().getContext().bakeLayer(loc);
  }
}

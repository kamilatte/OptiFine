package srg.net.optifine.entity.model;

import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.ModelAdapterMinecart;

public class ModelAdapterMinecartMobSpawner extends ModelAdapterMinecart {
  public ModelAdapterMinecartMobSpawner() {
    super(EntityType.SPAWNER_MINECART, "spawner_minecart", 0.5F);
  }
}

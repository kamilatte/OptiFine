package srg.net.optifine.entity.model;

import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.ModelAdapterMinecart;

public class ModelAdapterMinecartCommandBlock extends ModelAdapterMinecart {
  public ModelAdapterMinecartCommandBlock() {
    super(EntityType.COMMAND_BLOCK_MINECART, "command_block_minecart", 0.5F);
  }
}

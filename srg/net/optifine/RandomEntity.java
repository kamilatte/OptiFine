package srg.net.optifine;

import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.optifine.IRandomEntity;
import net.optifine.RandomEntityRule;

public class RandomEntity implements IRandomEntity {
  private Entity entity;
  
  public int getId() {
    UUID uuid = this.entity.getUUID();
    long uuidLow = uuid.getLeastSignificantBits();
    int id = (int)(uuidLow & 0x7FFFFFFFL);
    return id;
  }
  
  public BlockPos getSpawnPosition() {
    return (this.entity.getEntityData()).spawnPosition;
  }
  
  public Biome getSpawnBiome() {
    return (this.entity.getEntityData()).spawnBiome;
  }
  
  public String getName() {
    if (this.entity.hasCustomName())
      return this.entity.getCustomName().getString(); 
    return null;
  }
  
  public int getHealth() {
    if (!(this.entity instanceof LivingEntity))
      return 0; 
    LivingEntity el = (LivingEntity)this.entity;
    return (int)el.getHealth();
  }
  
  public int getMaxHealth() {
    if (!(this.entity instanceof LivingEntity))
      return 0; 
    LivingEntity el = (LivingEntity)this.entity;
    return (int)el.getMaxHealth();
  }
  
  public Entity getEntity() {
    return this.entity;
  }
  
  public void setEntity(Entity entity) {
    this.entity = entity;
  }
  
  public CompoundTag getNbtTag() {
    SynchedEntityData edm = this.entity.getEntityData();
    CompoundTag nbt = edm.nbtTag;
    long timeMs = System.currentTimeMillis();
    if (nbt == null || edm.nbtTagUpdateMs < timeMs - 1000L) {
      nbt = new CompoundTag();
      this.entity.saveWithoutId(nbt);
      if (this.entity instanceof TamableAnimal) {
        TamableAnimal et = (TamableAnimal)this.entity;
        nbt.putBoolean("Sitting", et.isInSittingPose());
      } 
      edm.nbtTag = nbt;
      edm.nbtTagUpdateMs = timeMs;
    } 
    return nbt;
  }
  
  public DyeColor getColor() {
    return RandomEntityRule.getEntityColor(this.entity);
  }
  
  public BlockState getBlockState() {
    if (this.entity instanceof ItemEntity) {
      ItemEntity ie = (ItemEntity)this.entity;
      Item item = ie.getItem().getItem();
      if (item instanceof BlockItem) {
        BlockItem bi = (BlockItem)item;
        BlockState blockState = bi.getBlock().defaultBlockState();
        return blockState;
      } 
    } 
    SynchedEntityData edm = this.entity.getEntityData();
    BlockState bs = edm.blockStateOn;
    long timeMs = System.currentTimeMillis();
    if (bs == null || edm.blockStateOnUpdateMs < timeMs - 50L) {
      BlockPos pos = this.entity.blockPosition();
      bs = this.entity.getCommandSenderWorld().getBlockState(pos);
      if (bs.isAir())
        bs = this.entity.getCommandSenderWorld().getBlockState(pos.below()); 
      edm.blockStateOn = bs;
      edm.blockStateOnUpdateMs = timeMs;
    } 
    return bs;
  }
  
  public String toString() {
    return this.entity.toString();
  }
}

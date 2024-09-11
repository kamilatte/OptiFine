package srg.net.optifine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.Property;
import net.optifine.Config;
import net.optifine.IRandomEntity;
import net.optifine.RandomEntityRule;
import net.optifine.util.TileEntityUtils;

public class RandomTileEntity implements IRandomEntity {
  private BlockEntity tileEntity;
  
  private static final CompoundTag EMPTY_TAG = new CompoundTag();
  
  public int getId() {
    return Config.getRandom(getSpawnPosition(), 0);
  }
  
  public BlockPos getSpawnPosition() {
    if (this.tileEntity instanceof BedBlockEntity) {
      BedBlockEntity bbe = (BedBlockEntity)this.tileEntity;
      BlockState bs = bbe.getBlockState();
      BedPart part = (BedPart)bs.getValue((Property)BedBlock.PART);
      if (part == BedPart.HEAD) {
        Direction dir = (Direction)bs.getValue((Property)BedBlock.FACING);
        return this.tileEntity.getBlockPos().relative(dir.getOpposite());
      } 
    } 
    return this.tileEntity.getBlockPos();
  }
  
  public String getName() {
    String name = TileEntityUtils.getTileEntityName(this.tileEntity);
    return name;
  }
  
  public Biome getSpawnBiome() {
    return (Biome)this.tileEntity.getLevel().getBiome(this.tileEntity.getBlockPos()).value();
  }
  
  public int getHealth() {
    return -1;
  }
  
  public int getMaxHealth() {
    return -1;
  }
  
  public BlockEntity getTileEntity() {
    return this.tileEntity;
  }
  
  public void setTileEntity(BlockEntity tileEntity) {
    this.tileEntity = tileEntity;
  }
  
  public CompoundTag getNbtTag() {
    CompoundTag nbt = this.tileEntity.nbtTag;
    long timeMs = System.currentTimeMillis();
    if (nbt == null || this.tileEntity.nbtTagUpdateMs < timeMs - 1000L) {
      this.tileEntity.nbtTag = makeNbtTag(this.tileEntity);
      this.tileEntity.nbtTagUpdateMs = timeMs;
    } 
    return nbt;
  }
  
  private static CompoundTag makeNbtTag(BlockEntity te) {
    Level world = te.getLevel();
    if (world == null)
      return EMPTY_TAG; 
    RegistryAccess ra = world.registryAccess();
    if (ra == null)
      return EMPTY_TAG; 
    return te.saveWithoutMetadata((HolderLookup.Provider)ra);
  }
  
  public DyeColor getColor() {
    return RandomEntityRule.getBlockEntityColor(this.tileEntity);
  }
  
  public BlockState getBlockState() {
    return this.tileEntity.getBlockState();
  }
  
  public String toString() {
    return this.tileEntity.toString();
  }
}

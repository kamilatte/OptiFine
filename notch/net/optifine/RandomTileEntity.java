package notch.net.optifine;

import cti;
import dcw;
import ddw;
import dfr;
import dqd;
import dqh;
import dtc;
import dtp;
import duf;
import jd;
import ji;
import jo;
import ka;
import net.optifine.Config;
import net.optifine.IRandomEntity;
import net.optifine.RandomEntityRule;
import net.optifine.util.TileEntityUtils;
import ub;

public class RandomTileEntity implements IRandomEntity {
  private dqh tileEntity;
  
  private static final ub EMPTY_TAG = new ub();
  
  public int getId() {
    return Config.getRandom(getSpawnPosition(), 0);
  }
  
  public jd getSpawnPosition() {
    if (this.tileEntity instanceof dqd) {
      dqd bbe = (dqd)this.tileEntity;
      dtc bs = bbe.n();
      dtp part = (dtp)bs.c((duf)dfr.b);
      if (part == dtp.a) {
        ji dir = (ji)bs.c((duf)dfr.aE);
        return this.tileEntity.aD_().a(dir.g());
      } 
    } 
    return this.tileEntity.aD_();
  }
  
  public String getName() {
    String name = TileEntityUtils.getTileEntityName(this.tileEntity);
    return name;
  }
  
  public ddw getSpawnBiome() {
    return (ddw)this.tileEntity.i().t(this.tileEntity.aD_()).a();
  }
  
  public int getHealth() {
    return -1;
  }
  
  public int getMaxHealth() {
    return -1;
  }
  
  public dqh getTileEntity() {
    return this.tileEntity;
  }
  
  public void setTileEntity(dqh tileEntity) {
    this.tileEntity = tileEntity;
  }
  
  public ub getNbtTag() {
    ub nbt = this.tileEntity.nbtTag;
    long timeMs = System.currentTimeMillis();
    if (nbt == null || this.tileEntity.nbtTagUpdateMs < timeMs - 1000L) {
      this.tileEntity.nbtTag = makeNbtTag(this.tileEntity);
      this.tileEntity.nbtTagUpdateMs = timeMs;
    } 
    return nbt;
  }
  
  private static ub makeNbtTag(dqh te) {
    dcw world = te.i();
    if (world == null)
      return EMPTY_TAG; 
    ka ra = world.H_();
    if (ra == null)
      return EMPTY_TAG; 
    return te.d((jo.a)ra);
  }
  
  public cti getColor() {
    return RandomEntityRule.getBlockEntityColor(this.tileEntity);
  }
  
  public dtc getBlockState() {
    return this.tileEntity.n();
  }
  
  public String toString() {
    return this.tileEntity.toString();
  }
}

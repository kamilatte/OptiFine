package notch.net.optifine;

import aka;
import bsr;
import btn;
import bul;
import cjh;
import cso;
import cti;
import cul;
import ddw;
import dtc;
import java.util.UUID;
import jd;
import net.optifine.IRandomEntity;
import net.optifine.RandomEntityRule;
import ub;

public class RandomEntity implements IRandomEntity {
  private bsr entity;
  
  public int getId() {
    UUID uuid = this.entity.cz();
    long uuidLow = uuid.getLeastSignificantBits();
    int id = (int)(uuidLow & 0x7FFFFFFFL);
    return id;
  }
  
  public jd getSpawnPosition() {
    return (this.entity.ar()).spawnPosition;
  }
  
  public ddw getSpawnBiome() {
    return (this.entity.ar()).spawnBiome;
  }
  
  public String getName() {
    if (this.entity.ai())
      return this.entity.aj().getString(); 
    return null;
  }
  
  public int getHealth() {
    if (!(this.entity instanceof btn))
      return 0; 
    btn el = (btn)this.entity;
    return (int)el.ew();
  }
  
  public int getMaxHealth() {
    if (!(this.entity instanceof btn))
      return 0; 
    btn el = (btn)this.entity;
    return (int)el.eN();
  }
  
  public bsr getEntity() {
    return this.entity;
  }
  
  public void setEntity(bsr entity) {
    this.entity = entity;
  }
  
  public ub getNbtTag() {
    aka edm = this.entity.ar();
    ub nbt = edm.nbtTag;
    long timeMs = System.currentTimeMillis();
    if (nbt == null || edm.nbtTagUpdateMs < timeMs - 1000L) {
      nbt = new ub();
      this.entity.f(nbt);
      if (this.entity instanceof bul) {
        bul et = (bul)this.entity;
        nbt.a("Sitting", et.x());
      } 
      edm.nbtTag = nbt;
      edm.nbtTagUpdateMs = timeMs;
    } 
    return nbt;
  }
  
  public cti getColor() {
    return RandomEntityRule.getEntityColor(this.entity);
  }
  
  public dtc getBlockState() {
    if (this.entity instanceof cjh) {
      cjh ie = (cjh)this.entity;
      cul item = ie.p().g();
      if (item instanceof cso) {
        cso bi = (cso)item;
        dtc dtc = bi.d().o();
        return dtc;
      } 
    } 
    aka edm = this.entity.ar();
    dtc bs = edm.blockStateOn;
    long timeMs = System.currentTimeMillis();
    if (bs == null || edm.blockStateOnUpdateMs < timeMs - 50L) {
      jd pos = this.entity.do();
      bs = this.entity.cN().a_(pos);
      if (bs.i())
        bs = this.entity.cN().a_(pos.e()); 
      edm.blockStateOn = bs;
      edm.blockStateOnUpdateMs = timeMs;
    } 
    return bs;
  }
  
  public String toString() {
    return this.entity.toString();
  }
}

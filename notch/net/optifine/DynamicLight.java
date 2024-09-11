package notch.net.optifine;

import ayo;
import bsr;
import gex;
import gia;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import jd;
import ji;
import net.optifine.Config;
import net.optifine.DynamicLights;

public class DynamicLight {
  private bsr entity = null;
  
  private double offsetY = 0.0D;
  
  private double lastPosX = -2.147483648E9D;
  
  private double lastPosY = -2.147483648E9D;
  
  private double lastPosZ = -2.147483648E9D;
  
  private int lastLightLevel = 0;
  
  private long timeCheckMs = 0L;
  
  private Set<jd> setLitChunkPos = new HashSet<>();
  
  private jd.a blockPosMutable = new jd.a();
  
  public DynamicLight(bsr entity) {
    this.entity = entity;
    this.offsetY = entity.cL();
  }
  
  public void update(gex renderGlobal) {
    if (Config.isDynamicLightsFast()) {
      long timeNowMs = System.currentTimeMillis();
      if (timeNowMs < this.timeCheckMs + 500L)
        return; 
      this.timeCheckMs = timeNowMs;
    } 
    double posX = this.entity.dt() - 0.5D;
    double posY = this.entity.dv() - 0.5D + this.offsetY;
    double posZ = this.entity.dz() - 0.5D;
    int lightLevel = DynamicLights.getLightLevel(this.entity);
    double dx = posX - this.lastPosX;
    double dy = posY - this.lastPosY;
    double dz = posZ - this.lastPosZ;
    double delta = 0.1D;
    if (Math.abs(dx) <= delta && Math.abs(dy) <= delta && Math.abs(dz) <= delta && this.lastLightLevel == lightLevel)
      return; 
    this.lastPosX = posX;
    this.lastPosY = posY;
    this.lastPosZ = posZ;
    this.lastLightLevel = lightLevel;
    Set<jd> setNewPos = new HashSet<>();
    if (lightLevel > 0) {
      ji dirX = ((ayo.a(posX) & 0xF) >= 8) ? ji.f : ji.e;
      ji dirY = ((ayo.a(posY) & 0xF) >= 8) ? ji.b : ji.a;
      ji dirZ = ((ayo.a(posZ) & 0xF) >= 8) ? ji.d : ji.c;
      jd chunkPos = jd.a(posX, posY, posZ);
      gia.b chunk = renderGlobal.getRenderChunk(chunkPos);
      jd chunkPosX = getChunkPos(chunk, chunkPos, dirX);
      gia.b chunkX = renderGlobal.getRenderChunk(chunkPosX);
      jd chunkPosZ = getChunkPos(chunk, chunkPos, dirZ);
      gia.b chunkZ = renderGlobal.getRenderChunk(chunkPosZ);
      jd chunkPosXZ = getChunkPos(chunkX, chunkPosX, dirZ);
      gia.b chunkXZ = renderGlobal.getRenderChunk(chunkPosXZ);
      jd chunkPosY = getChunkPos(chunk, chunkPos, dirY);
      gia.b chunkY = renderGlobal.getRenderChunk(chunkPosY);
      jd chunkPosYX = getChunkPos(chunkY, chunkPosY, dirX);
      gia.b chunkYX = renderGlobal.getRenderChunk(chunkPosYX);
      jd chunkPosYZ = getChunkPos(chunkY, chunkPosY, dirZ);
      gia.b chunkYZ = renderGlobal.getRenderChunk(chunkPosYZ);
      jd chunkPosYXZ = getChunkPos(chunkYX, chunkPosYX, dirZ);
      gia.b chunkYXZ = renderGlobal.getRenderChunk(chunkPosYXZ);
      updateChunkLight(chunk, this.setLitChunkPos, setNewPos);
      updateChunkLight(chunkX, this.setLitChunkPos, setNewPos);
      updateChunkLight(chunkZ, this.setLitChunkPos, setNewPos);
      updateChunkLight(chunkXZ, this.setLitChunkPos, setNewPos);
      updateChunkLight(chunkY, this.setLitChunkPos, setNewPos);
      updateChunkLight(chunkYX, this.setLitChunkPos, setNewPos);
      updateChunkLight(chunkYZ, this.setLitChunkPos, setNewPos);
      updateChunkLight(chunkYXZ, this.setLitChunkPos, setNewPos);
    } 
    updateLitChunks(renderGlobal);
    this.setLitChunkPos = setNewPos;
  }
  
  private jd getChunkPos(gia.b renderChunk, jd pos, ji facing) {
    if (renderChunk != null)
      return renderChunk.a(facing); 
    return pos.a(facing, 16);
  }
  
  private void updateChunkLight(gia.b renderChunk, Set<jd> setPrevPos, Set<jd> setNewPos) {
    if (renderChunk == null)
      return; 
    gia.a compiledChunk = renderChunk.d();
    if (compiledChunk != null && !compiledChunk.a()) {
      renderChunk.a(false);
      renderChunk.setNeedsBackgroundPriorityUpdate(true);
    } 
    jd pos = renderChunk.f().j();
    if (setPrevPos != null)
      setPrevPos.remove(pos); 
    if (setNewPos != null)
      setNewPos.add(pos); 
  }
  
  public void updateLitChunks(gex renderGlobal) {
    for (Iterator<jd> it = this.setLitChunkPos.iterator(); it.hasNext(); ) {
      jd posOld = it.next();
      gia.b chunkOld = renderGlobal.getRenderChunk(posOld);
      updateChunkLight(chunkOld, null, null);
    } 
  }
  
  public bsr getEntity() {
    return this.entity;
  }
  
  public double getLastPosX() {
    return this.lastPosX;
  }
  
  public double getLastPosY() {
    return this.lastPosY;
  }
  
  public double getLastPosZ() {
    return this.lastPosZ;
  }
  
  public int getLastLightLevel() {
    return this.lastLightLevel;
  }
  
  public double getOffsetY() {
    return this.offsetY;
  }
  
  public String toString() {
    return "Entity: " + String.valueOf(this.entity) + ", offsetY: " + this.offsetY;
  }
}

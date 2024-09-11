package notch.net.optifine.shaders;

import exb;
import kh;
import net.optifine.shaders.ProgramStage;

public class ComputeProgram {
  private final String name;
  
  private final ProgramStage programStage;
  
  private int id;
  
  private int ref;
  
  private kh localSize;
  
  private kh workGroups;
  
  private exb workGroupsRender;
  
  private int compositeMipmapSetting;
  
  public ComputeProgram(String name, ProgramStage programStage) {
    this.name = name;
    this.programStage = programStage;
  }
  
  public void resetProperties() {}
  
  public void resetId() {
    this.id = 0;
    this.ref = 0;
  }
  
  public void resetConfiguration() {
    this.localSize = null;
    this.workGroups = null;
    this.workGroupsRender = null;
  }
  
  public String getName() {
    return this.name;
  }
  
  public ProgramStage getProgramStage() {
    return this.programStage;
  }
  
  public int getId() {
    return this.id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public int getRef() {
    return this.ref;
  }
  
  public void setRef(int ref) {
    this.ref = ref;
  }
  
  public kh getLocalSize() {
    return this.localSize;
  }
  
  public void setLocalSize(kh localSize) {
    this.localSize = localSize;
  }
  
  public kh getWorkGroups() {
    return this.workGroups;
  }
  
  public void setWorkGroups(kh workGroups) {
    this.workGroups = workGroups;
  }
  
  public exb getWorkGroupsRender() {
    return this.workGroupsRender;
  }
  
  public void setWorkGroupsRender(exb workGroupsRender) {
    this.workGroupsRender = workGroupsRender;
  }
  
  public int getCompositeMipmapSetting() {
    return this.compositeMipmapSetting;
  }
  
  public void setCompositeMipmapSetting(int compositeMipmapSetting) {
    this.compositeMipmapSetting = compositeMipmapSetting;
  }
  
  public boolean hasCompositeMipmaps() {
    return (this.compositeMipmapSetting != 0);
  }
  
  public String toString() {
    return "name: " + this.name + ", id: " + this.id;
  }
}

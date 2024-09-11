package notch.net.optifine.model;

import dga;
import dtc;
import gfw;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListQuadsOverlay {
  private List<gfw> listQuads = new ArrayList<>();
  
  private List<dtc> listBlockStates = new ArrayList<>();
  
  private List<gfw> listQuadsSingle = Arrays.asList(new gfw[1]);
  
  public void addQuad(gfw quad, dtc blockState) {
    if (quad == null)
      return; 
    this.listQuads.add(quad);
    this.listBlockStates.add(blockState);
  }
  
  public int size() {
    return this.listQuads.size();
  }
  
  public gfw getQuad(int index) {
    return this.listQuads.get(index);
  }
  
  public dtc getBlockState(int index) {
    if (index < 0 || index >= this.listBlockStates.size())
      return dga.a.o(); 
    return this.listBlockStates.get(index);
  }
  
  public List<gfw> getListQuadsSingle(gfw quad) {
    this.listQuadsSingle.set(0, quad);
    return this.listQuadsSingle;
  }
  
  public void clear() {
    this.listQuads.clear();
    this.listBlockStates.clear();
  }
}

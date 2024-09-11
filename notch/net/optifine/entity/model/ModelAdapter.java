package notch.net.optifine.entity.model;

import akr;
import bsx;
import dqj;
import fgo;
import fwg;
import fyi;
import fyk;
import java.util.ArrayList;
import java.util.List;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.RendererCache;
import net.optifine.util.Either;

public abstract class ModelAdapter {
  private Either<bsx, dqj> type;
  
  private String name;
  
  private float shadowSize;
  
  private String[] aliases;
  
  public ModelAdapter(bsx entityType, String name, float shadowSize) {
    this(Either.makeLeft(entityType), name, shadowSize, null);
  }
  
  public ModelAdapter(bsx entityType, String name, float shadowSize, String[] aliases) {
    this(Either.makeLeft(entityType), name, shadowSize, aliases);
  }
  
  public ModelAdapter(dqj tileEntityType, String name, float shadowSize) {
    this(Either.makeRight(tileEntityType), name, shadowSize, null);
  }
  
  public ModelAdapter(dqj tileEntityType, String name, float shadowSize, String[] aliases) {
    this(Either.makeRight(tileEntityType), name, shadowSize, aliases);
  }
  
  public ModelAdapter(Either<bsx, dqj> type, String name, float shadowSize, String[] aliases) {
    this.type = type;
    this.name = name;
    this.shadowSize = shadowSize;
    this.aliases = aliases;
  }
  
  public Either<bsx, dqj> getType() {
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
  
  public abstract fwg makeModel();
  
  public abstract fyk getModelRenderer(fwg paramfwg, String paramString);
  
  public abstract String[] getModelRendererNames();
  
  public abstract IEntityRenderer makeEntityRender(fwg paramfwg, float paramFloat, RendererCache paramRendererCache, int paramInt);
  
  public boolean setTextureLocation(IEntityRenderer er, akr textureLocation) {
    return false;
  }
  
  public fyk[] getModelRenderers(fwg model) {
    String[] names = getModelRendererNames();
    List<fyk> list = new ArrayList<>();
    for (int i = 0; i < names.length; i++) {
      String name = names[i];
      fyk mr = getModelRenderer(model, name);
      if (mr != null)
        list.add(mr); 
    } 
    fyk[] mrs = list.<fyk>toArray(new fyk[list.size()]);
    return mrs;
  }
  
  public static fyk bakeModelLayer(fyi loc) {
    return fgo.Q().ap().getContext().a(loc);
  }
}

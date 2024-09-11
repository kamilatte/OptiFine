package notch.net.optifine.entity.model;

import akr;
import bsx;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import cov;
import fgo;
import fuq;
import fwg;
import fyj;
import fyk;
import gjn;
import gkh;
import gki;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.ModelRendererUtils;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterBoat extends ModelAdapter {
  public ModelAdapterBoat() {
    super(bsx.k, "boat", 0.5F);
  }
  
  protected ModelAdapterBoat(bsx entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public fwg makeModel() {
    return (fwg)new fuq(bakeModelLayer(fyj.c(cov.b.a)));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fuq))
      return null; 
    fuq modelBoat = (fuq)model;
    ImmutableList<fyk> parts = modelBoat.b();
    if (parts != null) {
      if (modelPart.equals("bottom"))
        return ModelRendererUtils.getModelRenderer(parts, 0); 
      if (modelPart.equals("back"))
        return ModelRendererUtils.getModelRenderer(parts, 1); 
      if (modelPart.equals("front"))
        return ModelRendererUtils.getModelRenderer(parts, 2); 
      if (modelPart.equals("right"))
        return ModelRendererUtils.getModelRenderer(parts, 3); 
      if (modelPart.equals("left"))
        return ModelRendererUtils.getModelRenderer(parts, 4); 
      if (modelPart.equals("paddle_left"))
        return ModelRendererUtils.getModelRenderer(parts, 5); 
      if (modelPart.equals("paddle_right"))
        return ModelRendererUtils.getModelRenderer(parts, 6); 
    } 
    if (modelPart.equals("bottom_no_water"))
      return modelBoat.c(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "bottom", "back", "front", "right", "left", "paddle_left", "paddle_right", "bottom_no_water" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gjn renderer = new gjn(renderManager.getContext(), false);
    rendererCache.put(bsx.k, index, (gki)renderer);
    return makeEntityRender(modelBase, shadowSize, renderer);
  }
  
  protected static IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, gjn render) {
    if (!Reflector.RenderBoat_boatResources.exists()) {
      Config.warn("Field not found: RenderBoat.boatResources");
      return null;
    } 
    Map<cov.b, Pair<akr, fwg>> resources = (Map<cov.b, Pair<akr, fwg>>)Reflector.RenderBoat_boatResources.getValue(render);
    if (resources instanceof com.google.common.collect.ImmutableMap) {
      resources = new HashMap<>(resources);
      Reflector.RenderBoat_boatResources.setValue(render, resources);
    } 
    Collection<cov.b> types = new HashSet<>(resources.keySet());
    for (cov.b type : types) {
      Pair<akr, fwg> pair = resources.get(type);
      if (modelBase.getClass() != ((fwg)pair.getSecond()).getClass())
        continue; 
      pair = Pair.of(pair.getFirst(), modelBase);
      resources.put(type, pair);
    } 
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}

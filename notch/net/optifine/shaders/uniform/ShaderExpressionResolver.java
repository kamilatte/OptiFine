package notch.net.optifine.shaders.uniform;

import akr;
import ddw;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.optifine.expr.ConstantFloat;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionResolver;
import net.optifine.shaders.SMCLog;
import net.optifine.shaders.uniform.ShaderParameterBool;
import net.optifine.shaders.uniform.ShaderParameterFloat;
import net.optifine.shaders.uniform.ShaderParameterIndexed;
import net.optifine.util.BiomeCategory;
import net.optifine.util.BiomeUtils;

public class ShaderExpressionResolver implements IExpressionResolver {
  private Map<String, IExpression> mapExpressions = new HashMap<>();
  
  public ShaderExpressionResolver(Map<String, IExpression> map) {
    registerExpressions();
    Set<String> keys = map.keySet();
    for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
      String name = it.next();
      IExpression expr = map.get(name);
      registerExpression(name, expr);
    } 
  }
  
  private void registerExpressions() {
    ShaderParameterFloat[] spfs = ShaderParameterFloat.values();
    for (int i = 0; i < spfs.length; i++) {
      ShaderParameterFloat spf = spfs[i];
      addParameterFloat(this.mapExpressions, spf);
    } 
    ShaderParameterBool[] spbs = ShaderParameterBool.values();
    for (int j = 0; j < spbs.length; j++) {
      ShaderParameterBool spb = spbs[j];
      this.mapExpressions.put(spb.getName(), spb);
    } 
    Set<akr> biomeLocations = BiomeUtils.getLocations();
    for (akr loc : biomeLocations) {
      String name = loc.a().trim();
      name = "BIOME_" + name.toUpperCase().replace(' ', '_');
      int id = BiomeUtils.getId(loc);
      ConstantFloat constantFloat = new ConstantFloat(id);
      registerExpression(name, (IExpression)constantFloat);
    } 
    BiomeCategory[] biomeCats = BiomeCategory.values();
    for (int k = 0; k < biomeCats.length; k++) {
      BiomeCategory bc = biomeCats[k];
      String name = "CAT_" + bc.getName().toUpperCase();
      int id = k;
      ConstantFloat constantFloat = new ConstantFloat(id);
      registerExpression(name, (IExpression)constantFloat);
    } 
    ddw.c[] biomePpts = ddw.c.values();
    for (int m = 0; m < biomePpts.length; m++) {
      ddw.c bp = biomePpts[m];
      String name = "PPT_" + bp.name().toUpperCase();
      int id = m;
      ConstantFloat constantFloat = new ConstantFloat(id);
      registerExpression(name, (IExpression)constantFloat);
    } 
  }
  
  private void addParameterFloat(Map<String, IExpression> map, ShaderParameterFloat spf) {
    String[] indexNames1 = spf.getIndexNames1();
    if (indexNames1 == null) {
      map.put(spf.getName(), new ShaderParameterIndexed(spf));
      return;
    } 
    for (int i1 = 0; i1 < indexNames1.length; i1++) {
      String indexName1 = indexNames1[i1];
      String[] indexNames2 = spf.getIndexNames2();
      if (indexNames2 == null) {
        map.put(spf.getName() + "." + spf.getName(), new ShaderParameterIndexed(spf, i1));
      } else {
        for (int i2 = 0; i2 < indexNames2.length; i2++) {
          String indexName2 = indexNames2[i2];
          map.put(spf.getName() + "." + spf.getName() + "." + indexName1, new ShaderParameterIndexed(spf, i1, i2));
        } 
      } 
    } 
  }
  
  public boolean registerExpression(String name, IExpression expr) {
    if (this.mapExpressions.containsKey(name)) {
      SMCLog.warning("Expression already defined: " + name);
      return false;
    } 
    this.mapExpressions.put(name, expr);
    return true;
  }
  
  public IExpression getExpression(String name) {
    return this.mapExpressions.get(name);
  }
  
  public boolean hasExpression(String name) {
    return this.mapExpressions.containsKey(name);
  }
}

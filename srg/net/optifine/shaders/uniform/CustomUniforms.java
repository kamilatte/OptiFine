package srg.net.optifine.shaders.uniform;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionCached;
import net.optifine.shaders.uniform.CustomUniform;

public class CustomUniforms {
  private CustomUniform[] uniforms;
  
  private IExpressionCached[] expressionsCached;
  
  public CustomUniforms(CustomUniform[] uniforms, Map<String, IExpression> mapExpressions) {
    this.uniforms = uniforms;
    List<IExpressionCached> list = new ArrayList<>();
    Set<String> keys = mapExpressions.keySet();
    for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
      String key = it.next();
      IExpression expr = mapExpressions.get(key);
      if (!(expr instanceof IExpressionCached))
        continue; 
      IExpressionCached exprCached = (IExpressionCached)expr;
      list.add(exprCached);
    } 
    this.expressionsCached = list.<IExpressionCached>toArray(new IExpressionCached[list.size()]);
  }
  
  public void setProgram(int program) {
    for (int i = 0; i < this.uniforms.length; i++) {
      CustomUniform uniform = this.uniforms[i];
      uniform.setProgram(program);
    } 
  }
  
  public void update() {
    resetCache();
    for (int i = 0; i < this.uniforms.length; i++) {
      CustomUniform uniform = this.uniforms[i];
      uniform.update();
    } 
  }
  
  private void resetCache() {
    for (int i = 0; i < this.expressionsCached.length; i++) {
      IExpressionCached exprCached = this.expressionsCached[i];
      exprCached.reset();
    } 
  }
  
  public void reset() {
    for (int i = 0; i < this.uniforms.length; i++) {
      CustomUniform cu = this.uniforms[i];
      cu.reset();
    } 
  }
}

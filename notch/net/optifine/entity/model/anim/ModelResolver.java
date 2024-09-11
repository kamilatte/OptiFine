package notch.net.optifine.entity.model.anim;

import bsx;
import dqj;
import fwg;
import fyk;
import net.optifine.Config;
import net.optifine.entity.model.CustomModelRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.anim.EntityVariableBool;
import net.optifine.entity.model.anim.EntityVariableFloat;
import net.optifine.entity.model.anim.IModelResolver;
import net.optifine.entity.model.anim.IModelVariable;
import net.optifine.entity.model.anim.IRenderResolver;
import net.optifine.entity.model.anim.ModelVariableType;
import net.optifine.entity.model.anim.RenderResolverEntity;
import net.optifine.entity.model.anim.RenderResolverTileEntity;
import net.optifine.entity.model.anim.RendererVariableFloat;
import net.optifine.expr.IExpression;
import net.optifine.util.Either;

public class ModelResolver implements IModelResolver {
  private ModelAdapter modelAdapter;
  
  private fwg model;
  
  private CustomModelRenderer[] customModelRenderers;
  
  private fyk thisModelRenderer;
  
  private fyk partModelRenderer;
  
  private IRenderResolver renderResolver;
  
  public ModelResolver(ModelAdapter modelAdapter, fwg model, CustomModelRenderer[] customModelRenderers) {
    this.modelAdapter = modelAdapter;
    this.model = model;
    this.customModelRenderers = customModelRenderers;
    Either<bsx, dqj> type = modelAdapter.getType();
    if (type != null && type.getRight().isPresent())
      this.renderResolver = (IRenderResolver)new RenderResolverTileEntity(); 
    if (type != null && type.getLeft().isPresent())
      this.renderResolver = (IRenderResolver)new RenderResolverEntity(); 
  }
  
  public IExpression getExpression(String name) {
    IModelVariable iModelVariable = getModelVariable(name);
    if (iModelVariable != null)
      return (IExpression)iModelVariable; 
    IExpression param = this.renderResolver.getParameter(name);
    if (param != null)
      return param; 
    return null;
  }
  
  public fyk getModelRenderer(String name) {
    if (name == null)
      return null; 
    if (name.indexOf(":") >= 0) {
      String[] parts = Config.tokenize(name, ":");
      fyk mr = getModelRenderer(parts[0]);
      for (int j = 1; j < parts.length; j++) {
        String part = parts[j];
        fyk mrSub = mr.getChildDeepById(part);
        if (mrSub == null)
          return null; 
        mr = mrSub;
      } 
      return mr;
    } 
    if (this.thisModelRenderer != null && name.equals("this"))
      return this.thisModelRenderer; 
    if (this.partModelRenderer != null && name.equals("part"))
      return this.partModelRenderer; 
    fyk mrPart = this.modelAdapter.getModelRenderer(this.model, name);
    if (mrPart != null)
      return mrPart; 
    for (int i = 0; i < this.customModelRenderers.length; i++) {
      CustomModelRenderer cmr = this.customModelRenderers[i];
      fyk mr = cmr.getModelRenderer();
      if (name.equals(mr.getId()))
        return mr; 
      fyk mrChild = mr.getChildDeepById(name);
      if (mrChild != null)
        return mrChild; 
    } 
    return null;
  }
  
  public IModelVariable getModelVariable(String name) {
    String[] parts = Config.tokenize(name, ".");
    if (parts.length != 2)
      return null; 
    String modelName = parts[0];
    String varName = parts[1];
    if (modelName.equals("var"))
      if (!this.renderResolver.isTileEntity())
        return (IModelVariable)new EntityVariableFloat(name);  
    if (modelName.equals("varb"))
      if (!this.renderResolver.isTileEntity())
        return (IModelVariable)new EntityVariableBool(name);  
    if (modelName.equals("render")) {
      if (!this.renderResolver.isTileEntity())
        return (IModelVariable)RendererVariableFloat.parse(varName); 
      return null;
    } 
    fyk mr = getModelRenderer(modelName);
    if (mr == null)
      return null; 
    ModelVariableType varType = ModelVariableType.parse(varName);
    if (varType == null)
      return null; 
    IModelVariable var = varType.makeModelVariable(name, mr);
    return var;
  }
  
  public void setPartModelRenderer(fyk partModelRenderer) {
    this.partModelRenderer = partModelRenderer;
  }
  
  public void setThisModelRenderer(fyk thisModelRenderer) {
    this.thisModelRenderer = thisModelRenderer;
  }
}

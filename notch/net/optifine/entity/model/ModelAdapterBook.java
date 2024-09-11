package notch.net.optifine.entity.model;

import dqj;
import fus;
import fwg;
import fyj;
import fyk;
import ggy;
import ggz;
import ghi;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterBook extends ModelAdapter {
  public ModelAdapterBook() {
    super(dqj.m, "enchanting_book", 0.0F, new String[] { "book" });
  }
  
  protected ModelAdapterBook(dqj tileEntityType, String name, float shadowSize) {
    super(tileEntityType, name, shadowSize);
  }
  
  public fwg makeModel() {
    return (fwg)new fus(bakeModelLayer(fyj.r));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fus))
      return null; 
    fus modelBook = (fus)model;
    fyk root = (fyk)Reflector.ModelBook_root.getValue(modelBook);
    if (root != null) {
      if (modelPart.equals("cover_right"))
        return root.getChildModelDeep("left_lid"); 
      if (modelPart.equals("cover_left"))
        return root.getChildModelDeep("right_lid"); 
      if (modelPart.equals("pages_right"))
        return root.getChildModelDeep("left_pages"); 
      if (modelPart.equals("pages_left"))
        return root.getChildModelDeep("right_pages"); 
      if (modelPart.equals("flipping_page_right"))
        return root.getChildModelDeep("flip_page1"); 
      if (modelPart.equals("flipping_page_left"))
        return root.getChildModelDeep("flip_page2"); 
      if (modelPart.equals("book_spine"))
        return root.getChildModelDeep("seam"); 
      if (modelPart.equals("root"))
        return root; 
    } 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "cover_right", "cover_left", "pages_right", "pages_left", "flipping_page_right", "flipping_page_left", "book_spine", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    ggy dispatcher = Config.getMinecraft().aq();
    ggz renderer = rendererCache.get(dqj.m, index, () -> new ghi(dispatcher.getContext()));
    if (!(renderer instanceof ghi))
      return null; 
    if (!Reflector.TileEntityEnchantmentTableRenderer_modelBook.exists()) {
      Config.warn("Field not found: TileEntityEnchantmentTableRenderer.modelBook");
      return null;
    } 
    Reflector.setFieldValue(renderer, Reflector.TileEntityEnchantmentTableRenderer_modelBook, modelBase);
    return (IEntityRenderer)renderer;
  }
}

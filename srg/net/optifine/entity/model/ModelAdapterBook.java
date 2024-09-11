package srg.net.optifine.entity.model;

import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.EnchantTableRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterBook extends ModelAdapter {
  public ModelAdapterBook() {
    super(BlockEntityType.ENCHANTING_TABLE, "enchanting_book", 0.0F, new String[] { "book" });
  }
  
  protected ModelAdapterBook(BlockEntityType tileEntityType, String name, float shadowSize) {
    super(tileEntityType, name, shadowSize);
  }
  
  public Model makeModel() {
    return (Model)new BookModel(bakeModelLayer(ModelLayers.BOOK));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof BookModel))
      return null; 
    BookModel modelBook = (BookModel)model;
    ModelPart root = (ModelPart)Reflector.ModelBook_root.getValue(modelBook);
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
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().getBlockEntityRenderDispatcher();
    BlockEntityRenderer renderer = rendererCache.get(BlockEntityType.ENCHANTING_TABLE, index, () -> new EnchantTableRenderer(dispatcher.getContext()));
    if (!(renderer instanceof EnchantTableRenderer))
      return null; 
    if (!Reflector.TileEntityEnchantmentTableRenderer_modelBook.exists()) {
      Config.warn("Field not found: TileEntityEnchantmentTableRenderer.modelBook");
      return null;
    } 
    Reflector.setFieldValue(renderer, Reflector.TileEntityEnchantmentTableRenderer_modelBook, modelBase);
    return (IEntityRenderer)renderer;
  }
}

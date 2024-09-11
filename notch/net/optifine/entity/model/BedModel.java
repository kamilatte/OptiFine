package notch.net.optifine.entity.model;

import fbi;
import fbm;
import fwg;
import fyk;
import gfh;
import ggw;
import ggy;
import ggz;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class BedModel extends fwg {
  public fyk headPiece;
  
  public fyk footPiece;
  
  public fyk[] legs = new fyk[4];
  
  public BedModel() {
    super(gfh::e);
    ggy dispatcher = Config.getMinecraft().aq();
    ggw renderer = new ggw(dispatcher.getContext());
    fyk headRoot = (fyk)Reflector.TileEntityBedRenderer_headModel.getValue(renderer);
    if (headRoot != null) {
      this.headPiece = headRoot.b("main");
      this.legs[0] = headRoot.b("left_leg");
      this.legs[1] = headRoot.b("right_leg");
    } 
    fyk footRoot = (fyk)Reflector.TileEntityBedRenderer_footModel.getValue(renderer);
    if (footRoot != null) {
      this.footPiece = footRoot.b("main");
      this.legs[2] = footRoot.b("left_leg");
      this.legs[3] = footRoot.b("right_leg");
    } 
  }
  
  public void a(fbi matrixStackIn, fbm bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {}
  
  public ggz updateRenderer(ggz renderer) {
    if (!Reflector.TileEntityBedRenderer_headModel.exists()) {
      Config.warn("Field not found: TileEntityBedRenderer.head");
      return null;
    } 
    if (!Reflector.TileEntityBedRenderer_footModel.exists()) {
      Config.warn("Field not found: TileEntityBedRenderer.footModel");
      return null;
    } 
    fyk headRoot = (fyk)Reflector.TileEntityBedRenderer_headModel.getValue(renderer);
    if (headRoot != null) {
      headRoot.addChildModel("main", this.headPiece);
      headRoot.addChildModel("left_leg", this.legs[0]);
      headRoot.addChildModel("right_leg", this.legs[1]);
    } 
    fyk footRoot = (fyk)Reflector.TileEntityBedRenderer_footModel.getValue(renderer);
    if (footRoot != null) {
      footRoot.addChildModel("main", this.footPiece);
      footRoot.addChildModel("left_leg", this.legs[2]);
      footRoot.addChildModel("right_leg", this.legs[3]);
    } 
    return renderer;
  }
}

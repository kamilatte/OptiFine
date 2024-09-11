package srg.net.optifine.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockElementRotation;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.AABB;
import net.optifine.Config;
import net.optifine.model.BakedQuadRetextured;
import net.optifine.model.ModelUtils;
import net.optifine.util.RandomUtils;
import org.joml.Vector3f;

public class BlockModelUtils {
  private static final float VERTEX_COORD_ACCURACY = 1.0E-6F;
  
  private static final RandomSource RANDOM = RandomUtils.makeThreadSafeRandomSource(0);
  
  public static BakedModel makeModelCube(String spriteName, int tintIndex) {
    TextureAtlasSprite sprite = Config.getTextureMap().getUploadedSprite(spriteName);
    return makeModelCube(sprite, tintIndex);
  }
  
  public static BakedModel makeModelCube(TextureAtlasSprite sprite, int tintIndex) {
    List generalQuads = new ArrayList();
    Direction[] facings = Direction.VALUES;
    Map<Direction, List<BakedQuad>> faceQuads = new HashMap<>();
    for (int i = 0; i < facings.length; i++) {
      Direction facing = facings[i];
      List<BakedQuad> quads = new ArrayList();
      quads.add(makeBakedQuad(facing, sprite, tintIndex));
      faceQuads.put(facing, quads);
    } 
    ItemOverrides itemOverrideList = ItemOverrides.EMPTY;
    return (BakedModel)new SimpleBakedModel(generalQuads, faceQuads, true, true, true, sprite, ItemTransforms.NO_TRANSFORMS, itemOverrideList);
  }
  
  public static BakedModel joinModelsCube(BakedModel modelBase, BakedModel modelAdd) {
    List<BakedQuad> generalQuads = new ArrayList<>();
    generalQuads.addAll(modelBase.getQuads(null, null, RANDOM));
    generalQuads.addAll(modelAdd.getQuads(null, null, RANDOM));
    Direction[] facings = Direction.VALUES;
    Map<Direction, List<BakedQuad>> faceQuads = new HashMap<>();
    for (int i = 0; i < facings.length; i++) {
      Direction facing = facings[i];
      List<BakedQuad> quads = new ArrayList();
      quads.addAll(modelBase.getQuads(null, facing, RANDOM));
      quads.addAll(modelAdd.getQuads(null, facing, RANDOM));
      faceQuads.put(facing, quads);
    } 
    boolean ao = modelBase.useAmbientOcclusion();
    boolean builtIn = modelBase.isCustomRenderer();
    TextureAtlasSprite sprite = modelBase.getParticleIcon();
    ItemTransforms transforms = modelBase.getTransforms();
    ItemOverrides itemOverrideList = modelBase.getOverrides();
    return (BakedModel)new SimpleBakedModel(generalQuads, faceQuads, ao, builtIn, true, sprite, transforms, itemOverrideList);
  }
  
  public static BakedQuad makeBakedQuad(Direction facing, TextureAtlasSprite sprite, int tintIndex) {
    Vector3f posFrom = new Vector3f(0.0F, 0.0F, 0.0F);
    Vector3f posTo = new Vector3f(16.0F, 16.0F, 16.0F);
    BlockFaceUV uv = new BlockFaceUV(new float[] { 0.0F, 0.0F, 16.0F, 16.0F }, 0);
    BlockElementFace face = new BlockElementFace(facing, tintIndex, "#" + facing.getSerializedName(), uv);
    BlockModelRotation modelRotation = BlockModelRotation.X0_Y0;
    BlockElementRotation partRotation = null;
    boolean shade = true;
    ResourceLocation modelLoc = sprite.getName();
    FaceBakery faceBakery = new FaceBakery();
    BakedQuad quad = faceBakery.bakeQuad(posFrom, posTo, face, sprite, facing, (ModelState)modelRotation, partRotation, shade);
    return quad;
  }
  
  public static BakedModel makeModel(String modelName, String spriteOldName, String spriteNewName) {
    TextureAtlas textureMap = Config.getTextureMap();
    TextureAtlasSprite spriteOld = textureMap.getUploadedSprite(spriteOldName);
    TextureAtlasSprite spriteNew = textureMap.getUploadedSprite(spriteNewName);
    return makeModel(modelName, spriteOld, spriteNew);
  }
  
  public static BakedModel makeModel(String modelName, TextureAtlasSprite spriteOld, TextureAtlasSprite spriteNew) {
    if (spriteOld == null || spriteNew == null)
      return null; 
    ModelManager modelManager = Config.getModelManager();
    if (modelManager == null)
      return null; 
    ModelResourceLocation mrl = ModelResourceLocation.vanilla(modelName, "");
    BakedModel model = modelManager.getModel(mrl);
    if (model == null || model == modelManager.getMissingModel())
      return null; 
    BakedModel modelNew = ModelUtils.duplicateModel(model);
    Direction[] faces = Direction.VALUES;
    for (int i = 0; i < faces.length; i++) {
      Direction face = faces[i];
      List<BakedQuad> quads = modelNew.getQuads(null, face, RANDOM);
      replaceTexture(quads, spriteOld, spriteNew);
    } 
    List<BakedQuad> quadsGeneral = modelNew.getQuads(null, null, RANDOM);
    replaceTexture(quadsGeneral, spriteOld, spriteNew);
    return modelNew;
  }
  
  private static void replaceTexture(List<BakedQuad> quads, TextureAtlasSprite spriteOld, TextureAtlasSprite spriteNew) {
    List<BakedQuad> quadsNew = new ArrayList<>();
    for (Iterator<BakedQuad> it = quads.iterator(); it.hasNext(); ) {
      BakedQuadRetextured bakedQuadRetextured;
      BakedQuad quad = it.next();
      if (quad.getSprite() == spriteOld)
        bakedQuadRetextured = new BakedQuadRetextured(quad, spriteNew); 
      quadsNew.add(bakedQuadRetextured);
    } 
    quads.clear();
    quads.addAll(quadsNew);
  }
  
  public static void snapVertexPosition(Vector3f pos) {
    pos.set(snapVertexCoord(pos.x()), snapVertexCoord(pos.y()), snapVertexCoord(pos.z()));
  }
  
  private static float snapVertexCoord(float x) {
    if (x > -1.0E-6F && x < 1.0E-6F)
      return 0.0F; 
    if (x > 0.999999F && x < 1.000001F)
      return 1.0F; 
    return x;
  }
  
  public static AABB getOffsetBoundingBox(AABB aabb, BlockBehaviour.OffsetType offsetType, BlockPos pos) {
    int x = pos.getX();
    int z = pos.getZ();
    long k = (x * 3129871) ^ z * 116129781L;
    k = k * k * 42317861L + k * 11L;
    double dx = (((float)(k >> 16L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
    double dz = (((float)(k >> 24L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
    double dy = 0.0D;
    if (offsetType == BlockBehaviour.OffsetType.XYZ)
      dy = (((float)(k >> 20L & 0xFL) / 15.0F) - 1.0D) * 0.2D; 
    return aabb.move(dx, dy, dz);
  }
}

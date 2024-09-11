package notch.net.optifine.render;

import dtc;
import fbd;
import gfh;
import gfk;
import gfv;
import gfw;
import ghz;
import it.unimi.dsi.fastutil.longs.Long2ByteLinkedOpenHashMap;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import jd;
import ji;
import net.optifine.BlockPosM;
import net.optifine.Config;
import net.optifine.model.ListQuadsOverlay;

public class RenderEnv {
  private dtc blockState;
  
  private jd blockPos;
  
  private int blockId = -1;
  
  private int metadata = -1;
  
  private int breakingAnimation = -1;
  
  private int smartLeaves = -1;
  
  private float[] quadBounds = new float[ji.r.length * 2];
  
  private BitSet boundsFlags = new BitSet(3);
  
  private gfv.b aoFace = new gfv.b();
  
  private BlockPosM colorizerBlockPosM = null;
  
  private jd.a renderMutableBlockPos = null;
  
  private boolean[] borderFlags = null;
  
  private boolean[] borderFlags2 = null;
  
  private boolean[] borderFlags3 = null;
  
  private ji[] borderDirections = null;
  
  private List<gfw> listQuadsCustomizer = new ArrayList<>();
  
  private List<gfw> listQuadsCtmMultipass = new ArrayList<>();
  
  private gfw[] arrayQuadsCtm1 = new gfw[1];
  
  private gfw[] arrayQuadsCtm2 = new gfw[2];
  
  private gfw[] arrayQuadsCtm3 = new gfw[3];
  
  private gfw[] arrayQuadsCtm4 = new gfw[4];
  
  private ghz sectionCompiler;
  
  private Map<gfh, fbd> bufferBuilderMap;
  
  private gfk sectionBufferBuilderPack;
  
  private ListQuadsOverlay[] listsQuadsOverlay = new ListQuadsOverlay[gfh.CHUNK_RENDER_TYPES.length];
  
  private boolean overlaysRendered = false;
  
  private Long2ByteLinkedOpenHashMap renderSideMap = new Long2ByteLinkedOpenHashMap();
  
  private static final int UNKNOWN = -1;
  
  private static final int FALSE = 0;
  
  private static final int TRUE = 1;
  
  public RenderEnv(dtc blockState, jd blockPos) {
    this.blockState = blockState;
    this.blockPos = blockPos;
  }
  
  public void reset(dtc blockStateIn, jd blockPosIn) {
    if (this.blockState == blockStateIn && this.blockPos == blockPosIn)
      return; 
    this.blockState = blockStateIn;
    this.blockPos = blockPosIn;
    this.blockId = -1;
    this.metadata = -1;
    this.breakingAnimation = -1;
    this.smartLeaves = -1;
    this.boundsFlags.clear();
  }
  
  public int getBlockId() {
    if (this.blockId < 0)
      this.blockId = this.blockState.getBlockId(); 
    return this.blockId;
  }
  
  public int getMetadata() {
    if (this.metadata < 0)
      this.metadata = this.blockState.getMetadata(); 
    return this.metadata;
  }
  
  public float[] getQuadBounds() {
    return this.quadBounds;
  }
  
  public BitSet getBoundsFlags() {
    return this.boundsFlags;
  }
  
  public gfv.b getAoFace() {
    return this.aoFace;
  }
  
  public boolean isBreakingAnimation(List listQuads) {
    if (this.breakingAnimation == -1)
      if (listQuads.size() > 0)
        if (listQuads.get(0) instanceof net.optifine.model.BakedQuadRetextured) {
          this.breakingAnimation = 1;
        } else {
          this.breakingAnimation = 0;
        }   
    return (this.breakingAnimation == 1);
  }
  
  public boolean isBreakingAnimation(gfw quad) {
    if (this.breakingAnimation < 0)
      if (quad instanceof net.optifine.model.BakedQuadRetextured) {
        this.breakingAnimation = 1;
      } else {
        this.breakingAnimation = 0;
      }  
    return (this.breakingAnimation == 1);
  }
  
  public boolean isBreakingAnimation() {
    return (this.breakingAnimation == 1);
  }
  
  public dtc getBlockState() {
    return this.blockState;
  }
  
  public BlockPosM getColorizerBlockPosM() {
    if (this.colorizerBlockPosM == null)
      this.colorizerBlockPosM = new BlockPosM(0, 0, 0); 
    return this.colorizerBlockPosM;
  }
  
  public jd.a getRenderMutableBlockPos() {
    if (this.renderMutableBlockPos == null)
      this.renderMutableBlockPos = new jd.a(0, 0, 0); 
    return this.renderMutableBlockPos;
  }
  
  public boolean[] getBorderFlags() {
    if (this.borderFlags == null)
      this.borderFlags = new boolean[4]; 
    return this.borderFlags;
  }
  
  public boolean[] getBorderFlags2() {
    if (this.borderFlags2 == null)
      this.borderFlags2 = new boolean[4]; 
    return this.borderFlags2;
  }
  
  public boolean[] getBorderFlags3() {
    if (this.borderFlags3 == null)
      this.borderFlags3 = new boolean[4]; 
    return this.borderFlags3;
  }
  
  public ji[] getBorderDirections() {
    if (this.borderDirections == null)
      this.borderDirections = new ji[4]; 
    return this.borderDirections;
  }
  
  public ji[] getBorderDirections(ji dir0, ji dir1, ji dir2, ji dir3) {
    ji[] dirs = getBorderDirections();
    dirs[0] = dir0;
    dirs[1] = dir1;
    dirs[2] = dir2;
    dirs[3] = dir3;
    return dirs;
  }
  
  public boolean isSmartLeaves() {
    if (this.smartLeaves == -1)
      if (Config.isTreesSmart() && this.blockState.b() instanceof dki) {
        this.smartLeaves = 1;
      } else {
        this.smartLeaves = 0;
      }  
    return (this.smartLeaves == 1);
  }
  
  public List<gfw> getListQuadsCustomizer() {
    return this.listQuadsCustomizer;
  }
  
  public gfw[] getArrayQuadsCtm(gfw quad) {
    this.arrayQuadsCtm1[0] = quad;
    return this.arrayQuadsCtm1;
  }
  
  public gfw[] getArrayQuadsCtm(gfw quad0, gfw quad1) {
    this.arrayQuadsCtm2[0] = quad0;
    this.arrayQuadsCtm2[1] = quad1;
    return this.arrayQuadsCtm2;
  }
  
  public gfw[] getArrayQuadsCtm(gfw quad0, gfw quad1, gfw quad2) {
    this.arrayQuadsCtm3[0] = quad0;
    this.arrayQuadsCtm3[1] = quad1;
    this.arrayQuadsCtm3[2] = quad2;
    return this.arrayQuadsCtm3;
  }
  
  public gfw[] getArrayQuadsCtm(gfw quad0, gfw quad1, gfw quad2, gfw quad3) {
    this.arrayQuadsCtm4[0] = quad0;
    this.arrayQuadsCtm4[1] = quad1;
    this.arrayQuadsCtm4[2] = quad2;
    this.arrayQuadsCtm4[3] = quad3;
    return this.arrayQuadsCtm4;
  }
  
  public List<gfw> getListQuadsCtmMultipass(gfw[] quads) {
    this.listQuadsCtmMultipass.clear();
    if (quads != null)
      for (int i = 0; i < quads.length; i++) {
        gfw quad = quads[i];
        this.listQuadsCtmMultipass.add(quad);
      }  
    return this.listQuadsCtmMultipass;
  }
  
  public void setCompileParams(ghz sectionCompiler, Map<gfh, fbd> bufferBuilderMap, gfk sectionBufferBuilderPack) {
    this.sectionCompiler = sectionCompiler;
    this.bufferBuilderMap = bufferBuilderMap;
    this.sectionBufferBuilderPack = sectionBufferBuilderPack;
  }
  
  public ghz getSectionCompiler() {
    return this.sectionCompiler;
  }
  
  public Map<gfh, fbd> getBufferBuilderMap() {
    return this.bufferBuilderMap;
  }
  
  public gfk getSectionBufferBuilderPack() {
    return this.sectionBufferBuilderPack;
  }
  
  public ListQuadsOverlay getListQuadsOverlay(gfh layer) {
    ListQuadsOverlay list = this.listsQuadsOverlay[layer.ordinal()];
    if (list == null) {
      list = new ListQuadsOverlay();
      this.listsQuadsOverlay[layer.ordinal()] = list;
    } 
    return list;
  }
  
  public boolean isOverlaysRendered() {
    return this.overlaysRendered;
  }
  
  public void setOverlaysRendered(boolean overlaysRendered) {
    this.overlaysRendered = overlaysRendered;
  }
  
  public Long2ByteLinkedOpenHashMap getRenderSideMap() {
    return this.renderSideMap;
  }
}

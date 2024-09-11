package notch.net.optifine.shaders;

import com.mojang.blaze3d.platform.GlStateManager;
import java.awt.Dimension;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import net.optifine.shaders.DrawBuffers;
import net.optifine.shaders.FixedFramebuffer;
import net.optifine.shaders.FlipTextures;
import net.optifine.shaders.GlState;
import net.optifine.shaders.SMCLog;
import net.optifine.shaders.Shaders;
import net.optifine.util.ArrayUtils;
import net.optifine.util.CompoundIntKey;
import net.optifine.util.CompoundKey;
import net.optifine.util.DynamicDimension;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class ShadersFramebuffer {
  private String name;
  
  private int width;
  
  private int height;
  
  private int usedColorBuffers;
  
  private int usedDepthBuffers;
  
  private int maxDrawBuffers;
  
  private boolean[] depthFilterNearest;
  
  private boolean[] depthFilterHardware;
  
  private boolean[] colorFilterNearest;
  
  private DynamicDimension[] colorBufferSizes;
  
  private int[] buffersFormat;
  
  private int[] colorTextureUnits;
  
  private int[] depthTextureUnits;
  
  private int[] colorImageUnits;
  
  private int glFramebuffer;
  
  private FlipTextures colorTexturesFlip;
  
  private IntBuffer depthTextures;
  
  private final DrawBuffers drawBuffers;
  
  private DrawBuffers activeDrawBuffers;
  
  private int[] drawColorTextures;
  
  private int[] drawColorTexturesMap;
  
  private boolean[] dirtyColorTextures;
  
  private Map<CompoundKey, FixedFramebuffer> fixedFramebuffers = new HashMap<>();
  
  public ShadersFramebuffer(String name, int width, int height, int usedColorBuffers, int usedDepthBuffers, int maxDrawBuffers, boolean[] depthFilterNearest, boolean[] depthFilterHardware, boolean[] colorFilterNearest, DynamicDimension[] colorBufferSizes, int[] buffersFormat, int[] colorTextureUnits, int[] depthTextureUnits, int[] colorImageUnits, DrawBuffers drawBuffers) {
    this.name = name;
    this.width = width;
    this.height = height;
    this.usedColorBuffers = usedColorBuffers;
    this.usedDepthBuffers = usedDepthBuffers;
    this.maxDrawBuffers = maxDrawBuffers;
    this.depthFilterNearest = depthFilterNearest;
    this.depthFilterHardware = depthFilterHardware;
    this.colorFilterNearest = colorFilterNearest;
    this.colorBufferSizes = colorBufferSizes;
    this.buffersFormat = buffersFormat;
    this.colorTextureUnits = colorTextureUnits;
    this.depthTextureUnits = depthTextureUnits;
    this.colorImageUnits = colorImageUnits;
    this.drawBuffers = drawBuffers;
  }
  
  public void setup() {
    if (exists())
      delete(); 
    this.colorTexturesFlip = new FlipTextures(this.name + "ColorTexturesFlip", this.usedColorBuffers);
    this.depthTextures = BufferUtils.createIntBuffer(this.usedDepthBuffers);
    this.drawColorTextures = new int[this.usedColorBuffers];
    this.drawColorTexturesMap = new int[this.usedColorBuffers];
    this.dirtyColorTextures = new boolean[this.maxDrawBuffers];
    Arrays.fill(this.drawColorTextures, 0);
    Arrays.fill(this.drawColorTexturesMap, -1);
    Arrays.fill(this.dirtyColorTextures, false);
    int i;
    for (i = 0; i < this.drawBuffers.limit(); i++)
      this.drawBuffers.put(i, 36064 + i); 
    this.glFramebuffer = GL30.glGenFramebuffers();
    bindFramebuffer();
    GL30.glDrawBuffers(0);
    GL30.glReadBuffer(0);
    GL30.glGenTextures(this.depthTextures.clear().limit(this.usedDepthBuffers));
    this.colorTexturesFlip.clear().limit(this.usedColorBuffers).genTextures();
    this.depthTextures.position(0);
    this.colorTexturesFlip.position(0);
    for (i = 0; i < this.usedDepthBuffers; i++) {
      GlStateManager._bindTexture(this.depthTextures.get(i));
      GL30.glTexParameteri(3553, 10242, 33071);
      GL30.glTexParameteri(3553, 10243, 33071);
      int filter = this.depthFilterNearest[i] ? 9728 : 9729;
      GL30.glTexParameteri(3553, 10241, filter);
      GL30.glTexParameteri(3553, 10240, filter);
      if (this.depthFilterHardware[i])
        GL30.glTexParameteri(3553, 34892, 34894); 
      GL30.glTexImage2D(3553, 0, 6402, this.width, this.height, 0, 6402, 5126, (FloatBuffer)null);
    } 
    setFramebufferTexture2D(36160, 36096, 3553, this.depthTextures.get(0), 0);
    Shaders.checkGLError("FBS " + this.name + " depth");
    for (i = 0; i < this.usedColorBuffers; i++) {
      GlStateManager._bindTexture(this.colorTexturesFlip.getA(i));
      GL30.glTexParameteri(3553, 10242, 33071);
      GL30.glTexParameteri(3553, 10243, 33071);
      int filter = this.colorFilterNearest[i] ? 9728 : 9729;
      GL30.glTexParameteri(3553, 10241, filter);
      GL30.glTexParameteri(3553, 10240, filter);
      Dimension dim = (this.colorBufferSizes[i] != null) ? this.colorBufferSizes[i].getDimension(this.width, this.height) : new Dimension(this.width, this.height);
      GL30.glTexImage2D(3553, 0, this.buffersFormat[i], dim.width, dim.height, 0, Shaders.getPixelFormat(this.buffersFormat[i]), 33639, (ByteBuffer)null);
      setFramebufferTexture2D(36160, 36064 + i, 3553, this.colorTexturesFlip.getA(i), 0);
      Shaders.checkGLError("FBS " + this.name + " colorA");
    } 
    for (i = 0; i < this.usedColorBuffers; i++) {
      GlStateManager._bindTexture(this.colorTexturesFlip.getB(i));
      GL30.glTexParameteri(3553, 10242, 33071);
      GL30.glTexParameteri(3553, 10243, 33071);
      int filter = this.colorFilterNearest[i] ? 9728 : 9729;
      GL30.glTexParameteri(3553, 10241, filter);
      GL30.glTexParameteri(3553, 10240, filter);
      Dimension dim = (this.colorBufferSizes[i] != null) ? this.colorBufferSizes[i].getDimension(this.width, this.height) : new Dimension(this.width, this.height);
      GL30.glTexImage2D(3553, 0, this.buffersFormat[i], dim.width, dim.height, 0, Shaders.getPixelFormat(this.buffersFormat[i]), 33639, (ByteBuffer)null);
      Shaders.checkGLError("FBS " + this.name + " colorB");
    } 
    GlStateManager._bindTexture(0);
    if (this.usedColorBuffers > 0) {
      setDrawBuffers(this.drawBuffers);
      GL30.glReadBuffer(0);
    } 
    int status = GL30.glCheckFramebufferStatus(36160);
    if (status != 36053) {
      Shaders.printChatAndLogError("[Shaders] Error creating framebuffer: " + this.name + ", status: " + status);
      return;
    } 
    SMCLog.info("Framebuffer created: " + this.name);
  }
  
  public void delete() {
    if (this.glFramebuffer != 0) {
      GL30.glDeleteFramebuffers(this.glFramebuffer);
      this.glFramebuffer = 0;
    } 
    if (this.colorTexturesFlip != null) {
      this.colorTexturesFlip.deleteTextures();
      this.colorTexturesFlip = null;
    } 
    if (this.depthTextures != null) {
      GlStateManager.deleteTextures(this.depthTextures);
      this.depthTextures = null;
    } 
    this.drawBuffers.position(0).fill(0);
    for (FixedFramebuffer ff : this.fixedFramebuffers.values())
      ff.delete(); 
    this.fixedFramebuffers.clear();
  }
  
  public String getName() {
    return this.name;
  }
  
  public int getWidth() {
    return this.width;
  }
  
  public int getHeight() {
    return this.height;
  }
  
  public int getGlFramebuffer() {
    return this.glFramebuffer;
  }
  
  public boolean exists() {
    return (this.glFramebuffer != 0);
  }
  
  public void bindFramebuffer() {
    GlState.bindFramebuffer(this);
  }
  
  public void setColorTextures(boolean main) {
    for (int i = 0; i < this.usedColorBuffers; i++)
      setFramebufferTexture2D(36160, 36064 + i, 3553, this.colorTexturesFlip.get(main, i), 0); 
  }
  
  public void setDepthTexture() {
    setFramebufferTexture2D(36160, 36096, 3553, this.depthTextures.get(0), 0);
  }
  
  public void setColorBuffersFiltering(int minFilter, int magFilter) {
    GlStateManager._activeTexture(33984);
    for (int i = 0; i < this.usedColorBuffers; i++) {
      GlStateManager._bindTexture(this.colorTexturesFlip.getA(i));
      GL11.glTexParameteri(3553, 10241, minFilter);
      GL11.glTexParameteri(3553, 10240, magFilter);
      GlStateManager._bindTexture(this.colorTexturesFlip.getB(i));
      GL11.glTexParameteri(3553, 10241, minFilter);
      GL11.glTexParameteri(3553, 10240, magFilter);
    } 
    GlStateManager._bindTexture(0);
  }
  
  public void setFramebufferTexture2D(int target, int attachment, int texTarget, int texture, int level) {
    int colorIndex = attachment - 36064;
    if (isColorBufferIndex(colorIndex)) {
      if (this.colorBufferSizes[colorIndex] != null) {
        if (isColorExtendedIndex(colorIndex))
          return; 
        texture = 0;
      } 
      this.drawColorTextures[colorIndex] = texture;
      if (colorIndex >= this.maxDrawBuffers) {
        int indexMapped = this.drawColorTexturesMap[colorIndex];
        if (!isDrawBufferIndex(indexMapped))
          return; 
        attachment = 36064 + indexMapped;
      } 
    } 
    bindFramebuffer();
    GL30.glFramebufferTexture2D(target, attachment, texTarget, texture, level);
  }
  
  public boolean isColorBufferIndex(int index) {
    return (index >= 0 && index < this.usedColorBuffers);
  }
  
  public boolean isColorExtendedIndex(int index) {
    return (index >= this.maxDrawBuffers && index < this.usedColorBuffers);
  }
  
  public boolean isDrawBufferIndex(int index) {
    return (index >= 0 && index < this.maxDrawBuffers);
  }
  
  private void setDrawColorTexturesMap(int[] newColorTexturesMap) {
    bindFramebuffer();
    int i;
    for (i = 0; i < this.maxDrawBuffers; i++) {
      if (this.dirtyColorTextures[i]) {
        int texture = this.drawColorTextures[i];
        GL30.glFramebufferTexture2D(36160, 36064 + i, 3553, texture, 0);
        this.dirtyColorTextures[i] = false;
      } 
    } 
    this.drawColorTexturesMap = newColorTexturesMap;
    for (i = this.maxDrawBuffers; i < this.drawColorTexturesMap.length; i++) {
      int ai = this.drawColorTexturesMap[i];
      if (ai >= 0) {
        int texture = this.drawColorTextures[i];
        GL30.glFramebufferTexture2D(36160, 36064 + ai, 3553, texture, 0);
        this.dirtyColorTextures[ai] = true;
      } 
    } 
  }
  
  public void setDrawBuffers(DrawBuffers drawBuffersIn) {
    if (drawBuffersIn == null)
      drawBuffersIn = Shaders.drawBuffersNone; 
    setDrawColorTexturesMap(drawBuffersIn.getAttachmentMappings());
    this.activeDrawBuffers = drawBuffersIn;
    bindFramebuffer();
    GL30.glDrawBuffers(drawBuffersIn.getGlDrawBuffers());
    Shaders.checkGLError("setDrawBuffers");
  }
  
  public void setDrawBuffers() {
    setDrawBuffers(this.drawBuffers);
  }
  
  public DrawBuffers getDrawBuffers() {
    return this.activeDrawBuffers;
  }
  
  public void bindDepthTextures(int[] depthTextureImageUnits) {
    for (int i = 0; i < this.usedDepthBuffers; i++) {
      GlStateManager._activeTexture(33984 + depthTextureImageUnits[i]);
      GlStateManager._bindTexture(this.depthTextures.get(i));
    } 
    GlStateManager._activeTexture(33984);
  }
  
  public void bindColorTextures(int startColorBuffer) {
    for (int i = startColorBuffer; i < this.usedColorBuffers; i++) {
      GlStateManager._activeTexture(33984 + this.colorTextureUnits[i]);
      GlStateManager._bindTexture(this.colorTexturesFlip.getA(i));
      bindColorImage(i, true);
    } 
  }
  
  public void bindColorImages(boolean main) {
    if (this.colorImageUnits == null)
      return; 
    for (int i = 0; i < this.usedColorBuffers; i++)
      bindColorImage(i, main); 
  }
  
  public void bindColorImage(int index, boolean main) {
    if (this.colorImageUnits == null)
      return; 
    if (index >= 0 && index < this.colorImageUnits.length) {
      int imageFormat = Shaders.getImageFormat(this.buffersFormat[index]);
      GlStateManager.bindImageTexture(this.colorImageUnits[index], this.colorTexturesFlip.get(main, index), 0, false, 0, 35002, imageFormat);
    } 
    GlStateManager._activeTexture(33984);
  }
  
  public void generateDepthMipmaps(boolean[] depthMipmapEnabled) {
    for (int i = 0; i < this.usedDepthBuffers; i++) {
      if (depthMipmapEnabled[i]) {
        GlStateManager._activeTexture(33984 + this.depthTextureUnits[i]);
        GlStateManager._bindTexture(this.depthTextures.get(i));
        GL30.glGenerateMipmap(3553);
        GL30.glTexParameteri(3553, 10241, this.depthFilterNearest[i] ? 9984 : 9987);
      } 
    } 
    GlStateManager._activeTexture(33984);
  }
  
  public void generateColorMipmaps(boolean main, boolean[] colorMipmapEnabled) {
    for (int i = 0; i < this.usedColorBuffers; i++) {
      if (colorMipmapEnabled[i]) {
        GlStateManager._activeTexture(33984 + this.colorTextureUnits[i]);
        GlStateManager._bindTexture(this.colorTexturesFlip.get(main, i));
        GL30.glGenerateMipmap(3553);
        GL30.glTexParameteri(3553, 10241, this.colorFilterNearest[i] ? 9984 : 9987);
      } 
    } 
    GlStateManager._activeTexture(33984);
  }
  
  public void genCompositeMipmap(int compositeMipmapSetting) {
    if (Shaders.hasGlGenMipmap) {
      for (int i = 0; i < this.usedColorBuffers; i++) {
        if ((compositeMipmapSetting & 1 << i) != 0) {
          GlStateManager._activeTexture(33984 + this.colorTextureUnits[i]);
          GL30.glTexParameteri(3553, 10241, 9987);
          GL30.glGenerateMipmap(3553);
        } 
      } 
      GlStateManager._activeTexture(33984);
    } 
  }
  
  public void flipColorTextures(boolean[] toggleColorTextures) {
    for (int i = 0; i < this.colorTexturesFlip.limit(); i++) {
      if (toggleColorTextures[i])
        flipColorTexture(i); 
    } 
  }
  
  public void flipColorTexture(int index) {
    this.colorTexturesFlip.flip(index);
    GlStateManager._activeTexture(33984 + this.colorTextureUnits[index]);
    GlStateManager._bindTexture(this.colorTexturesFlip.getA(index));
    bindColorImage(index, true);
    setFramebufferTexture2D(36160, 36064 + index, 3553, this.colorTexturesFlip.getB(index), 0);
    GlStateManager._activeTexture(33984);
  }
  
  public void clearColorBuffers(boolean[] buffersClear, Vector4f[] clearColors) {
    for (int i = 0; i < this.usedColorBuffers; i++) {
      if (buffersClear[i]) {
        Vector4f col = clearColors[i];
        if (col != null)
          GL30.glClearColor(col.x(), col.y(), col.z(), col.w()); 
        if (this.colorBufferSizes[i] != null) {
          if (this.colorTexturesFlip.isChanged(i))
            clearColorBufferFixedSize(i, false); 
          clearColorBufferFixedSize(i, true);
        } else {
          if (this.colorTexturesFlip.isChanged(i)) {
            setFramebufferTexture2D(36160, 36064 + i, 3553, this.colorTexturesFlip.getB(i), 0);
            setDrawBuffers(Shaders.drawBuffersColorAtt[i]);
            GL30.glClear(16384);
            setFramebufferTexture2D(36160, 36064 + i, 3553, this.colorTexturesFlip.getA(i), 0);
          } 
          setDrawBuffers(Shaders.drawBuffersColorAtt[i]);
          GL30.glClear(16384);
        } 
      } 
    } 
  }
  
  private void clearColorBufferFixedSize(int i, boolean main) {
    Dimension dim = this.colorBufferSizes[i].getDimension(this.width, this.height);
    if (dim == null)
      return; 
    FixedFramebuffer ff = getFixedFramebuffer(dim.width, dim.height, Shaders.drawBuffersColorAtt[i], main);
    ff.bindFramebuffer();
    GL30.glClear(16384);
  }
  
  public void clearDepthBuffer(Vector4f col) {
    setFramebufferTexture2D(36160, 36096, 3553, this.depthTextures.get(0), 0);
    GL30.glClearColor(col.x(), col.y(), col.z(), col.w());
    GL30.glClear(256);
  }
  
  public String toString() {
    return this.name + ", width: " + this.name + ", height: " + this.width + ", usedColorBuffers: " + this.height + ", usedDepthBuffers: " + this.usedColorBuffers + ", maxDrawBuffers: " + this.usedDepthBuffers;
  }
  
  public FixedFramebuffer getFixedFramebuffer(int width, int height, DrawBuffers dbs, boolean main) {
    IntBuffer glDbs = dbs.getGlDrawBuffers();
    int dbsLen = dbs.limit();
    int[] glTexs = new int[dbsLen];
    int[] glAtts = new int[dbsLen];
    for (int i = 0; i < glTexs.length; i++) {
      int att = dbs.get(i);
      int ix = att - 36064;
      if (isColorBufferIndex(ix)) {
        glTexs[i] = this.colorTexturesFlip.get(main, ix);
        glAtts[i] = glDbs.get(i);
      } 
    } 
    CompoundKey key = new CompoundKey(new CompoundIntKey(glTexs), new CompoundIntKey(glAtts));
    FixedFramebuffer ff = this.fixedFramebuffers.get(key);
    if (ff == null) {
      String fixedName = this.name + ", [" + this.name + "], [" + ArrayUtils.arrayToString(glTexs) + "]";
      ff = new FixedFramebuffer(fixedName, width, height, glTexs, glAtts, this.depthFilterNearest[0], this.depthFilterHardware[0]);
      ff.setup();
      this.fixedFramebuffers.put(key, ff);
    } 
    return ff;
  }
}

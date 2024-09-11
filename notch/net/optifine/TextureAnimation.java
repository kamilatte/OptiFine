package notch.net.optifine;

import akr;
import com.mojang.blaze3d.platform.GlStateManager;
import fac;
import gpw;
import java.nio.ByteBuffer;
import java.util.Properties;
import net.optifine.Config;
import net.optifine.SmartAnimations;
import net.optifine.TextureAnimationFrame;
import net.optifine.util.TextureUtils;
import org.lwjgl.opengl.GL11;

public class TextureAnimation {
  private String srcTex = null;
  
  private String dstTex = null;
  
  akr dstTexLoc = null;
  
  private int dstTextId = -1;
  
  private int dstX = 0;
  
  private int dstY = 0;
  
  private int frameWidth = 0;
  
  private int frameHeight = 0;
  
  private TextureAnimationFrame[] frames = null;
  
  private int currentFrameIndex = 0;
  
  private boolean interpolate = false;
  
  private int interpolateSkip = 0;
  
  private ByteBuffer interpolateData = null;
  
  byte[] srcData = null;
  
  private ByteBuffer imageData = null;
  
  private boolean active = true;
  
  private boolean valid = true;
  
  public TextureAnimation(String texFrom, byte[] srcData, String texTo, akr locTexTo, int dstX, int dstY, int frameWidth, int frameHeight, Properties props) {
    this.srcTex = texFrom;
    this.dstTex = texTo;
    this.dstTexLoc = locTexTo;
    this.dstX = dstX;
    this.dstY = dstY;
    this.frameWidth = frameWidth;
    this.frameHeight = frameHeight;
    int frameLen = frameWidth * frameHeight * 4;
    if (srcData.length % frameLen != 0)
      Config.warn("Invalid animated texture length: " + srcData.length + ", frameWidth: " + frameWidth + ", frameHeight: " + frameHeight); 
    this.srcData = srcData;
    int numFrames = srcData.length / frameLen;
    if (props.get("tile.0") != null)
      for (int j = 0; props.get("tile." + j) != null; j++)
        numFrames = j + 1;  
    String durationDefStr = (String)props.get("duration");
    int durationDef = Math.max(Config.parseInt(durationDefStr, 1), 1);
    this.frames = new TextureAnimationFrame[numFrames];
    for (int i = 0; i < this.frames.length; i++) {
      String indexStr = (String)props.get("tile." + i);
      int index = Config.parseInt(indexStr, i);
      String durationStr = (String)props.get("duration." + i);
      int duration = Math.max(Config.parseInt(durationStr, durationDef), 1);
      TextureAnimationFrame frm = new TextureAnimationFrame(index, duration);
      this.frames[i] = frm;
    } 
    this.interpolate = Config.parseBoolean(props.getProperty("interpolate"), false);
    this.interpolateSkip = Config.parseInt(props.getProperty("skip"), 0);
    if (this.interpolate)
      this.interpolateData = fac.a(frameLen); 
  }
  
  public boolean nextFrame() {
    TextureAnimationFrame frame = getCurrentFrame();
    if (frame == null)
      return false; 
    frame.counter++;
    if (frame.counter < frame.duration)
      return this.interpolate; 
    frame.counter = 0;
    this.currentFrameIndex++;
    if (this.currentFrameIndex >= this.frames.length)
      this.currentFrameIndex = 0; 
    return true;
  }
  
  public TextureAnimationFrame getCurrentFrame() {
    return getFrame(this.currentFrameIndex);
  }
  
  public TextureAnimationFrame getFrame(int index) {
    if (this.frames.length <= 0)
      return null; 
    if (index < 0 || index >= this.frames.length)
      index = 0; 
    TextureAnimationFrame frame = this.frames[index];
    return frame;
  }
  
  public int getFrameCount() {
    return this.frames.length;
  }
  
  public void updateTexture() {
    if (!this.valid)
      return; 
    if (this.dstTextId < 0) {
      gpw tex = TextureUtils.getTexture(this.dstTexLoc);
      if (tex == null) {
        this.valid = false;
        return;
      } 
      this.dstTextId = tex.a();
    } 
    if (this.imageData == null) {
      this.imageData = fac.a(this.srcData.length);
      this.imageData.put(this.srcData);
      this.imageData.flip();
      this.srcData = null;
    } 
    this.active = SmartAnimations.isActive() ? SmartAnimations.isTextureRendered(this.dstTextId) : true;
    if (!nextFrame())
      return; 
    if (!this.active)
      return; 
    int frameLen = this.frameWidth * this.frameHeight * 4;
    TextureAnimationFrame frame = getCurrentFrame();
    if (frame == null)
      return; 
    int offset = frameLen * frame.index;
    if (offset + frameLen > this.imageData.limit())
      return; 
    if (this.interpolate && frame.counter > 0) {
      if (this.interpolateSkip > 1)
        if (frame.counter % this.interpolateSkip != 0)
          return;  
      TextureAnimationFrame frameNext = getFrame(this.currentFrameIndex + 1);
      double k = 1.0D * frame.counter / frame.duration;
      updateTextureInerpolate(frame, frameNext, k);
      return;
    } 
    this.imageData.position(offset);
    GlStateManager._bindTexture(this.dstTextId);
    TextureUtils.resetDataUnpacking();
    GL11.glTexSubImage2D(3553, 0, this.dstX, this.dstY, this.frameWidth, this.frameHeight, 6408, 5121, this.imageData);
  }
  
  private void updateTextureInerpolate(TextureAnimationFrame frame1, TextureAnimationFrame frame2, double k) {
    int frameLen = this.frameWidth * this.frameHeight * 4;
    int offset1 = frameLen * frame1.index;
    if (offset1 + frameLen > this.imageData.limit())
      return; 
    int offset2 = frameLen * frame2.index;
    if (offset2 + frameLen > this.imageData.limit())
      return; 
    this.interpolateData.clear();
    for (int i = 0; i < frameLen; i++) {
      int c1 = this.imageData.get(offset1 + i) & 0xFF;
      int c2 = this.imageData.get(offset2 + i) & 0xFF;
      int c = mix(c1, c2, k);
      byte b = (byte)c;
      this.interpolateData.put(b);
    } 
    this.interpolateData.flip();
    GlStateManager._bindTexture(this.dstTextId);
    TextureUtils.resetDataUnpacking();
    GL11.glTexSubImage2D(3553, 0, this.dstX, this.dstY, this.frameWidth, this.frameHeight, 6408, 5121, this.interpolateData);
  }
  
  private int mix(int col1, int col2, double k) {
    return (int)(col1 * (1.0D - k) + col2 * k);
  }
  
  public String getSrcTex() {
    return this.srcTex;
  }
  
  public String getDstTex() {
    return this.dstTex;
  }
  
  public akr getDstTexLoc() {
    return this.dstTexLoc;
  }
  
  public boolean isActive() {
    return this.active;
  }
}

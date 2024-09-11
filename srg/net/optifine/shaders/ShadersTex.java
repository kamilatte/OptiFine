package srg.net.optifine.shaders;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.optifine.Config;
import net.optifine.shaders.MultiTexID;
import net.optifine.shaders.SMCLog;
import net.optifine.shaders.Shaders;
import net.optifine.util.TextureUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class ShadersTex {
  public static final int initialBufferSize = 1048576;
  
  public static ByteBuffer byteBuffer = BufferUtils.createByteBuffer(4194304);
  
  public static IntBuffer intBuffer = byteBuffer.asIntBuffer();
  
  public static int[] intArray = new int[1048576];
  
  public static final int defBaseTexColor = 0;
  
  public static final int defNormTexColor = -8421377;
  
  public static final int defSpecTexColor = 0;
  
  public static Map<Integer, MultiTexID> multiTexMap = new HashMap<>();
  
  public static IntBuffer getIntBuffer(int size) {
    if (intBuffer.capacity() < size) {
      int bufferSize = roundUpPOT(size);
      byteBuffer = BufferUtils.createByteBuffer(bufferSize * 4);
      intBuffer = byteBuffer.asIntBuffer();
    } 
    return intBuffer;
  }
  
  public static int[] getIntArray(int size) {
    if (intArray == null)
      intArray = new int[1048576]; 
    if (intArray.length < size)
      intArray = new int[roundUpPOT(size)]; 
    return intArray;
  }
  
  public static int roundUpPOT(int x) {
    int i = x - 1;
    i |= i >> 1;
    i |= i >> 2;
    i |= i >> 4;
    i |= i >> 8;
    i |= i >> 16;
    return i + 1;
  }
  
  public static int log2(int x) {
    int log = 0;
    if ((x & 0xFFFF0000) != 0) {
      log += 16;
      x >>= 16;
    } 
    if ((x & 0xFF00) != 0) {
      log += 8;
      x >>= 8;
    } 
    if ((x & 0xF0) != 0) {
      log += 4;
      x >>= 4;
    } 
    if ((x & 0x6) != 0) {
      log += 2;
      x >>= 2;
    } 
    if ((x & 0x2) != 0)
      log++; 
    return log;
  }
  
  public static IntBuffer fillIntBuffer(int size, int value) {
    int[] aint = getIntArray(size);
    IntBuffer intBuf = getIntBuffer(size);
    Arrays.fill(intArray, 0, size, value);
    intBuffer.put(intArray, 0, size);
    return intBuffer;
  }
  
  public static int[] createAIntImage(int size) {
    int[] aint = new int[size * 3];
    Arrays.fill(aint, 0, size, 0);
    Arrays.fill(aint, size, size * 2, -8421377);
    Arrays.fill(aint, size * 2, size * 3, 0);
    return aint;
  }
  
  public static int[] createAIntImage(int size, int color) {
    int[] aint = new int[size * 3];
    Arrays.fill(aint, 0, size, color);
    Arrays.fill(aint, size, size * 2, -8421377);
    Arrays.fill(aint, size * 2, size * 3, 0);
    return aint;
  }
  
  public static MultiTexID getMultiTexID(AbstractTexture tex) {
    MultiTexID multiTex = tex.multiTex;
    if (multiTex == null) {
      int baseTex = tex.getId();
      multiTex = multiTexMap.get(Integer.valueOf(baseTex));
      if (multiTex == null) {
        multiTex = new MultiTexID(baseTex, GL11.glGenTextures(), GL11.glGenTextures());
        multiTexMap.put(Integer.valueOf(baseTex), multiTex);
      } 
      tex.multiTex = multiTex;
    } 
    return multiTex;
  }
  
  public static void deleteTextures(AbstractTexture atex, int texid) {
    MultiTexID multiTex = atex.multiTex;
    if (multiTex != null) {
      atex.multiTex = null;
      multiTexMap.remove(Integer.valueOf(multiTex.base));
      GlStateManager._deleteTexture(multiTex.norm);
      GlStateManager._deleteTexture(multiTex.spec);
      if (multiTex.base != texid) {
        SMCLog.warning("Error : MultiTexID.base mismatch: " + multiTex.base + ", texid: " + texid);
        GlStateManager._deleteTexture(multiTex.base);
      } 
    } 
  }
  
  public static void bindNSTextures(int normTex, int specTex, boolean normalBlend, boolean specularBlend, boolean mipmaps) {
    if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
      if (Shaders.configNormalMap) {
        GlStateManager._activeTexture(33985);
        GlStateManager._bindTexture(normTex);
        if (!normalBlend) {
          int minFilter = mipmaps ? 9984 : 9728;
          GlStateManager._texParameter(3553, 10241, minFilter);
          GlStateManager._texParameter(3553, 10240, 9728);
        } 
      } 
      if (Shaders.configSpecularMap) {
        GlStateManager._activeTexture(33987);
        GlStateManager._bindTexture(specTex);
        if (!specularBlend) {
          int minFilter = mipmaps ? 9984 : 9728;
          GlStateManager._texParameter(3553, 10241, minFilter);
          GlStateManager._texParameter(3553, 10240, 9728);
        } 
      } 
      GlStateManager._activeTexture(33984);
    } 
  }
  
  public static void bindNSTextures(MultiTexID multiTex) {
    bindNSTextures(multiTex.norm, multiTex.spec, true, true, false);
  }
  
  public static void bindTextures(int baseTex, int normTex, int specTex) {
    if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
      GlStateManager._activeTexture(33985);
      GlStateManager._bindTexture(normTex);
      GlStateManager._activeTexture(33987);
      GlStateManager._bindTexture(specTex);
      GlStateManager._activeTexture(33984);
    } 
    GlStateManager._bindTexture(baseTex);
  }
  
  public static void bindTextures(MultiTexID multiTex, boolean normalBlend, boolean specularBlend, boolean mipmaps) {
    if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
      if (Shaders.configNormalMap) {
        GlStateManager._activeTexture(33985);
        GlStateManager._bindTexture(multiTex.norm);
        if (!normalBlend) {
          int minFilter = mipmaps ? 9984 : 9728;
          GlStateManager._texParameter(3553, 10241, minFilter);
          GlStateManager._texParameter(3553, 10240, 9728);
        } 
      } 
      if (Shaders.configSpecularMap) {
        GlStateManager._activeTexture(33987);
        GlStateManager._bindTexture(multiTex.spec);
        if (!specularBlend) {
          int minFilter = mipmaps ? 9984 : 9728;
          GlStateManager._texParameter(3553, 10241, minFilter);
          GlStateManager._texParameter(3553, 10240, 9728);
        } 
      } 
      GlStateManager._activeTexture(33984);
    } 
    GlStateManager._bindTexture(multiTex.base);
  }
  
  public static void bindTexture(int id) {
    AbstractTexture tex = Config.getTextureManager().getTextureById(id);
    if (tex == null) {
      GlStateManager._bindTexture(id);
      return;
    } 
    bindTexture(tex);
  }
  
  public static void bindTexture(AbstractTexture tex) {
    int texId = tex.getId();
    boolean normalBlend = true;
    boolean specularBlend = true;
    boolean mipmaps = false;
    if (tex instanceof TextureAtlas) {
      TextureAtlas at = (TextureAtlas)tex;
      normalBlend = at.isNormalBlend();
      specularBlend = at.isSpecularBlend();
      mipmaps = at.isMipmaps();
    } 
    MultiTexID multiTex = tex.getMultiTexID();
    if (multiTex != null) {
      bindTextures(multiTex, normalBlend, specularBlend, mipmaps);
    } else {
      GlStateManager._bindTexture(texId);
    } 
    if (GlStateManager.getActiveTextureUnit() == 33984) {
      int prevSizeX = Shaders.atlasSizeX;
      int prevSizeY = Shaders.atlasSizeY;
      if (tex instanceof TextureAtlas) {
        Shaders.atlasSizeX = ((TextureAtlas)tex).atlasWidth;
        Shaders.atlasSizeY = ((TextureAtlas)tex).atlasHeight;
      } else {
        Shaders.atlasSizeX = 0;
        Shaders.atlasSizeY = 0;
      } 
    } 
  }
  
  public static void bindTextures(int baseTex) {
    MultiTexID multiTex = multiTexMap.get(Integer.valueOf(baseTex));
    bindTextures(multiTex, true, true, false);
  }
  
  public static void initDynamicTextureNS(DynamicTexture tex) {
    MultiTexID multiTex = tex.getMultiTexID();
    NativeImage nativeImage = tex.getPixels();
    int width = nativeImage.getWidth();
    int height = nativeImage.getHeight();
    NativeImage imageNormal = makeImageColor(width, height, -8421377);
    TextureUtil.prepareImage(multiTex.norm, width, height);
    imageNormal.upload(0, 0, 0, 0, 0, width, height, false, false, false, true);
    NativeImage imageSpecular = makeImageColor(width, height, 0);
    TextureUtil.prepareImage(multiTex.spec, width, height);
    imageSpecular.upload(0, 0, 0, 0, 0, width, height, false, false, false, true);
    GlStateManager._bindTexture(multiTex.base);
  }
  
  public static void updateDynTexSubImage1(int[] src, int width, int height, int posX, int posY, int page) {
    int size = width * height;
    IntBuffer intBuf = getIntBuffer(size);
    intBuf.clear();
    int offset = page * size;
    if (src.length < offset + size)
      return; 
    intBuf.put(src, offset, size).position(0).limit(size);
    TextureUtils.resetDataUnpacking();
    GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intBuf);
    intBuf.clear();
  }
  
  public static AbstractTexture createDefaultTexture() {
    DynamicTexture tex = new DynamicTexture(1, 1, true);
    tex.getPixels().setPixelRGBA(0, 0, -1);
    tex.upload();
    return (AbstractTexture)tex;
  }
  
  public static void allocateTextureMapNS(int mipmapLevels, int width, int height, TextureAtlas tex) {
    MultiTexID multiTex = getMultiTexID((AbstractTexture)tex);
    if (Shaders.configNormalMap) {
      SMCLog.info("Allocate texture map normal: " + width + "x" + height + ", mipmaps: " + mipmapLevels);
      TextureUtil.prepareImage(multiTex.norm, mipmapLevels, width, height);
    } 
    if (Shaders.configSpecularMap) {
      SMCLog.info("Allocate texture map specular: " + width + "x" + height + ", mipmaps: " + mipmapLevels);
      TextureUtil.prepareImage(multiTex.spec, mipmapLevels, width, height);
    } 
    GlStateManager._bindTexture(multiTex.base);
  }
  
  private static NativeImage[] generateMipmaps(NativeImage image, int levels) {
    if (levels < 0)
      levels = 0; 
    NativeImage[] mipmapImages = new NativeImage[levels + 1];
    mipmapImages[0] = image;
    if (levels > 0)
      for (int level = 1; level <= levels; level++) {
        NativeImage imageParent = mipmapImages[level - 1];
        NativeImage imageChild = new NativeImage(imageParent.getWidth() >> 1, imageParent.getHeight() >> 1, false);
        int k = imageChild.getWidth();
        int l = imageChild.getHeight();
        for (int x = 0; x < k; x++) {
          for (int y = 0; y < l; y++)
            imageChild.setPixelRGBA(x, y, blend4Simple(imageParent.getPixelRGBA(x * 2 + 0, y * 2 + 0), imageParent.getPixelRGBA(x * 2 + 1, y * 2 + 0), imageParent.getPixelRGBA(x * 2 + 0, y * 2 + 1), imageParent.getPixelRGBA(x * 2 + 1, y * 2 + 1))); 
        } 
        mipmapImages[level] = imageChild;
      }  
    return mipmapImages;
  }
  
  public static BufferedImage readImage(ResourceLocation resLoc) {
    try {
      if (!Config.hasResource(resLoc))
        return null; 
      InputStream istr = Config.getResourceStream(resLoc);
      if (istr == null)
        return null; 
      BufferedImage image = ImageIO.read(istr);
      istr.close();
      return image;
    } catch (IOException e) {
      return null;
    } 
  }
  
  public static int[][] genMipmapsSimple(int maxLevel, int width, int[][] data) {
    for (int level = 1; level <= maxLevel; level++) {
      if (data[level] == null) {
        int cw = width >> level;
        int pw = cw * 2;
        int[] aintp = data[level - 1];
        int[] aintc = data[level] = new int[cw * cw];
        for (int y = 0; y < cw; y++) {
          for (int x = 0; x < cw; x++) {
            int ppos = y * 2 * pw + x * 2;
            aintc[y * cw + x] = blend4Simple(aintp[ppos], aintp[ppos + 1], aintp[ppos + pw], aintp[ppos + pw + 1]);
          } 
        } 
      } 
    } 
    return data;
  }
  
  public static void uploadTexSub1(int[][] src, int width, int height, int posX, int posY, int page) {
    TextureUtils.resetDataUnpacking();
    int size = width * height;
    IntBuffer intBuf = getIntBuffer(size);
    int numLevel = src.length;
    int level = 0, lw = width, lh = height, px = posX, py = posY;
    for (; lw > 0 && lh > 0 && level < numLevel; 
      level++) {
      int lsize = lw * lh;
      int[] aint = src[level];
      intBuf.clear();
      if (aint.length >= lsize * (page + 1)) {
        intBuf.put(aint, lsize * page, lsize).position(0).limit(lsize);
        GL11.glTexSubImage2D(3553, level, px, py, lw, lh, 32993, 33639, intBuf);
      } 
      lw >>= 1;
      lh >>= 1;
      px >>= 1;
      py >>= 1;
    } 
    intBuf.clear();
  }
  
  public static int blend4Alpha(int c0, int c1, int c2, int c3) {
    int dv, a0 = c0 >>> 24 & 0xFF;
    int a1 = c1 >>> 24 & 0xFF;
    int a2 = c2 >>> 24 & 0xFF;
    int a3 = c3 >>> 24 & 0xFF;
    int as = a0 + a1 + a2 + a3;
    int an = (as + 2) / 4;
    if (as != 0) {
      dv = as;
    } else {
      dv = 4;
      a3 = a2 = a1 = a0 = 1;
    } 
    int frac = (dv + 1) / 2;
    int color = an << 24 | ((c0 >>> 16 & 0xFF) * a0 + (c1 >>> 16 & 0xFF) * a1 + (c2 >>> 16 & 0xFF) * a2 + (c3 >>> 16 & 0xFF) * a3 + frac) / dv << 16 | ((c0 >>> 8 & 0xFF) * a0 + (c1 >>> 8 & 0xFF) * a1 + (c2 >>> 8 & 0xFF) * a2 + (c3 >>> 8 & 0xFF) * a3 + frac) / dv << 8 | ((c0 >>> 0 & 0xFF) * a0 + (c1 >>> 0 & 0xFF) * a1 + (c2 >>> 0 & 0xFF) * a2 + (c3 >>> 0 & 0xFF) * a3 + frac) / dv << 0;
    return color;
  }
  
  public static int blend4Simple(int c0, int c1, int c2, int c3) {
    int color = ((c0 >>> 24 & 0xFF) + (c1 >>> 24 & 0xFF) + (c2 >>> 24 & 0xFF) + (c3 >>> 24 & 0xFF) + 2) / 4 << 24 | ((c0 >>> 16 & 0xFF) + (c1 >>> 16 & 0xFF) + (c2 >>> 16 & 0xFF) + (c3 >>> 16 & 0xFF) + 2) / 4 << 16 | ((c0 >>> 8 & 0xFF) + (c1 >>> 8 & 0xFF) + (c2 >>> 8 & 0xFF) + (c3 >>> 8 & 0xFF) + 2) / 4 << 8 | ((c0 >>> 0 & 0xFF) + (c1 >>> 0 & 0xFF) + (c2 >>> 0 & 0xFF) + (c3 >>> 0 & 0xFF) + 2) / 4 << 0;
    return color;
  }
  
  public static void genMipmapAlpha(int[] aint, int offset, int width, int height) {
    int minwh = Math.min(width, height);
    int w2 = width, w1 = w2;
    int h2 = height, h1 = h2;
    int o2 = offset, o1 = o2;
    o2 = offset;
    w2 = width;
    h2 = height;
    o1 = 0;
    w1 = 0;
    h1 = 0;
    int level;
    for (level = 0; w2 > 1 && h2 > 1; level++, w2 = w1, h2 = h1, o2 = o1) {
      o1 = o2 + w2 * h2;
      w1 = w2 / 2;
      h1 = h2 / 2;
      for (int y = 0; y < h1; y++) {
        int p1 = o1 + y * w1;
        int p2 = o2 + y * 2 * w2;
        for (int x = 0; x < w1; x++)
          aint[p1 + x] = blend4Alpha(aint[p2 + x * 2], aint[p2 + x * 2 + 1], aint[p2 + w2 + x * 2], aint[p2 + w2 + x * 2 + 1]); 
      } 
    } 
    while (level > 0) {
      level--;
      w2 = width >> level;
      h2 = height >> level;
      o2 = o1 - w2 * h2;
      int p2 = o2;
      for (int y = 0; y < h2; y++) {
        for (int x = 0; x < w2; x++) {
          if (aint[p2] == 0)
            aint[p2] = aint[o1 + y / 2 * w1 + x / 2] & 0xFFFFFF; 
          p2++;
        } 
      } 
      o1 = o2;
      w1 = w2;
      h1 = h2;
    } 
  }
  
  public static void genMipmapSimple(int[] aint, int offset, int width, int height) {
    int minwh = Math.min(width, height);
    int w2 = width, w1 = w2;
    int h2 = height, h1 = h2;
    int o2 = offset, o1 = o2;
    o2 = offset;
    w2 = width;
    h2 = height;
    o1 = 0;
    w1 = 0;
    h1 = 0;
    int level;
    for (level = 0; w2 > 1 && h2 > 1; level++, w2 = w1, h2 = h1, o2 = o1) {
      o1 = o2 + w2 * h2;
      w1 = w2 / 2;
      h1 = h2 / 2;
      for (int y = 0; y < h1; y++) {
        int p1 = o1 + y * w1;
        int p2 = o2 + y * 2 * w2;
        for (int x = 0; x < w1; x++)
          aint[p1 + x] = blend4Simple(aint[p2 + x * 2], aint[p2 + x * 2 + 1], aint[p2 + w2 + x * 2], aint[p2 + w2 + x * 2 + 1]); 
      } 
    } 
    while (level > 0) {
      level--;
      w2 = width >> level;
      h2 = height >> level;
      o2 = o1 - w2 * h2;
      int p2 = o2;
      for (int y = 0; y < h2; y++) {
        for (int x = 0; x < w2; x++) {
          if (aint[p2] == 0)
            aint[p2] = aint[o1 + y / 2 * w1 + x / 2] & 0xFFFFFF; 
          p2++;
        } 
      } 
      o1 = o2;
      w1 = w2;
      h1 = h2;
    } 
  }
  
  public static boolean isSemiTransparent(int[] aint, int width, int height) {
    int size = width * height;
    if (aint[0] >>> 24 == 255 && aint[size - 1] == 0)
      return true; 
    for (int i = 0; i < size; i++) {
      int alpha = aint[i] >>> 24;
      if (alpha != 0 && alpha != 255)
        return true; 
    } 
    return false;
  }
  
  public static void updateSubTex1(int[] src, int width, int height, int posX, int posY) {
    int level = 0, cw = width, ch = height, cx = posX, cy = posY;
    for (; cw > 0 && ch > 0; 
      level++, cw /= 2, ch /= 2, cx /= 2, cy /= 2)
      GL11.glCopyTexSubImage2D(3553, level, cx, cy, 0, 0, cw, ch); 
  }
  
  public static void updateSubImage(MultiTexID multiTex, int[] src, int width, int height, int posX, int posY, boolean linear, boolean clamp) {
    int size = width * height;
    IntBuffer intBuf = getIntBuffer(size);
    TextureUtils.resetDataUnpacking();
    intBuf.clear();
    intBuf.put(src, 0, size);
    intBuf.position(0).limit(size);
    GlStateManager._bindTexture(multiTex.base);
    GL11.glTexParameteri(3553, 10241, 9728);
    GL11.glTexParameteri(3553, 10240, 9728);
    GL11.glTexParameteri(3553, 10242, 10497);
    GL11.glTexParameteri(3553, 10243, 10497);
    GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intBuf);
    if (src.length == size * 3) {
      intBuf.clear();
      intBuf.put(src, size, size).position(0);
      intBuf.position(0).limit(size);
    } 
    GlStateManager._bindTexture(multiTex.norm);
    GL11.glTexParameteri(3553, 10241, 9728);
    GL11.glTexParameteri(3553, 10240, 9728);
    GL11.glTexParameteri(3553, 10242, 10497);
    GL11.glTexParameteri(3553, 10243, 10497);
    GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intBuf);
    if (src.length == size * 3) {
      intBuf.clear();
      intBuf.put(src, size * 2, size);
      intBuf.position(0).limit(size);
    } 
    GlStateManager._bindTexture(multiTex.spec);
    GL11.glTexParameteri(3553, 10241, 9728);
    GL11.glTexParameteri(3553, 10240, 9728);
    GL11.glTexParameteri(3553, 10242, 10497);
    GL11.glTexParameteri(3553, 10243, 10497);
    GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intBuf);
    GlStateManager._activeTexture(33984);
  }
  
  public static ResourceLocation getNSMapLocation(ResourceLocation location, String mapName) {
    if (location == null)
      return null; 
    String basename = location.getPath();
    String[] basenameParts = basename.split(".png");
    String basenameNoFileType = basenameParts[0];
    return new ResourceLocation(location.getNamespace(), basenameNoFileType + "_" + basenameNoFileType + ".png");
  }
  
  private static NativeImage loadNSMapImage(ResourceManager manager, ResourceLocation location, int width, int height, int defaultColor) {
    NativeImage image = loadNSMapFile(manager, location, width, height);
    if (image == null) {
      image = new NativeImage(width, height, false);
      int colAbgr = TextureUtils.toAbgr(defaultColor);
      image.fillRect(0, 0, width, height, colAbgr);
    } 
    return image;
  }
  
  private static NativeImage makeImageColor(int width, int height, int defaultColor) {
    NativeImage image = new NativeImage(width, height, false);
    int colAbgr = TextureUtils.toAbgr(defaultColor);
    image.fillRect(0, 0, width, height, colAbgr);
    return image;
  }
  
  private static NativeImage loadNSMapFile(ResourceManager manager, ResourceLocation location, int width, int height) {
    if (location == null)
      return null; 
    try {
      Resource res = manager.getResourceOrThrow(location);
      NativeImage image = NativeImage.read(res.open());
      if (image == null)
        return null; 
      if (image.getWidth() != width || image.getHeight() != height) {
        image.close();
        return null;
      } 
      return image;
    } catch (IOException ex) {
      return null;
    } 
  }
  
  public static void loadSimpleTextureNS(int textureID, NativeImage nativeImage, boolean blur, boolean clamp, ResourceManager resourceManager, ResourceLocation location, MultiTexID multiTex) {
    int width = nativeImage.getWidth();
    int height = nativeImage.getHeight();
    ResourceLocation locNormal = getNSMapLocation(location, "n");
    NativeImage imageNormal = loadNSMapImage(resourceManager, locNormal, width, height, -8421377);
    TextureUtil.prepareImage(multiTex.norm, 0, width, height);
    imageNormal.upload(0, 0, 0, 0, 0, width, height, blur, clamp, false, true);
    ResourceLocation locSpecular = getNSMapLocation(location, "s");
    NativeImage imageSpecular = loadNSMapImage(resourceManager, locSpecular, width, height, 0);
    TextureUtil.prepareImage(multiTex.spec, 0, width, height);
    imageSpecular.upload(0, 0, 0, 0, 0, width, height, blur, clamp, false, true);
    GlStateManager._bindTexture(multiTex.base);
  }
  
  public static void mergeImage(int[] aint, int dstoff, int srcoff, int size) {}
  
  public static int blendColor(int color1, int color2, int factor1) {
    int factor2 = 255 - factor1;
    return ((color1 >>> 24 & 0xFF) * factor1 + (color2 >>> 24 & 0xFF) * factor2) / 255 << 24 | ((color1 >>> 16 & 0xFF) * factor1 + (color2 >>> 16 & 0xFF) * factor2) / 255 << 16 | ((color1 >>> 8 & 0xFF) * factor1 + (color2 >>> 8 & 0xFF) * factor2) / 255 << 8 | ((color1 >>> 0 & 0xFF) * factor1 + (color2 >>> 0 & 0xFF) * factor2) / 255 << 0;
  }
  
  public static void updateTextureMinMagFilter() {
    TextureManager texman = Minecraft.getInstance().getTextureManager();
    AbstractTexture texObj = texman.getTexture(TextureAtlas.LOCATION_BLOCKS);
    if (texObj != null) {
      MultiTexID multiTex = texObj.getMultiTexID();
      GlStateManager._bindTexture(multiTex.base);
      GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilB]);
      GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilB]);
      GlStateManager._bindTexture(multiTex.norm);
      GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilN]);
      GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilN]);
      GlStateManager._bindTexture(multiTex.spec);
      GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilS]);
      GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilS]);
      GlStateManager._bindTexture(0);
    } 
  }
  
  public static int[][] getFrameTexData(int[][] src, int width, int height, int frameIndex) {
    int numLevel = src.length;
    int[][] dst = new int[numLevel][];
    for (int level = 0; level < numLevel; level++) {
      int[] sr1 = src[level];
      if (sr1 != null) {
        int frameSize = (width >> level) * (height >> level);
        int[] ds1 = new int[frameSize * 3];
        dst[level] = ds1;
        int srcSize = sr1.length / 3;
        int srcPos = frameSize * frameIndex;
        int dstPos = 0;
        System.arraycopy(sr1, srcPos, ds1, dstPos, frameSize);
        srcPos += srcSize;
        dstPos += frameSize;
        System.arraycopy(sr1, srcPos, ds1, dstPos, frameSize);
        srcPos += srcSize;
        dstPos += frameSize;
        System.arraycopy(sr1, srcPos, ds1, dstPos, frameSize);
      } 
    } 
    return dst;
  }
  
  public static int[][] prepareAF(TextureAtlasSprite tas, int[][] src, int width, int height) {
    boolean skip = true;
    return src;
  }
  
  public static void fixTransparentColor(TextureAtlasSprite tas, int[] aint) {}
}

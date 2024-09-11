package notch.net.optifine;

import akr;
import dtc;
import gfw;
import gqk;
import gql;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import jd;
import net.optifine.Config;
import net.optifine.ConnectedTextures;
import net.optifine.NaturalProperties;
import net.optifine.util.TextureUtils;

public class NaturalTextures {
  private static NaturalProperties[] propertiesByIndex = new NaturalProperties[0];
  
  public static void update() {
    propertiesByIndex = new NaturalProperties[0];
    if (!Config.isNaturalTextures())
      return; 
    String fileName = "optifine/natural.properties";
    try {
      akr loc = new akr(fileName);
      if (!Config.hasResource(loc)) {
        Config.dbg("NaturalTextures: configuration \"" + fileName + "\" not found");
        return;
      } 
      boolean defaultConfig = Config.isFromDefaultResourcePack(loc);
      InputStream in = Config.getResourceStream(loc);
      ArrayList<NaturalProperties> list = new ArrayList(256);
      String configStr = Config.readInputStream(in);
      in.close();
      String[] configLines = Config.tokenize(configStr, "\n\r");
      if (defaultConfig) {
        Config.dbg("Natural Textures: Parsing default configuration \"" + fileName + "\"");
        Config.dbg("Natural Textures: Valid only for textures from default resource pack");
      } else {
        Config.dbg("Natural Textures: Parsing configuration \"" + fileName + "\"");
      } 
      int countTextures = 0;
      gqk textureMapBlocks = TextureUtils.getTextureMapBlocks();
      for (int i = 0; i < configLines.length; i++) {
        String line = configLines[i].trim();
        if (!line.startsWith("#")) {
          String[] strs = Config.tokenize(line, "=");
          if (strs.length != 2) {
            Config.warn("Natural Textures: Invalid \"" + fileName + "\" line: " + line);
          } else {
            String key = strs[0].trim();
            String type = strs[1].trim();
            String texName = key;
            gql ts = textureMapBlocks.getUploadedSprite("minecraft:block/" + texName);
            if (ts == null) {
              Config.warn("Natural Textures: Texture not found: \"" + fileName + "\" line: " + line);
            } else {
              int tileNum = ts.getIndexInMap();
              if (tileNum < 0) {
                Config.warn("Natural Textures: Invalid \"" + fileName + "\" line: " + line);
              } else {
                if (defaultConfig)
                  if (!Config.isFromDefaultResourcePack(new akr("textures/block/" + texName + ".png")))
                    return;  
                NaturalProperties props = new NaturalProperties(type);
                if (props.isValid()) {
                  while (list.size() <= tileNum)
                    list.add(null); 
                  list.set(tileNum, props);
                  countTextures++;
                } 
              } 
            } 
          } 
        } 
      } 
      propertiesByIndex = list.<NaturalProperties>toArray(new NaturalProperties[list.size()]);
      if (countTextures > 0)
        Config.dbg("NaturalTextures: " + countTextures); 
    } catch (FileNotFoundException e) {
      Config.warn("NaturalTextures: configuration \"" + fileName + "\" not found");
      return;
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public static gfw getNaturalTexture(dtc stateIn, jd blockPosIn, gfw quad) {
    if (stateIn.b() instanceof dox)
      return quad; 
    gql sprite = quad.a();
    if (sprite == null)
      return quad; 
    NaturalProperties nps = getNaturalProperties(sprite);
    if (nps == null)
      return quad; 
    int side = ConnectedTextures.getSide(quad.e());
    int rand = Config.getRandom(blockPosIn, side);
    int rotate = 0;
    boolean flipU = false;
    if (nps.rotation > 1)
      rotate = rand & 0x3; 
    if (nps.rotation == 2)
      rotate = rotate / 2 * 2; 
    if (nps.flip)
      flipU = ((rand & 0x4) != 0); 
    return nps.getQuad(quad, rotate, flipU);
  }
  
  public static NaturalProperties getNaturalProperties(gql icon) {
    if (!(icon instanceof gql))
      return null; 
    gql ts = icon;
    int tileNum = ts.getIndexInMap();
    if (tileNum < 0 || tileNum >= propertiesByIndex.length)
      return null; 
    NaturalProperties props = propertiesByIndex[tileNum];
    return props;
  }
}

package notch.net.optifine;

import dcc;
import dtc;
import gfh;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import jd;
import net.optifine.Config;
import net.optifine.config.ConnectedParser;
import net.optifine.config.MatchBlock;
import net.optifine.render.RenderTypes;
import net.optifine.shaders.BlockAliases;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;

public class CustomBlockLayers {
  private static gfh[] renderLayers = null;
  
  public static boolean active = false;
  
  public static gfh getRenderLayer(dcc worldReader, dtc blockState, jd blockPos) {
    if (renderLayers == null)
      return null; 
    if (blockState.i(worldReader, blockPos))
      return null; 
    int id = blockState.getBlockId();
    if (id <= 0 || id >= renderLayers.length)
      return null; 
    return renderLayers[id];
  }
  
  public static void update() {
    renderLayers = null;
    active = false;
    List<gfh> list = new ArrayList<>();
    String pathProps = "optifine/block.properties";
    Properties props = ResUtils.readProperties(pathProps, "CustomBlockLayers");
    if (props != null)
      readLayers(pathProps, props, list); 
    if (Config.isShaders()) {
      PropertiesOrdered propsShaders = BlockAliases.getBlockLayerPropertes();
      if (propsShaders != null) {
        String pathPropsShaders = "shaders/block.properties";
        readLayers(pathPropsShaders, (Properties)propsShaders, list);
      } 
    } 
    if (list.isEmpty())
      return; 
    renderLayers = list.<gfh>toArray(new gfh[list.size()]);
    active = true;
  }
  
  private static void readLayers(String pathProps, Properties props, List<gfh> list) {
    Config.dbg("CustomBlockLayers: " + pathProps);
    readLayer("solid", RenderTypes.SOLID, props, list);
    readLayer("cutout", RenderTypes.CUTOUT, props, list);
    readLayer("cutout_mipped", RenderTypes.CUTOUT_MIPPED, props, list);
    readLayer("translucent", RenderTypes.TRANSLUCENT, props, list);
  }
  
  private static void readLayer(String name, gfh layer, Properties props, List<gfh> listLayers) {
    String key = "layer." + name;
    String val = props.getProperty(key);
    if (val == null)
      return; 
    ConnectedParser cp = new ConnectedParser("CustomBlockLayers");
    MatchBlock[] mbs = cp.parseMatchBlocks(val);
    if (mbs == null)
      return; 
    for (int i = 0; i < mbs.length; i++) {
      MatchBlock mb = mbs[i];
      int blockId = mb.getBlockId();
      if (blockId > 0) {
        while (listLayers.size() < blockId + 1)
          listLayers.add(null); 
        if (listLayers.get(blockId) != null)
          Config.warn("CustomBlockLayers: Block layer is already set, block: " + blockId + ", layer: " + name); 
        listLayers.set(blockId, layer);
      } 
    } 
  }
  
  public static boolean isActive() {
    return active;
  }
}

package srg.net.optifine.shaders;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.optifine.Config;
import net.optifine.ConnectedProperties;
import net.optifine.config.ConnectedParser;
import net.optifine.config.MatchBlock;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.shaders.BlockAlias;
import net.optifine.shaders.IShaderPack;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.config.MacroProcessor;
import net.optifine.util.BlockUtils;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.StrUtils;

public class BlockAliases {
  private static BlockAlias[][] blockAliases = null;
  
  private static boolean hasAliasMetadata = false;
  
  private static PropertiesOrdered blockLayerPropertes = null;
  
  private static boolean updateOnResourcesReloaded;
  
  private static List<List<BlockAlias>> legacyAliases;
  
  public static int getAliasBlockId(BlockState blockState) {
    int blockId = blockState.getBlockId();
    int metadata = blockState.getMetadata();
    BlockAlias alias = getBlockAlias(blockId, metadata);
    if (alias != null)
      return alias.getAliasBlockId(); 
    return -1;
  }
  
  public static boolean hasAliasMetadata() {
    return hasAliasMetadata;
  }
  
  public static int getAliasMetadata(BlockState blockState) {
    if (!hasAliasMetadata)
      return 0; 
    int blockId = blockState.getBlockId();
    int metadata = blockState.getMetadata();
    BlockAlias alias = getBlockAlias(blockId, metadata);
    if (alias != null)
      return alias.getAliasMetadata(); 
    return 0;
  }
  
  public static BlockAlias getBlockAlias(int blockId, int metadata) {
    if (blockAliases == null)
      return null; 
    if (blockId < 0 || blockId >= blockAliases.length)
      return null; 
    BlockAlias[] aliases = blockAliases[blockId];
    if (aliases == null)
      return null; 
    for (int i = 0; i < aliases.length; i++) {
      BlockAlias ba = aliases[i];
      if (ba.matches(blockId, metadata))
        return ba; 
    } 
    return null;
  }
  
  public static BlockAlias[] getBlockAliases(int blockId) {
    if (blockAliases == null)
      return null; 
    if (blockId < 0 || blockId >= blockAliases.length)
      return null; 
    BlockAlias[] aliases = blockAliases[blockId];
    return aliases;
  }
  
  public static void resourcesReloaded() {
    if (!updateOnResourcesReloaded)
      return; 
    updateOnResourcesReloaded = false;
    update(Shaders.getShaderPack());
  }
  
  public static void update(IShaderPack shaderPack) {
    reset();
    if (shaderPack == null)
      return; 
    if (shaderPack instanceof net.optifine.shaders.ShaderPackNone)
      return; 
    if (Reflector.ModList.exists() && Minecraft.getInstance().getResourceManager() == null) {
      Config.dbg("[Shaders] Delayed loading of block mappings after resources are loaded");
      updateOnResourcesReloaded = true;
      return;
    } 
    List<List<BlockAlias>> listBlockAliases = new ArrayList<>();
    String path = "/shaders/block.properties";
    InputStream in = shaderPack.getResourceAsStream(path);
    if (in != null)
      loadBlockAliases(in, path, listBlockAliases); 
    loadModBlockAliases(listBlockAliases);
    if (listBlockAliases.size() <= 0) {
      listBlockAliases = getLegacyAliases();
      hasAliasMetadata = true;
    } 
    blockAliases = toBlockAliasArrays(listBlockAliases);
  }
  
  private static void loadModBlockAliases(List<List<BlockAlias>> listBlockAliases) {
    String[] modIds = ReflectorForge.getForgeModIds();
    for (int i = 0; i < modIds.length; i++) {
      String modId = modIds[i];
      try {
        ResourceLocation loc = new ResourceLocation(modId, "shaders/block.properties");
        InputStream in = Config.getResourceStream(loc);
        loadBlockAliases(in, loc.toString(), listBlockAliases);
      } catch (IOException iOException) {}
    } 
  }
  
  private static void loadBlockAliases(InputStream in, String path, List<List<BlockAlias>> listBlockAliases) {
    if (in == null)
      return; 
    try {
      in = MacroProcessor.process(in, path, true);
      PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
      propertiesOrdered.load(in);
      in.close();
      Config.dbg("[Shaders] Parsing block mappings: " + path);
      ConnectedParser cp = new ConnectedParser("Shaders");
      Set<Object> keys = propertiesOrdered.keySet();
      for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
        String key = it.next();
        String val = propertiesOrdered.getProperty(key);
        if (key.startsWith("layer.")) {
          if (blockLayerPropertes == null)
            blockLayerPropertes = new PropertiesOrdered(); 
          blockLayerPropertes.put(key, val);
          continue;
        } 
        String prefix = "block.";
        if (!key.startsWith(prefix)) {
          Config.warn("[Shaders] Invalid block ID: " + key);
          continue;
        } 
        String blockIdStr = StrUtils.removePrefix(key, prefix);
        int blockId = Config.parseInt(blockIdStr, -1);
        if (blockId < 0) {
          Config.warn("[Shaders] Invalid block ID: " + key);
          continue;
        } 
        MatchBlock[] matchBlocks = cp.parseMatchBlocks(val);
        if (matchBlocks == null || matchBlocks.length < 1) {
          Config.warn("[Shaders] Invalid block ID mapping: " + key + "=" + val);
          continue;
        } 
        BlockAlias ba = new BlockAlias(blockId, matchBlocks);
        addToList(listBlockAliases, ba);
      } 
    } catch (IOException e) {
      Config.warn("[Shaders] Error reading: " + path);
    } 
  }
  
  private static void addToList(List<List<BlockAlias>> blocksAliases, BlockAlias ba) {
    int[] blockIds = ba.getMatchBlockIds();
    for (int i = 0; i < blockIds.length; i++) {
      int blockId = blockIds[i];
      while (blockId >= blocksAliases.size())
        blocksAliases.add((List<BlockAlias>)null); 
      List<BlockAlias> blockAliases = blocksAliases.get(blockId);
      if (blockAliases == null) {
        blockAliases = new ArrayList<>();
        blocksAliases.set(blockId, blockAliases);
      } 
      BlockAlias baBlock = new BlockAlias(ba.getAliasBlockId(), ba.getMatchBlocks(blockId));
      blockAliases.add(baBlock);
    } 
  }
  
  private static BlockAlias[][] toBlockAliasArrays(List<List<BlockAlias>> listBlocksAliases) {
    BlockAlias[][] bas = new BlockAlias[listBlocksAliases.size()][];
    for (int i = 0; i < bas.length; i++) {
      List<BlockAlias> listBlockAliases = listBlocksAliases.get(i);
      if (listBlockAliases != null)
        bas[i] = listBlockAliases.<BlockAlias>toArray(new BlockAlias[listBlockAliases.size()]); 
    } 
    return bas;
  }
  
  private static List<List<BlockAlias>> getLegacyAliases() {
    if (legacyAliases == null)
      legacyAliases = makeLegacyAliases(); 
    return legacyAliases;
  }
  
  private static List<List<BlockAlias>> makeLegacyAliases() {
    try {
      String path = "flattening_ids.txt";
      Config.dbg("Using legacy block aliases: " + path);
      List<List<BlockAlias>> listAliases = new ArrayList<>();
      List<String> listLines = new ArrayList<>();
      int countAliases = 0;
      InputStream in = Config.getOptiFineResourceStream("/" + path);
      if (in == null)
        return listAliases; 
      String[] lines = Config.readLines(in);
      for (int i = 0; i < lines.length; i++) {
        int lineNum = i + 1;
        String line = lines[i];
        if (line.trim().length() > 0) {
          listLines.add(line);
          if (!line.startsWith("#"))
            if (line.startsWith("alias")) {
              String[] parts = Config.tokenize(line, " ");
              String blockAliasStr = parts[1];
              String blockStr = parts[2];
              String prefix = "{Name:'" + blockStr + "'";
              List<String> blockLines = (List<String>)listLines.stream().filter(s -> s.startsWith(prefix)).collect(Collectors.toList());
              if (blockLines.size() <= 0) {
                Config.warn("Block not processed: " + line);
              } else {
                for (String lineBlock : blockLines) {
                  String prefixNew = "{Name:'" + blockAliasStr + "'";
                  String lineNew = lineBlock.replace(prefix, prefixNew);
                  listLines.add(lineNew);
                  addLegacyAlias(lineNew, lineNum, listAliases);
                  countAliases++;
                } 
              } 
            } else {
              addLegacyAlias(line, lineNum, listAliases);
              countAliases++;
            }  
        } 
      } 
      Config.dbg("Legacy block aliases: " + countAliases);
      return listAliases;
    } catch (IOException e) {
      Config.warn("Error loading legacy block aliases: " + e.getClass().getName() + ": " + e.getMessage());
      return new ArrayList<>();
    } 
  }
  
  private static void addLegacyAlias(String line, int lineNum, List<List<BlockAlias>> listAliases) {
    String[] parts = Config.tokenize(line, " ");
    if (parts.length != 4) {
      Config.warn("Invalid flattening line: " + line);
      return;
    } 
    String partNew = parts[0];
    String partOld = parts[1];
    int blockIdOld = Config.parseInt(parts[2], -2147483648);
    int metadataOld = Config.parseInt(parts[3], -2147483648);
    if (blockIdOld < 0 || metadataOld < 0) {
      Config.warn("Invalid blockID or metadata (" + lineNum + "): " + blockIdOld + ":" + metadataOld);
      return;
    } 
    try {
      JsonParser jp = new JsonParser();
      JsonObject jo = jp.parse(partNew).getAsJsonObject();
      String name = jo.get("Name").getAsString();
      ResourceLocation loc = new ResourceLocation(name);
      Block block = BlockUtils.getBlock(loc);
      if (block == null) {
        Config.warn("Invalid block name (" + lineNum + "): " + name);
        return;
      } 
      BlockState blockState = block.defaultBlockState();
      Collection<Property<?>> stateProperties = blockState.getProperties();
      Map<Property, Comparable> mapProperties = new LinkedHashMap<>();
      JsonObject properties = (JsonObject)jo.get("Properties");
      if (properties != null) {
        Set<Map.Entry<String, JsonElement>> entries = properties.entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
          String key = entry.getKey();
          String value = ((JsonElement)entry.getValue()).getAsString();
          Property prop = ConnectedProperties.getProperty(key, stateProperties);
          if (prop == null) {
            Config.warn("Invalid property (" + lineNum + "): " + key);
            continue;
          } 
          Comparable propVal = ConnectedParser.parsePropertyValue(prop, value);
          if (propVal == null) {
            Config.warn("Invalid property value (" + lineNum + "): " + value);
            continue;
          } 
          mapProperties.put(prop, propVal);
        } 
      } 
      int blockId = blockState.getBlockId();
      while (listAliases.size() <= blockId)
        listAliases.add((List<BlockAlias>)null); 
      List<BlockAlias> las = listAliases.get(blockId);
      if (las == null) {
        las = new ArrayList<>(BlockUtils.getMetadataCount(block));
        listAliases.set(blockId, las);
      } 
      MatchBlock mb = getMatchBlock(blockState.getBlock(), blockState.getBlockId(), mapProperties);
      addBlockAlias(las, blockIdOld, metadataOld, mb);
    } catch (Exception e) {
      Config.warn("Error parsing: " + line);
    } 
  }
  
  private static void addBlockAlias(List<BlockAlias> listBlockAliases, int aliasBlockId, int aliasMetadata, MatchBlock matchBlock) {
    for (BlockAlias blockAlias : listBlockAliases) {
      if (blockAlias.getAliasBlockId() != aliasBlockId)
        continue; 
      if (blockAlias.getAliasMetadata() != aliasMetadata)
        continue; 
      MatchBlock[] mbs = blockAlias.getMatchBlocks();
      for (int i = 0; i < mbs.length; ) {
        MatchBlock mb = mbs[i];
        if (mb.getBlockId() != matchBlock.getBlockId()) {
          i++;
          continue;
        } 
        mb.addMetadatas(matchBlock.getMetadatas());
        return;
      } 
    } 
    BlockAlias ba = new BlockAlias(aliasBlockId, aliasMetadata, new MatchBlock[] { matchBlock });
    listBlockAliases.add(ba);
  }
  
  private static MatchBlock getMatchBlock(Block block, int blockId, Map<Property, Comparable> mapProperties) {
    List<BlockState> matchingStates = new ArrayList<>();
    Collection<Property> props = mapProperties.keySet();
    List<BlockState> states = BlockUtils.getBlockStates(block);
    for (BlockState bs : states) {
      boolean match = true;
      for (Property prop : props) {
        if (!bs.hasProperty(prop)) {
          match = false;
          break;
        } 
        Comparable val1 = mapProperties.get(prop);
        Comparable val2 = bs.getValue(prop);
        if (!val1.equals(val2)) {
          match = false;
          break;
        } 
      } 
      if (match)
        matchingStates.add(bs); 
    } 
    Set<Integer> setMetadatas = new LinkedHashSet<>();
    for (BlockState bs : matchingStates)
      setMetadatas.add(Integer.valueOf(bs.getMetadata())); 
    Integer[] metadatas = setMetadatas.<Integer>toArray(new Integer[setMetadatas.size()]);
    int[] mds = Config.toPrimitive(metadatas);
    MatchBlock mb = new MatchBlock(blockId, mds);
    return mb;
  }
  
  private static void checkLegacyAliases() {
    Set<ResourceLocation> locs = BuiltInRegistries.BLOCK.keySet();
    for (ResourceLocation loc : locs) {
      Block block = (Block)BuiltInRegistries.BLOCK.get(loc);
      int blockId = block.defaultBlockState().getBlockId();
      BlockAlias[] bas = getBlockAliases(blockId);
      if (bas == null) {
        Config.warn("Block has no alias: " + String.valueOf(block));
        continue;
      } 
      List<BlockState> states = BlockUtils.getBlockStates(block);
      for (BlockState state : states) {
        int metadata = state.getMetadata();
        BlockAlias ba = getBlockAlias(blockId, metadata);
        if (ba == null)
          Config.warn("State has no alias: " + String.valueOf(state)); 
      } 
    } 
  }
  
  public static PropertiesOrdered getBlockLayerPropertes() {
    return blockLayerPropertes;
  }
  
  public static void reset() {
    blockAliases = null;
    hasAliasMetadata = false;
    blockLayerPropertes = null;
  }
  
  public static int getRenderType(BlockState blockState) {
    if (hasAliasMetadata) {
      Block block = blockState.getBlock();
      if (block instanceof net.minecraft.world.level.block.LiquidBlock)
        return 1; 
      RenderShape brt = blockState.getRenderShape();
      if (brt == RenderShape.ENTITYBLOCK_ANIMATED || brt == RenderShape.MODEL)
        return brt.ordinal() + 1; 
      return brt.ordinal();
    } 
    return blockState.getRenderShape().ordinal();
  }
}

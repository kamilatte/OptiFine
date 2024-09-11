package notch.net.optifine.player;

import akr;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import faj;
import fgo;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import net.optifine.Config;
import net.optifine.http.HttpPipeline;
import net.optifine.http.HttpUtils;
import net.optifine.player.PlayerConfiguration;
import net.optifine.player.PlayerItemModel;
import net.optifine.player.PlayerItemParser;
import net.optifine.util.Json;

public class PlayerConfigurationParser {
  private String player = null;
  
  public static final String CONFIG_ITEMS = "items";
  
  public static final String ITEM_TYPE = "type";
  
  public static final String ITEM_ACTIVE = "active";
  
  public PlayerConfigurationParser(String player) {
    this.player = player;
  }
  
  public PlayerConfiguration parsePlayerConfiguration(JsonElement je) {
    if (je == null)
      throw new JsonParseException("JSON object is null, player: " + this.player); 
    JsonObject jo = (JsonObject)je;
    PlayerConfiguration pc = new PlayerConfiguration();
    JsonArray items = (JsonArray)jo.get("items");
    if (items != null)
      for (int i = 0; i < items.size(); i++) {
        JsonObject item = (JsonObject)items.get(i);
        boolean active = Json.getBoolean(item, "active", true);
        if (!active)
          continue; 
        String type = Json.getString(item, "type");
        if (type == null) {
          Config.warn("Item type is null, player: " + this.player);
          continue;
        } 
        String modelPath = Json.getString(item, "model");
        if (modelPath == null)
          modelPath = "items/" + type + "/model.cfg"; 
        PlayerItemModel model = downloadModel(modelPath);
        if (model == null)
          continue; 
        if (!model.isUsePlayerTexture()) {
          String texturePath = Json.getString(item, "texture");
          if (texturePath == null)
            texturePath = "items/" + type + "/users/" + this.player + ".png"; 
          faj image = downloadTextureImage(texturePath);
          if (image == null)
            continue; 
          model.setTextureImage(image);
          akr loc = new akr("optifine.net", texturePath);
          model.setTextureLocation(loc);
        } 
        pc.addPlayerItemModel(model);
        continue;
      }  
    return pc;
  }
  
  private faj downloadTextureImage(String texturePath) {
    String textureUrl = HttpUtils.getPlayerItemsUrl() + "/" + HttpUtils.getPlayerItemsUrl();
    try {
      byte[] body = HttpPipeline.get(textureUrl, fgo.Q().Z());
      faj image = faj.a(new ByteArrayInputStream(body));
      return image;
    } catch (IOException e) {
      Config.warn("Error loading item texture " + texturePath + ": " + e.getClass().getName() + ": " + e.getMessage());
      return null;
    } 
  }
  
  private PlayerItemModel downloadModel(String modelPath) {
    String modelUrl = HttpUtils.getPlayerItemsUrl() + "/" + HttpUtils.getPlayerItemsUrl();
    try {
      byte[] bytes = HttpPipeline.get(modelUrl, fgo.Q().Z());
      String jsonStr = new String(bytes, "ASCII");
      JsonParser jp = new JsonParser();
      JsonObject jo = (JsonObject)jp.parse(jsonStr);
      PlayerItemModel pim = PlayerItemParser.parseItemModel(jo);
      return pim;
    } catch (Exception e) {
      Config.warn("Error loading item model " + modelPath + ": " + e.getClass().getName() + ": " + e.getMessage());
      return null;
    } 
  }
}

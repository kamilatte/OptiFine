package notch.net.optifine.reflect;

import bsr;
import dbz;
import dtc;
import ffy;
import fgo;
import fim;
import fod;
import fof;
import gex;
import gfw;
import gie;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jd;
import ji;
import net.minecraftforge.client.model.ForgeFaceData;
import net.optifine.Log;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorClass;
import net.optifine.reflect.ReflectorConstructor;
import net.optifine.reflect.ReflectorField;
import net.optifine.util.StrUtils;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import wz;

public class ReflectorForge {
  public static final boolean FORGE_ENTITY_CAN_UPDATE = Reflector.IForgeEntity_canUpdate.exists();
  
  public static void putLaunchBlackboard(String key, Object value) {
    Map<String, Object> blackboard = (Map)Reflector.getFieldValue(Reflector.Launch_blackboard);
    if (blackboard == null)
      return; 
    blackboard.put(key, value);
  }
  
  public static InputStream getOptiFineResourceStream(String path) {
    if (!Reflector.OptiFineResourceLocator.exists())
      return null; 
    path = StrUtils.removePrefix(path, "/");
    InputStream in = (InputStream)Reflector.call(Reflector.OptiFineResourceLocator_getOptiFineResourceStream, new Object[] { path });
    return in;
  }
  
  public static ReflectorClass getReflectorClassOptiFineResourceLocator() {
    String className = "optifine.OptiFineResourceLocator";
    Object ofrlClass = System.getProperties().get(className + ".class");
    if (ofrlClass instanceof Class) {
      Class cls = (Class)ofrlClass;
      return new ReflectorClass(cls);
    } 
    return new ReflectorClass(className);
  }
  
  public static boolean calculateFaceWithoutAO(dbz getter, dtc state, jd pos, gfw quad, boolean isFaceCubic, float[] brightness, int[] lightmap) {
    if (quad.hasAmbientOcclusion())
      return false; 
    jd lightmapPos = isFaceCubic ? pos.a(quad.e()) : pos;
    brightness[3] = getter.a(quad.e(), quad.f());
    brightness[2] = getter.a(quad.e(), quad.f());
    brightness[1] = getter.a(quad.e(), quad.f());
    brightness[0] = getter.a(quad.e(), quad.f());
    lightmap[3] = gex.a(getter, state, lightmapPos);
    lightmap[2] = gex.a(getter, state, lightmapPos);
    lightmap[1] = gex.a(getter, state, lightmapPos);
    lightmap[0] = gex.a(getter, state, lightmapPos);
    return true;
  }
  
  public static String[] getForgeModIds() {
    if (!Reflector.ModList.exists())
      return new String[0]; 
    Object modList = Reflector.ModList_get.call(new Object[0]);
    List listMods = (List)Reflector.getFieldValue(modList, Reflector.ModList_mods);
    if (listMods == null)
      return new String[0]; 
    List<String> listModIds = new ArrayList<>();
    for (Iterator it = listMods.iterator(); it.hasNext(); ) {
      Object modContainer = it.next();
      if (!Reflector.ModContainer.isInstance(modContainer))
        continue; 
      String modId = Reflector.callString(modContainer, Reflector.ModContainer_getModId, new Object[0]);
      if (modId == null)
        continue; 
      if (modId.equals("minecraft") || modId.equals("forge"))
        continue; 
      listModIds.add(modId);
    } 
    String[] modIds = listModIds.<String>toArray(new String[listModIds.size()]);
    return modIds;
  }
  
  public static fim makeButtonMods(fof guiMainMenu, int yIn, int rowHeightIn) {
    if (!Reflector.ModListScreen_Constructor.exists())
      return null; 
    fim modButton = fim.a((wz)wz.c("fml.menu.mods"), button -> {
          fod modListScreen = (fod)Reflector.ModListScreen_Constructor.newInstance(new Object[] { guiMainMenu });
          fgo.Q().a(modListScreen);
        }).a(guiMainMenu.m / 2 - 100, yIn + rowHeightIn * 2).b(98, 20).a();
    return modButton;
  }
  
  public static void setForgeLightPipelineEnabled(boolean value) {
    if (Reflector.ForgeConfig_Client_forgeLightPipelineEnabled.exists())
      setConfigClientBoolean(Reflector.ForgeConfig_Client_forgeLightPipelineEnabled, value); 
  }
  
  public static boolean getForgeUseCombinedDepthStencilAttachment() {
    if (Reflector.ForgeConfig_Client_useCombinedDepthStencilAttachment.exists())
      return getConfigClientBoolean(Reflector.ForgeConfig_Client_useCombinedDepthStencilAttachment, false); 
    return false;
  }
  
  public static boolean getForgeCalculateAllNormals() {
    if (Reflector.ForgeConfig_Client_calculateAllNormals.exists())
      return getConfigClientBoolean(Reflector.ForgeConfig_Client_calculateAllNormals, false); 
    return false;
  }
  
  public static boolean getConfigClientBoolean(ReflectorField configField, boolean def) {
    if (!configField.exists())
      return def; 
    Object client = Reflector.ForgeConfig_CLIENT.getValue();
    if (client == null)
      return def; 
    Object configValue = Reflector.getFieldValue(client, configField);
    if (configValue == null)
      return def; 
    boolean value = Reflector.callBoolean(configValue, Reflector.ForgeConfigSpec_ConfigValue_get, new Object[0]);
    return value;
  }
  
  private static void setConfigClientBoolean(ReflectorField clientField, boolean value) {
    if (!clientField.exists())
      return; 
    Object client = Reflector.ForgeConfig_CLIENT.getValue();
    if (client == null)
      return; 
    Object configValue = Reflector.getFieldValue(client, clientField);
    if (configValue == null)
      return; 
    Object object1 = new Object(value);
    Reflector.setFieldValue(configValue, Reflector.ForgeConfigSpec_ConfigValue_defaultSupplier, object1);
    Object spec = Reflector.getFieldValue(configValue, Reflector.ForgeConfigSpec_ConfigValue_spec);
    if (spec != null)
      Reflector.setFieldValue(spec, Reflector.ForgeConfigSpec_childConfig, null); 
    Log.dbg("Set ForgeConfig.CLIENT." + clientField.getTargetField().getName() + "=" + value);
  }
  
  public static boolean canUpdate(bsr entity) {
    if (FORGE_ENTITY_CAN_UPDATE)
      return Reflector.callBoolean(entity, Reflector.IForgeEntity_canUpdate, new Object[0]); 
    return true;
  }
  
  public static void fillNormal(int[] faceData, ji facing, ForgeFaceData data) {
    Vector3f v2;
    boolean calculateNormals = false;
    if (Reflector.ForgeFaceData_calculateNormals.exists())
      calculateNormals = Reflector.callBoolean(data, Reflector.ForgeFaceData_calculateNormals, new Object[0]); 
    if (calculateNormals || getForgeCalculateAllNormals()) {
      Vector3f v1 = getVertexPos(faceData, 3);
      Vector3f t1 = getVertexPos(faceData, 1);
      v2 = getVertexPos(faceData, 2);
      Vector3f t2 = getVertexPos(faceData, 0);
      v1.sub((Vector3fc)t1);
      v2.sub((Vector3fc)t2);
      v2.cross((Vector3fc)v1);
      v2.normalize();
    } else {
      v2 = new Vector3f(facing.q().u(), facing.q().v(), facing.q().w());
    } 
    int x = (byte)Math.round(v2.x() * 127.0F) & 0xFF;
    int y = (byte)Math.round(v2.y() * 127.0F) & 0xFF;
    int z = (byte)Math.round(v2.z() * 127.0F) & 0xFF;
    int normal = x | y << 8 | z << 16;
    int step = faceData.length / 4;
    for (int i = 0; i < 4; i++)
      faceData[i * step + 7] = normal; 
  }
  
  private static Vector3f getVertexPos(int[] data, int vertex) {
    int step = data.length / 4;
    int idx = vertex * step;
    float x = Float.intBitsToFloat(data[idx]);
    float y = Float.intBitsToFloat(data[idx + 1]);
    float z = Float.intBitsToFloat(data[idx + 2]);
    return new Vector3f(x, y, z);
  }
  
  public static void postModLoaderEvent(ReflectorConstructor constr, Object... params) {
    Object event = Reflector.newInstance(constr, params);
    if (event == null)
      return; 
    postModLoaderEvent(event);
  }
  
  public static void postModLoaderEvent(Object event) {
    if (event == null)
      return; 
    Object modLoader = Reflector.ModLoader_get.call(new Object[0]);
    if (modLoader == null)
      return; 
    Reflector.callVoid(modLoader, Reflector.ModLoader_postEvent, new Object[] { event });
  }
  
  public static void dispatchRenderStageS(ReflectorField stageField, gex levelRenderer, Matrix4f matrixView, Matrix4f matrixProjection, int ticks, ffy camera, gie frustum) {
    if (!Reflector.RenderLevelStageEvent_dispatch.exists())
      return; 
    if (!stageField.exists())
      return; 
    Object stage = stageField.getValue();
    Reflector.call(stage, Reflector.RenderLevelStageEvent_dispatch, new Object[] { levelRenderer, matrixView, matrixProjection, Integer.valueOf(ticks), camera, frustum });
  }
}

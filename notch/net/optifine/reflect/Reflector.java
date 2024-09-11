package notch.net.optifine.reflect;

import ajw;
import akq;
import aqy;
import awn;
import bsr;
import cjh;
import cnd;
import com.google.common.collect.ImmutableMap;
import cuq;
import cws;
import dah;
import dcw;
import dcz;
import dpw;
import dqb;
import dqc;
import dtc;
import duy;
import ffy;
import fgm;
import fgo;
import fhq;
import fhx;
import fhz;
import fim;
import flb;
import fod;
import fof;
import fpn;
import fqr;
import ful;
import fum;
import fuo;
import fus;
import fuy;
import fuz;
import fvc;
import fvk;
import fvl;
import fvm;
import fvu;
import fvv;
import fwb;
import fwd;
import fwe;
import fwi;
import fwn;
import fwo;
import fwu;
import fwv;
import fxb;
import fxc;
import fxd;
import fxg;
import fxo;
import fxp;
import fxq;
import fxr;
import fxs;
import fxy;
import fyb;
import fye;
import fyg;
import fyk;
import gdm;
import gex;
import gfh;
import gfs;
import gfw;
import gfy;
import ggf;
import ggu;
import ggw;
import ggx;
import ghf;
import ghg;
import ghh;
import ghi;
import ghj;
import ghk;
import ghm;
import ghn;
import gie;
import gjn;
import gkd;
import gke;
import gkl;
import gli;
import glm;
import glo;
import gmc;
import gmi;
import gmw;
import gna;
import gni;
import gnm;
import gop;
import gpf;
import gst;
import gsx;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import jd;
import ji;
import kr;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.model.ForgeFaceData;
import net.minecraftforge.eventbus.api.Event;
import net.optifine.Log;
import net.optifine.reflect.FieldLocatorTypes;
import net.optifine.reflect.IFieldLocator;
import net.optifine.reflect.IResolvable;
import net.optifine.reflect.ReflectorClass;
import net.optifine.reflect.ReflectorConstructor;
import net.optifine.reflect.ReflectorField;
import net.optifine.reflect.ReflectorFields;
import net.optifine.reflect.ReflectorForge;
import net.optifine.reflect.ReflectorMethod;
import net.optifine.reflect.ReflectorResolver;
import net.optifine.util.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Matrix4f;
import wz;

public class Reflector {
  private static final Logger LOGGER = LogManager.getLogger();
  
  private static boolean logForge = registerResolvable("*** Reflector Forge ***");
  
  public static ReflectorClass BrandingControl = new ReflectorClass("net.minecraftforge.internal.BrandingControl");
  
  public static ReflectorMethod BrandingControl_getBrandings = new ReflectorMethod(BrandingControl, "getBrandings");
  
  public static ReflectorMethod BrandingControl_getBranding = new ReflectorMethod(BrandingControl, "getBranding");
  
  public static ReflectorMethod BrandingControl_forEachLine = new ReflectorMethod(BrandingControl, "forEachLine");
  
  public static ReflectorMethod BrandingControl_forEachAboveCopyrightLine = new ReflectorMethod(BrandingControl, "forEachAboveCopyrightLine");
  
  public static ReflectorClass CapabilityProvider = new ReflectorClass("net.minecraftforge.common.capabilities.CapabilityProvider");
  
  public static ReflectorMethod CapabilityProvider_gatherCapabilities = new ReflectorMethod(CapabilityProvider, "gatherCapabilities", new Class[0]);
  
  public static ReflectorClass ClientModLoader = new ReflectorClass("net.minecraftforge.client.loading.ClientModLoader");
  
  public static ReflectorMethod ClientModLoader_isLoading = new ReflectorMethod(ClientModLoader, "isLoading");
  
  public static ReflectorClass ChunkEvent_Load = new ReflectorClass("net.minecraftforge.event.level.ChunkEvent$Load");
  
  public static ReflectorConstructor ChunkEvent_Load_Constructor = new ReflectorConstructor(ChunkEvent_Load, new Class[] { duy.class, boolean.class });
  
  public static ReflectorClass ChunkEvent_Unload = new ReflectorClass("net.minecraftforge.event.level.ChunkEvent$Unload");
  
  public static ReflectorConstructor ChunkEvent_Unload_Constructor = new ReflectorConstructor(ChunkEvent_Unload, new Class[] { duy.class });
  
  public static ReflectorClass ColorResolverManager = new ReflectorClass("net.minecraftforge.client.ColorResolverManager");
  
  public static ReflectorMethod ColorResolverManager_registerBlockTintCaches = ColorResolverManager.makeMethod("registerBlockTintCaches");
  
  public static ReflectorClass CrashReportAnalyser = new ReflectorClass("net.minecraftforge.logging.CrashReportAnalyser");
  
  public static ReflectorMethod CrashReportAnalyser_appendSuspectedMods = new ReflectorMethod(CrashReportAnalyser, "appendSuspectedMods");
  
  public static ReflectorClass CrashReportExtender = new ReflectorClass("net.minecraftforge.logging.CrashReportExtender");
  
  public static ReflectorMethod CrashReportExtender_extendSystemReport = new ReflectorMethod(CrashReportExtender, "extendSystemReport");
  
  public static ReflectorMethod CrashReportExtender_generateEnhancedStackTraceT = new ReflectorMethod(CrashReportExtender, "generateEnhancedStackTrace", new Class[] { Throwable.class });
  
  public static ReflectorMethod CrashReportExtender_generateEnhancedStackTraceSTE = new ReflectorMethod(CrashReportExtender, "generateEnhancedStackTrace", new Class[] { StackTraceElement[].class });
  
  public static ReflectorClass EntityRenderersEvent_CreateSkullModels = new ReflectorClass("net.minecraftforge.client.event.EntityRenderersEvent$CreateSkullModels");
  
  public static ReflectorConstructor EntityRenderersEvent_CreateSkullModels_Constructor = EntityRenderersEvent_CreateSkullModels.makeConstructor(new Class[] { ImmutableMap.Builder.class, fyg.class });
  
  public static ReflectorClass EventBus = new ReflectorClass("net.minecraftforge.eventbus.api.IEventBus");
  
  public static ReflectorMethod EventBus_post = new ReflectorMethod(EventBus, "post", new Class[] { Event.class });
  
  public static ReflectorClass ForgeModelBlockRenderer = new ReflectorClass("net.minecraftforge.client.model.lighting.ForgeModelBlockRenderer");
  
  public static ReflectorConstructor ForgeModelBlockRenderer_Constructor = new ReflectorConstructor(ForgeModelBlockRenderer, new Class[] { fhq.class });
  
  public static ReflectorClass ForgeBlockModelShapes = new ReflectorClass(gfs.class);
  
  public static ReflectorMethod ForgeBlockModelShapes_getTexture3 = new ReflectorMethod(ForgeBlockModelShapes, "getTexture", new Class[] { dtc.class, dcw.class, jd.class });
  
  public static ReflectorClass ForgeBlockElementFace = new ReflectorClass(gfy.class);
  
  public static ReflectorMethod ForgeBlockElementFace_data = ForgeBlockElementFace.makeMethod("data");
  
  public static ReflectorClass IForgeBlockState = new ReflectorClass("net.minecraftforge.common.extensions.IForgeBlockState");
  
  public static ReflectorMethod IForgeBlockState_getSoundType3 = new ReflectorMethod(IForgeBlockState, "getSoundType", new Class[] { dcz.class, jd.class, bsr.class });
  
  public static ReflectorMethod IForgeBlockState_getStateAtViewpoint = new ReflectorMethod(IForgeBlockState, "getStateAtViewpoint");
  
  public static ReflectorMethod IForgeBlockState_shouldDisplayFluidOverlay = new ReflectorMethod(IForgeBlockState, "shouldDisplayFluidOverlay");
  
  public static ReflectorClass IForgeEntity = new ReflectorClass("net.minecraftforge.common.extensions.IForgeEntity");
  
  public static ReflectorMethod IForgeEntity_canUpdate = new ReflectorMethod(IForgeEntity, "canUpdate", new Class[0]);
  
  public static ReflectorMethod IForgeEntity_getEyeInFluidType = new ReflectorMethod(IForgeEntity, "getEyeInFluidType");
  
  public static ReflectorMethod IForgeEntity_getParts = new ReflectorMethod(IForgeEntity, "getParts");
  
  public static ReflectorMethod IForgeEntity_hasCustomOutlineRendering = new ReflectorMethod(IForgeEntity, "hasCustomOutlineRendering");
  
  public static ReflectorMethod IForgeEntity_isMultipartEntity = new ReflectorMethod(IForgeEntity, "isMultipartEntity");
  
  public static ReflectorMethod IForgeEntity_onAddedToWorld = new ReflectorMethod(IForgeEntity, "onAddedToWorld");
  
  public static ReflectorMethod IForgeEntity_onRemovedFromWorld = new ReflectorMethod(IForgeEntity, "onRemovedFromWorld");
  
  public static ReflectorMethod IForgeEntity_shouldRiderSit = new ReflectorMethod(IForgeEntity, "shouldRiderSit");
  
  public static ReflectorClass ForgeEventFactory = new ReflectorClass("net.minecraftforge.event.ForgeEventFactory");
  
  public static ReflectorMethod ForgeEventFactory_canEntityDespawn = new ReflectorMethod(ForgeEventFactory, "canEntityDespawn");
  
  public static ReflectorMethod ForgeEventFactory_fireChunkTicketLevelUpdated = new ReflectorMethod(ForgeEventFactory, "fireChunkTicketLevelUpdated");
  
  public static ReflectorMethod ForgeEventFactory_getMobGriefingEvent = new ReflectorMethod(ForgeEventFactory, "getMobGriefingEvent");
  
  public static ReflectorMethod ForgeEventFactory_onChunkDataSave = new ReflectorMethod(ForgeEventFactory, "onChunkDataSave");
  
  public static ReflectorMethod ForgeEventFactory_onChunkLoad = new ReflectorMethod(ForgeEventFactory, "onChunkLoad");
  
  public static ReflectorMethod ForgeEventFactory_onChunkUnload = new ReflectorMethod(ForgeEventFactory, "onChunkUnload");
  
  public static ReflectorMethod ForgeEventFactory_onDifficultyChange = new ReflectorMethod(ForgeEventFactory, "onDifficultyChange");
  
  public static ReflectorMethod ForgeEventFactory_onEntityJoinLevel = new ReflectorMethod(ForgeEventFactory, "onEntityJoinLevel", new Class[] { bsr.class, dcw.class });
  
  public static ReflectorMethod ForgeEventFactory_onEntityLeaveLevel = new ReflectorMethod(ForgeEventFactory, "onEntityLeaveLevel");
  
  public static ReflectorMethod ForgeEventFactory_onLevelLoad = new ReflectorMethod(ForgeEventFactory, "onLevelLoad");
  
  public static ReflectorMethod ForgeEventFactory_onLivingChangeTargetMob = new ReflectorMethod(ForgeEventFactory, "onLivingChangeTargetMob");
  
  public static ReflectorMethod ForgeEventFactory_onPlaySoundAtEntity = new ReflectorMethod(ForgeEventFactory, "onPlaySoundAtEntity");
  
  public static ReflectorMethod ForgeEventFactory_onPlaySoundAtPosition = new ReflectorMethod(ForgeEventFactory, "onPlaySoundAtPosition");
  
  public static ReflectorClass ForgeEventFactoryClient = new ReflectorClass("net.minecraftforge.client.event.ForgeEventFactoryClient");
  
  public static ReflectorMethod ForgeEventFactoryClient_fireComputeCameraAngles = new ReflectorMethod(ForgeEventFactoryClient, "fireComputeCameraAngles");
  
  public static ReflectorMethod ForgeEventFactoryClient_fireComputeFov = new ReflectorMethod(ForgeEventFactoryClient, "fireComputeFov");
  
  public static ReflectorMethod ForgeEventFactoryClient_fireRenderNameTagEvent = new ReflectorMethod(ForgeEventFactoryClient, "fireRenderNameTagEvent");
  
  public static ReflectorMethod ForgeEventFactoryClient_onGatherLayers = new ReflectorMethod(ForgeEventFactoryClient, "onGatherLayers");
  
  public static ReflectorMethod ForgeEventFactoryClient_onRegisterShaders = new ReflectorMethod(ForgeEventFactoryClient, "onRegisterShaders");
  
  public static ReflectorMethod ForgeEventFactoryClient_onRenderItemInFrame = new ReflectorMethod(ForgeEventFactoryClient, "onRenderItemInFrame");
  
  public static ReflectorMethod ForgeEventFactoryClient_onRenderLivingPre = new ReflectorMethod(ForgeEventFactoryClient, "onRenderLivingPre");
  
  public static ReflectorMethod ForgeEventFactoryClient_onRenderLivingPost = new ReflectorMethod(ForgeEventFactoryClient, "onRenderLivingPost");
  
  public static ReflectorMethod ForgeEventFactoryClient_onScreenshot = new ReflectorMethod(ForgeEventFactoryClient, "onScreenshot");
  
  public static ReflectorClass ForgeFaceData = new ReflectorClass(ForgeFaceData.class);
  
  public static ReflectorMethod ForgeFaceData_calculateNormals = ForgeFaceData.makeMethod("calculateNormals");
  
  public static ReflectorClass ForgeHooks = new ReflectorClass("net.minecraftforge.common.ForgeHooks");
  
  public static ReflectorMethod ForgeHooks_getDyeColorFromItemStack = new ReflectorMethod(ForgeHooks, "getDyeColorFromItemStack");
  
  public static ReflectorClass ForgeHooksClient = new ReflectorClass("net.minecraftforge.client.ForgeHooksClient");
  
  public static ReflectorMethod ForgeHooksClient_onCustomizeBossEventProgress = new ReflectorMethod(ForgeHooksClient, "onCustomizeBossEventProgress");
  
  public static ReflectorMethod ForgeHooksClient_onRenderTooltipColor = new ReflectorMethod(ForgeHooksClient, "onRenderTooltipColor");
  
  public static ReflectorMethod ForgeHooksClient_dispatchRenderStageRT = new ReflectorMethod(ForgeHooksClient, "dispatchRenderStage", new Class[] { gfh.class, gex.class, Matrix4f.class, Matrix4f.class, int.class, ffy.class, gie.class });
  
  public static ReflectorMethod ForgeHooksClient_drawScreen = new ReflectorMethod(ForgeHooksClient, "drawScreen");
  
  public static ReflectorMethod ForgeHooksClient_fillNormal = new ReflectorMethod(ForgeHooksClient, "fillNormal", new Class[] { int[].class, ji.class });
  
  public static ReflectorMethod ForgeHooksClient_gatherTooltipComponents6 = new ReflectorMethod(ForgeHooksClient, "gatherTooltipComponents", new Class[] { cuq.class, List.class, int.class, int.class, int.class, fhx.class });
  
  public static ReflectorMethod ForgeHooksClient_gatherTooltipComponents7 = new ReflectorMethod(ForgeHooksClient, "gatherTooltipComponents", new Class[] { cuq.class, List.class, Optional.class, int.class, int.class, int.class, fhx.class });
  
  public static ReflectorMethod ForgeHooksClient_gatherTooltipComponentsFromElements = new ReflectorMethod(ForgeHooksClient, "gatherTooltipComponentsFromElements");
  
  public static ReflectorMethod ForgeHooksClient_onKeyInput = new ReflectorMethod(ForgeHooksClient, "onKeyInput");
  
  public static ReflectorMethod ForgeHooksClient_getFogColor = new ReflectorMethod(ForgeHooksClient, "getFogColor");
  
  public static ReflectorMethod ForgeHooksClient_getArmorModel = new ReflectorMethod(ForgeHooksClient, "getArmorModel");
  
  public static ReflectorMethod ForgeHooksClient_getArmorTexture = new ReflectorMethod(ForgeHooksClient, "getArmorTexture");
  
  public static ReflectorMethod ForgeHooksClient_getFluidSprites = new ReflectorMethod(ForgeHooksClient, "getFluidSprites");
  
  public static ReflectorMethod ForgeHooksClient_getFieldOfViewModifier = new ReflectorMethod(ForgeHooksClient, "getFieldOfViewModifier");
  
  public static ReflectorMethod ForgeHooksClient_getGuiFarPlane = new ReflectorMethod(ForgeHooksClient, "getGuiFarPlane");
  
  public static ReflectorMethod ForgeHooksClient_getShaderImportLocation = new ReflectorMethod(ForgeHooksClient, "getShaderImportLocation");
  
  public static ReflectorMethod ForgeHooksClient_isNameplateInRenderDistance = new ReflectorMethod(ForgeHooksClient, "isNameplateInRenderDistance");
  
  public static ReflectorMethod ForgeHooksClient_loadEntityShader = new ReflectorMethod(ForgeHooksClient, "loadEntityShader");
  
  public static ReflectorMethod ForgeHooksClient_loadTextureAtlasSprite = new ReflectorMethod(ForgeHooksClient, "loadTextureAtlasSprite");
  
  public static ReflectorMethod ForgeHooksClient_loadSpriteContents = new ReflectorMethod(ForgeHooksClient, "loadSpriteContents");
  
  public static ReflectorMethod ForgeHooksClient_makeParticleRenderTypeComparator = new ReflectorMethod(ForgeHooksClient, "makeParticleRenderTypeComparator");
  
  public static ReflectorMethod ForgeHooksClient_onCustomizeChatEvent = new ReflectorMethod(ForgeHooksClient, "onCustomizeChatEvent");
  
  public static ReflectorMethod ForgeHooksClient_onCustomizeDebugEvent = new ReflectorMethod(ForgeHooksClient, "onCustomizeDebugEvent");
  
  public static ReflectorMethod ForgeHooksClient_onDrawHighlight = new ReflectorMethod(ForgeHooksClient, "onDrawHighlight");
  
  public static ReflectorMethod ForgeHooksClient_onFogRender = new ReflectorMethod(ForgeHooksClient, "onFogRender");
  
  public static ReflectorMethod ForgeHooksClient_onRegisterAdditionalModels = new ReflectorMethod(ForgeHooksClient, "onRegisterAdditionalModels");
  
  public static ReflectorMethod ForgeHooksClient_onRenderTooltipPre = new ReflectorMethod(ForgeHooksClient, "onRenderTooltipPre");
  
  public static ReflectorMethod ForgeHooksClient_onScreenCharTyped = new ReflectorMethod(ForgeHooksClient, "onScreenCharTyped");
  
  public static ReflectorMethod ForgeHooksClient_onScreenKeyPressed = new ReflectorMethod(ForgeHooksClient, "onScreenKeyPressed");
  
  public static ReflectorMethod ForgeHooksClient_onScreenKeyReleased = new ReflectorMethod(ForgeHooksClient, "onScreenKeyReleased");
  
  public static ReflectorMethod ForgeHooksClient_onTextureStitchedPost = new ReflectorMethod(ForgeHooksClient, "onTextureStitchedPost");
  
  public static ReflectorMethod ForgeHooksClient_renderBlockOverlay = new ReflectorMethod(ForgeHooksClient, "renderBlockOverlay");
  
  public static ReflectorMethod ForgeHooksClient_renderFireOverlay = new ReflectorMethod(ForgeHooksClient, "renderFireOverlay");
  
  public static ReflectorMethod ForgeHooksClient_renderWaterOverlay = new ReflectorMethod(ForgeHooksClient, "renderWaterOverlay");
  
  public static ReflectorMethod ForgeHooksClient_renderMainMenu = new ReflectorMethod(ForgeHooksClient, "renderMainMenu");
  
  public static ReflectorMethod ForgeHooksClient_renderSpecificFirstPersonHand = new ReflectorMethod(ForgeHooksClient, "renderSpecificFirstPersonHand");
  
  public static ReflectorMethod ForgeHooksClient_shouldCauseReequipAnimation = new ReflectorMethod(ForgeHooksClient, "shouldCauseReequipAnimation");
  
  public static ReflectorClass ForgeConfig = new ReflectorClass("net.minecraftforge.common.ForgeConfig");
  
  public static ReflectorField ForgeConfig_CLIENT = new ReflectorField(ForgeConfig, "CLIENT");
  
  public static ReflectorClass ForgeConfig_Client = new ReflectorClass("net.minecraftforge.common.ForgeConfig$Client");
  
  public static ReflectorField ForgeConfig_Client_calculateAllNormals = new ReflectorField(ForgeConfig_Client, "calculateAllNormals");
  
  public static ReflectorField ForgeConfig_Client_forgeLightPipelineEnabled = new ReflectorField(ForgeConfig_Client, "experimentalForgeLightPipelineEnabled");
  
  public static ReflectorField ForgeConfig_Client_useCombinedDepthStencilAttachment = new ReflectorField(ForgeConfig_Client, "useCombinedDepthStencilAttachment");
  
  public static ReflectorClass ForgeConfigSpec = new ReflectorClass("net.minecraftforge.common.ForgeConfigSpec");
  
  public static ReflectorField ForgeConfigSpec_childConfig = new ReflectorField(ForgeConfigSpec, "childConfig");
  
  public static ReflectorClass ForgeConfigSpec_ConfigValue = new ReflectorClass("net.minecraftforge.common.ForgeConfigSpec$ConfigValue");
  
  public static ReflectorField ForgeConfigSpec_ConfigValue_defaultSupplier = new ReflectorField(ForgeConfigSpec_ConfigValue, "defaultSupplier");
  
  public static ReflectorField ForgeConfigSpec_ConfigValue_spec = new ReflectorField(ForgeConfigSpec_ConfigValue, "spec");
  
  public static ReflectorMethod ForgeConfigSpec_ConfigValue_get = new ReflectorMethod(ForgeConfigSpec_ConfigValue, "get");
  
  public static ReflectorClass ForgeIChunk = new ReflectorClass(duy.class);
  
  public static ReflectorMethod ForgeIChunk_getWorldForge = new ReflectorMethod(ForgeIChunk, "getWorldForge");
  
  public static ReflectorClass IForgeItemStack = new ReflectorClass("net.minecraftforge.common.extensions.IForgeItemStack");
  
  public static ReflectorMethod IForgeItemStack_getHighlightTip = new ReflectorMethod(IForgeItemStack, "getHighlightTip");
  
  public static ReflectorClass ForgeItemTags = new ReflectorClass(awn.class);
  
  public static ReflectorMethod ForgeItemTags_create = ForgeItemTags.makeMethod("create", new Class[] { String.class, String.class });
  
  public static ReflectorClass ForgeKeyBinding = new ReflectorClass(fgm.class);
  
  public static ReflectorMethod ForgeKeyBinding_setKeyConflictContext = new ReflectorMethod(ForgeKeyBinding, "setKeyConflictContext");
  
  public static ReflectorMethod ForgeKeyBinding_setKeyModifierAndCode = new ReflectorMethod(ForgeKeyBinding, "setKeyModifierAndCode");
  
  public static ReflectorMethod ForgeKeyBinding_getKeyModifier = new ReflectorMethod(ForgeKeyBinding, "getKeyModifier");
  
  public static ReflectorClass ForgeTicket = new ReflectorClass(aqy.class);
  
  public static ReflectorField ForgeTicket_forceTicks = ForgeTicket.makeField("forceTicks");
  
  public static ReflectorMethod ForgeTicket_isForceTicks = ForgeTicket.makeMethod("isForceTicks");
  
  public static ReflectorClass IForgeBlockEntity = new ReflectorClass("net.minecraftforge.common.extensions.IForgeBlockEntity");
  
  public static ReflectorMethod IForgeBlockEntity_getRenderBoundingBox = new ReflectorMethod(IForgeBlockEntity, "getRenderBoundingBox");
  
  public static ReflectorMethod IForgeBlockEntity_hasCustomOutlineRendering = new ReflectorMethod(IForgeBlockEntity, "hasCustomOutlineRendering");
  
  public static ReflectorClass IForgeDimensionSpecialEffects = new ReflectorClass("net.minecraftforge.client.extensions.IForgeDimensionSpecialEffects");
  
  public static ReflectorMethod IForgeDimensionSpecialEffects_adjustLightmapColors = IForgeDimensionSpecialEffects.makeMethod("adjustLightmapColors");
  
  public static ReflectorMethod IForgeDimensionSpecialEffects_renderClouds = IForgeDimensionSpecialEffects.makeMethod("renderClouds");
  
  public static ReflectorMethod IForgeDimensionSpecialEffects_renderSky = IForgeDimensionSpecialEffects.makeMethod("renderSky");
  
  public static ReflectorMethod IForgeDimensionSpecialEffects_tickRain = IForgeDimensionSpecialEffects.makeMethod("tickRain");
  
  public static ReflectorMethod IForgeDimensionSpecialEffects_renderSnowAndRain = IForgeDimensionSpecialEffects.makeMethod("renderSnowAndRain");
  
  public static ReflectorClass ForgeVersion = new ReflectorClass("net.minecraftforge.versions.forge.ForgeVersion");
  
  public static ReflectorMethod ForgeVersion_getVersion = ForgeVersion.makeMethod("getVersion");
  
  public static ReflectorClass ImmediateWindowHandler = new ReflectorClass("net.minecraftforge.fml.loading.ImmediateWindowHandler");
  
  public static ReflectorMethod ImmediateWindowHandler_positionWindow = ImmediateWindowHandler.makeMethod("positionWindow");
  
  public static ReflectorMethod ImmediateWindowHandler_setupMinecraftWindow = ImmediateWindowHandler.makeMethod("setupMinecraftWindow");
  
  public static ReflectorMethod ImmediateWindowHandler_updateFBSize = ImmediateWindowHandler.makeMethod("updateFBSize");
  
  public static ReflectorClass ItemDecoratorHandler = new ReflectorClass("net.minecraftforge.client.ItemDecoratorHandler");
  
  public static ReflectorMethod ItemDecoratorHandler_of = ItemDecoratorHandler.makeMethod("of", new Class[] { cuq.class });
  
  public static ReflectorMethod ItemDecoratorHandler_render = ItemDecoratorHandler.makeMethod("render");
  
  public static ReflectorClass ForgeItemModelShaper = new ReflectorClass("net.minecraftforge.client.model.ForgeItemModelShaper");
  
  public static ReflectorConstructor ForgeItemModelShaper_Constructor = new ReflectorConstructor(ForgeItemModelShaper, new Class[] { gst.class });
  
  public static ReflectorClass KeyConflictContext = new ReflectorClass("net.minecraftforge.client.settings.KeyConflictContext");
  
  public static ReflectorField KeyConflictContext_IN_GAME = new ReflectorField(KeyConflictContext, "IN_GAME");
  
  public static ReflectorClass KeyModifier = new ReflectorClass("net.minecraftforge.client.settings.KeyModifier");
  
  public static ReflectorMethod KeyModifier_valueFromString = new ReflectorMethod(KeyModifier, "valueFromString");
  
  public static ReflectorField KeyModifier_NONE = new ReflectorField(KeyModifier, "NONE");
  
  public static ReflectorClass Launch = new ReflectorClass("net.minecraft.launchwrapper.Launch");
  
  public static ReflectorField Launch_blackboard = new ReflectorField(Launch, "blackboard");
  
  public static ReflectorClass MinecraftForge = new ReflectorClass("net.minecraftforge.common.MinecraftForge");
  
  public static ReflectorField MinecraftForge_EVENT_BUS = new ReflectorField(MinecraftForge, "EVENT_BUS");
  
  public static ReflectorClass ModContainer = new ReflectorClass("net.minecraftforge.fml.ModContainer");
  
  public static ReflectorMethod ModContainer_getModId = new ReflectorMethod(ModContainer, "getModId");
  
  public static ReflectorClass ModList = new ReflectorClass("net.minecraftforge.fml.ModList");
  
  public static ReflectorField ModList_mods = ModList.makeField("mods");
  
  public static ReflectorMethod ModList_get = ModList.makeMethod("get");
  
  public static ReflectorClass ModListScreen = new ReflectorClass("net.minecraftforge.client.gui.ModListScreen");
  
  public static ReflectorConstructor ModListScreen_Constructor = new ReflectorConstructor(ModListScreen, new Class[] { fod.class });
  
  public static ReflectorClass ModLoader = new ReflectorClass("net.minecraftforge.fml.ModLoader");
  
  public static ReflectorMethod ModLoader_get = ModLoader.makeMethod("get");
  
  public static ReflectorMethod ModLoader_postEvent = ModLoader.makeMethod("postEvent");
  
  public static ReflectorClass TitleScreenModUpdateIndicator = new ReflectorClass("net.minecraftforge.client.gui.TitleScreenModUpdateIndicator");
  
  public static ReflectorMethod TitleScreenModUpdateIndicator_init = TitleScreenModUpdateIndicator.makeMethod("init", new Class[] { fof.class, fim.class });
  
  public static ReflectorClass PartEntity = new ReflectorClass("net.minecraftforge.entity.PartEntity");
  
  public static ReflectorClass PlayLevelSoundEvent = new ReflectorClass("net.minecraftforge.event.PlayLevelSoundEvent");
  
  public static ReflectorMethod PlayLevelSoundEvent_getSound = new ReflectorMethod(PlayLevelSoundEvent, "getSound");
  
  public static ReflectorMethod PlayLevelSoundEvent_getSource = new ReflectorMethod(PlayLevelSoundEvent, "getSource");
  
  public static ReflectorMethod PlayLevelSoundEvent_getNewVolume = new ReflectorMethod(PlayLevelSoundEvent, "getNewVolume");
  
  public static ReflectorMethod PlayLevelSoundEvent_getNewPitch = new ReflectorMethod(PlayLevelSoundEvent, "getNewPitch");
  
  public static ReflectorClass QuadBakingVertexConsumer = new ReflectorClass("net.minecraftforge.client.model.pipeline.QuadBakingVertexConsumer");
  
  public static ReflectorField QuadBakingVertexConsumer_QUAD_DATA_SIZE = QuadBakingVertexConsumer.makeField("QUAD_DATA_SIZE");
  
  public static ReflectorClass QuadTransformers = new ReflectorClass("net.minecraftforge.client.model.QuadTransformers");
  
  public static ReflectorMethod QuadTransformers_applyingLightmap = QuadTransformers.makeMethod("applyingLightmap", new Class[] { int.class, int.class });
  
  public static ReflectorMethod QuadTransformers_applyingColor = QuadTransformers.makeMethod("applyingColor", new Class[] { int.class });
  
  public static ReflectorClass IQuadTransformer = new ReflectorClass("net.minecraftforge.client.model.IQuadTransformer");
  
  public static ReflectorField IQuadTransformer_STRIDE = IQuadTransformer.makeField("STRIDE");
  
  public static ReflectorMethod IQuadTransformer_processInPlace = IQuadTransformer.makeMethod("processInPlace", new Class[] { gfw.class });
  
  public static ReflectorClass RenderBlockScreenEffectEvent_OverlayType = new ReflectorClass("net.minecraftforge.client.event.RenderBlockScreenEffectEvent$OverlayType");
  
  public static ReflectorField RenderBlockScreenEffectEvent_OverlayType_BLOCK = new ReflectorField(RenderBlockScreenEffectEvent_OverlayType, "BLOCK");
  
  public static ReflectorClass CustomizeGuiOverlayEvent_BossEventProgress = new ReflectorClass("net.minecraftforge.client.event.CustomizeGuiOverlayEvent$BossEventProgress");
  
  public static ReflectorMethod CustomizeGuiOverlayEvent_BossEventProgress_getIncrement = CustomizeGuiOverlayEvent_BossEventProgress.makeMethod("getIncrement");
  
  public static ReflectorClass RenderLevelStageEvent_Stage = new ReflectorClass(RenderLevelStageEvent.Stage.class);
  
  public static ReflectorField RenderLevelStageEvent_Stage_AFTER_SKY = RenderLevelStageEvent_Stage.makeField("AFTER_SKY");
  
  public static ReflectorField RenderLevelStageEvent_Stage_AFTER_ENTITIES = RenderLevelStageEvent_Stage.makeField("AFTER_ENTITIES");
  
  public static ReflectorField RenderLevelStageEvent_Stage_AFTER_BLOCK_ENTITIES = RenderLevelStageEvent_Stage.makeField("AFTER_BLOCK_ENTITIES");
  
  public static ReflectorField RenderLevelStageEvent_Stage_AFTER_PARTICLES = RenderLevelStageEvent_Stage.makeField("AFTER_PARTICLES");
  
  public static ReflectorField RenderLevelStageEvent_Stage_AFTER_WEATHER = RenderLevelStageEvent_Stage.makeField("AFTER_WEATHER");
  
  public static ReflectorField RenderLevelStageEvent_Stage_AFTER_LEVEL = RenderLevelStageEvent_Stage.makeField("AFTER_LEVEL");
  
  public static ReflectorMethod RenderLevelStageEvent_dispatch = RenderLevelStageEvent_Stage.makeMethod("dispatch");
  
  public static ReflectorClass RenderNameTagEvent = new ReflectorClass("net.minecraftforge.client.event.RenderNameTagEvent");
  
  public static ReflectorMethod RenderNameTagEvent_getContent = new ReflectorMethod(RenderNameTagEvent, "getContent");
  
  public static ReflectorClass RenderTooltipEvent = new ReflectorClass("net.minecraftforge.client.event.RenderTooltipEvent");
  
  public static ReflectorMethod RenderTooltipEvent_getFont = RenderTooltipEvent.makeMethod("getFont");
  
  public static ReflectorMethod RenderTooltipEvent_getX = RenderTooltipEvent.makeMethod("getX");
  
  public static ReflectorMethod RenderTooltipEvent_getY = RenderTooltipEvent.makeMethod("getY");
  
  public static ReflectorClass RenderTooltipEvent_Color = new ReflectorClass("net.minecraftforge.client.event.RenderTooltipEvent$Color");
  
  public static ReflectorMethod RenderTooltipEvent_Color_getBackgroundStart = RenderTooltipEvent_Color.makeMethod("getBackgroundStart");
  
  public static ReflectorMethod RenderTooltipEvent_Color_getBackgroundEnd = RenderTooltipEvent_Color.makeMethod("getBackgroundEnd");
  
  public static ReflectorMethod RenderTooltipEvent_Color_getBorderStart = RenderTooltipEvent_Color.makeMethod("getBorderStart");
  
  public static ReflectorMethod RenderTooltipEvent_Color_getBorderEnd = RenderTooltipEvent_Color.makeMethod("getBorderEnd");
  
  public static ReflectorClass ScreenshotEvent = new ReflectorClass("net.minecraftforge.client.event.ScreenshotEvent");
  
  public static ReflectorMethod ScreenshotEvent_getCancelMessage = new ReflectorMethod(ScreenshotEvent, "getCancelMessage");
  
  public static ReflectorMethod ScreenshotEvent_getScreenshotFile = new ReflectorMethod(ScreenshotEvent, "getScreenshotFile");
  
  public static ReflectorMethod ScreenshotEvent_getResultMessage = new ReflectorMethod(ScreenshotEvent, "getResultMessage");
  
  public static ReflectorClass ServerLifecycleHooks = new ReflectorClass("net.minecraftforge.server.ServerLifecycleHooks");
  
  public static ReflectorMethod ServerLifecycleHooks_handleServerAboutToStart = new ReflectorMethod(ServerLifecycleHooks, "handleServerAboutToStart");
  
  public static ReflectorMethod ServerLifecycleHooks_handleServerStarting = new ReflectorMethod(ServerLifecycleHooks, "handleServerStarting");
  
  public static ReflectorClass TerrainParticle = new ReflectorClass(gdm.class);
  
  public static ReflectorMethod TerrainParticle_updateSprite = TerrainParticle.makeMethod("updateSprite");
  
  public static ReflectorClass TooltipRenderUtil = new ReflectorClass(fqr.class);
  
  public static ReflectorMethod TooltipRenderUtil_renderTooltipBackground10 = TooltipRenderUtil.makeMethod("renderTooltipBackground", new Class[] { fhz.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class });
  
  private static boolean logVanilla = registerResolvable("*** Reflector Vanilla ***");
  
  public static ReflectorClass AbstractArrow = new ReflectorClass(cnd.class);
  
  public static ReflectorField AbstractArrow_inGround = new ReflectorField((IFieldLocator)new FieldLocatorTypes(cnd.class, new Class[] { dtc.class }, boolean.class, new Class[] { int.class }, "AbstractArrow.inGround"));
  
  public static ReflectorClass BannerBlockEntity = new ReflectorClass(dpw.class);
  
  public static ReflectorField BannerBlockEntity_customName = BannerBlockEntity.makeField(wz.class);
  
  public static ReflectorClass BaseContainerBlockEntity = new ReflectorClass(dqb.class);
  
  public static ReflectorField BaseContainerBlockEntity_customName = BaseContainerBlockEntity.makeField(wz.class);
  
  public static ReflectorClass Enchantments = new ReflectorClass(dah.class);
  
  public static ReflectorFields Enchantments_ResourceKeys = new ReflectorFields(Enchantments, akq.class, -1);
  
  public static ReflectorClass EntityItem = new ReflectorClass(cjh.class);
  
  public static ReflectorField EntityItem_ITEM = new ReflectorField(EntityItem, ajw.class);
  
  public static ReflectorClass EnderDragonRenderer = new ReflectorClass(gke.class);
  
  public static ReflectorField EnderDragonRenderer_model = new ReflectorField(EnderDragonRenderer, gke.a.class);
  
  public static ReflectorClass GuiEnchantment = new ReflectorClass(fpn.class);
  
  public static ReflectorField GuiEnchantment_bookModel = new ReflectorField(GuiEnchantment, fus.class);
  
  public static ReflectorClass ItemOverride = new ReflectorClass(ggf.class);
  
  public static ReflectorField ItemOverride_listResourceValues = new ReflectorField(ItemOverride, List.class);
  
  public static ReflectorClass ItemStack = new ReflectorClass(cuq.class);
  
  public static ReflectorField ItemStack_components = ItemStack.makeField(kr.class);
  
  public static ReflectorClass LayerLlamaDecor = new ReflectorClass(gop.class);
  
  public static ReflectorField LayerLlamaDecor_model = new ReflectorField(LayerLlamaDecor, fwd.class);
  
  public static ReflectorClass Minecraft = new ReflectorClass(fgo.class);
  
  public static ReflectorField Minecraft_debugFPS = new ReflectorField((IFieldLocator)new FieldLocatorTypes(fgo.class, new Class[] { Supplier.class }, int.class, new Class[] { String.class }, "debugFPS"));
  
  public static ReflectorField Minecraft_fontResourceManager = new ReflectorField(Minecraft, flb.class);
  
  public static ReflectorClass ModelArmorStand = new ReflectorClass(ful.class);
  
  public static ReflectorFields ModelArmorStand_ModelRenderers = new ReflectorFields(ModelArmorStand, fyk.class, 4);
  
  public static ReflectorClass ModelBee = new ReflectorClass(fuo.class);
  
  public static ReflectorFields ModelBee_ModelRenderers = new ReflectorFields(ModelBee, fyk.class, 2);
  
  public static ReflectorClass ModelBoar = new ReflectorClass(fvu.class);
  
  public static ReflectorFields ModelBoar_ModelRenderers = new ReflectorFields(ModelBoar, fyk.class, 9);
  
  public static ReflectorClass ModelBook = new ReflectorClass(fus.class);
  
  public static ReflectorField ModelBook_root = new ReflectorField(ModelBook, fyk.class);
  
  public static ReflectorClass ModelChicken = new ReflectorClass(fuz.class);
  
  public static ReflectorFields ModelChicken_ModelRenderers = new ReflectorFields(ModelChicken, fyk.class, 8);
  
  public static ReflectorClass ModelDragon = new ReflectorClass(gke.a.class);
  
  public static ReflectorFields ModelDragon_ModelRenderers = new ReflectorFields(ModelDragon, fyk.class, 20);
  
  public static ReflectorClass RenderEnderCrystal = new ReflectorClass(gkd.class);
  
  public static ReflectorFields RenderEnderCrystal_modelRenderers = new ReflectorFields(RenderEnderCrystal, fyk.class, 3);
  
  public static ReflectorClass ModelDragonHead = new ReflectorClass(fye.class);
  
  public static ReflectorField ModelDragonHead_head = new ReflectorField(ModelDragonHead, fyk.class, 0);
  
  public static ReflectorField ModelDragonHead_jaw = new ReflectorField(ModelDragonHead, fyk.class, 1);
  
  public static ReflectorClass ModelHorse = new ReflectorClass(fvv.class);
  
  public static ReflectorFields ModelHorse_ModelRenderers = new ReflectorFields(ModelHorse, fyk.class, 11);
  
  public static ReflectorClass ModelHorseChests = new ReflectorClass(fuy.class);
  
  public static ReflectorFields ModelHorseChests_ModelRenderers = new ReflectorFields(ModelHorseChests, fyk.class, 2);
  
  public static ReflectorClass ModelAxolotl = new ReflectorClass(fum.class);
  
  public static ReflectorFields ModelAxolotl_ModelRenderers = new ReflectorFields(ModelAxolotl, fyk.class, 10);
  
  public static ReflectorClass ModelFox = new ReflectorClass(fvm.class);
  
  public static ReflectorFields ModelFox_ModelRenderers = new ReflectorFields(ModelFox, fyk.class, 7);
  
  public static ReflectorClass RenderLeashKnot = new ReflectorClass(gli.class);
  
  public static ReflectorField RenderLeashKnot_leashKnotModel = new ReflectorField(RenderLeashKnot, fwb.class);
  
  public static ReflectorClass ModelLlama = new ReflectorClass(fwd.class);
  
  public static ReflectorFields ModelLlama_ModelRenderers = new ReflectorFields(ModelLlama, fyk.class, 8);
  
  public static ReflectorClass ModelOcelot = new ReflectorClass(fwi.class);
  
  public static ReflectorFields ModelOcelot_ModelRenderers = new ReflectorFields(ModelOcelot, fyk.class, 8);
  
  public static ReflectorClass ModelPiglin = new ReflectorClass(fwo.class);
  
  public static ReflectorFields ModelPiglin_ModelRenderers = new ReflectorFields(ModelPiglin, fyk.class, 2);
  
  public static ReflectorClass ModelPiglinHead = new ReflectorClass(fwn.class);
  
  public static ReflectorFields ModelPiglinHead_ModelRenderers = new ReflectorFields(ModelPiglinHead, fyk.class, 3);
  
  public static ReflectorClass ModelQuadruped = new ReflectorClass(fwu.class);
  
  public static ReflectorFields ModelQuadruped_ModelRenderers = new ReflectorFields(ModelQuadruped, fyk.class, 6);
  
  public static ReflectorClass ModelRabbit = new ReflectorClass(fwv.class);
  
  public static ReflectorFields ModelRabbit_ModelRenderers = new ReflectorFields(ModelRabbit, fyk.class, 12);
  
  public static ReflectorClass ModelShulker = new ReflectorClass(fxd.class);
  
  public static ReflectorFields ModelShulker_ModelRenderers = new ReflectorFields(ModelShulker, fyk.class, 3);
  
  public static ReflectorClass ModelShield = new ReflectorClass(fxb.class);
  
  public static ReflectorFields ModelShield_ModelRenderers = new ReflectorFields(ModelShield, fyk.class, 3);
  
  public static ReflectorClass ModelSkull = new ReflectorClass(fxg.class);
  
  public static ReflectorFields ModelSkull_renderers = new ReflectorFields(ModelSkull, fyk.class, 2);
  
  public static ReflectorClass ModelTadpole = new ReflectorClass(fxo.class);
  
  public static ReflectorFields ModelTadpole_ModelRenderers = new ReflectorFields(ModelTadpole, fyk.class, 2);
  
  public static ReflectorClass ModelTrident = new ReflectorClass(fxp.class);
  
  public static ReflectorField ModelTrident_root = new ReflectorField(ModelTrident, fyk.class);
  
  public static ReflectorClass ModelTurtle = new ReflectorClass(fxs.class);
  
  public static ReflectorField ModelTurtle_body2 = new ReflectorField(ModelTurtle, fyk.class, 0);
  
  public static ReflectorClass ModelWolf = new ReflectorClass(fyb.class);
  
  public static ReflectorFields ModelWolf_ModelRenderers = new ReflectorFields(ModelWolf, fyk.class, 10);
  
  public static ReflectorClass OptiFineResourceLocator = ReflectorForge.getReflectorClassOptiFineResourceLocator();
  
  public static ReflectorMethod OptiFineResourceLocator_getOptiFineResourceStream = new ReflectorMethod(OptiFineResourceLocator, "getOptiFineResourceStream");
  
  public static ReflectorClass Potion = new ReflectorClass(cws.class);
  
  public static ReflectorField Potion_baseName = Potion.makeField(String.class);
  
  public static ReflectorClass RenderBoat = new ReflectorClass(gjn.class);
  
  public static ReflectorField RenderBoat_boatResources = new ReflectorField(RenderBoat, Map.class);
  
  public static ReflectorClass RenderEvokerFangs = new ReflectorClass(gkl.class);
  
  public static ReflectorField RenderEvokerFangs_model = new ReflectorField(RenderEvokerFangs, fvl.class);
  
  public static ReflectorClass RenderLlamaSpit = new ReflectorClass(glm.class);
  
  public static ReflectorField RenderLlamaSpit_model = new ReflectorField(RenderLlamaSpit, fwe.class);
  
  public static ReflectorClass RenderPufferfish = new ReflectorClass(gmc.class);
  
  public static ReflectorField RenderPufferfish_modelSmall = new ReflectorField(RenderPufferfish, fvk.class, 0);
  
  public static ReflectorField RenderPufferfish_modelMedium = new ReflectorField(RenderPufferfish, fvk.class, 1);
  
  public static ReflectorField RenderPufferfish_modelBig = new ReflectorField(RenderPufferfish, fvk.class, 2);
  
  public static ReflectorClass RenderMinecart = new ReflectorClass(glo.class);
  
  public static ReflectorField RenderMinecart_modelMinecart = new ReflectorField(RenderMinecart, fvk.class);
  
  public static ReflectorClass RenderShulkerBullet = new ReflectorClass(gmi.class);
  
  public static ReflectorField RenderShulkerBullet_model = new ReflectorField(RenderShulkerBullet, fxc.class);
  
  public static ReflectorClass RenderTrident = new ReflectorClass(gmw.class);
  
  public static ReflectorField RenderTrident_modelTrident = new ReflectorField(RenderTrident, fxp.class);
  
  public static ReflectorClass RenderTropicalFish = new ReflectorClass(gna.class);
  
  public static ReflectorField RenderTropicalFish_modelA = new ReflectorField(RenderTropicalFish, fvc.class, 0);
  
  public static ReflectorField RenderTropicalFish_modelB = new ReflectorField(RenderTropicalFish, fvc.class, 1);
  
  public static ReflectorClass RenderWindCharge = new ReflectorClass(gni.class);
  
  public static ReflectorField RenderWindCharge_model = new ReflectorField(RenderWindCharge, fxy.class);
  
  public static ReflectorClass TropicalFishPatternLayer = new ReflectorClass(gpf.class);
  
  public static ReflectorField TropicalFishPatternLayer_modelA = new ReflectorField(TropicalFishPatternLayer, fxq.class);
  
  public static ReflectorField TropicalFishPatternLayer_modelB = new ReflectorField(TropicalFishPatternLayer, fxr.class);
  
  public static ReflectorClass RenderWitherSkull = new ReflectorClass(gnm.class);
  
  public static ReflectorField RenderWitherSkull_model = new ReflectorField(RenderWitherSkull, fxg.class);
  
  public static ReflectorClass SimpleBakedModel = new ReflectorClass(gsx.class);
  
  public static ReflectorField SimpleBakedModel_generalQuads = SimpleBakedModel.makeField(List.class);
  
  public static ReflectorField SimpleBakedModel_faceQuads = SimpleBakedModel.makeField(Map.class);
  
  public static ReflectorClass TileEntityBannerRenderer = new ReflectorClass(ggu.class);
  
  public static ReflectorFields TileEntityBannerRenderer_modelRenderers = new ReflectorFields(TileEntityBannerRenderer, fyk.class, 3);
  
  public static ReflectorClass TileEntityBedRenderer = new ReflectorClass(ggw.class);
  
  public static ReflectorField TileEntityBedRenderer_headModel = new ReflectorField(TileEntityBedRenderer, fyk.class, 0);
  
  public static ReflectorField TileEntityBedRenderer_footModel = new ReflectorField(TileEntityBedRenderer, fyk.class, 1);
  
  public static ReflectorClass TileEntityBellRenderer = new ReflectorClass(ggx.class);
  
  public static ReflectorField TileEntityBellRenderer_modelRenderer = new ReflectorField(TileEntityBellRenderer, fyk.class);
  
  public static ReflectorClass TileEntityBeacon = new ReflectorClass(dqc.class);
  
  public static ReflectorField TileEntityBeacon_customName = new ReflectorField(TileEntityBeacon, wz.class);
  
  public static ReflectorField TileEntityBeacon_levels = new ReflectorField((IFieldLocator)new FieldLocatorTypes(dqc.class, new Class[] { List.class }, int.class, new Class[] { int.class }, "BeaconBlockEntity.levels"));
  
  public static ReflectorClass TileEntityChestRenderer = new ReflectorClass(ghf.class);
  
  public static ReflectorFields TileEntityChestRenderer_modelRenderers = new ReflectorFields(TileEntityChestRenderer, fyk.class, 9);
  
  public static ReflectorClass TileEntityConduitRenderer = new ReflectorClass(ghg.class);
  
  public static ReflectorFields TileEntityConduitRenderer_modelRenderers = new ReflectorFields(TileEntityConduitRenderer, fyk.class, 4);
  
  public static ReflectorClass TileEntityDecoratedPotRenderer = new ReflectorClass(ghh.class);
  
  public static ReflectorFields TileEntityDecoratedPotRenderer_modelRenderers = new ReflectorFields(TileEntityDecoratedPotRenderer, fyk.class, 7);
  
  public static ReflectorClass TileEntityEnchantmentTableRenderer = new ReflectorClass(ghi.class);
  
  public static ReflectorField TileEntityEnchantmentTableRenderer_modelBook = new ReflectorField(TileEntityEnchantmentTableRenderer, fus.class);
  
  public static ReflectorClass TileEntityHangingSignRenderer = new ReflectorClass(ghj.class);
  
  public static ReflectorField TileEntityHangingSignRenderer_hangingSignModels = new ReflectorField(TileEntityHangingSignRenderer, Map.class);
  
  public static ReflectorClass TileEntityLecternRenderer = new ReflectorClass(ghk.class);
  
  public static ReflectorField TileEntityLecternRenderer_modelBook = new ReflectorField(TileEntityLecternRenderer, fus.class);
  
  public static ReflectorClass TileEntityShulkerBoxRenderer = new ReflectorClass(ghm.class);
  
  public static ReflectorField TileEntityShulkerBoxRenderer_model = new ReflectorField(TileEntityShulkerBoxRenderer, fxd.class);
  
  public static ReflectorClass TileEntitySignRenderer = new ReflectorClass(ghn.class);
  
  public static ReflectorField TileEntitySignRenderer_signModels = new ReflectorField(TileEntitySignRenderer, Map.class);
  
  public static void callVoid(ReflectorMethod refMethod, Object... params) {
    try {
      Method m = refMethod.getTargetMethod();
      if (m == null)
        return; 
      m.invoke(null, params);
    } catch (Throwable e) {
      handleException(e, null, refMethod, params);
    } 
  }
  
  public static boolean callBoolean(ReflectorMethod refMethod, Object... params) {
    try {
      Method method = refMethod.getTargetMethod();
      if (method == null)
        return false; 
      Boolean retVal = (Boolean)method.invoke(null, params);
      return retVal.booleanValue();
    } catch (Throwable e) {
      handleException(e, null, refMethod, params);
      return false;
    } 
  }
  
  public static int callInt(ReflectorMethod refMethod, Object... params) {
    try {
      Method method = refMethod.getTargetMethod();
      if (method == null)
        return 0; 
      Integer retVal = (Integer)method.invoke(null, params);
      return retVal.intValue();
    } catch (Throwable e) {
      handleException(e, null, refMethod, params);
      return 0;
    } 
  }
  
  public static long callLong(ReflectorMethod refMethod, Object... params) {
    try {
      Method method = refMethod.getTargetMethod();
      if (method == null)
        return 0L; 
      Long retVal = (Long)method.invoke(null, params);
      return retVal.longValue();
    } catch (Throwable e) {
      handleException(e, null, refMethod, params);
      return 0L;
    } 
  }
  
  public static float callFloat(ReflectorMethod refMethod, Object... params) {
    try {
      Method method = refMethod.getTargetMethod();
      if (method == null)
        return 0.0F; 
      Float retVal = (Float)method.invoke(null, params);
      return retVal.floatValue();
    } catch (Throwable e) {
      handleException(e, null, refMethod, params);
      return 0.0F;
    } 
  }
  
  public static double callDouble(ReflectorMethod refMethod, Object... params) {
    try {
      Method method = refMethod.getTargetMethod();
      if (method == null)
        return 0.0D; 
      Double retVal = (Double)method.invoke(null, params);
      return retVal.doubleValue();
    } catch (Throwable e) {
      handleException(e, null, refMethod, params);
      return 0.0D;
    } 
  }
  
  public static String callString(ReflectorMethod refMethod, Object... params) {
    try {
      Method method = refMethod.getTargetMethod();
      if (method == null)
        return null; 
      String retVal = (String)method.invoke(null, params);
      return retVal;
    } catch (Throwable e) {
      handleException(e, null, refMethod, params);
      return null;
    } 
  }
  
  public static Object call(ReflectorMethod refMethod, Object... params) {
    try {
      Method method = refMethod.getTargetMethod();
      if (method == null)
        return null; 
      Object retVal = method.invoke(null, params);
      return retVal;
    } catch (Throwable e) {
      handleException(e, null, refMethod, params);
      return null;
    } 
  }
  
  public static void callVoid(Object obj, ReflectorMethod refMethod, Object... params) {
    try {
      if (obj == null)
        return; 
      Method method = refMethod.getTargetMethod();
      if (method == null)
        return; 
      method.invoke(obj, params);
    } catch (Throwable e) {
      handleException(e, obj, refMethod, params);
    } 
  }
  
  public static boolean callBoolean(Object obj, ReflectorMethod refMethod, Object... params) {
    try {
      Method method = refMethod.getTargetMethod();
      if (method == null)
        return false; 
      Boolean retVal = (Boolean)method.invoke(obj, params);
      return retVal.booleanValue();
    } catch (Throwable e) {
      handleException(e, obj, refMethod, params);
      return false;
    } 
  }
  
  public static int callInt(Object obj, ReflectorMethod refMethod, Object... params) {
    try {
      Method method = refMethod.getTargetMethod();
      if (method == null)
        return 0; 
      Integer retVal = (Integer)method.invoke(obj, params);
      return retVal.intValue();
    } catch (Throwable e) {
      handleException(e, obj, refMethod, params);
      return 0;
    } 
  }
  
  public static long callLong(Object obj, ReflectorMethod refMethod, Object... params) {
    try {
      Method method = refMethod.getTargetMethod();
      if (method == null)
        return 0L; 
      Long retVal = (Long)method.invoke(obj, params);
      return retVal.longValue();
    } catch (Throwable e) {
      handleException(e, obj, refMethod, params);
      return 0L;
    } 
  }
  
  public static float callFloat(Object obj, ReflectorMethod refMethod, Object... params) {
    try {
      Method method = refMethod.getTargetMethod();
      if (method == null)
        return 0.0F; 
      Float retVal = (Float)method.invoke(obj, params);
      return retVal.floatValue();
    } catch (Throwable e) {
      handleException(e, obj, refMethod, params);
      return 0.0F;
    } 
  }
  
  public static double callDouble(Object obj, ReflectorMethod refMethod, Object... params) {
    try {
      Method method = refMethod.getTargetMethod();
      if (method == null)
        return 0.0D; 
      Double retVal = (Double)method.invoke(obj, params);
      return retVal.doubleValue();
    } catch (Throwable e) {
      handleException(e, obj, refMethod, params);
      return 0.0D;
    } 
  }
  
  public static String callString(Object obj, ReflectorMethod refMethod, Object... params) {
    try {
      Method method = refMethod.getTargetMethod();
      if (method == null)
        return null; 
      String retVal = (String)method.invoke(obj, params);
      return retVal;
    } catch (Throwable e) {
      handleException(e, obj, refMethod, params);
      return null;
    } 
  }
  
  public static Object call(Object obj, ReflectorMethod refMethod, Object... params) {
    try {
      Method method = refMethod.getTargetMethod();
      if (method == null)
        return null; 
      Object retVal = method.invoke(obj, params);
      return retVal;
    } catch (Throwable e) {
      handleException(e, obj, refMethod, params);
      return null;
    } 
  }
  
  public static Object getFieldValue(ReflectorField refField) {
    return getFieldValue(null, refField);
  }
  
  public static Object getFieldValue(Object obj, ReflectorField refField) {
    try {
      Field field = refField.getTargetField();
      if (field == null)
        return null; 
      Object value = field.get(obj);
      return value;
    } catch (Throwable e) {
      Log.error("", e);
      return null;
    } 
  }
  
  public static boolean getFieldValueBoolean(Object obj, ReflectorField refField, boolean def) {
    try {
      Field field = refField.getTargetField();
      if (field == null)
        return def; 
      boolean value = field.getBoolean(obj);
      return value;
    } catch (Throwable e) {
      Log.error("", e);
      return def;
    } 
  }
  
  public static Object getFieldValue(ReflectorFields refFields, int index) {
    ReflectorField refField = refFields.getReflectorField(index);
    if (refField == null)
      return null; 
    return getFieldValue(refField);
  }
  
  public static Object getFieldValue(Object obj, ReflectorFields refFields, int index) {
    ReflectorField refField = refFields.getReflectorField(index);
    if (refField == null)
      return null; 
    return getFieldValue(obj, refField);
  }
  
  public static float getFieldValueFloat(Object obj, ReflectorField refField, float def) {
    try {
      Field field = refField.getTargetField();
      if (field == null)
        return def; 
      float value = field.getFloat(obj);
      return value;
    } catch (Throwable e) {
      Log.error("", e);
      return def;
    } 
  }
  
  public static int getFieldValueInt(ReflectorField refField, int def) {
    return getFieldValueInt(null, refField, def);
  }
  
  public static int getFieldValueInt(Object obj, ReflectorField refField, int def) {
    try {
      Field field = refField.getTargetField();
      if (field == null)
        return def; 
      int value = field.getInt(obj);
      return value;
    } catch (Throwable e) {
      Log.error("", e);
      return def;
    } 
  }
  
  public static long getFieldValueLong(Object obj, ReflectorField refField, long def) {
    try {
      Field field = refField.getTargetField();
      if (field == null)
        return def; 
      long value = field.getLong(obj);
      return value;
    } catch (Throwable e) {
      Log.error("", e);
      return def;
    } 
  }
  
  public static boolean setFieldValue(ReflectorField refField, Object value) {
    return setFieldValue(null, refField, value);
  }
  
  public static boolean setFieldValue(Object obj, ReflectorFields refFields, int index, Object value) {
    ReflectorField refField = refFields.getReflectorField(index);
    if (refField == null)
      return false; 
    setFieldValue(obj, refField, value);
    return true;
  }
  
  public static boolean setFieldValue(Object obj, ReflectorField refField, Object value) {
    try {
      Field field = refField.getTargetField();
      if (field == null)
        return false; 
      field.set(obj, value);
      return true;
    } catch (Throwable e) {
      Log.error("", e);
      return false;
    } 
  }
  
  public static boolean setFieldValueInt(ReflectorField refField, int value) {
    return setFieldValueInt(null, refField, value);
  }
  
  public static boolean setFieldValueInt(Object obj, ReflectorField refField, int value) {
    try {
      Field field = refField.getTargetField();
      if (field == null)
        return false; 
      field.setInt(obj, value);
      return true;
    } catch (Throwable e) {
      Log.error("", e);
      return false;
    } 
  }
  
  public static boolean postForgeBusEvent(ReflectorConstructor constr, Object... params) {
    Object event = newInstance(constr, params);
    if (event == null)
      return false; 
    return postForgeBusEvent(event);
  }
  
  public static boolean postForgeBusEvent(Object event) {
    if (event == null)
      return false; 
    Object eventBus = getFieldValue(MinecraftForge_EVENT_BUS);
    if (eventBus == null)
      return false; 
    Object ret = call(eventBus, EventBus_post, new Object[] { event });
    if (!(ret instanceof Boolean))
      return false; 
    Boolean retBool = (Boolean)ret;
    return retBool.booleanValue();
  }
  
  public static Object newInstance(ReflectorConstructor constr, Object... params) {
    Constructor c = constr.getTargetConstructor();
    if (c == null)
      return null; 
    try {
      Object obj = c.newInstance(params);
      return obj;
    } catch (Throwable e) {
      handleException(e, constr, params);
      return null;
    } 
  }
  
  public static boolean matchesTypes(Class[] pTypes, Class[] cTypes) {
    if (pTypes.length != cTypes.length)
      return false; 
    for (int i = 0; i < cTypes.length; i++) {
      Class pType = pTypes[i];
      Class cType = cTypes[i];
      if (pType != cType)
        return false; 
    } 
    return true;
  }
  
  private static void dbgCall(boolean isStatic, String callType, ReflectorMethod refMethod, Object[] params, Object retVal) {
    String className = refMethod.getTargetMethod().getDeclaringClass().getName();
    String methodName = refMethod.getTargetMethod().getName();
    String staticStr = "";
    if (isStatic)
      staticStr = " static"; 
    Log.dbg(callType + callType + " " + staticStr + "." + className + "(" + methodName + ") => " + ArrayUtils.arrayToString(params));
  }
  
  private static void dbgCallVoid(boolean isStatic, String callType, ReflectorMethod refMethod, Object[] params) {
    String className = refMethod.getTargetMethod().getDeclaringClass().getName();
    String methodName = refMethod.getTargetMethod().getName();
    String staticStr = "";
    if (isStatic)
      staticStr = " static"; 
    Log.dbg(callType + callType + " " + staticStr + "." + className + "(" + methodName + ")");
  }
  
  private static void dbgFieldValue(boolean isStatic, String accessType, ReflectorField refField, Object val) {
    String className = refField.getTargetField().getDeclaringClass().getName();
    String fieldName = refField.getTargetField().getName();
    String staticStr = "";
    if (isStatic)
      staticStr = " static"; 
    Log.dbg(accessType + accessType + " " + staticStr + "." + className + " => " + fieldName);
  }
  
  private static void handleException(Throwable e, Object obj, ReflectorMethod refMethod, Object[] params) {
    if (e instanceof java.lang.reflect.InvocationTargetException) {
      Throwable cause = e.getCause();
      if (cause instanceof RuntimeException) {
        RuntimeException causeRuntime = (RuntimeException)cause;
        throw causeRuntime;
      } 
      Log.error("", e);
      return;
    } 
    Log.warn("*** Exception outside of method ***");
    Log.warn("Method deactivated: " + String.valueOf(refMethod.getTargetMethod()));
    refMethod.deactivate();
    if (e instanceof IllegalArgumentException) {
      Log.warn("*** IllegalArgumentException ***");
      Log.warn("Method: " + String.valueOf(refMethod.getTargetMethod()));
      Log.warn("Object: " + String.valueOf(obj));
      Log.warn("Parameter classes: " + ArrayUtils.arrayToString(getClasses(params)));
      Log.warn("Parameters: " + ArrayUtils.arrayToString(params));
    } 
    Log.warn("", e);
  }
  
  private static void handleException(Throwable e, ReflectorConstructor refConstr, Object[] params) {
    if (e instanceof java.lang.reflect.InvocationTargetException) {
      Log.error("", e);
      return;
    } 
    Log.warn("*** Exception outside of constructor ***");
    Log.warn("Constructor deactivated: " + String.valueOf(refConstr.getTargetConstructor()));
    refConstr.deactivate();
    if (e instanceof IllegalArgumentException) {
      Log.warn("*** IllegalArgumentException ***");
      Log.warn("Constructor: " + String.valueOf(refConstr.getTargetConstructor()));
      Log.warn("Parameter classes: " + ArrayUtils.arrayToString(getClasses(params)));
      Log.warn("Parameters: " + ArrayUtils.arrayToString(params));
    } 
    Log.warn("", e);
  }
  
  private static Object[] getClasses(Object[] objs) {
    if (objs == null)
      return (Object[])new Class[0]; 
    Class[] classes = new Class[objs.length];
    for (int i = 0; i < classes.length; i++) {
      Object obj = objs[i];
      if (obj != null)
        classes[i] = obj.getClass(); 
    } 
    return (Object[])classes;
  }
  
  private static ReflectorField[] getReflectorFields(ReflectorClass parentClass, Class fieldType, int count) {
    ReflectorField[] rfs = new ReflectorField[count];
    for (int i = 0; i < rfs.length; i++)
      rfs[i] = new ReflectorField(parentClass, fieldType, i); 
    return rfs;
  }
  
  private static boolean registerResolvable(String str) {
    String msg = str;
    Object object = new Object(str);
    ReflectorResolver.register((IResolvable)object);
    return true;
  }
}

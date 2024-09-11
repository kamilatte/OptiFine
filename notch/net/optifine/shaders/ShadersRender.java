package notch.net.optifine.shaders;

import a;
import akr;
import brc;
import bsr;
import btn;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import dcc;
import dqh;
import drz;
import ewx;
import exc;
import fbi;
import fbm;
import fbq;
import ffy;
import fgo;
import geb;
import ges;
import gev;
import gex;
import gez;
import gfh;
import gfo;
import ghn;
import gia;
import gie;
import gkh;
import gqk;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jd;
import ji;
import kh;
import net.minecraftforge.client.event.ViewportEvent;
import net.optifine.Config;
import net.optifine.Lagometer;
import net.optifine.reflect.Reflector;
import net.optifine.render.GlBlendState;
import net.optifine.render.GlCullState;
import net.optifine.render.ICamera;
import net.optifine.render.RenderTypes;
import net.optifine.shaders.ClippingHelperDummy;
import net.optifine.shaders.DrawBuffers;
import net.optifine.shaders.GlState;
import net.optifine.shaders.RenderStage;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadowFrustum;
import net.optifine.util.MathUtils;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class ShadersRender {
  private static final akr END_PORTAL_TEXTURE = new akr("textures/entity/end_portal.png");
  
  public static boolean frustumTerrainShadowChanged = false;
  
  public static boolean frustumEntitiesShadowChanged = false;
  
  public static int countEntitiesRenderedShadow;
  
  public static int countTileEntitiesRenderedShadow;
  
  private static Map<String, List<bsr>> mapEntityLists = new HashMap<>();
  
  public static void setFrustrumPosition(ICamera frustum, double x, double y, double z) {
    frustum.setCameraPosition(x, y, z);
  }
  
  public static void beginTerrainSolid() {
    if (Shaders.isRenderingWorld) {
      Shaders.fogEnabled = true;
      Shaders.useProgram(Shaders.ProgramTerrain);
      Shaders.setRenderStage(RenderStage.TERRAIN_SOLID);
    } 
  }
  
  public static void beginTerrainCutoutMipped() {
    if (Shaders.isRenderingWorld) {
      Shaders.useProgram(Shaders.ProgramTerrain);
      Shaders.setRenderStage(RenderStage.TERRAIN_CUTOUT_MIPPED);
    } 
  }
  
  public static void beginTerrainCutout() {
    if (Shaders.isRenderingWorld) {
      Shaders.useProgram(Shaders.ProgramTerrain);
      Shaders.setRenderStage(RenderStage.TERRAIN_CUTOUT);
    } 
  }
  
  public static void endTerrain() {
    if (Shaders.isRenderingWorld) {
      Shaders.useProgram(Shaders.ProgramTexturedLit);
      Shaders.setRenderStage(RenderStage.NONE);
    } 
  }
  
  public static void beginTranslucent() {
    if (Shaders.isRenderingWorld) {
      Shaders.useProgram(Shaders.ProgramWater);
      Shaders.setRenderStage(RenderStage.TERRAIN_TRANSLUCENT);
    } 
  }
  
  public static void endTranslucent() {
    if (Shaders.isRenderingWorld) {
      Shaders.useProgram(Shaders.ProgramTexturedLit);
      Shaders.setRenderStage(RenderStage.NONE);
    } 
  }
  
  public static void beginTripwire() {
    if (Shaders.isRenderingWorld)
      Shaders.setRenderStage(RenderStage.TRIPWIRE); 
  }
  
  public static void endTripwire() {
    if (Shaders.isRenderingWorld)
      Shaders.setRenderStage(RenderStage.NONE); 
  }
  
  public static void renderHand0(ges er, Matrix4f viewIn, ffy activeRenderInfo, float partialTicks) {
    if (!Shaders.isShadowPass) {
      boolean blockTranslucentMain = Shaders.isItemToRenderMainTranslucent();
      boolean blockTranslucentOff = Shaders.isItemToRenderOffTranslucent();
      if (!blockTranslucentMain || !blockTranslucentOff) {
        Shaders.readCenterDepth();
        Shaders.beginHand(false);
        Shaders.setSkipRenderHands(blockTranslucentMain, blockTranslucentOff);
        er.renderHand(activeRenderInfo, partialTicks, viewIn, true, false, false);
        Shaders.endHand();
        Shaders.setHandsRendered(!blockTranslucentMain, !blockTranslucentOff);
        Shaders.setSkipRenderHands(false, false);
      } 
    } 
  }
  
  public static void renderHand1(ges er, Matrix4f viewIn, ffy activeRenderInfo, float partialTicks) {
    if (!Shaders.isShadowPass)
      if (!Shaders.isBothHandsRendered()) {
        Shaders.readCenterDepth();
        GlStateManager._enableBlend();
        Shaders.beginHand(true);
        Shaders.setSkipRenderHands(Shaders.isHandRenderedMain(), Shaders.isHandRenderedOff());
        er.renderHand(activeRenderInfo, partialTicks, viewIn, true, false, true);
        Shaders.endHand();
        Shaders.setHandsRendered(true, true);
        Shaders.setSkipRenderHands(false, false);
      }  
  }
  
  public static void renderItemFP(gev itemRenderer, float partialTicks, fbi matrixStackIn, gez.a bufferIn, geb playerEntityIn, int combinedLightIn, boolean renderTranslucent) {
    Config.getEntityRenderDispatcher().setRenderedEntity((bsr)playerEntityIn);
    GlStateManager._depthMask(true);
    if (renderTranslucent) {
      GlStateManager._depthFunc(519);
      matrixStackIn.a();
      DrawBuffers drawBuffers = GlState.getDrawBuffers();
      GlState.setDrawBuffers(Shaders.drawBuffersNone);
      Shaders.renderItemKeepDepthMask = true;
      itemRenderer.a(partialTicks, matrixStackIn, bufferIn, playerEntityIn, combinedLightIn);
      Shaders.renderItemKeepDepthMask = false;
      GlState.setDrawBuffers(drawBuffers);
      matrixStackIn.b();
    } 
    GlStateManager._depthFunc(515);
    itemRenderer.a(partialTicks, matrixStackIn, bufferIn, playerEntityIn, combinedLightIn);
    Config.getEntityRenderDispatcher().setRenderedEntity(null);
  }
  
  public static void renderFPOverlay(ges er, Matrix4f viewIn, ffy activeRenderInfo, float partialTicks) {
    if (!Shaders.isShadowPass) {
      Shaders.beginFPOverlay();
      er.renderHand(activeRenderInfo, partialTicks, viewIn, false, true, false);
      Shaders.endFPOverlay();
    } 
  }
  
  public static void beginBlockDamage() {
    if (Shaders.isRenderingWorld) {
      Shaders.useProgram(Shaders.ProgramDamagedBlock);
      Shaders.setRenderStage(RenderStage.DESTROY);
      if (Shaders.ProgramDamagedBlock.getId() == Shaders.ProgramTerrain.getId()) {
        GlState.setDrawBuffers(Shaders.drawBuffersColorAtt[0]);
        GlStateManager._depthMask(false);
      } 
    } 
  }
  
  public static void endBlockDamage() {
    if (Shaders.isRenderingWorld) {
      GlStateManager._depthMask(true);
      Shaders.useProgram(Shaders.ProgramTexturedLit);
      Shaders.setRenderStage(RenderStage.NONE);
    } 
  }
  
  public static void beginOutline() {
    if (Shaders.isRenderingWorld) {
      Shaders.useProgram(Shaders.ProgramBasic);
      Shaders.setRenderStage(RenderStage.OUTLINE);
    } 
  }
  
  public static void endOutline() {
    if (Shaders.isRenderingWorld) {
      Shaders.useProgram(Shaders.ProgramTexturedLit);
      Shaders.setRenderStage(RenderStage.NONE);
    } 
  }
  
  public static void beginDebug() {
    if (Shaders.isRenderingWorld)
      Shaders.setRenderStage(RenderStage.DEBUG); 
  }
  
  public static void endDebug() {
    if (Shaders.isRenderingWorld)
      Shaders.setRenderStage(RenderStage.NONE); 
  }
  
  public static void renderShadowMap(ges entityRenderer, ffy activeRenderInfo, int pass, float partialTicks) {
    if (Shaders.hasShadowMap) {
      fgo mc = fgo.Q();
      mc.aH().b("shadow pass");
      gex renderGlobal = mc.f;
      Shaders.isShadowPass = true;
      Shaders.updateProjectionMatrix();
      Shaders.checkGLError("pre shadow");
      Matrix4f projectionPrev = RenderSystem.getProjectionMatrix();
      fbq vertexSortingPrev = RenderSystem.getVertexSorting();
      RenderSystem.getModelViewStack().pushMatrix();
      mc.aH().b("shadow clear");
      Shaders.sfb.bindFramebuffer();
      Shaders.checkGLError("shadow bind sfb");
      mc.aH().b("shadow camera");
      updateActiveRenderInfo(activeRenderInfo, mc, partialTicks);
      fbi matrixStack = new fbi();
      Shaders.setCameraShadow(matrixStack, activeRenderInfo, partialTicks);
      Matrix4f projectionMatrix = RenderSystem.getProjectionMatrix();
      Matrix4f viewMatrix = matrixStack.c().a();
      Shaders.checkGLError("shadow camera");
      Shaders.dispatchComputes(Shaders.dfb, Shaders.ProgramShadow.getComputePrograms());
      Shaders.useProgram(Shaders.ProgramShadow);
      Shaders.sfb.setDrawBuffers();
      Shaders.checkGLError("shadow drawbuffers");
      GL30.glReadBuffer(0);
      Shaders.checkGLError("shadow readbuffer");
      Shaders.sfb.setDepthTexture();
      Shaders.sfb.setColorTextures(true);
      Shaders.checkFramebufferStatus("shadow fb");
      GlStateManager._clearColor(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.clear(256);
      for (int i = 0; i < Shaders.usedShadowColorBuffers; i++) {
        if (Shaders.shadowBuffersClear[i]) {
          Vector4f col = Shaders.shadowBuffersClearColor[i];
          if (col != null) {
            GlStateManager._clearColor(col.x(), col.y(), col.z(), col.w());
          } else {
            GlStateManager._clearColor(1.0F, 1.0F, 1.0F, 1.0F);
          } 
          GlState.setDrawBuffers(Shaders.drawBuffersColorAtt[i]);
          GlStateManager.clear(16384);
        } 
      } 
      Shaders.sfb.setDrawBuffers();
      Shaders.checkGLError("shadow clear");
      mc.aH().b("shadow frustum");
      gie frustum = makeShadowFrustum(activeRenderInfo, partialTicks);
      mc.aH().b("shadow culling");
      exc cameraPos = activeRenderInfo.b();
      frustum.a(cameraPos.c, cameraPos.d, cameraPos.e);
      GlStateManager._enableDepthTest();
      GlStateManager._depthFunc(515);
      GlStateManager._depthMask(true);
      GlStateManager._colorMask(true, true, true, true);
      GlStateManager.lockCull(new GlCullState(false));
      GlStateManager.lockBlend(new GlBlendState(false));
      mc.aH().b("shadow prepareterrain");
      mc.aa().a(gqk.e);
      mc.aH().b("shadow setupterrain");
      renderGlobal.setShadowRenderInfos(true);
      Lagometer.timerVisibility.start();
      if (!renderGlobal.isDebugFrustum())
        applyFrustumShadow(renderGlobal, frustum); 
      Lagometer.timerVisibility.end();
      mc.aH().b("shadow updatechunks");
      mc.aH().b("shadow terrain");
      double x = cameraPos.a();
      double y = cameraPos.b();
      double z = cameraPos.c();
      Lagometer.timerTerrain.start();
      if (Shaders.isRenderShadowTerrain()) {
        GlStateManager.disableAlphaTest();
        renderGlobal.a(RenderTypes.SOLID, x, y, z, viewMatrix, projectionMatrix);
        Shaders.checkGLError("shadow terrain solid");
        GlStateManager.enableAlphaTest();
        renderGlobal.a(RenderTypes.CUTOUT_MIPPED, x, y, z, viewMatrix, projectionMatrix);
        Shaders.checkGLError("shadow terrain cutoutmipped");
        mc.aa().b(gqk.e).a(false, false);
        renderGlobal.a(RenderTypes.CUTOUT, x, y, z, viewMatrix, projectionMatrix);
        mc.aa().b(gqk.e).restoreLastBlurMipmap();
        Shaders.checkGLError("shadow terrain cutout");
      } 
      mc.aH().b("shadow entities");
      countEntitiesRenderedShadow = 0;
      countTileEntitiesRenderedShadow = 0;
      brc tickRateManager = mc.r.s();
      float frozenPartialTicks = tickRateManager.i() ? partialTicks : 1.0F;
      gex wr = mc.f;
      gkh renderManager = mc.ap();
      gez.a irendertypebuffer = wr.getRenderTypeTextures().c();
      boolean playerShadowPass = (Shaders.isShadowPass && !mc.s.R_());
      int minWorldY = mc.r.I_();
      int maxWorldY = mc.r.am();
      Iterable<bsr> entities = Shaders.isRenderShadowEntities() ? Shaders.getCurrentWorld().e() : Collections.EMPTY_LIST;
      for (bsr entity : entities) {
        if (!wr.shouldRenderEntity(entity, minWorldY, maxWorldY))
          continue; 
        if ((renderManager.a(entity, frustum, x, y, z) || entity.z((bsr)mc.s)) && (entity != activeRenderInfo.g() || playerShadowPass || activeRenderInfo.i() || (activeRenderInfo.g() instanceof btn && ((btn)activeRenderInfo.g()).fH())) && (!(entity instanceof geb) || activeRenderInfo.g() == entity)) {
          String key = entity.getClass().getName();
          List<bsr> listEntities = mapEntityLists.get(key);
          if (listEntities == null) {
            listEntities = new ArrayList<>();
            mapEntityLists.put(key, listEntities);
          } 
          listEntities.add(entity);
        } 
      } 
      Collection<List<bsr>> entityLists = mapEntityLists.values();
      for (List<bsr> entityList : entityLists) {
        for (bsr entity : entityList) {
          countEntitiesRenderedShadow++;
          Shaders.nextEntity(entity);
          float entityPartialTicks = tickRateManager.a(entity) ? frozenPartialTicks : partialTicks;
          wr.a(entity, x, y, z, entityPartialTicks, matrixStack, (gez)irendertypebuffer);
        } 
        entityList.clear();
      } 
      irendertypebuffer.a();
      wr.a(matrixStack);
      irendertypebuffer.a(gfh.c(gqk.e));
      irendertypebuffer.a(gfh.d(gqk.e));
      irendertypebuffer.a(gfh.e(gqk.e));
      irendertypebuffer.a(gfh.k(gqk.e));
      Shaders.endEntities();
      Shaders.beginBlockEntities();
      ghn.updateTextRenderDistance();
      boolean forgeRenderBoundingBox = Reflector.IForgeBlockEntity_getRenderBoundingBox.exists();
      gie camera = frustum;
      float blockEntityPartialTicks = tickRateManager.l() ? frozenPartialTicks : partialTicks;
      List<gia.b> renderInfosTileEntities = Shaders.isRenderShadowBlockEntities() ? wr.getRenderInfosTileEntities() : Collections.EMPTY_LIST;
      for (gia.b worldrenderer$localrenderinformationcontainer : renderInfosTileEntities) {
        List<dqh> list = worldrenderer$localrenderinformationcontainer.d().b();
        if (!list.isEmpty())
          for (dqh tileentity1 : list) {
            if (forgeRenderBoundingBox) {
              ewx aabb = (ewx)Reflector.call(tileentity1, Reflector.IForgeBlockEntity_getRenderBoundingBox, new Object[0]);
              if (aabb != null && !camera.a(aabb))
                continue; 
            } 
            countTileEntitiesRenderedShadow++;
            Shaders.nextBlockEntity(tileentity1);
            jd blockpos3 = tileentity1.aD_();
            matrixStack.a();
            matrixStack.a(blockpos3.u() - x, blockpos3.v() - y, blockpos3.w() - z);
            mc.aq().a(tileentity1, blockEntityPartialTicks, matrixStack, (gez)irendertypebuffer);
            matrixStack.b();
          }  
      } 
      wr.a(matrixStack);
      irendertypebuffer.a(gfh.c());
      irendertypebuffer.a(gfo.h());
      irendertypebuffer.a(gfo.i());
      irendertypebuffer.a(gfo.c());
      irendertypebuffer.a(gfo.d());
      irendertypebuffer.a(gfo.e());
      irendertypebuffer.a(gfo.g());
      irendertypebuffer.b();
      Shaders.endBlockEntities();
      Lagometer.timerTerrain.end();
      Shaders.checkGLError("shadow entities");
      GlStateManager._depthMask(true);
      GlStateManager._disableBlend();
      GlStateManager.unlockCull();
      GlStateManager._enableCull();
      GlStateManager._blendFuncSeparate(770, 771, 1, 0);
      GlStateManager.alphaFunc(516, 0.1F);
      if (Shaders.usedShadowDepthBuffers >= 2) {
        GlStateManager._activeTexture(33989);
        Shaders.checkGLError("pre copy shadow depth");
        GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, Shaders.shadowMapWidth, Shaders.shadowMapHeight);
        Shaders.checkGLError("copy shadow depth");
        GlStateManager._activeTexture(33984);
      } 
      GlStateManager._disableBlend();
      GlStateManager._depthMask(true);
      mc.aa().a(gqk.e);
      Shaders.checkGLError("shadow pre-translucent");
      Shaders.sfb.setDrawBuffers();
      Shaders.checkGLError("shadow drawbuffers pre-translucent");
      Shaders.checkFramebufferStatus("shadow pre-translucent");
      if (Shaders.isRenderShadowTranslucent()) {
        Lagometer.timerTerrain.start();
        mc.aH().b("shadow translucent");
        renderGlobal.a(RenderTypes.TRANSLUCENT, x, y, z, viewMatrix, projectionMatrix);
        Shaders.checkGLError("shadow translucent");
        Lagometer.timerTerrain.end();
      } 
      GlStateManager.unlockBlend();
      GlStateManager._depthMask(true);
      GlStateManager._enableCull();
      GlStateManager._disableBlend();
      GL30.glFlush();
      Shaders.checkGLError("shadow flush");
      Shaders.isShadowPass = false;
      renderGlobal.setShadowRenderInfos(false);
      mc.aH().b("shadow postprocess");
      if (Shaders.hasGlGenMipmap) {
        Shaders.sfb.generateDepthMipmaps(Shaders.shadowMipmapEnabled);
        Shaders.sfb.generateColorMipmaps(true, Shaders.shadowColorMipmapEnabled);
      } 
      Shaders.checkGLError("shadow postprocess");
      if (Shaders.hasShadowcompPrograms)
        Shaders.renderShadowComposites(); 
      Shaders.dfb.bindFramebuffer();
      GlStateManager._viewport(0, 0, Shaders.renderWidth, Shaders.renderHeight);
      GlState.setDrawBuffers(null);
      mc.aa().a(gqk.e);
      Shaders.useProgram(Shaders.ProgramTerrain);
      RenderSystem.getModelViewStack().popMatrix();
      RenderSystem.applyModelViewMatrix();
      RenderSystem.setProjectionMatrix(projectionPrev, vertexSortingPrev);
      Shaders.checkGLError("shadow end");
    } 
  }
  
  public static void applyFrustumShadow(gex renderGlobal, gie frustum) {
    fgo mc = Config.getMinecraft();
    mc.aH().a("apply_shadow_frustum");
    int shadowRenderDistance = (int)Shaders.getShadowRenderDistance();
    int farPlaneDistance = (int)Config.getGameRenderer().k();
    boolean checkDistance = (shadowRenderDistance > 0 && shadowRenderDistance < farPlaneDistance);
    int maxChunkDist = checkDistance ? shadowRenderDistance : -1;
    if (frustumTerrainShadowChanged || renderGlobal.needsFrustumUpdate()) {
      renderGlobal.applyFrustum(frustum, false, maxChunkDist);
      frustumTerrainShadowChanged = false;
    } 
    if (frustumEntitiesShadowChanged || mc.r.getSectionStorage().isUpdated()) {
      renderGlobal.applyFrustumEntities(frustum, maxChunkDist);
      frustumEntitiesShadowChanged = false;
    } 
    mc.aH().c();
  }
  
  public static gie makeShadowFrustum(ffy camera, float partialTicks) {
    if (!Shaders.isShadowCulling())
      return (gie)new ClippingHelperDummy(); 
    fgo mc = Config.getMinecraft();
    ges gameRenderer = Config.getGameRenderer();
    fbi matrixStackIn = new fbi();
    if (Reflector.ForgeEventFactoryClient_fireComputeCameraAngles.exists()) {
      ViewportEvent.ComputeCameraAngles cameraSetup = (ViewportEvent.ComputeCameraAngles)Reflector.ForgeEventFactoryClient_fireComputeCameraAngles.call(new Object[] { gameRenderer, camera, Float.valueOf(partialTicks) });
      camera.setRotation(cameraSetup.getYaw(), cameraSetup.getPitch(), cameraSetup.getRoll());
      matrixStackIn.a(a.f.rotationDegrees(cameraSetup.getRoll()));
    } 
    matrixStackIn.a(a.b.rotationDegrees(camera.d()));
    matrixStackIn.a(a.d.rotationDegrees(camera.e() + 180.0F));
    double fov = gameRenderer.a(camera, partialTicks, true);
    double fovProjection = Math.max(fov, ((Integer)mc.m.ah().c()).intValue());
    Matrix4f matrixProjection = gameRenderer.a(fovProjection);
    Matrix4f matrix4f = matrixStackIn.c().a();
    exc pos = camera.b();
    double x = pos.a();
    double y = pos.b();
    double z = pos.c();
    ShadowFrustum shadowFrustum = new ShadowFrustum(matrix4f, matrixProjection);
    shadowFrustum.a(x, y, z);
    return (gie)shadowFrustum;
  }
  
  public static void updateActiveRenderInfo(ffy activeRenderInfo, fgo mc, float partialTicks) {
    activeRenderInfo.a((dcc)mc.r, (mc.an() == null) ? (bsr)mc.s : mc.an(), !mc.m.aB().a(), mc.m.aB().b(), partialTicks);
  }
  
  public static void preRenderChunkLayer(gfh blockLayerIn) {
    if (blockLayerIn == RenderTypes.SOLID)
      beginTerrainSolid(); 
    if (blockLayerIn == RenderTypes.CUTOUT_MIPPED)
      beginTerrainCutoutMipped(); 
    if (blockLayerIn == RenderTypes.CUTOUT)
      beginTerrainCutout(); 
    if (blockLayerIn == RenderTypes.TRANSLUCENT)
      beginTranslucent(); 
    if (blockLayerIn == gfh.t())
      beginTripwire(); 
    if (Shaders.isRenderBackFace(blockLayerIn))
      GlStateManager._disableCull(); 
  }
  
  public static void postRenderChunkLayer(gfh blockLayerIn) {
    if (Shaders.isRenderBackFace(blockLayerIn))
      GlStateManager._enableCull(); 
  }
  
  public static void preRender(gfh renderType) {
    if (!Shaders.isRenderingWorld)
      return; 
    if (Shaders.isShadowPass)
      return; 
    if (renderType.isGlint()) {
      renderEnchantedGlintBegin();
    } else if (renderType.getName().equals("eyes")) {
      Shaders.beginSpiderEyes();
    } else if (renderType.getName().equals("crumbling")) {
      beginBlockDamage();
    } else if (renderType == gfh.aT || renderType == gfh.aU) {
      Shaders.beginLines();
    } else if (renderType == gfh.i()) {
      Shaders.beginWaterMask();
    } else if (renderType.getName().equals("beacon_beam")) {
      Shaders.beginBeacon();
    } 
  }
  
  public static void postRender(gfh renderType) {
    if (!Shaders.isRenderingWorld)
      return; 
    if (Shaders.isShadowPass)
      return; 
    if (renderType.isGlint()) {
      renderEnchantedGlintEnd();
    } else if (renderType.getName().equals("eyes")) {
      Shaders.endSpiderEyes();
    } else if (renderType.getName().equals("crumbling")) {
      endBlockDamage();
    } else if (renderType == gfh.aT || renderType == gfh.aU) {
      Shaders.endLines();
    } else if (renderType == gfh.i()) {
      Shaders.endWaterMask();
    } else if (renderType.getName().equals("beacon_beam")) {
      Shaders.endBeacon();
    } 
  }
  
  public static void enableArrayPointerVbo() {
    GL20.glEnableVertexAttribArray(Shaders.midBlockAttrib);
    GL20.glEnableVertexAttribArray(Shaders.midTexCoordAttrib);
    GL20.glEnableVertexAttribArray(Shaders.tangentAttrib);
    GL20.glEnableVertexAttribArray(Shaders.entityAttrib);
  }
  
  public static void setupArrayPointersVbo() {
    int vertexSizeI = 18;
    enableArrayPointerVbo();
    GL20.glVertexAttribPointer(Shaders.midBlockAttrib, 3, 5120, false, 72, 32L);
    GL20.glVertexAttribPointer(Shaders.midTexCoordAttrib, 2, 5126, false, 72, 36L);
    GL20.glVertexAttribPointer(Shaders.tangentAttrib, 4, 5122, false, 72, 44L);
    GL20.glVertexAttribPointer(Shaders.entityAttrib, 3, 5122, false, 72, 52L);
  }
  
  public static void beaconBeamBegin() {
    Shaders.useProgram(Shaders.ProgramBeaconBeam);
  }
  
  public static void beaconBeamStartQuad1() {}
  
  public static void beaconBeamStartQuad2() {}
  
  public static void beaconBeamDraw1() {}
  
  public static void beaconBeamDraw2() {
    GlStateManager._disableBlend();
  }
  
  public static void renderEnchantedGlintBegin() {
    Shaders.useProgram(Shaders.ProgramArmorGlint);
  }
  
  public static void renderEnchantedGlintEnd() {
    if (Shaders.isRenderingWorld) {
      if (Shaders.isRenderingFirstPersonHand() && Shaders.isRenderBothHands()) {
        Shaders.useProgram(Shaders.ProgramHand);
      } else {
        Shaders.useProgram(Shaders.ProgramEntities);
      } 
    } else {
      Shaders.useProgram(Shaders.ProgramNone);
    } 
  }
  
  public static boolean renderEndPortal(drz te, float partialTicks, float offset, fbi matrixStackIn, gez bufferIn, int combinedLightIn, int combinedOverlayIn) {
    if (!Shaders.isShadowPass && Shaders.activeProgram.getId() == 0)
      return false; 
    fbi.a matrixEntry = matrixStackIn.c();
    Matrix4f matrix = matrixEntry.a();
    Matrix3f matrixNormal = matrixEntry.b();
    fbm bufferbuilder = bufferIn.getBuffer(gfh.c(END_PORTAL_TEXTURE));
    float col = 0.5F;
    float r = col * 0.15F;
    float g = col * 0.3F;
    float b = col * 0.4F;
    float u0 = 0.0F;
    float u1 = 0.2F;
    float v0 = u0;
    float v1 = u1;
    float du = (float)(System.currentTimeMillis() % 100000L) / 100000.0F;
    float dv = du;
    float dy = offset;
    int lm = combinedLightIn;
    int ov = combinedOverlayIn;
    float x = 0.0F;
    float y = 0.0F;
    float z = 0.0F;
    if (te.a(ji.d)) {
      kh vec3i = ji.d.q();
      float xv = vec3i.u();
      float yv = vec3i.v();
      float zv = vec3i.w();
      float xn = MathUtils.getTransformX(matrixNormal, xv, yv, zv);
      float yn = MathUtils.getTransformY(matrixNormal, xv, yv, zv);
      float zn = MathUtils.getTransformZ(matrixNormal, xv, yv, zv);
      bufferbuilder.a(matrix, x, y, z + 1.0F).a(r, g, b, 1.0F).a(u0 + du, v0 + dv).b(ov).c(lm).b(xn, yn, zn);
      bufferbuilder.a(matrix, x + 1.0F, y, z + 1.0F).a(r, g, b, 1.0F).a(u0 + du, v1 + dv).b(ov).c(lm).b(xn, yn, zn);
      bufferbuilder.a(matrix, x + 1.0F, y + 1.0F, z + 1.0F).a(r, g, b, 1.0F).a(u1 + du, v1 + dv).b(ov).c(lm).b(xn, yn, zn);
      bufferbuilder.a(matrix, x, y + 1.0F, z + 1.0F).a(r, g, b, 1.0F).a(u1 + du, v0 + dv).b(ov).c(lm).b(xn, yn, zn);
    } 
    if (te.a(ji.c)) {
      kh vec3i = ji.c.q();
      float xv = vec3i.u();
      float yv = vec3i.v();
      float zv = vec3i.w();
      float xn = MathUtils.getTransformX(matrixNormal, xv, yv, zv);
      float yn = MathUtils.getTransformY(matrixNormal, xv, yv, zv);
      float zn = MathUtils.getTransformZ(matrixNormal, xv, yv, zv);
      bufferbuilder.a(matrix, x, y + 1.0F, z).a(r, g, b, 1.0F).a(u1 + du, v1 + dv).b(ov).c(lm).b(xn, yn, zn);
      bufferbuilder.a(matrix, x + 1.0F, y + 1.0F, z).a(r, g, b, 1.0F).a(u1 + du, v0 + dv).b(ov).c(lm).b(xn, yn, zn);
      bufferbuilder.a(matrix, x + 1.0F, y, z).a(r, g, b, 1.0F).a(u0 + du, v0 + dv).b(ov).c(lm).b(xn, yn, zn);
      bufferbuilder.a(matrix, x, y, z).a(r, g, b, 1.0F).a(u0 + du, v1 + dv).b(ov).c(lm).b(xn, yn, zn);
    } 
    if (te.a(ji.f)) {
      kh vec3i = ji.f.q();
      float xv = vec3i.u();
      float yv = vec3i.v();
      float zv = vec3i.w();
      float xn = MathUtils.getTransformX(matrixNormal, xv, yv, zv);
      float yn = MathUtils.getTransformY(matrixNormal, xv, yv, zv);
      float zn = MathUtils.getTransformZ(matrixNormal, xv, yv, zv);
      bufferbuilder.a(matrix, x + 1.0F, y + 1.0F, z).a(r, g, b, 1.0F).a(u1 + du, v1 + dv).b(ov).c(lm).b(xn, yn, zn);
      bufferbuilder.a(matrix, x + 1.0F, y + 1.0F, z + 1.0F).a(r, g, b, 1.0F).a(u1 + du, v0 + dv).b(ov).c(lm).b(xn, yn, zn);
      bufferbuilder.a(matrix, x + 1.0F, y, z + 1.0F).a(r, g, b, 1.0F).a(u0 + du, v0 + dv).b(ov).c(lm).b(xn, yn, zn);
      bufferbuilder.a(matrix, x + 1.0F, y, z).a(r, g, b, 1.0F).a(u0 + du, v1 + dv).b(ov).c(lm).b(xn, yn, zn);
    } 
    if (te.a(ji.e)) {
      kh vec3i = ji.e.q();
      float xv = vec3i.u();
      float yv = vec3i.v();
      float zv = vec3i.w();
      float xn = MathUtils.getTransformX(matrixNormal, xv, yv, zv);
      float yn = MathUtils.getTransformY(matrixNormal, xv, yv, zv);
      float zn = MathUtils.getTransformZ(matrixNormal, xv, yv, zv);
      bufferbuilder.a(matrix, x, y, z).a(r, g, b, 1.0F).a(u0 + du, v0 + dv).b(ov).c(lm).b(xn, yn, zn);
      bufferbuilder.a(matrix, x, y, z + 1.0F).a(r, g, b, 1.0F).a(u0 + du, v1 + dv).b(ov).c(lm).b(xn, yn, zn);
      bufferbuilder.a(matrix, x, y + 1.0F, z + 1.0F).a(r, g, b, 1.0F).a(u1 + du, v1 + dv).b(ov).c(lm).b(xn, yn, zn);
      bufferbuilder.a(matrix, x, y + 1.0F, z).a(r, g, b, 1.0F).a(u1 + du, v0 + dv).b(ov).c(lm).b(xn, yn, zn);
    } 
    if (te.a(ji.a)) {
      kh vec3i = ji.a.q();
      float xv = vec3i.u();
      float yv = vec3i.v();
      float zv = vec3i.w();
      float xn = MathUtils.getTransformX(matrixNormal, xv, yv, zv);
      float yn = MathUtils.getTransformY(matrixNormal, xv, yv, zv);
      float zn = MathUtils.getTransformZ(matrixNormal, xv, yv, zv);
      bufferbuilder.a(matrix, x, y, z).a(r, g, b, 1.0F).a(u0 + du, v0 + dv).b(ov).c(lm).b(xn, yn, zn);
      bufferbuilder.a(matrix, x + 1.0F, y, z).a(r, g, b, 1.0F).a(u0 + du, v1 + dv).b(ov).c(lm).b(xn, yn, zn);
      bufferbuilder.a(matrix, x + 1.0F, y, z + 1.0F).a(r, g, b, 1.0F).a(u1 + du, v1 + dv).b(ov).c(lm).b(xn, yn, zn);
      bufferbuilder.a(matrix, x, y, z + 1.0F).a(r, g, b, 1.0F).a(u1 + du, v0 + dv).b(ov).c(lm).b(xn, yn, zn);
    } 
    if (te.a(ji.b)) {
      kh vec3i = ji.b.q();
      float xv = vec3i.u();
      float yv = vec3i.v();
      float zv = vec3i.w();
      float xn = MathUtils.getTransformX(matrixNormal, xv, yv, zv);
      float yn = MathUtils.getTransformY(matrixNormal, xv, yv, zv);
      float zn = MathUtils.getTransformZ(matrixNormal, xv, yv, zv);
      bufferbuilder.a(matrix, x, y + dy, z + 1.0F).a(r, g, b, 1.0F).a(u0 + du, v0 + dv).b(ov).c(lm).b(xn, yn, zn);
      bufferbuilder.a(matrix, x + 1.0F, y + dy, z + 1.0F).a(r, g, b, 1.0F).a(u0 + du, v1 + dv).b(ov).c(lm).b(xn, yn, zn);
      bufferbuilder.a(matrix, x + 1.0F, y + dy, z).a(r, g, b, 1.0F).a(u1 + du, v1 + dv).b(ov).c(lm).b(xn, yn, zn);
      bufferbuilder.a(matrix, x, y + dy, z).a(r, g, b, 1.0F).a(u1 + du, v0 + dv).b(ov).c(lm).b(xn, yn, zn);
    } 
    return true;
  }
}

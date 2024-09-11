package srg.net.optifine.shaders;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexSorting;
import com.mojang.math.Axis;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.TickRateManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
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
  private static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");
  
  public static boolean frustumTerrainShadowChanged = false;
  
  public static boolean frustumEntitiesShadowChanged = false;
  
  public static int countEntitiesRenderedShadow;
  
  public static int countTileEntitiesRenderedShadow;
  
  private static Map<String, List<Entity>> mapEntityLists = new HashMap<>();
  
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
  
  public static void renderHand0(GameRenderer er, Matrix4f viewIn, Camera activeRenderInfo, float partialTicks) {
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
  
  public static void renderHand1(GameRenderer er, Matrix4f viewIn, Camera activeRenderInfo, float partialTicks) {
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
  
  public static void renderItemFP(ItemInHandRenderer itemRenderer, float partialTicks, PoseStack matrixStackIn, MultiBufferSource.BufferSource bufferIn, LocalPlayer playerEntityIn, int combinedLightIn, boolean renderTranslucent) {
    Config.getEntityRenderDispatcher().setRenderedEntity((Entity)playerEntityIn);
    GlStateManager._depthMask(true);
    if (renderTranslucent) {
      GlStateManager._depthFunc(519);
      matrixStackIn.pushPose();
      DrawBuffers drawBuffers = GlState.getDrawBuffers();
      GlState.setDrawBuffers(Shaders.drawBuffersNone);
      Shaders.renderItemKeepDepthMask = true;
      itemRenderer.renderHandsWithItems(partialTicks, matrixStackIn, bufferIn, playerEntityIn, combinedLightIn);
      Shaders.renderItemKeepDepthMask = false;
      GlState.setDrawBuffers(drawBuffers);
      matrixStackIn.popPose();
    } 
    GlStateManager._depthFunc(515);
    itemRenderer.renderHandsWithItems(partialTicks, matrixStackIn, bufferIn, playerEntityIn, combinedLightIn);
    Config.getEntityRenderDispatcher().setRenderedEntity(null);
  }
  
  public static void renderFPOverlay(GameRenderer er, Matrix4f viewIn, Camera activeRenderInfo, float partialTicks) {
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
  
  public static void renderShadowMap(GameRenderer entityRenderer, Camera activeRenderInfo, int pass, float partialTicks) {
    if (Shaders.hasShadowMap) {
      Minecraft mc = Minecraft.getInstance();
      mc.getProfiler().popPush("shadow pass");
      LevelRenderer renderGlobal = mc.levelRenderer;
      Shaders.isShadowPass = true;
      Shaders.updateProjectionMatrix();
      Shaders.checkGLError("pre shadow");
      Matrix4f projectionPrev = RenderSystem.getProjectionMatrix();
      VertexSorting vertexSortingPrev = RenderSystem.getVertexSorting();
      RenderSystem.getModelViewStack().pushMatrix();
      mc.getProfiler().popPush("shadow clear");
      Shaders.sfb.bindFramebuffer();
      Shaders.checkGLError("shadow bind sfb");
      mc.getProfiler().popPush("shadow camera");
      updateActiveRenderInfo(activeRenderInfo, mc, partialTicks);
      PoseStack matrixStack = new PoseStack();
      Shaders.setCameraShadow(matrixStack, activeRenderInfo, partialTicks);
      Matrix4f projectionMatrix = RenderSystem.getProjectionMatrix();
      Matrix4f viewMatrix = matrixStack.last().pose();
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
      mc.getProfiler().popPush("shadow frustum");
      Frustum frustum = makeShadowFrustum(activeRenderInfo, partialTicks);
      mc.getProfiler().popPush("shadow culling");
      Vec3 cameraPos = activeRenderInfo.getPosition();
      frustum.prepare(cameraPos.x, cameraPos.y, cameraPos.z);
      GlStateManager._enableDepthTest();
      GlStateManager._depthFunc(515);
      GlStateManager._depthMask(true);
      GlStateManager._colorMask(true, true, true, true);
      GlStateManager.lockCull(new GlCullState(false));
      GlStateManager.lockBlend(new GlBlendState(false));
      mc.getProfiler().popPush("shadow prepareterrain");
      mc.getTextureManager().bindForSetup(TextureAtlas.LOCATION_BLOCKS);
      mc.getProfiler().popPush("shadow setupterrain");
      renderGlobal.setShadowRenderInfos(true);
      Lagometer.timerVisibility.start();
      if (!renderGlobal.isDebugFrustum())
        applyFrustumShadow(renderGlobal, frustum); 
      Lagometer.timerVisibility.end();
      mc.getProfiler().popPush("shadow updatechunks");
      mc.getProfiler().popPush("shadow terrain");
      double x = cameraPos.x();
      double y = cameraPos.y();
      double z = cameraPos.z();
      Lagometer.timerTerrain.start();
      if (Shaders.isRenderShadowTerrain()) {
        GlStateManager.disableAlphaTest();
        renderGlobal.renderSectionLayer(RenderTypes.SOLID, x, y, z, viewMatrix, projectionMatrix);
        Shaders.checkGLError("shadow terrain solid");
        GlStateManager.enableAlphaTest();
        renderGlobal.renderSectionLayer(RenderTypes.CUTOUT_MIPPED, x, y, z, viewMatrix, projectionMatrix);
        Shaders.checkGLError("shadow terrain cutoutmipped");
        mc.getTextureManager().getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
        renderGlobal.renderSectionLayer(RenderTypes.CUTOUT, x, y, z, viewMatrix, projectionMatrix);
        mc.getTextureManager().getTexture(TextureAtlas.LOCATION_BLOCKS).restoreLastBlurMipmap();
        Shaders.checkGLError("shadow terrain cutout");
      } 
      mc.getProfiler().popPush("shadow entities");
      countEntitiesRenderedShadow = 0;
      countTileEntitiesRenderedShadow = 0;
      TickRateManager tickRateManager = mc.level.tickRateManager();
      float frozenPartialTicks = tickRateManager.runsNormally() ? partialTicks : 1.0F;
      LevelRenderer wr = mc.levelRenderer;
      EntityRenderDispatcher renderManager = mc.getEntityRenderDispatcher();
      MultiBufferSource.BufferSource irendertypebuffer = wr.getRenderTypeTextures().bufferSource();
      boolean playerShadowPass = (Shaders.isShadowPass && !mc.player.isSpectator());
      int minWorldY = mc.level.getMinBuildHeight();
      int maxWorldY = mc.level.getMaxBuildHeight();
      Iterable<Entity> entities = Shaders.isRenderShadowEntities() ? Shaders.getCurrentWorld().entitiesForRendering() : Collections.EMPTY_LIST;
      for (Entity entity : entities) {
        if (!wr.shouldRenderEntity(entity, minWorldY, maxWorldY))
          continue; 
        if ((renderManager.shouldRender(entity, frustum, x, y, z) || entity.hasIndirectPassenger((Entity)mc.player)) && (entity != activeRenderInfo.getEntity() || playerShadowPass || activeRenderInfo.isDetached() || (activeRenderInfo.getEntity() instanceof LivingEntity && ((LivingEntity)activeRenderInfo.getEntity()).isSleeping())) && (!(entity instanceof LocalPlayer) || activeRenderInfo.getEntity() == entity)) {
          String key = entity.getClass().getName();
          List<Entity> listEntities = mapEntityLists.get(key);
          if (listEntities == null) {
            listEntities = new ArrayList<>();
            mapEntityLists.put(key, listEntities);
          } 
          listEntities.add(entity);
        } 
      } 
      Collection<List<Entity>> entityLists = mapEntityLists.values();
      for (List<Entity> entityList : entityLists) {
        for (Entity entity : entityList) {
          countEntitiesRenderedShadow++;
          Shaders.nextEntity(entity);
          float entityPartialTicks = tickRateManager.isEntityFrozen(entity) ? frozenPartialTicks : partialTicks;
          wr.renderEntity(entity, x, y, z, entityPartialTicks, matrixStack, (MultiBufferSource)irendertypebuffer);
        } 
        entityList.clear();
      } 
      irendertypebuffer.endLastBatch();
      wr.checkPoseStack(matrixStack);
      irendertypebuffer.endBatch(RenderType.entitySolid(TextureAtlas.LOCATION_BLOCKS));
      irendertypebuffer.endBatch(RenderType.entityCutout(TextureAtlas.LOCATION_BLOCKS));
      irendertypebuffer.endBatch(RenderType.entityCutoutNoCull(TextureAtlas.LOCATION_BLOCKS));
      irendertypebuffer.endBatch(RenderType.entitySmoothCutout(TextureAtlas.LOCATION_BLOCKS));
      Shaders.endEntities();
      Shaders.beginBlockEntities();
      SignRenderer.updateTextRenderDistance();
      boolean forgeRenderBoundingBox = Reflector.IForgeBlockEntity_getRenderBoundingBox.exists();
      Frustum camera = frustum;
      float blockEntityPartialTicks = tickRateManager.isFrozen() ? frozenPartialTicks : partialTicks;
      List<SectionRenderDispatcher.RenderSection> renderInfosTileEntities = Shaders.isRenderShadowBlockEntities() ? wr.getRenderInfosTileEntities() : Collections.EMPTY_LIST;
      for (SectionRenderDispatcher.RenderSection worldrenderer$localrenderinformationcontainer : renderInfosTileEntities) {
        List<BlockEntity> list = worldrenderer$localrenderinformationcontainer.getCompiled().getRenderableBlockEntities();
        if (!list.isEmpty())
          for (BlockEntity tileentity1 : list) {
            if (forgeRenderBoundingBox) {
              AABB aabb = (AABB)Reflector.call(tileentity1, Reflector.IForgeBlockEntity_getRenderBoundingBox, new Object[0]);
              if (aabb != null && !camera.isVisible(aabb))
                continue; 
            } 
            countTileEntitiesRenderedShadow++;
            Shaders.nextBlockEntity(tileentity1);
            BlockPos blockpos3 = tileentity1.getBlockPos();
            matrixStack.pushPose();
            matrixStack.translate(blockpos3.getX() - x, blockpos3.getY() - y, blockpos3.getZ() - z);
            mc.getBlockEntityRenderDispatcher().render(tileentity1, blockEntityPartialTicks, matrixStack, (MultiBufferSource)irendertypebuffer);
            matrixStack.popPose();
          }  
      } 
      wr.checkPoseStack(matrixStack);
      irendertypebuffer.endBatch(RenderType.solid());
      irendertypebuffer.endBatch(Sheets.solidBlockSheet());
      irendertypebuffer.endBatch(Sheets.cutoutBlockSheet());
      irendertypebuffer.endBatch(Sheets.bedSheet());
      irendertypebuffer.endBatch(Sheets.shulkerBoxSheet());
      irendertypebuffer.endBatch(Sheets.signSheet());
      irendertypebuffer.endBatch(Sheets.chestSheet());
      irendertypebuffer.endBatch();
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
      mc.getTextureManager().bindForSetup(TextureAtlas.LOCATION_BLOCKS);
      Shaders.checkGLError("shadow pre-translucent");
      Shaders.sfb.setDrawBuffers();
      Shaders.checkGLError("shadow drawbuffers pre-translucent");
      Shaders.checkFramebufferStatus("shadow pre-translucent");
      if (Shaders.isRenderShadowTranslucent()) {
        Lagometer.timerTerrain.start();
        mc.getProfiler().popPush("shadow translucent");
        renderGlobal.renderSectionLayer(RenderTypes.TRANSLUCENT, x, y, z, viewMatrix, projectionMatrix);
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
      mc.getProfiler().popPush("shadow postprocess");
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
      mc.getTextureManager().bindForSetup(TextureAtlas.LOCATION_BLOCKS);
      Shaders.useProgram(Shaders.ProgramTerrain);
      RenderSystem.getModelViewStack().popMatrix();
      RenderSystem.applyModelViewMatrix();
      RenderSystem.setProjectionMatrix(projectionPrev, vertexSortingPrev);
      Shaders.checkGLError("shadow end");
    } 
  }
  
  public static void applyFrustumShadow(LevelRenderer renderGlobal, Frustum frustum) {
    Minecraft mc = Config.getMinecraft();
    mc.getProfiler().push("apply_shadow_frustum");
    int shadowRenderDistance = (int)Shaders.getShadowRenderDistance();
    int farPlaneDistance = (int)Config.getGameRenderer().getRenderDistance();
    boolean checkDistance = (shadowRenderDistance > 0 && shadowRenderDistance < farPlaneDistance);
    int maxChunkDist = checkDistance ? shadowRenderDistance : -1;
    if (frustumTerrainShadowChanged || renderGlobal.needsFrustumUpdate()) {
      renderGlobal.applyFrustum(frustum, false, maxChunkDist);
      frustumTerrainShadowChanged = false;
    } 
    if (frustumEntitiesShadowChanged || mc.level.getSectionStorage().isUpdated()) {
      renderGlobal.applyFrustumEntities(frustum, maxChunkDist);
      frustumEntitiesShadowChanged = false;
    } 
    mc.getProfiler().pop();
  }
  
  public static Frustum makeShadowFrustum(Camera camera, float partialTicks) {
    if (!Shaders.isShadowCulling())
      return (Frustum)new ClippingHelperDummy(); 
    Minecraft mc = Config.getMinecraft();
    GameRenderer gameRenderer = Config.getGameRenderer();
    PoseStack matrixStackIn = new PoseStack();
    if (Reflector.ForgeEventFactoryClient_fireComputeCameraAngles.exists()) {
      ViewportEvent.ComputeCameraAngles cameraSetup = (ViewportEvent.ComputeCameraAngles)Reflector.ForgeEventFactoryClient_fireComputeCameraAngles.call(new Object[] { gameRenderer, camera, Float.valueOf(partialTicks) });
      camera.setRotation(cameraSetup.getYaw(), cameraSetup.getPitch(), cameraSetup.getRoll());
      matrixStackIn.mulPose(Axis.ZP.rotationDegrees(cameraSetup.getRoll()));
    } 
    matrixStackIn.mulPose(Axis.XP.rotationDegrees(camera.getXRot()));
    matrixStackIn.mulPose(Axis.YP.rotationDegrees(camera.getYRot() + 180.0F));
    double fov = gameRenderer.getFov(camera, partialTicks, true);
    double fovProjection = Math.max(fov, ((Integer)mc.options.fov().get()).intValue());
    Matrix4f matrixProjection = gameRenderer.getProjectionMatrix(fovProjection);
    Matrix4f matrix4f = matrixStackIn.last().pose();
    Vec3 pos = camera.getPosition();
    double x = pos.x();
    double y = pos.y();
    double z = pos.z();
    ShadowFrustum shadowFrustum = new ShadowFrustum(matrix4f, matrixProjection);
    shadowFrustum.prepare(x, y, z);
    return (Frustum)shadowFrustum;
  }
  
  public static void updateActiveRenderInfo(Camera activeRenderInfo, Minecraft mc, float partialTicks) {
    activeRenderInfo.setup((BlockGetter)mc.level, (mc.getCameraEntity() == null) ? (Entity)mc.player : mc.getCameraEntity(), !mc.options.getCameraType().isFirstPerson(), mc.options.getCameraType().isMirrored(), partialTicks);
  }
  
  public static void preRenderChunkLayer(RenderType blockLayerIn) {
    if (blockLayerIn == RenderTypes.SOLID)
      beginTerrainSolid(); 
    if (blockLayerIn == RenderTypes.CUTOUT_MIPPED)
      beginTerrainCutoutMipped(); 
    if (blockLayerIn == RenderTypes.CUTOUT)
      beginTerrainCutout(); 
    if (blockLayerIn == RenderTypes.TRANSLUCENT)
      beginTranslucent(); 
    if (blockLayerIn == RenderType.tripwire())
      beginTripwire(); 
    if (Shaders.isRenderBackFace(blockLayerIn))
      GlStateManager._disableCull(); 
  }
  
  public static void postRenderChunkLayer(RenderType blockLayerIn) {
    if (Shaders.isRenderBackFace(blockLayerIn))
      GlStateManager._enableCull(); 
  }
  
  public static void preRender(RenderType renderType) {
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
    } else if (renderType == RenderType.LINES || renderType == RenderType.LINE_STRIP) {
      Shaders.beginLines();
    } else if (renderType == RenderType.waterMask()) {
      Shaders.beginWaterMask();
    } else if (renderType.getName().equals("beacon_beam")) {
      Shaders.beginBeacon();
    } 
  }
  
  public static void postRender(RenderType renderType) {
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
    } else if (renderType == RenderType.LINES || renderType == RenderType.LINE_STRIP) {
      Shaders.endLines();
    } else if (renderType == RenderType.waterMask()) {
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
  
  public static boolean renderEndPortal(TheEndPortalBlockEntity te, float partialTicks, float offset, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
    if (!Shaders.isShadowPass && Shaders.activeProgram.getId() == 0)
      return false; 
    PoseStack.Pose matrixEntry = matrixStackIn.last();
    Matrix4f matrix = matrixEntry.pose();
    Matrix3f matrixNormal = matrixEntry.normal();
    VertexConsumer bufferbuilder = bufferIn.getBuffer(RenderType.entitySolid(END_PORTAL_TEXTURE));
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
    if (te.shouldRenderFace(Direction.SOUTH)) {
      Vec3i vec3i = Direction.SOUTH.getNormal();
      float xv = vec3i.getX();
      float yv = vec3i.getY();
      float zv = vec3i.getZ();
      float xn = MathUtils.getTransformX(matrixNormal, xv, yv, zv);
      float yn = MathUtils.getTransformY(matrixNormal, xv, yv, zv);
      float zn = MathUtils.getTransformZ(matrixNormal, xv, yv, zv);
      bufferbuilder.addVertex(matrix, x, y, z + 1.0F).setColor(r, g, b, 1.0F).setUv(u0 + du, v0 + dv).setOverlay(ov).setLight(lm).setNormal(xn, yn, zn);
      bufferbuilder.addVertex(matrix, x + 1.0F, y, z + 1.0F).setColor(r, g, b, 1.0F).setUv(u0 + du, v1 + dv).setOverlay(ov).setLight(lm).setNormal(xn, yn, zn);
      bufferbuilder.addVertex(matrix, x + 1.0F, y + 1.0F, z + 1.0F).setColor(r, g, b, 1.0F).setUv(u1 + du, v1 + dv).setOverlay(ov).setLight(lm).setNormal(xn, yn, zn);
      bufferbuilder.addVertex(matrix, x, y + 1.0F, z + 1.0F).setColor(r, g, b, 1.0F).setUv(u1 + du, v0 + dv).setOverlay(ov).setLight(lm).setNormal(xn, yn, zn);
    } 
    if (te.shouldRenderFace(Direction.NORTH)) {
      Vec3i vec3i = Direction.NORTH.getNormal();
      float xv = vec3i.getX();
      float yv = vec3i.getY();
      float zv = vec3i.getZ();
      float xn = MathUtils.getTransformX(matrixNormal, xv, yv, zv);
      float yn = MathUtils.getTransformY(matrixNormal, xv, yv, zv);
      float zn = MathUtils.getTransformZ(matrixNormal, xv, yv, zv);
      bufferbuilder.addVertex(matrix, x, y + 1.0F, z).setColor(r, g, b, 1.0F).setUv(u1 + du, v1 + dv).setOverlay(ov).setLight(lm).setNormal(xn, yn, zn);
      bufferbuilder.addVertex(matrix, x + 1.0F, y + 1.0F, z).setColor(r, g, b, 1.0F).setUv(u1 + du, v0 + dv).setOverlay(ov).setLight(lm).setNormal(xn, yn, zn);
      bufferbuilder.addVertex(matrix, x + 1.0F, y, z).setColor(r, g, b, 1.0F).setUv(u0 + du, v0 + dv).setOverlay(ov).setLight(lm).setNormal(xn, yn, zn);
      bufferbuilder.addVertex(matrix, x, y, z).setColor(r, g, b, 1.0F).setUv(u0 + du, v1 + dv).setOverlay(ov).setLight(lm).setNormal(xn, yn, zn);
    } 
    if (te.shouldRenderFace(Direction.EAST)) {
      Vec3i vec3i = Direction.EAST.getNormal();
      float xv = vec3i.getX();
      float yv = vec3i.getY();
      float zv = vec3i.getZ();
      float xn = MathUtils.getTransformX(matrixNormal, xv, yv, zv);
      float yn = MathUtils.getTransformY(matrixNormal, xv, yv, zv);
      float zn = MathUtils.getTransformZ(matrixNormal, xv, yv, zv);
      bufferbuilder.addVertex(matrix, x + 1.0F, y + 1.0F, z).setColor(r, g, b, 1.0F).setUv(u1 + du, v1 + dv).setOverlay(ov).setLight(lm).setNormal(xn, yn, zn);
      bufferbuilder.addVertex(matrix, x + 1.0F, y + 1.0F, z + 1.0F).setColor(r, g, b, 1.0F).setUv(u1 + du, v0 + dv).setOverlay(ov).setLight(lm).setNormal(xn, yn, zn);
      bufferbuilder.addVertex(matrix, x + 1.0F, y, z + 1.0F).setColor(r, g, b, 1.0F).setUv(u0 + du, v0 + dv).setOverlay(ov).setLight(lm).setNormal(xn, yn, zn);
      bufferbuilder.addVertex(matrix, x + 1.0F, y, z).setColor(r, g, b, 1.0F).setUv(u0 + du, v1 + dv).setOverlay(ov).setLight(lm).setNormal(xn, yn, zn);
    } 
    if (te.shouldRenderFace(Direction.WEST)) {
      Vec3i vec3i = Direction.WEST.getNormal();
      float xv = vec3i.getX();
      float yv = vec3i.getY();
      float zv = vec3i.getZ();
      float xn = MathUtils.getTransformX(matrixNormal, xv, yv, zv);
      float yn = MathUtils.getTransformY(matrixNormal, xv, yv, zv);
      float zn = MathUtils.getTransformZ(matrixNormal, xv, yv, zv);
      bufferbuilder.addVertex(matrix, x, y, z).setColor(r, g, b, 1.0F).setUv(u0 + du, v0 + dv).setOverlay(ov).setLight(lm).setNormal(xn, yn, zn);
      bufferbuilder.addVertex(matrix, x, y, z + 1.0F).setColor(r, g, b, 1.0F).setUv(u0 + du, v1 + dv).setOverlay(ov).setLight(lm).setNormal(xn, yn, zn);
      bufferbuilder.addVertex(matrix, x, y + 1.0F, z + 1.0F).setColor(r, g, b, 1.0F).setUv(u1 + du, v1 + dv).setOverlay(ov).setLight(lm).setNormal(xn, yn, zn);
      bufferbuilder.addVertex(matrix, x, y + 1.0F, z).setColor(r, g, b, 1.0F).setUv(u1 + du, v0 + dv).setOverlay(ov).setLight(lm).setNormal(xn, yn, zn);
    } 
    if (te.shouldRenderFace(Direction.DOWN)) {
      Vec3i vec3i = Direction.DOWN.getNormal();
      float xv = vec3i.getX();
      float yv = vec3i.getY();
      float zv = vec3i.getZ();
      float xn = MathUtils.getTransformX(matrixNormal, xv, yv, zv);
      float yn = MathUtils.getTransformY(matrixNormal, xv, yv, zv);
      float zn = MathUtils.getTransformZ(matrixNormal, xv, yv, zv);
      bufferbuilder.addVertex(matrix, x, y, z).setColor(r, g, b, 1.0F).setUv(u0 + du, v0 + dv).setOverlay(ov).setLight(lm).setNormal(xn, yn, zn);
      bufferbuilder.addVertex(matrix, x + 1.0F, y, z).setColor(r, g, b, 1.0F).setUv(u0 + du, v1 + dv).setOverlay(ov).setLight(lm).setNormal(xn, yn, zn);
      bufferbuilder.addVertex(matrix, x + 1.0F, y, z + 1.0F).setColor(r, g, b, 1.0F).setUv(u1 + du, v1 + dv).setOverlay(ov).setLight(lm).setNormal(xn, yn, zn);
      bufferbuilder.addVertex(matrix, x, y, z + 1.0F).setColor(r, g, b, 1.0F).setUv(u1 + du, v0 + dv).setOverlay(ov).setLight(lm).setNormal(xn, yn, zn);
    } 
    if (te.shouldRenderFace(Direction.UP)) {
      Vec3i vec3i = Direction.UP.getNormal();
      float xv = vec3i.getX();
      float yv = vec3i.getY();
      float zv = vec3i.getZ();
      float xn = MathUtils.getTransformX(matrixNormal, xv, yv, zv);
      float yn = MathUtils.getTransformY(matrixNormal, xv, yv, zv);
      float zn = MathUtils.getTransformZ(matrixNormal, xv, yv, zv);
      bufferbuilder.addVertex(matrix, x, y + dy, z + 1.0F).setColor(r, g, b, 1.0F).setUv(u0 + du, v0 + dv).setOverlay(ov).setLight(lm).setNormal(xn, yn, zn);
      bufferbuilder.addVertex(matrix, x + 1.0F, y + dy, z + 1.0F).setColor(r, g, b, 1.0F).setUv(u0 + du, v1 + dv).setOverlay(ov).setLight(lm).setNormal(xn, yn, zn);
      bufferbuilder.addVertex(matrix, x + 1.0F, y + dy, z).setColor(r, g, b, 1.0F).setUv(u1 + du, v1 + dv).setOverlay(ov).setLight(lm).setNormal(xn, yn, zn);
      bufferbuilder.addVertex(matrix, x, y + dy, z).setColor(r, g, b, 1.0F).setUv(u1 + du, v0 + dv).setOverlay(ov).setLight(lm).setNormal(xn, yn, zn);
    } 
    return true;
  }
}

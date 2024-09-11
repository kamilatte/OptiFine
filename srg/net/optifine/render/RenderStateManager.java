package srg.net.optifine.render;

import java.util.Arrays;
import java.util.List;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;

public class RenderStateManager {
  private static boolean cacheEnabled;
  
  private static final RenderStateShard[] PENDING_CLEAR_STATES = new RenderStateShard[RenderType.getCountRenderStates()];
  
  public static void setupRenderStates(List<RenderStateShard> renderStates) {
    if (cacheEnabled) {
      setupCached(renderStates);
      return;
    } 
    for (int i = 0; i < renderStates.size(); i++) {
      RenderStateShard renderState = renderStates.get(i);
      renderState.setupRenderState();
    } 
  }
  
  public static void clearRenderStates(List<RenderStateShard> renderStates) {
    if (cacheEnabled) {
      clearCached(renderStates);
      return;
    } 
    for (int i = 0; i < renderStates.size(); i++) {
      RenderStateShard renderState = renderStates.get(i);
      renderState.clearRenderState();
    } 
  }
  
  private static void setupCached(List<RenderStateShard> renderStates) {
    for (int i = 0; i < renderStates.size(); i++) {
      RenderStateShard state = renderStates.get(i);
      setupCached(state, i);
    } 
  }
  
  private static void clearCached(List<RenderStateShard> renderStates) {
    for (int i = 0; i < renderStates.size(); i++) {
      RenderStateShard state = renderStates.get(i);
      clearCached(state, i);
    } 
  }
  
  private static void setupCached(RenderStateShard state, int index) {
    RenderStateShard pendingClearState = PENDING_CLEAR_STATES[index];
    if (pendingClearState != null) {
      if (state == pendingClearState) {
        PENDING_CLEAR_STATES[index] = null;
        return;
      } 
      pendingClearState.clearRenderState();
      PENDING_CLEAR_STATES[index] = null;
    } 
    state.setupRenderState();
  }
  
  private static void clearCached(RenderStateShard state, int index) {
    RenderStateShard pendingClearState = PENDING_CLEAR_STATES[index];
    if (pendingClearState != null)
      pendingClearState.clearRenderState(); 
    PENDING_CLEAR_STATES[index] = state;
  }
  
  public static void enableCache() {
    if (cacheEnabled)
      return; 
    cacheEnabled = true;
    Arrays.fill((Object[])PENDING_CLEAR_STATES, (Object)null);
  }
  
  public static void flushCache() {
    if (!cacheEnabled)
      return; 
    disableCache();
    enableCache();
  }
  
  public static void disableCache() {
    if (!cacheEnabled)
      return; 
    cacheEnabled = false;
    for (int i = 0; i < PENDING_CLEAR_STATES.length; i++) {
      RenderStateShard pendingClearState = PENDING_CLEAR_STATES[i];
      if (pendingClearState != null)
        pendingClearState.clearRenderState(); 
    } 
    Arrays.fill((Object[])PENDING_CLEAR_STATES, (Object)null);
  }
}

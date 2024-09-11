package notch.net.optifine.render;

import gfg;
import gfh;
import java.util.Arrays;
import java.util.List;

public class RenderStateManager {
  private static boolean cacheEnabled;
  
  private static final gfg[] PENDING_CLEAR_STATES = new gfg[gfh.getCountRenderStates()];
  
  public static void setupRenderStates(List<gfg> renderStates) {
    if (cacheEnabled) {
      setupCached(renderStates);
      return;
    } 
    for (int i = 0; i < renderStates.size(); i++) {
      gfg renderState = renderStates.get(i);
      renderState.a();
    } 
  }
  
  public static void clearRenderStates(List<gfg> renderStates) {
    if (cacheEnabled) {
      clearCached(renderStates);
      return;
    } 
    for (int i = 0; i < renderStates.size(); i++) {
      gfg renderState = renderStates.get(i);
      renderState.b();
    } 
  }
  
  private static void setupCached(List<gfg> renderStates) {
    for (int i = 0; i < renderStates.size(); i++) {
      gfg state = renderStates.get(i);
      setupCached(state, i);
    } 
  }
  
  private static void clearCached(List<gfg> renderStates) {
    for (int i = 0; i < renderStates.size(); i++) {
      gfg state = renderStates.get(i);
      clearCached(state, i);
    } 
  }
  
  private static void setupCached(gfg state, int index) {
    gfg pendingClearState = PENDING_CLEAR_STATES[index];
    if (pendingClearState != null) {
      if (state == pendingClearState) {
        PENDING_CLEAR_STATES[index] = null;
        return;
      } 
      pendingClearState.b();
      PENDING_CLEAR_STATES[index] = null;
    } 
    state.a();
  }
  
  private static void clearCached(gfg state, int index) {
    gfg pendingClearState = PENDING_CLEAR_STATES[index];
    if (pendingClearState != null)
      pendingClearState.b(); 
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
      gfg pendingClearState = PENDING_CLEAR_STATES[i];
      if (pendingClearState != null)
        pendingClearState.b(); 
    } 
    Arrays.fill((Object[])PENDING_CLEAR_STATES, (Object)null);
  }
}

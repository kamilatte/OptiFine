package notch.net.optifine.util;

import fgo;
import ges;
import java.util.HashMap;
import java.util.Map;

public class FrameEvent {
  private static Map<String, Integer> mapEventFrames = new HashMap<>();
  
  public static boolean isActive(String name, int frameInterval) {
    synchronized (mapEventFrames) {
      ges gameRenderer = (fgo.Q()).j;
      if (gameRenderer == null)
        return false; 
      int frameCount = gameRenderer.getFrameCount();
      if (frameCount <= 0)
        return false; 
      Integer frameCountLastObj = mapEventFrames.get(name);
      if (frameCountLastObj == null) {
        frameCountLastObj = new Integer(frameCount);
        mapEventFrames.put(name, frameCountLastObj);
      } 
      int frameCountLast = frameCountLastObj.intValue();
      if (frameCount > frameCountLast && frameCount < frameCountLast + frameInterval)
        return false; 
      mapEventFrames.put(name, new Integer(frameCount));
      return true;
    } 
  }
}

package notch.net.optifine.util;

import ayw;
import dzr;
import java.util.Random;

public class RandomUtils {
  private static final Random random = new Random();
  
  public static Random getRandom() {
    return random;
  }
  
  public static byte[] getRandomBytes(int length) {
    byte[] bytes = new byte[length];
    random.nextBytes(bytes);
    return bytes;
  }
  
  public static int getRandomInt(int bound) {
    return random.nextInt(bound);
  }
  
  public static ayw makeThreadSafeRandomSource(int seed) {
    return (ayw)new dzr(seed);
  }
}

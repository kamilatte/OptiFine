package notch.net.optifine.texture;

import akr;
import gqp;
import java.util.Set;
import java.util.function.Predicate;

public class SpriteSourceCollector implements gqp.a {
  private Set<akr> spriteNames;
  
  public SpriteSourceCollector(Set<akr> spriteNames) {
    this.spriteNames = spriteNames;
  }
  
  public void a(akr locIn, gqp.b supplierIn) {
    this.spriteNames.add(locIn);
  }
  
  public void a(Predicate<akr> checkIn) {}
  
  public Set<akr> getSpriteNames() {
    return this.spriteNames;
  }
}

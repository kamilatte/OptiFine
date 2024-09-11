package notch.net.minecraftforge.entity;

import aka;
import bsr;
import ub;

public class PartEntity<T extends bsr> extends bsr {
  private final T parent;
  
  public PartEntity(T parent) {
    super(parent.am(), parent.dO());
    this.parent = parent;
  }
  
  public T getParent() {
    return this.parent;
  }
  
  protected void a(aka.a builderIn) {
    throw new UnsupportedOperationException();
  }
  
  protected void a(ub compound) {
    throw new UnsupportedOperationException();
  }
  
  protected void b(ub compound) {
    throw new UnsupportedOperationException();
  }
}

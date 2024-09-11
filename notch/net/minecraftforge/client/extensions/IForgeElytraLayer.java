package notch.net.minecraftforge.client.extensions;

import akr;
import cuq;
import cut;
import goe;

public interface IForgeElytraLayer<T> {
  default boolean shouldRender(cuq stack, T entity) {
    return (stack.g() == cut.nT);
  }
  
  default akr getElytraTexture(cuq stack, T entity) {
    return goe.a;
  }
}

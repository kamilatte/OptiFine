package notch.net.optifine.config;

public interface ToBooleanFunction<T> {
  boolean applyAsBool(T paramT);
}
package notch.net.minecraftforge.client.model;

public final class ForgeFaceData extends Record {
  private final int color;
  
  private final int blockLight;
  
  private final int skyLight;
  
  private final boolean ambientOcclusion;
  
  public ForgeFaceData(int color, int blockLight, int skyLight, boolean ambientOcclusion) {
    this.color = color;
    this.blockLight = blockLight;
    this.skyLight = skyLight;
    this.ambientOcclusion = ambientOcclusion;
  }
  
  public final String toString() {
    // Byte code:
    //   0: aload_0
    //   1: <illegal opcode> toString : (Lnet/minecraftforge/client/model/ForgeFaceData;)Ljava/lang/String;
    //   6: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #3	-> 0
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	7	0	this	Lnet/minecraftforge/client/model/ForgeFaceData;
  }
  
  public final int hashCode() {
    // Byte code:
    //   0: aload_0
    //   1: <illegal opcode> hashCode : (Lnet/minecraftforge/client/model/ForgeFaceData;)I
    //   6: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #3	-> 0
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	7	0	this	Lnet/minecraftforge/client/model/ForgeFaceData;
  }
  
  public final boolean equals(Object o) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: <illegal opcode> equals : (Lnet/minecraftforge/client/model/ForgeFaceData;Ljava/lang/Object;)Z
    //   7: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #3	-> 0
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	8	0	this	Lnet/minecraftforge/client/model/ForgeFaceData;
    //   0	8	1	o	Ljava/lang/Object;
  }
  
  public int color() {
    return this.color;
  }
  
  public int blockLight() {
    return this.blockLight;
  }
  
  public int skyLight() {
    return this.skyLight;
  }
  
  public boolean ambientOcclusion() {
    return this.ambientOcclusion;
  }
  
  public static final net.minecraftforge.client.model.ForgeFaceData DEFAULT = new net.minecraftforge.client.model.ForgeFaceData(-1, 0, 0, true);
}

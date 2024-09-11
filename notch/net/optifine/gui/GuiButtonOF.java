package notch.net.optifine.gui;

import akr;
import fhz;
import fim;
import wz;

public class GuiButtonOF extends fim {
  public final int id;
  
  public GuiButtonOF(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, fim.c pressable, fim.b narrationIn) {
    super(x, y, widthIn, heightIn, (wz)wz.b(buttonText), pressable, narrationIn);
    this.id = buttonId;
  }
  
  public GuiButtonOF(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
    this(buttonId, x, y, widthIn, heightIn, buttonText, btn -> {
        
        }q);
  }
  
  public GuiButtonOF(int buttonId, int x, int y, String buttonText) {
    this(buttonId, x, y, 200, 20, buttonText, btn -> {
        
        }q);
  }
  
  public void setMessage(String messageIn) {
    b((wz)wz.b(messageIn));
  }
  
  public static void blit(fhz graphicsIn, akr locationIn, int x, int y, int rectX, int rectY, int width, int height) {
    graphicsIn.a(locationIn, x, y, rectX, rectY, width, height);
  }
  
  public void blit(fhz graphicsIn, akr locationIn, int x, int y, int width, int height, float rectX, float rectY, int rectWidth, int rectHeight, int texWidth, int texHeight) {
    graphicsIn.a(locationIn, x, y, width, height, rectX, rectY, rectWidth, rectHeight, texWidth, texHeight);
  }
}

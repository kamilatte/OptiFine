package notch.net.optifine.shaders.gui;

import fhz;
import fnb;
import fod;
import grr;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import net.optifine.Config;
import net.optifine.Lang;
import net.optifine.gui.SlotGui;
import net.optifine.shaders.IShaderPack;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.gui.GuiShaders;
import net.optifine.util.ResUtils;
import wz;

class GuiSlotShaders extends SlotGui {
  private ArrayList shaderslist;
  
  private int selectedIndex;
  
  private long lastClicked = Long.MIN_VALUE;
  
  private long lastClickedCached = 0L;
  
  final GuiShaders shadersGui;
  
  public GuiSlotShaders(GuiShaders par1GuiShaders, int width, int height, int top, int bottom, int slotHeight) {
    super(par1GuiShaders.getMc(), width, height, top, bottom, slotHeight);
    this.shadersGui = par1GuiShaders;
    updateList();
    this.yo = 0.0D;
    int posYSelected = this.selectedIndex * slotHeight;
    int wMid = (bottom - top) / 2;
    if (posYSelected > wMid)
      scroll(posYSelected - wMid); 
  }
  
  public int getRowWidth() {
    return this.width - 20;
  }
  
  public void updateList() {
    this.shaderslist = Shaders.listOfShaders();
    this.selectedIndex = 0;
    for (int i = 0, n = this.shaderslist.size(); i < n; i++) {
      if (((String)this.shaderslist.get(i)).equals(Shaders.currentShaderName)) {
        this.selectedIndex = i;
        break;
      } 
    } 
  }
  
  protected int getItemCount() {
    return this.shaderslist.size();
  }
  
  protected boolean selectItem(int index, int buttons, double x, double y) {
    if (index == this.selectedIndex && this.lastClicked == this.lastClickedCached)
      return false; 
    String name = this.shaderslist.get(index);
    IShaderPack sp = Shaders.getShaderPack(name);
    if (!checkCompatible(sp, index))
      return false; 
    selectIndex(index);
    return true;
  }
  
  private void selectIndex(int index) {
    this.selectedIndex = index;
    this.lastClickedCached = this.lastClicked;
    Shaders.setShaderPack(this.shaderslist.get(index));
    Shaders.uninit();
    this.shadersGui.updateButtons();
  }
  
  private boolean checkCompatible(IShaderPack sp, int index) {
    if (sp == null)
      return true; 
    InputStream in = sp.getResourceAsStream("/shaders/shaders.properties");
    Properties props = ResUtils.readProperties(in, "Shaders");
    if (props == null)
      return true; 
    String keyVer = "version.1.21.1";
    String relMin = props.getProperty(keyVer);
    if (relMin == null)
      return true; 
    relMin = relMin.trim();
    String rel = "J1_pre10";
    int res = Config.compareRelease(rel, relMin);
    if (res >= 0)
      return true; 
    String verMin = ("HD_U_" + relMin).replace('_', ' ');
    String msg1 = grr.a("of.message.shaders.nv1", new Object[] { verMin });
    String msg2 = grr.a("of.message.shaders.nv2", new Object[0]);
    int theIndex = index;
    BooleanConsumer callback = result -> {
        if (result)
          selectIndex(theIndex); 
        this.minecraft.a((fod)this.shadersGui);
      };
    fnb guiYesNo = new fnb(callback, (wz)wz.b(msg1), (wz)wz.b(msg2));
    this.minecraft.a((fod)guiYesNo);
    return false;
  }
  
  protected boolean isSelectedItem(int index) {
    return (index == this.selectedIndex);
  }
  
  protected int getScrollbarPosition() {
    return this.width - 6;
  }
  
  public int getItemHeight() {
    return getItemCount() * 18;
  }
  
  protected void renderBackground() {}
  
  protected void renderItem(fhz graphicsIn, int index, int posX, int posY, int contentY, int mouseX, int mouseY, float partialTicks) {
    String label = this.shaderslist.get(index);
    if (label.equals("OFF")) {
      label = Lang.get("of.options.shaders.packNone");
    } else if (label.equals("(internal)")) {
      label = Lang.get("of.options.shaders.packDefault");
    } 
    this.shadersGui.drawCenteredString(graphicsIn, label, this.width / 2, posY + 1, 14737632);
  }
  
  public int getSelectedIndex() {
    return this.selectedIndex;
  }
  
  public boolean a(double mouseX, double mouseY, double deltaH, double deltaV) {
    return super.a(mouseX, mouseY, deltaH, deltaV * 3.0D);
  }
}

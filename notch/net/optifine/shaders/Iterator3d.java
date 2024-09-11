package notch.net.optifine.shaders;

import exc;
import java.util.Iterator;
import jd;
import net.optifine.BlockPosM;
import net.optifine.shaders.IteratorAxis;

public class Iterator3d implements Iterator<jd> {
  private IteratorAxis iteratorAxis;
  
  private BlockPosM blockPos = new BlockPosM(0, 0, 0);
  
  private int axis = 0;
  
  private int kX;
  
  private int kY;
  
  private int kZ;
  
  private static final int AXIS_X = 0;
  
  private static final int AXIS_Y = 1;
  
  private static final int AXIS_Z = 2;
  
  public Iterator3d(jd posStart, jd posEnd, int width, int height) {
    boolean revX = (posStart.u() > posEnd.u());
    boolean revY = (posStart.v() > posEnd.v());
    boolean revZ = (posStart.w() > posEnd.w());
    posStart = reverseCoord(posStart, revX, revY, revZ);
    posEnd = reverseCoord(posEnd, revX, revY, revZ);
    this.kX = revX ? -1 : 1;
    this.kY = revY ? -1 : 1;
    this.kZ = revZ ? -1 : 1;
    exc vec = new exc((posEnd.u() - posStart.u()), (posEnd.v() - posStart.v()), (posEnd.w() - posStart.w()));
    exc vecN = vec.d();
    exc vecX = new exc(1.0D, 0.0D, 0.0D);
    double dotX = vecN.b(vecX);
    double dotXabs = Math.abs(dotX);
    exc vecY = new exc(0.0D, 1.0D, 0.0D);
    double dotY = vecN.b(vecY);
    double dotYabs = Math.abs(dotY);
    exc vecZ = new exc(0.0D, 0.0D, 1.0D);
    double dotZ = vecN.b(vecZ);
    double dotZabs = Math.abs(dotZ);
    if (dotZabs >= dotYabs && dotZabs >= dotXabs) {
      this.axis = 2;
      jd pos1 = new jd(posStart.w(), posStart.v() - width, posStart.u() - height);
      jd pos2 = new jd(posEnd.w(), posStart.v() + width + 1, posStart.u() + height + 1);
      int countX = posEnd.w() - posStart.w();
      double deltaY = (posEnd.v() - posStart.v()) / 1.0D * countX;
      double deltaZ = (posEnd.u() - posStart.u()) / 1.0D * countX;
      this.iteratorAxis = new IteratorAxis(pos1, pos2, deltaY, deltaZ);
    } else if (dotYabs >= dotXabs && dotYabs >= dotZabs) {
      this.axis = 1;
      jd pos1 = new jd(posStart.v(), posStart.u() - width, posStart.w() - height);
      jd pos2 = new jd(posEnd.v(), posStart.u() + width + 1, posStart.w() + height + 1);
      int countX = posEnd.v() - posStart.v();
      double deltaY = (posEnd.u() - posStart.u()) / 1.0D * countX;
      double deltaZ = (posEnd.w() - posStart.w()) / 1.0D * countX;
      this.iteratorAxis = new IteratorAxis(pos1, pos2, deltaY, deltaZ);
    } else {
      this.axis = 0;
      jd pos1 = new jd(posStart.u(), posStart.v() - width, posStart.w() - height);
      jd pos2 = new jd(posEnd.u(), posStart.v() + width + 1, posStart.w() + height + 1);
      int countX = posEnd.u() - posStart.u();
      double deltaY = (posEnd.v() - posStart.v()) / 1.0D * countX;
      double deltaZ = (posEnd.w() - posStart.w()) / 1.0D * countX;
      this.iteratorAxis = new IteratorAxis(pos1, pos2, deltaY, deltaZ);
    } 
  }
  
  private jd reverseCoord(jd pos, boolean revX, boolean revY, boolean revZ) {
    if (revX)
      pos = new jd(-pos.u(), pos.v(), pos.w()); 
    if (revY)
      pos = new jd(pos.u(), -pos.v(), pos.w()); 
    if (revZ)
      pos = new jd(pos.u(), pos.v(), -pos.w()); 
    return pos;
  }
  
  public boolean hasNext() {
    return this.iteratorAxis.hasNext();
  }
  
  public jd next() {
    jd pos = this.iteratorAxis.next();
    switch (this.axis) {
      case 0:
        this.blockPos.setXyz(pos.u() * this.kX, pos.v() * this.kY, pos.w() * this.kZ);
        return (jd)this.blockPos;
      case 1:
        this.blockPos.setXyz(pos.v() * this.kX, pos.u() * this.kY, pos.w() * this.kZ);
        return (jd)this.blockPos;
      case 2:
        this.blockPos.setXyz(pos.w() * this.kX, pos.v() * this.kY, pos.u() * this.kZ);
        return (jd)this.blockPos;
    } 
    this.blockPos.setXyz(pos.u() * this.kX, pos.v() * this.kY, pos.w() * this.kZ);
    return (jd)this.blockPos;
  }
  
  public void remove() {
    throw new RuntimeException("Not supported");
  }
  
  public static void main(String[] args) {
    jd posStart = new jd(10, 20, 30);
    jd posEnd = new jd(30, 40, 20);
    net.optifine.shaders.Iterator3d it = new net.optifine.shaders.Iterator3d(posStart, posEnd, 1, 1);
    while (it.hasNext()) {
      jd blockPos = it.next();
      System.out.println(String.valueOf(blockPos));
    } 
  }
}

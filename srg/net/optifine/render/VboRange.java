package srg.net.optifine.render;

import net.optifine.util.LinkedList;

public class VboRange {
  private int position = -1;
  
  private int size = 0;
  
  private LinkedList.Node<net.optifine.render.VboRange> node = new LinkedList.Node(this);
  
  public int getPosition() {
    return this.position;
  }
  
  public int getSize() {
    return this.size;
  }
  
  public int getPositionNext() {
    return this.position + this.size;
  }
  
  public void setPosition(int position) {
    this.position = position;
  }
  
  public void setSize(int size) {
    this.size = size;
  }
  
  public LinkedList.Node<net.optifine.render.VboRange> getNode() {
    return this.node;
  }
  
  public net.optifine.render.VboRange getPrev() {
    LinkedList.Node<net.optifine.render.VboRange> nodePrev = this.node.getPrev();
    if (nodePrev == null)
      return null; 
    return (net.optifine.render.VboRange)nodePrev.getItem();
  }
  
  public net.optifine.render.VboRange getNext() {
    LinkedList.Node<net.optifine.render.VboRange> nodeNext = this.node.getNext();
    if (nodeNext == null)
      return null; 
    return (net.optifine.render.VboRange)nodeNext.getItem();
  }
  
  public String toString() {
    return "" + this.position + "/" + this.position + "/" + this.size;
  }
}

package srg.net.optifine.util;

import java.util.Iterator;

public class LinkedList<T> {
  private Node<T> first;
  
  private Node<T> last;
  
  private int size;
  
  public void addFirst(Node<T> node) {
    checkNoParent(node);
    if (isEmpty()) {
      this.first = node;
      this.last = node;
    } else {
      Node<T> nodeNext = this.first;
      node.setNext(nodeNext);
      nodeNext.setPrev(node);
      this.first = node;
    } 
    node.setParent(this);
    this.size++;
  }
  
  public void addLast(Node<T> node) {
    checkNoParent(node);
    if (isEmpty()) {
      this.first = node;
      this.last = node;
    } else {
      Node<T> nodePrev = this.last;
      node.setPrev(nodePrev);
      nodePrev.setNext(node);
      this.last = node;
    } 
    node.setParent(this);
    this.size++;
  }
  
  public void addAfter(Node<T> nodePrev, Node<T> node) {
    if (nodePrev == null) {
      addFirst(node);
      return;
    } 
    if (nodePrev == this.last) {
      addLast(node);
      return;
    } 
    checkParent(nodePrev);
    checkNoParent(node);
    Node<T> nodeNext = nodePrev.getNext();
    nodePrev.setNext(node);
    node.setPrev(nodePrev);
    nodeNext.setPrev(node);
    node.setNext(nodeNext);
    node.setParent(this);
    this.size++;
  }
  
  public Node<T> remove(Node<T> node) {
    checkParent(node);
    Node<T> prev = node.getPrev();
    Node<T> next = node.getNext();
    if (prev != null) {
      prev.setNext(next);
    } else {
      this.first = next;
    } 
    if (next != null) {
      next.setPrev(prev);
    } else {
      this.last = prev;
    } 
    node.setPrev(null);
    node.setNext(null);
    node.setParent(null);
    this.size--;
    return node;
  }
  
  public void moveAfter(Node<T> nodePrev, Node<T> node) {
    remove(node);
    addAfter(nodePrev, node);
  }
  
  public boolean find(Node<T> nodeFind, Node<T> nodeFrom, Node<T> nodeTo) {
    checkParent(nodeFrom);
    if (nodeTo != null)
      checkParent(nodeTo); 
    Node<T> node = nodeFrom;
    while (node != null && node != nodeTo) {
      if (node == nodeFind)
        return true; 
      node = node.getNext();
    } 
    if (node != nodeTo)
      throw new IllegalArgumentException("Sublist is not linked, from: " + String.valueOf(nodeFrom) + ", to: " + String.valueOf(nodeTo)); 
    return false;
  }
  
  private void checkParent(Node<T> node) {
    if (node.parent != this)
      throw new IllegalArgumentException("Node has different parent, node: " + String.valueOf(node) + ", parent: " + String.valueOf(node.parent) + ", this: " + String.valueOf(this)); 
  }
  
  private void checkNoParent(Node<T> node) {
    if (node.parent != null)
      throw new IllegalArgumentException("Node has different parent, node: " + String.valueOf(node) + ", parent: " + String.valueOf(node.parent) + ", this: " + String.valueOf(this)); 
  }
  
  public boolean contains(Node<T> node) {
    return (node.parent == this);
  }
  
  public Iterator<Node<T>> iterator() {
    return (Iterator<Node<T>>)new Object(this);
  }
  
  public Node<T> getFirst() {
    return this.first;
  }
  
  public Node<T> getLast() {
    return this.last;
  }
  
  public int getSize() {
    return this.size;
  }
  
  public boolean isEmpty() {
    return (this.size <= 0);
  }
  
  public String toString() {
    StringBuffer sb = new StringBuffer();
    for (Iterator<Node<T>> it = iterator(); it.hasNext(); ) {
      Node<T> node = it.next();
      if (sb.length() > 0)
        sb.append(", "); 
      sb.append(node.getItem());
    } 
    return "" + this.size + " [" + this.size + "]";
  }
}

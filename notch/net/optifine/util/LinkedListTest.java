package notch.net.optifine.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.optifine.render.VboRange;
import net.optifine.util.LinkedList;

public class LinkedListTest {
  public static void main(String[] args) throws Exception {
    LinkedList<VboRange> linkedList = new LinkedList();
    List<VboRange> listFree = new ArrayList<>();
    List<VboRange> listUsed = new ArrayList<>();
    Random random = new Random();
    int count = 100;
    int i;
    for (i = 0; i < count; i++) {
      VboRange range = new VboRange();
      range.setPosition(i);
      listFree.add(range);
    } 
    for (i = 0; i < 100000; i++) {
      checkLists(listFree, listUsed, count);
      checkLinkedList(linkedList, listUsed.size());
      if (i % 5 == 0)
        dbgLinkedList(linkedList); 
      if (random.nextBoolean()) {
        if (listFree.isEmpty())
          continue; 
        VboRange range = listFree.get(random.nextInt(listFree.size()));
        LinkedList.Node<VboRange> node = range.getNode();
        if (random.nextBoolean()) {
          linkedList.addFirst(node);
          dbg("Add first: " + range.getPosition());
        } else if (random.nextBoolean()) {
          linkedList.addLast(node);
          dbg("Add last: " + range.getPosition());
        } else {
          if (listUsed.isEmpty())
            continue; 
          VboRange rangePrev = listUsed.get(random.nextInt(listUsed.size()));
          LinkedList.Node<VboRange> nodePrev = rangePrev.getNode();
          linkedList.addAfter(nodePrev, node);
          dbg("Add after: " + rangePrev.getPosition() + ", " + range.getPosition());
        } 
        listFree.remove(range);
        listUsed.add(range);
        continue;
      } 
      if (!listUsed.isEmpty()) {
        VboRange range = listUsed.get(random.nextInt(listUsed.size()));
        LinkedList.Node<VboRange> node = range.getNode();
        linkedList.remove(node);
        dbg("Remove: " + range.getPosition());
        listUsed.remove(range);
        listFree.add(range);
      } 
      continue;
    } 
  }
  
  private static void dbgLinkedList(LinkedList<VboRange> linkedList) {
    StringBuffer sb = new StringBuffer();
    for (Iterator<LinkedList.Node<VboRange>> it = linkedList.iterator(); it.hasNext(); ) {
      LinkedList.Node<VboRange> node = it.next();
      VboRange range = (VboRange)node.getItem();
      if (sb.length() > 0)
        sb.append(", "); 
      sb.append(range.getPosition());
    } 
    dbg("List: " + String.valueOf(sb));
  }
  
  private static void checkLinkedList(LinkedList<VboRange> linkedList, int used) {
    if (linkedList.getSize() != used)
      throw new RuntimeException("Wrong size, linked: " + linkedList.getSize() + ", used: " + used); 
    int count = 0;
    LinkedList.Node<VboRange> node = linkedList.getFirst();
    while (node != null) {
      count++;
      node = node.getNext();
    } 
    if (linkedList.getSize() != count)
      throw new RuntimeException("Wrong count, linked: " + linkedList.getSize() + ", count: " + count); 
    int countBack = 0;
    LinkedList.Node<VboRange> nodeBack = linkedList.getLast();
    while (nodeBack != null) {
      countBack++;
      nodeBack = nodeBack.getPrev();
    } 
    if (linkedList.getSize() != countBack)
      throw new RuntimeException("Wrong count back, linked: " + linkedList.getSize() + ", count: " + countBack); 
  }
  
  private static void checkLists(List<VboRange> listFree, List<VboRange> listUsed, int count) {
    int total = listFree.size() + listUsed.size();
    if (total != count)
      throw new RuntimeException("Total size: " + total); 
  }
  
  private static void dbg(String str) {
    System.out.println(str);
  }
}

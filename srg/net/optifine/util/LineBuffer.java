package srg.net.optifine.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineBuffer implements Iterable<String> {
  private ArrayList<String> lines = new ArrayList<>();
  
  public LineBuffer() {}
  
  public LineBuffer(String[] linesArr) {
    this.lines.addAll(Arrays.asList(linesArr));
  }
  
  public int size() {
    return this.lines.size();
  }
  
  public String get(int index) {
    String line = this.lines.get(index);
    return line;
  }
  
  public String set(int index, String line) {
    return this.lines.set(index, line);
  }
  
  public void add(String line) {
    checkLine(line);
    this.lines.add(line);
  }
  
  public void add(String[] ls) {
    for (int i = 0; i < ls.length; i++) {
      String line = ls[i];
      add(line);
    } 
  }
  
  public void insert(int index, String line) {
    checkLine(line);
    this.lines.add(index, line);
  }
  
  public void insert(int index, String[] ls) {
    for (int i = 0; i < ls.length; i++) {
      String line = ls[i];
      checkLine(line);
    } 
    this.lines.addAll(index, Arrays.asList(ls));
  }
  
  private void checkLine(String line) {
    if (line == null)
      throw new IllegalArgumentException("Line is null"); 
  }
  
  public int indexMatch(Pattern regexp) {
    return indexMatch(regexp, 0, true);
  }
  
  public int indexMatch(Pattern regexp, int startIndex) {
    return indexMatch(regexp, startIndex, true);
  }
  
  public int indexNonMatch(Pattern regexp) {
    return indexMatch(regexp, 0, false);
  }
  
  public int indexNonMatch(Pattern regexp, int startIndex) {
    return indexMatch(regexp, startIndex, false);
  }
  
  public int indexMatch(Pattern regexp, int startIndex, boolean match) {
    if (startIndex < 0)
      return -1; 
    for (int i = startIndex; i < this.lines.size(); i++) {
      String line = this.lines.get(i);
      Matcher matcher = regexp.matcher(line);
      if (matcher.matches() == match)
        return i; 
    } 
    return -1;
  }
  
  public int lastIndexMatch(Pattern regexp) {
    return lastIndexMatch(regexp, this.lines.size(), true);
  }
  
  public int lastIndexMatch(Pattern regexp, int startIndex) {
    return lastIndexMatch(regexp, startIndex, true);
  }
  
  public int lastIndexMatch(Pattern regexp, int startIndex, boolean match) {
    if (startIndex > this.lines.size())
      return -1; 
    for (int i = startIndex - 1; i >= 0; i--) {
      String line = this.lines.get(i);
      Matcher matcher = regexp.matcher(line);
      if (matcher.matches() == match)
        return i; 
    } 
    return -1;
  }
  
  public static net.optifine.util.LineBuffer readAll(Reader reader) throws IOException {
    net.optifine.util.LineBuffer lb = new net.optifine.util.LineBuffer();
    BufferedReader br = new BufferedReader(reader);
    while (true) {
      String line = br.readLine();
      if (line == null)
        break; 
      lb.add(line);
    } 
    br.close();
    return lb;
  }
  
  public String[] getLines() {
    String[] ls = this.lines.<String>toArray(new String[this.lines.size()]);
    return ls;
  }
  
  public Iterator<String> iterator() {
    return (Iterator<String>)new Itr(this);
  }
  
  public boolean contains(String line) {
    return (indexOf(line) >= 0);
  }
  
  private int indexOf(String lineFind) {
    for (int i = 0; i < this.lines.size(); i++) {
      String line = this.lines.get(i);
      if (line.equals(lineFind))
        return i; 
    } 
    return -1;
  }
  
  public boolean remove(String lineRemove) {
    return this.lines.remove(lineRemove);
  }
  
  public String remove(int index) {
    return this.lines.remove(index);
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < this.lines.size(); i++) {
      String line = this.lines.get(i);
      sb.append(line);
      sb.append("\n");
    } 
    return sb.toString();
  }
}

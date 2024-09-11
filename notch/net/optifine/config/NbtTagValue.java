package notch.net.optifine.config;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;
import net.optifine.Config;
import net.optifine.config.ConnectedParser;
import net.optifine.config.RangeListInt;
import net.optifine.util.StrUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import tz;
import ub;
import uc;
import ue;
import ug;
import uh;
import uj;
import ut;
import uw;
import uy;

public class NbtTagValue {
  private String[] parents = null;
  
  private String name = null;
  
  private boolean negative = false;
  
  private boolean raw = false;
  
  private int type = 0;
  
  private String value = null;
  
  private Pattern valueRegex = null;
  
  private RangeListInt valueRange = null;
  
  private int valueFormat = 0;
  
  private static final int TYPE_INVALID = -1;
  
  private static final int TYPE_TEXT = 0;
  
  private static final int TYPE_PATTERN = 1;
  
  private static final int TYPE_IPATTERN = 2;
  
  private static final int TYPE_REGEX = 3;
  
  private static final int TYPE_IREGEX = 4;
  
  private static final int TYPE_RANGE = 5;
  
  private static final int TYPE_EXISTS = 6;
  
  private static final String PREFIX_PATTERN = "pattern:";
  
  private static final String PREFIX_IPATTERN = "ipattern:";
  
  private static final String PREFIX_REGEX = "regex:";
  
  private static final String PREFIX_IREGEX = "iregex:";
  
  private static final String PREFIX_RAW = "raw:";
  
  private static final String PREFIX_RANGE = "range:";
  
  private static final String PREFIX_EXISTS = "exists:";
  
  private static final int FORMAT_DEFAULT = 0;
  
  private static final int FORMAT_HEX_COLOR = 1;
  
  private static final String PREFIX_HEX_COLOR = "#";
  
  private static final Pattern PATTERN_HEX_COLOR = Pattern.compile("^#[0-9a-f]{6}+$");
  
  public NbtTagValue(String tag, String value) {
    String[] names = Config.tokenize(tag, ".");
    this.parents = Arrays.<String>copyOfRange(names, 0, names.length - 1);
    this.name = names[names.length - 1];
    if (value.startsWith("!")) {
      this.negative = true;
      value = value.substring(1);
    } 
    if (value.startsWith("raw:")) {
      this.raw = true;
      value = value.substring("raw:".length());
    } 
    if (value.startsWith("pattern:")) {
      this.type = 1;
      value = value.substring("pattern:".length());
      if (value.equals("*"))
        this.type = 6; 
    } else if (value.startsWith("ipattern:")) {
      this.type = 2;
      value = value.substring("ipattern:".length()).toLowerCase();
      if (value.equals("*"))
        this.type = 6; 
    } else if (value.startsWith("regex:")) {
      this.type = 3;
      value = value.substring("regex:".length());
      this.valueRegex = Pattern.compile(value);
      if (value.equals(".*"))
        this.type = 6; 
    } else if (value.startsWith("iregex:")) {
      this.type = 4;
      value = value.substring("iregex:".length());
      this.valueRegex = Pattern.compile(value, 2);
      if (value.equals(".*"))
        this.type = 6; 
    } else if (value.startsWith("range:")) {
      this.type = 5;
      value = value.substring("range:".length());
      ConnectedParser cp = new ConnectedParser("NbtTag");
      this.valueRange = cp.parseRangeListIntNeg(value);
      if (this.valueRange == null) {
        Config.warn("Invalid range: " + value);
        this.type = -1;
        this.negative = false;
      } 
    } else if (value.startsWith("exists:")) {
      this.type = 6;
      value = value.substring("exists:".length());
      Boolean valB = Config.parseBoolean(value, null);
      if (Config.isFalse(valB))
        this.negative = !this.negative; 
      if (valB == null) {
        Config.warn("Invalid exists: " + value);
        this.type = -1;
        this.negative = false;
      } 
    } else {
      this.type = 0;
    } 
    value = StringEscapeUtils.unescapeJava(value);
    if (this.type == 0)
      if (PATTERN_HEX_COLOR.matcher(value).matches())
        this.valueFormat = 1;  
    this.value = value;
  }
  
  public boolean matches(ub nbt) {
    if (this.negative)
      return !matchesCompound(nbt); 
    return matchesCompound(nbt);
  }
  
  public boolean matchesCompound(ub nbt) {
    if (nbt == null)
      return false; 
    ub ub1 = nbt;
    for (int i = 0; i < this.parents.length; i++) {
      String tag = this.parents[i];
      uy = getChildTag((uy)ub1, tag);
      if (uy == null)
        return false; 
    } 
    if (this.name.equals("*"))
      return matchesAnyChild(uy); 
    uy uy = getChildTag(uy, this.name);
    if (uy == null)
      return false; 
    if (matchesBase(uy))
      return true; 
    return false;
  }
  
  private boolean matchesAnyChild(uy tagBase) {
    if (tagBase instanceof ub) {
      ub tagCompound = (ub)tagBase;
      Set nbtKeySet = tagCompound.e();
      for (Iterator<String> it = nbtKeySet.iterator(); it.hasNext(); ) {
        String key = it.next();
        uy nbtBase = tagCompound.c(key);
        if (matchesBase(nbtBase))
          return true; 
      } 
    } 
    if (tagBase instanceof uh) {
      uh tagList = (uh)tagBase;
      int count = tagList.size();
      for (int i = 0; i < count; i++) {
        uy nbtBase = tagList.k(i);
        if (matchesBase(nbtBase))
          return true; 
      } 
    } 
    return false;
  }
  
  private static uy getChildTag(uy tagBase, String tag) {
    if (tagBase instanceof ub) {
      ub tagCompound = (ub)tagBase;
      return tagCompound.c(tag);
    } 
    if (tagBase instanceof uh) {
      uh tagList = (uh)tagBase;
      if (tag.equals("count"))
        return (uy)ug.a(tagList.size()); 
      int index = Config.parseInt(tag, -1);
      if (index < 0 || index >= tagList.size())
        return null; 
      return tagList.k(index);
    } 
    return null;
  }
  
  public boolean matchesBase(uy nbtBase) {
    int nbtValueInt;
    if (nbtBase == null)
      return false; 
    switch (this.type) {
      case -1:
        return false;
      case 5:
        nbtValueInt = getNbtInt(nbtBase, -2147483648);
        if (nbtValueInt != Integer.MIN_VALUE)
          return matchesRange(nbtValueInt, this.valueRange); 
      case 6:
        return true;
    } 
    String nbtValue = this.raw ? String.valueOf(nbtBase) : getNbtString(nbtBase, this.valueFormat);
    return matchesValue(nbtValue);
  }
  
  public boolean matchesValue(String nbtValue) {
    if (nbtValue == null)
      return false; 
    switch (this.type) {
      case -1:
        return false;
      case 0:
        return nbtValue.equals(this.value);
      case 1:
        return matchesPattern(nbtValue, this.value);
      case 2:
        return matchesPattern(nbtValue.toLowerCase(), this.value);
      case 3:
      case 4:
        return matchesRegex(nbtValue, this.valueRegex);
      case 5:
        return matchesRange(nbtValue, this.valueRange);
      case 6:
        return true;
    } 
    throw new IllegalArgumentException("Unknown NbtTagValue type: " + this.type);
  }
  
  private static boolean matchesPattern(String str, String pattern) {
    return StrUtils.equalsMask(str, pattern, '*', '?');
  }
  
  private static boolean matchesRegex(String str, Pattern regex) {
    return regex.matcher(str).matches();
  }
  
  private static boolean matchesRange(String str, RangeListInt range) {
    if (range == null)
      return false; 
    int valInt = Config.parseInt(str, -2147483648);
    if (valInt == Integer.MIN_VALUE)
      return false; 
    return matchesRange(valInt, range);
  }
  
  private static boolean matchesRange(int valInt, RangeListInt range) {
    if (range == null)
      return false; 
    return range.isInRange(valInt);
  }
  
  private static String getNbtString(uy nbtBase, int format) {
    if (nbtBase == null)
      return null; 
    if (nbtBase instanceof uw) {
      uw nbtString = (uw)nbtBase;
      String text = nbtString.s_();
      if (text.startsWith("{") && text.endsWith("}")) {
        text = getMergedJsonText(text);
      } else if (text.startsWith("[{") && text.endsWith("}]")) {
        text = getMergedJsonText(text);
      } else if (text.startsWith("\"") && text.endsWith("\"") && text.length() > 1) {
        text = text.substring(1, text.length() - 1);
      } 
      return text;
    } 
    if (nbtBase instanceof ug) {
      ug i = (ug)nbtBase;
      if (format == 1)
        return "#" + StrUtils.fillLeft(Integer.toHexString(i.g()), 6, '0'); 
      return Integer.toString(i.g());
    } 
    if (nbtBase instanceof tz) {
      tz b = (tz)nbtBase;
      return Byte.toString(b.i());
    } 
    if (nbtBase instanceof ut) {
      ut s = (ut)nbtBase;
      return Short.toString(s.h());
    } 
    if (nbtBase instanceof uj) {
      uj l = (uj)nbtBase;
      return Long.toString(l.f());
    } 
    if (nbtBase instanceof ue) {
      ue f = (ue)nbtBase;
      return Float.toString(f.k());
    } 
    if (nbtBase instanceof uc) {
      uc d = (uc)nbtBase;
      return Double.toString(d.j());
    } 
    return nbtBase.toString();
  }
  
  private static int getNbtInt(uy nbtBase, int defVal) {
    if (nbtBase == null)
      return defVal; 
    if (nbtBase instanceof ug) {
      ug i = (ug)nbtBase;
      return i.g();
    } 
    if (nbtBase instanceof tz) {
      tz b = (tz)nbtBase;
      return b.i();
    } 
    if (nbtBase instanceof ut) {
      ut s = (ut)nbtBase;
      return s.h();
    } 
    if (nbtBase instanceof uj) {
      uj l = (uj)nbtBase;
      return (int)l.f();
    } 
    if (nbtBase instanceof ue) {
      ue f = (ue)nbtBase;
      return (int)f.k();
    } 
    if (nbtBase instanceof uc) {
      uc d = (uc)nbtBase;
      return (int)d.j();
    } 
    return defVal;
  }
  
  private static String getMergedJsonText(String text) {
    StringBuilder sb = new StringBuilder();
    String TOKEN_TEXT = "\"text\":\"";
    int pos = -1;
    while (true) {
      pos = text.indexOf(TOKEN_TEXT, pos + 1);
      if (pos < 0)
        break; 
      String str = parseString(text, pos + TOKEN_TEXT.length());
      if (str != null)
        sb.append(str); 
    } 
    return sb.toString();
  }
  
  private static String parseString(String text, int pos) {
    StringBuilder sb = new StringBuilder();
    boolean escapeMode = false;
    for (int i = pos; i < text.length(); i++) {
      char ch = text.charAt(i);
      if (escapeMode) {
        if (ch == 'b') {
          sb.append('\b');
        } else if (ch == 'f') {
          sb.append('\f');
        } else if (ch == 'n') {
          sb.append('\n');
        } else if (ch == 'r') {
          sb.append('\r');
        } else if (ch == 't') {
          sb.append('\t');
        } else {
          sb.append(ch);
        } 
        escapeMode = false;
      } else if (ch == '\\') {
        escapeMode = true;
      } else {
        if (ch == '"')
          break; 
        sb.append(ch);
      } 
    } 
    return sb.toString();
  }
  
  public String toString() {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < this.parents.length; i++) {
      String parent = this.parents[i];
      if (i > 0)
        sb.append("."); 
      sb.append(parent);
    } 
    if (sb.length() > 0)
      sb.append("."); 
    sb.append(this.name);
    sb.append(" = ");
    sb.append(this.value);
    return sb.toString();
  }
}

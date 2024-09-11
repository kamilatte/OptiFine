package optifine.xdelta;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Checksum {
  public static final int BASE = 65521;
  
  public static final int S = 16;
  
  public static boolean debug = false;
  
  protected int[] hashtable;
  
  protected long[] checksums;
  
  protected int prime;
  
  protected static final char[] single_hash = new char[] { 
      '병', '뭥', '䋂', '?', '陦', '䌛', '蔄', '', 
      '捹', '푠', 
      '켔', '叏', '?', '?', 'ወ', '', 
      '', '⎔', '┍', '?', 
      'ꙸ', 'ʯ', 'ꗆ', '约', 
      '뙅', '쭍', '쑋', '', '鿦', '孜', 
      '㗵', '瀚', 
      '∏', '永', 'ᩖ', '䲣', 'ￆ', '녒', '赡', '穘', 
      '逥', '謽', '뼏', '閣', '', '섧', '㯭', '㈋', 
      '럳', '恔', 
      '㌼', '펃', '腔', '剂', '不', 'ઔ', 
      '瀨', '蚉', '㨢', 'ঀ', 
      'ᡇ', '냱', '魜', '䅶', 
      '롘', '핂', 'Ὤ', '⒗', '橚', '龩', 
      '豚', '睃', 
      'ꢩ', '騂', '䤘', '䎌', '쎈', '鸫', '䲭', 'ƶ', 
      '꬙', '', '㙟', 'Ẳ', 'ञ', '篸', '窎', '刧', 
      '', '⁴', 
      '䔣', '', 'ƣ', 'ᘽ', '㬮', '⡽', 
      '广', 'ꁣ', '넴', '辮', 
      '庎', '랷', '䕈', '὚', 
      '節', '稤', '透', '䋜', '챩', 'ʠ', 
      'ଢ', '?', 
      '燾', '౽', 'ᜲ', 'ᅙ', '쬉', '', 'ፑ', '勩', 
      '', '婏', '쌖', '毹', '覔', '띴', '弾', '', 
      '㩡', '', 
      '찢', '鴆', '⦜', '৥', 'Ử', '兏', 
      '赓', 'Ꙑ', '屮', '앷', 
      '祘', '熬', '褖', '魏', 
      'Ⰹ', '刑', '', '쪪', '', '⡿', 
      '窔', 'ꭉ', 
      '館', '爢', '', '휚', 'Ã', '᩶', '', '쀷', 
      '興', '尭', '?', '', '୅', 'ᗎ', '詾', 'ﲭ', 
      'ꨭ', '䭜', 
      '퐮', '뉑', '遾', '驇', '즦', '?', 
      '࡞', '㗎', 'ꅓ', '繻', 
      '鼋', '▪', '嶟', '쁍', 
      '討', '⡵', '䨜', '⥟', '᎓', '', 
      '酸', 'ཛ', 
      '墳', '莴', '₂', '爝', '摢', 'ͨ', '柢', '蘤', 
      '᥍', '⋶', '磻', '枑', '눸', '댲', '牶', '', 
      '䟬', '䔄', 
      'ꥡ', '鿈', '㿜', '됓', 'z', 'ࠆ', 
      '瑘', '闆', '첪', 'ᣖ', 
      '', 'ᬆ', '', '偐', 
      '죨', '', '쁌', '', '餯', '깄', 
      '弛', 'ᄓ', 
      '᜸', '?', '᧪', 'ⴳ', '隘', '⿩', '㈿', '췢', 
      '浱', '', '뚗', 'ⱏ', '䍳', '鄂', 'ݝ', '踥', 
      'ᙲ', '', 
      '櫋', '蛌', 'ᡮ', '鐔', '홴', '톥' };
  
  public static long queryChecksum(byte[] buf, int len) {
    int high = 0, low = 0;
    for (int i = 0; i < len; i++) {
      low += single_hash[buf[i] + 128];
      high += low;
    } 
    return ((high & 0xFFFF) << 16 | low & 0xFFFF);
  }
  
  public static long incrementChecksum(long checksum, byte out, byte in) {
    char old_c = single_hash[out + 128];
    char new_c = single_hash[in + 128];
    int low = (int)(checksum & 0xFFFFL) - old_c + new_c & 0xFFFF;
    int high = (int)(checksum >> 16L) - old_c * 16 + low & 0xFFFF;
    return (high << 16 | low & 0xFFFF);
  }
  
  public static int generateHash(long checksum) {
    long high = checksum >> 16L & 0xFFFFL;
    long low = checksum & 0xFFFFL;
    long it = (high >> 2L) + (low << 3L) + (high << 16L);
    int hash = (int)(it ^ high ^ low);
    return (hash > 0) ? hash : -hash;
  }
  
  public void generateChecksums(File sourceFile, int length) throws IOException {
    FileInputStream fis = new FileInputStream(sourceFile);
    try {
      generateChecksums(fis, length);
    } catch (IOException e) {
      throw e;
    } finally {
      fis.close();
    } 
  }
  
  public void generateChecksums(InputStream sis, int length) throws IOException {
    InputStream is = new BufferedInputStream(sis);
    int checksumcount = length / 16;
    if (debug)
      System.out.println("generating checksum array of size " + checksumcount); 
    this.checksums = new long[checksumcount];
    this.hashtable = new int[checksumcount];
    this.prime = findClosestPrime(checksumcount);
    if (debug)
      System.out.println("using prime " + this.prime); 
    int i;
    for (i = 0; i < checksumcount; i++) {
      byte[] buf = new byte[16];
      is.read(buf, 0, 16);
      this.checksums[i] = queryChecksum(buf, 16);
    } 
    for (i = 0; i < checksumcount; ) {
      this.hashtable[i] = -1;
      i++;
    } 
    for (i = 0; i < checksumcount; i++) {
      int hash = generateHash(this.checksums[i]) % this.prime;
      if (debug)
        System.out.println("checking with hash: " + hash); 
      if (this.hashtable[hash] != -1) {
        if (debug)
          System.out.println("hash table collision for index " + i); 
      } else {
        this.hashtable[hash] = i;
      } 
    } 
  }
  
  public int findChecksumIndex(long checksum) {
    return this.hashtable[generateHash(checksum) % this.prime];
  }
  
  private static int findClosestPrime(int size) {
    int prime = (int)SimplePrime.belowOrEqual((size - 1));
    return (prime < 2) ? 1 : prime;
  }
  
  private String printIntArray(int[] a) {
    String result = "";
    result = String.valueOf(result) + "[";
    for (int i = 0; i < a.length; i++) {
      result = String.valueOf(result) + a[i];
      if (i != a.length - 1) {
        result = String.valueOf(result) + ",";
      } else {
        result = String.valueOf(result) + "]";
      } 
    } 
    return result;
  }
  
  private String printLongArray(long[] a) {
    String result = "";
    result = String.valueOf(result) + "[";
    for (int i = 0; i < a.length; i++) {
      result = String.valueOf(result) + a[i];
      if (i != a.length - 1) {
        result = String.valueOf(result) + ",";
      } else {
        result = String.valueOf(result) + "]";
      } 
    } 
    return result;
  }
}

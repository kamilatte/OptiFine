package notch.net.optifine.config;

import cmn;
import net.optifine.Config;

public class MatchProfession {
  private cmn profession;
  
  private int[] levels;
  
  public MatchProfession(cmn profession) {
    this(profession, null);
  }
  
  public MatchProfession(cmn profession, int level) {
    this(profession, new int[] { level });
  }
  
  public MatchProfession(cmn profession, int[] levels) {
    this.profession = profession;
    this.levels = levels;
  }
  
  public boolean matches(cmn prof, int lev) {
    if (this.profession != prof)
      return false; 
    if (this.levels != null)
      if (!Config.equalsOne(lev, this.levels))
        return false;  
    return true;
  }
  
  private boolean hasLevel(int lev) {
    if (this.levels == null)
      return false; 
    return Config.equalsOne(lev, this.levels);
  }
  
  public boolean addLevel(int lev) {
    if (this.levels == null) {
      this.levels = new int[] { lev };
      return true;
    } 
    if (hasLevel(lev))
      return false; 
    this.levels = Config.addIntToArray(this.levels, lev);
    return true;
  }
  
  public cmn getProfession() {
    return this.profession;
  }
  
  public int[] getLevels() {
    return this.levels;
  }
  
  public static boolean matchesOne(cmn prof, int level, net.optifine.config.MatchProfession[] mps) {
    if (mps == null)
      return false; 
    for (int i = 0; i < mps.length; i++) {
      net.optifine.config.MatchProfession mp = mps[i];
      if (mp.matches(prof, level))
        return true; 
    } 
    return false;
  }
  
  public String toString() {
    if (this.levels == null)
      return String.valueOf(this.profession); 
    return String.valueOf(this.profession) + ":" + String.valueOf(this.profession);
  }
}

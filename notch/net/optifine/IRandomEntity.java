package notch.net.optifine;

import cti;
import ddw;
import dtc;
import jd;
import ub;

public interface IRandomEntity {
  int getId();
  
  jd getSpawnPosition();
  
  ddw getSpawnBiome();
  
  String getName();
  
  int getHealth();
  
  int getMaxHealth();
  
  ub getNbtTag();
  
  cti getColor();
  
  dtc getBlockState();
}

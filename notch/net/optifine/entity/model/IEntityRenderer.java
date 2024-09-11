package notch.net.optifine.entity.model;

import akr;
import bsx;
import dqj;
import net.optifine.util.Either;

public interface IEntityRenderer {
  Either<bsx, dqj> getType();
  
  void setType(Either<bsx, dqj> paramEither);
  
  akr getLocationTextureCustom();
  
  void setLocationTextureCustom(akr paramakr);
}

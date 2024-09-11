package notch.net.minecraftforge.client.extensions;

import akr;
import gql;
import gsm;
import gsq;
import gsv;
import java.util.function.Function;

public interface IForgeModelBaker {
  gsm bake(akr paramakr, gsv paramgsv, Function<gsq, gql> paramFunction);
  
  Function<gsq, gql> getModelTextureGetter();
}

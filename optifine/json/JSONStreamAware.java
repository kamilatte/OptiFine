package optifine.json;

import java.io.IOException;
import java.io.Writer;

public interface JSONStreamAware {
  void writeJSONString(Writer paramWriter) throws IOException;
}

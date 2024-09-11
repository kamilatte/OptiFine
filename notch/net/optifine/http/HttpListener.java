package notch.net.optifine.http;

import net.optifine.http.HttpRequest;
import net.optifine.http.HttpResponse;

public interface HttpListener {
  void finished(HttpRequest paramHttpRequest, HttpResponse paramHttpResponse);
  
  void failed(HttpRequest paramHttpRequest, Exception paramException);
}

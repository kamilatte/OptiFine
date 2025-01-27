package notch.net.optifine.http;

import java.util.Map;
import net.optifine.http.HttpUtils;
import net.optifine.http.IFileUploadListener;

public class FileUploadThread extends Thread {
  private String urlString;
  
  private Map headers;
  
  private byte[] content;
  
  private IFileUploadListener listener;
  
  public FileUploadThread(String urlString, Map headers, byte[] content, IFileUploadListener listener) {
    this.urlString = urlString;
    this.headers = headers;
    this.content = content;
    this.listener = listener;
  }
  
  public void run() {
    try {
      HttpUtils.post(this.urlString, this.headers, this.content);
      this.listener.fileUploadFinished(this.urlString, this.content, null);
    } catch (Exception e) {
      this.listener.fileUploadFinished(this.urlString, this.content, e);
    } 
  }
  
  public String getUrlString() {
    return this.urlString;
  }
  
  public byte[] getContent() {
    return this.content;
  }
  
  public IFileUploadListener getListener() {
    return this.listener;
  }
}

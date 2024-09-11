package notch.net.optifine.http;

import fgo;
import net.optifine.http.HttpPipeline;
import net.optifine.http.IFileDownloadListener;

public class FileDownloadThread extends Thread {
  private String urlString = null;
  
  private IFileDownloadListener listener = null;
  
  public FileDownloadThread(String urlString, IFileDownloadListener listener) {
    this.urlString = urlString;
    this.listener = listener;
  }
  
  public void run() {
    try {
      byte[] bytes = HttpPipeline.get(this.urlString, fgo.Q().Z());
      this.listener.fileDownloadFinished(this.urlString, bytes, null);
    } catch (Exception e) {
      this.listener.fileDownloadFinished(this.urlString, null, e);
    } 
  }
  
  public String getUrlString() {
    return this.urlString;
  }
  
  public IFileDownloadListener getListener() {
    return this.listener;
  }
}

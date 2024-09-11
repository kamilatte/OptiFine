package notch.net.optifine.shaders.config;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.optifine.Config;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.config.MacroState;
import net.optifine.shaders.config.ShaderMacro;
import net.optifine.shaders.config.ShaderMacros;
import net.optifine.shaders.config.ShaderOption;

public class MacroProcessor {
  public static InputStream process(InputStream in, String path, boolean useShaderOptions) throws IOException {
    String str = Config.readInputStream(in, "ASCII");
    String strMacroHeader = getMacroHeader(str, useShaderOptions);
    if (!strMacroHeader.isEmpty()) {
      str = strMacroHeader + strMacroHeader;
      if (Shaders.saveFinalShaders) {
        String filePath = path.replace(':', '/') + ".pre";
        Shaders.saveShader(filePath, str);
      } 
      str = process(str);
    } 
    if (Shaders.saveFinalShaders) {
      String filePath = path.replace(':', '/');
      Shaders.saveShader(filePath, str);
    } 
    byte[] bytes = str.getBytes("ASCII");
    ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
    return bais;
  }
  
  public static String process(String strIn) throws IOException {
    StringReader sr = new StringReader(strIn);
    BufferedReader br = new BufferedReader(sr);
    MacroState macroState = new MacroState();
    StringBuilder sb = new StringBuilder();
    while (true) {
      String line = br.readLine();
      if (line == null)
        break; 
      if (!macroState.processLine(line))
        continue; 
      if (MacroState.isMacroLine(line))
        continue; 
      sb.append(line);
      sb.append("\n");
    } 
    String strOut = sb.toString();
    return strOut;
  }
  
  private static String getMacroHeader(String str, boolean useShaderOptions) throws IOException {
    StringBuilder sb = new StringBuilder();
    List<ShaderOption> sos = null;
    List<ShaderMacro> sms = null;
    StringReader sr = new StringReader(str);
    BufferedReader br = new BufferedReader(sr);
    while (true) {
      String line = br.readLine();
      if (line == null)
        break; 
      if (!MacroState.isMacroLine(line))
        continue; 
      if (sb.length() == 0)
        sb.append(ShaderMacros.getFixedMacroLines()); 
      if (useShaderOptions) {
        if (sos == null)
          sos = getMacroOptions(); 
        for (Iterator<ShaderOption> iterator = sos.iterator(); iterator.hasNext(); ) {
          ShaderOption so = iterator.next();
          if (!line.contains(so.getName()))
            continue; 
          sb.append(so.getSourceLine());
          sb.append("\n");
          iterator.remove();
        } 
      } 
      if (sms == null)
        sms = new ArrayList<>(Arrays.asList(ShaderMacros.getExtensions())); 
      for (Iterator<ShaderMacro> it = sms.iterator(); it.hasNext(); ) {
        ShaderMacro sm = it.next();
        if (!line.contains(sm.getName()))
          continue; 
        sb.append(sm.getSourceLine());
        sb.append("\n");
        it.remove();
      } 
    } 
    return sb.toString();
  }
  
  private static List<ShaderOption> getMacroOptions() {
    List<ShaderOption> list = new ArrayList<>();
    ShaderOption[] sos = Shaders.getShaderPackOptions();
    for (int i = 0; i < sos.length; i++) {
      ShaderOption so = sos[i];
      String sourceLine = so.getSourceLine();
      if (sourceLine != null)
        if (sourceLine.startsWith("#"))
          list.add(so);  
    } 
    return list;
  }
}

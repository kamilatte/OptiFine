package optifine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OptiFineTweaker implements ITweaker {
  private List<String> args;
  
  private static final Logger LOGGER = LogManager.getLogger();
  
  public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
    dbg("OptiFineTweaker: acceptOptions");
    this.args = new ArrayList<>(args);
    this.args.add("--gameDir");
    this.args.add(gameDir.getAbsolutePath());
    this.args.add("--assetsDir");
    this.args.add(assetsDir.getAbsolutePath());
    this.args.add("--version");
    this.args.add(profile);
  }
  
  public void injectIntoClassLoader(LaunchClassLoader classLoader) {
    dbg("OptiFineTweaker: injectIntoClassLoader");
    classLoader.registerTransformer("optifine.OptiFineClassTransformer");
  }
  
  public String getLaunchTarget() {
    dbg("OptiFineTweaker: getLaunchTarget");
    return "net.minecraft.client.main.Main";
  }
  
  public String[] getLaunchArguments() {
    dbg("OptiFineTweaker: getLaunchArguments");
    return this.args.<String>toArray(new String[this.args.size()]);
  }
  
  private static void dbg(String str) {
    LOGGER.info("[OptiFine] " + str);
  }
}

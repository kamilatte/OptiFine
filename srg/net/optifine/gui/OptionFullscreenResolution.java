package srg.net.optifine.gui;

import com.mojang.blaze3d.platform.Monitor;
import com.mojang.blaze3d.platform.VideoMode;
import com.mojang.blaze3d.platform.Window;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;

public class OptionFullscreenResolution {
  public static OptionInstance make() {
    int j;
    Window window = Minecraft.getInstance().getWindow();
    Monitor monitor = window.findBestMonitor();
    if (monitor == null) {
      j = -1;
    } else {
      Optional<VideoMode> optional = window.getPreferredFullscreenVideoMode();
      Objects.requireNonNull(monitor);
      j = ((Integer)optional.<Integer>map(monitor::getVideoModeIndex).orElse(Integer.valueOf(-1))).intValue();
    } 
    OptionInstance<Integer> optioninstance = new OptionInstance("options.fullscreen.resolution", OptionInstance.noTooltip(), (p_232804_1_, p_232804_2_) -> (monitor == null) ? Component.translatable("options.fullscreen.unavailable") : ((p_232804_2_.intValue() == -1) ? Options.genericValueLabel(p_232804_1_, (Component)Component.translatable("options.fullscreen.current")) : Options.genericValueLabel(p_232804_1_, (Component)Component.literal(monitor.getMode(p_232804_2_.intValue()).toString()))), (OptionInstance.ValueSet)new OptionInstance.IntRange(-1, (monitor != null) ? (monitor.getModeCount() - 1) : -1), Integer.valueOf(j), p_232800_2_ -> {
          if (monitor != null)
            window.setPreferredFullscreenVideoMode((p_232800_2_.intValue() == -1) ? Optional.empty() : Optional.<VideoMode>of(monitor.getMode(p_232800_2_.intValue()))); 
        });
    return optioninstance;
  }
}

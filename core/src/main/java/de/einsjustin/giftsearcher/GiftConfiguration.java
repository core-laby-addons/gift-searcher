package de.einsjustin.giftsearcher;

import de.einsjustin.giftsearcher.api.GiftData;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget.ButtonSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.util.MethodOrder;

@ConfigName("settings")
public class GiftConfiguration extends AddonConfig {

  @Exclude
  private final List<GiftData> gifts = new ArrayList<>();

  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

  @MethodOrder(after = "enabled")
  @ButtonSetting
  public void clear() {
    DefaultGiftController.gifts.clear();
  }

  @Override
  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }

  public List<GiftData> getGifts() {
    return this.gifts;
  }
}

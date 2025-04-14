package de.einsjustin.giftsearcher;

import de.einsjustin.giftsearcher.api.generated.ReferenceStorage;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class GiftAddon extends LabyAddon<GiftConfiguration> {

  private static GiftAddon INSTANCE;

  @Override
  protected void enable() {
    INSTANCE = this;
    this.registerSettingCategory();
    this.registerListener(new BlockInteractListener());
  }

  @Override
  protected Class<GiftConfiguration> configurationClass() {
    return GiftConfiguration.class;
  }

  public static ReferenceStorage references() {
    return INSTANCE.referenceStorageAccessor();
  }

  public static GiftAddon getInstance() {
    return INSTANCE;
  }
}

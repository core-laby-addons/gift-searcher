package de.einsjustin.giftsearcher;

import de.einsjustin.giftsearcher.api.generated.ReferenceStorage;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;
import java.util.HashMap;
import java.util.List;

@AddonMain
public class GiftAddon extends LabyAddon<GiftConfiguration> {

  private static GiftAddon INSTANCE;

  private String currentServer = "";
  private HashMap<String, List<String>> headOwners = new HashMap<>();

  @Override
  protected void enable() {
    INSTANCE = this;
    this.registerSettingCategory();
    this.registerListener(new ServerListener(this));
    this.registerListener(new BlockInteractListener());

    this.headOwners.put("gommehd.net", List.of("GommeHD"));
    this.headOwners.put("smashmc.eu", List.of(
        "9d17d97b-241e-3100-9057-dc917fcf9ca1",
        "77722c99-ad31-3a73-b20c-63419386a7d3",
        "eb501d22-a6e4-3348-a0bd-0ff19f6429a4",
        "63d71819-fdb9-3a27-aa9f-e53d541cfe1f",
        "7b6f1218-2010-372a-a19b-f2a0c41b4393",
        "36bc2b9b-0e72-350b-a8e2-8da00bbf584f"
    ));
    this.headOwners.put("smashmc.de", List.of(
        "9d17d97b-241e-3100-9057-dc917fcf9ca1",
        "77722c99-ad31-3a73-b20c-63419386a7d3",
        "eb501d22-a6e4-3348-a0bd-0ff19f6429a4",
        "63d71819-fdb9-3a27-aa9f-e53d541cfe1f",
        "7b6f1218-2010-372a-a19b-f2a0c41b4393",
        "36bc2b9b-0e72-350b-a8e2-8da00bbf584f"
    ));
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

  public String currentServer() {
    return currentServer;
  }

  public void currentServer(String currentServer) {
    this.currentServer = currentServer;
  }

  public HashMap<String, List<String>> headOwners() {
    return headOwners;
  }

}

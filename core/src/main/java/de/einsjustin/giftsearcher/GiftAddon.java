package de.einsjustin.giftsearcher;

import de.einsjustin.giftsearcher.api.Gift;
import de.einsjustin.giftsearcher.api.GiftController;
import de.einsjustin.giftsearcher.api.GiftState;
import de.einsjustin.giftsearcher.api.event.BlockInteractEvent;
import de.einsjustin.giftsearcher.core.generated.DefaultReferenceStorage;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.NetworkPayloadEvent;
import net.labymod.api.models.addon.annotation.AddonMain;
import net.labymod.api.util.math.position.Position;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@AddonMain
public class GiftAddon extends LabyAddon<GiftConfiguration> {

  private static GiftAddon giftAddon;

  public final List<Gift> gifts = new ArrayList<>();

  public List<Gift> getGifts() {
    return gifts;
  }

  @Override
  protected void enable() {
    giftAddon = this;
    this.registerSettingCategory();
    this.registerListener(this);

    Thread thread = new Thread(() -> {
      while (true) {
        check();
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    });
    thread.start();
  }

  @Override
  protected Class<GiftConfiguration> configurationClass() {
    return GiftConfiguration.class;
  }

  public static GiftAddon getGiftAddon() {
    return giftAddon;
  }

  public DefaultReferenceStorage referenceStorage() {
    return this.referenceStorageAccessor();
  }

  @Subscribe
  public void onClientInteract(BlockInteractEvent event) {
    if (!GiftAddon.getGiftAddon().configuration().enabled().get()) {
      return;
    }
    GiftController giftController = this.referenceStorage().giftController();
    Gift giftByPosition = giftController.getGiftByPosition(event.getX(), event.getY(), event.getZ());
    if (giftByPosition == null) {
      return;
    }
    if (giftByPosition.getState().equals(GiftState.UNCLAIMED)) {
      giftController.changeGiftState(giftByPosition, GiftState.CLAIMED);
    }
  }

  @Subscribe
  public void on(NetworkPayloadEvent event) {
  }

  private void check() {
    if (!GiftAddon.getGiftAddon().configuration().enabled().get()) {
      return;
    }
    Minecraft minecraft = labyAPI().minecraft();
    ClientPlayer clientPlayer = minecraft.getClientPlayer();
    if (clientPlayer == null) {
      return;
    }
    Position position = clientPlayer.position();
    if (!minecraft.isIngame()) {
      return;
    }
    Gift nearestGift = this.referenceStorage().giftController().getNearestGift(position, false);
    if (nearestGift == null) {
      minecraft.chatExecutor().displayActionBar(Component.text("No gift found"));
      return;
    }
    double distance = distance(position, nearestGift);

    DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.US);
    DecimalFormat df = new DecimalFormat("#.###", decimalFormatSymbols);

    minecraft.chatExecutor().displayActionBar(Component.text("Gift at " + nearestGift.getX() + " " + nearestGift.getY() + " " + nearestGift.getZ() + ", Distance: " + df.format(distance)));
  }

  public double distance(Position a, Gift gift) {
    double dx = (int) (a.getX() - gift.getX());
    double dy = (int) (a.getY() - gift.getY());
    double dz = (int) (a.getZ() - gift.getZ());
    return Math.sqrt(dx * dx + dy * dy + dz * dz);
  }
}

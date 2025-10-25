package de.einsjustin.giftsearcher;

import de.einsjustin.giftsearcher.api.GiftController;
import de.einsjustin.giftsearcher.api.GiftData;
import de.einsjustin.giftsearcher.api.event.BlockInteractEvent;
import net.labymod.api.event.Subscribe;

public class BlockInteractListener {

  private final GiftController controller;

  public BlockInteractListener() {
    this.controller = GiftAddon.references().giftController();
  }

  @Subscribe
  public void onClientInteract(BlockInteractEvent event) {
    GiftData gift = this.controller.getGiftByPosition(event.x(), event.y(), event.z());
    if (gift == null) return;
    if (gift.isClaimed()) return;
    gift.setClaimed(true);
    this.controller.updateGift(gift);
  }
}

package de.einsjustin.giftsearcher.api;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface GiftController {

  void addGift(GiftData gift);

  void updateGift(GiftData gift);

  GiftData getGiftByPosition(float x, float y, float z);
}

package de.einsjustin.giftsearcher.api;

import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.math.position.Position;
import java.util.List;

@Referenceable
public interface GiftController {
  void addGift(Gift gift);
  void changeGiftState(Gift gift, GiftState state);
  List<Gift> getGifts();
  Gift getNearestGift(Position playerPosition, boolean showClaimed);
  Gift getGiftByPosition(int x, int y, int z);
}

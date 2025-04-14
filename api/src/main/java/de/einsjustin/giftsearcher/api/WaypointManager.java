package de.einsjustin.giftsearcher.api;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface WaypointManager {

  default void createWaypoint(GiftData gift) {
    this.createWaypoint(gift.getX(), gift.getY(), gift.getZ());
  }

  void createWaypoint(float x, float y, float z);
}

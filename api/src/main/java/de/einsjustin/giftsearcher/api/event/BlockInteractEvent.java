package de.einsjustin.giftsearcher.api.event;

import net.labymod.api.event.Event;

public class BlockInteractEvent implements Event {

  private final int x;
  private final int y;
  private final int z;

  public BlockInteractEvent(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getZ() {
    return z;
  }
}

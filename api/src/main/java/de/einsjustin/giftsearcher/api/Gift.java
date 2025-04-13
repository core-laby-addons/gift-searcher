package de.einsjustin.giftsearcher.api;

public class Gift {

  private final int x;
  private final int y;
  private final int z;
  private GiftState state = GiftState.UNCLAIMED;

  public Gift(int x, int y, int z) {
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

  public GiftState getState() {
    return state;
  }

  public void setState(GiftState state) {
    this.state = state;
  }
}

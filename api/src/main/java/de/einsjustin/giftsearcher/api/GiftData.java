package de.einsjustin.giftsearcher.api;

public class GiftData {

  private final float x;
  private final float y;
  private final float z;
  private boolean claimed = false;

  public GiftData(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public float getX() {
    return this.x;
  }

  public float getY() {
    return this.y;
  }

  public float getZ() {
    return this.z;
  }

  public boolean isClaimed() {
    return this.claimed;
  }

  public void setClaimed(boolean claimed) {
    this.claimed = claimed;
  }

  @Override
  public String toString() {
    return "GiftData{" +
        "x=" + this.x +
        ", y=" + this.y +
        ", z=" + this.z +
        ", claimed=" + this.claimed +
        '}';
  }
}

package de.einsjustin.giftsearcher;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.einsjustin.giftsearcher.api.Gift;
import de.einsjustin.giftsearcher.api.GiftController;
import de.einsjustin.giftsearcher.api.GiftState;
import net.labymod.addons.waypoints.WaypointService;
import net.labymod.addons.waypoints.Waypoints;
import net.labymod.addons.waypoints.waypoint.WaypointMeta;
import net.labymod.addons.waypoints.waypoint.WaypointType;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.models.Implements;
import net.labymod.api.util.Color;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector3;
import javax.inject.Singleton;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Singleton
@Implements(GiftController.class)
public class DefaultGiftController implements GiftController {

  private final List<Gift> gifts = GiftAddon.getGiftAddon().getGifts();

  private final JsonArray array;

  public DefaultGiftController() {
    File file = new File("C:\\Users\\JXTN\\Desktop\\Test", "gifts.json");
    if (!file.exists()) {
      try {
        if (file.createNewFile()) {
          try (FileWriter fw = new FileWriter(file)) {
            fw.write("[]");
          }
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    try {
      array = JsonParser.parseReader(new FileReader(file)).getAsJsonArray();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void addGift(Gift gift) {
    Gift giftByPosition = getGiftByPosition(gift.getX(), gift.getY(), gift.getZ());
    if (giftByPosition == null) {
      gifts.add(gift);

      if (!isClaimed(gift)) {
        WaypointService service = Waypoints.getReferences().waypointService();

        FloatVector3 location = new FloatVector3(gift.getX() + 0.5f, gift.getY(), gift.getZ() + 0.5f);

        service.addWaypoint(new WaypointMeta(
            Component.text("Gift"),
            Color.YELLOW,
            WaypointType.SERVER_SESSION,
            location,
            true,
            service.actualWorld(),
            service.actualServer(),
            service.actualDimension() != null
                ? service.actualDimension()
                : "labymod:unknown"
        ));

        service.refreshWaypoints();
      }
    }
  }

  @Override
  public void changeGiftState(Gift gift, GiftState state) {
    gift.setState(state);

    if (state == GiftState.CLAIMED) {
      appendToFile(gift.getX(), gift.getY(), gift.getZ());

      WaypointService service = Waypoints.getReferences().waypointService();
      service.removeWaypoints(waypoint -> {
        DoubleVector3 position = waypoint.position();
        Gift giftByPosition = getGiftByPosition((int) position.getX(), (int) position.getY(), (int) position.getZ());
        return giftByPosition != null;
      });
      service.refreshWaypoints();

    }
  }

  private void appendToFile(int x, int y, int z) {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("x", x);
    jsonObject.addProperty("y", y);
    jsonObject.addProperty("z", z);
    array.add(jsonObject);

    try (FileWriter fileWriter = new FileWriter(new File("C:\\Users\\JXTN\\Desktop\\Test", "gifts.json"))) {
      fileWriter.write(array.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Gift> getGifts() {
    return gifts;
  }

  @Override
  public Gift getNearestGift(Position playerPosition, boolean showClaimed) {

    Gift nearestGift = null;
    double closestDistance = Double.MAX_VALUE;

    if (gifts.isEmpty()) {
      return null;
    }
    for (Gift gift : gifts) {
      if (isClaimed(gift) && !showClaimed) {
        continue;
      }
      double distance = distance(playerPosition, gift);
      if (distance < closestDistance) {
        closestDistance = distance;
        nearestGift = gift;
      }
    }

    return nearestGift;
  }

  private boolean isClaimed(Gift gift) {

    for (JsonElement jsonElement : array) {
      JsonObject asJsonObject = jsonElement.getAsJsonObject();
      int x = asJsonObject.get("x").getAsInt();
      int y = asJsonObject.get("y").getAsInt();
      int z = asJsonObject.get("z").getAsInt();

      if (x == gift.getX() && y == gift.getY() && z == gift.getZ()) {
        return true;
      }
    }

    return gift.getState().equals(GiftState.CLAIMED);
  }

  @Override
  public Gift getGiftByPosition(int x, int y, int z) {
    for (Gift gift : gifts) {
      if (gift.getX() == x && gift.getY() == y && gift.getZ() == z) {
        return gift;
      }
    }
    return null;
  }

  private double distance(Position a, Gift gift) {
    double dx = (int) (a.getX() - gift.getX());
    double dy = (int) (a.getY() - gift.getY());
    double dz = (int) (a.getZ() - gift.getZ());
    return Math.sqrt(dx * dx + dy * dy + dz * dz);
  }
}

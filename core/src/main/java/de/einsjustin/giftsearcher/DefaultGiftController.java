package de.einsjustin.giftsearcher;

import de.einsjustin.giftsearcher.api.GiftController;
import de.einsjustin.giftsearcher.api.GiftData;
import de.einsjustin.giftsearcher.api.WaypointManager;
import net.labymod.addons.waypoints.WaypointService;
import net.labymod.addons.waypoints.Waypoints;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.vector.DoubleVector3;

import javax.inject.Singleton;
import java.util.List;

@Singleton
@Implements(GiftController.class)
public class DefaultGiftController implements GiftController {

  public static List<GiftData> gifts = new java.util.ArrayList<>();

    @Override
    public void addGift(GiftData gift) {
        GiftData existing = this.getGiftByPosition(gift.getX(), gift.getY(), gift.getZ());
        WaypointManager manager = GiftAddon.references().waypointManager();
        if (existing == null) {
            gifts.add(gift);
            manager.createWaypoint(gift);
        } else {
            if (existing.isClaimed()) {
                return;
            }
            manager.createWaypoint(existing);
        }
    }

    @Override
    public void updateGift(GiftData gift) {
        gifts.removeIf((data) ->
                gift.getX() == data.getX() && gift.getY() == data.getY() && gift.getZ() == data.getZ()
        );
        this.addGift(gift);
        if (gift.isClaimed()) {
            WaypointService service = Waypoints.references().waypointService();
            service.remove((waypoint) -> {
                DoubleVector3 position = waypoint.position();
                return Math.floor(position.getX()) == gift.getX()
                        && Math.floor(position.getY()) == gift.getY()
                        && Math.floor(position.getZ()) == gift.getZ();
            });
            service.refresh();
        }
    }

    @Override
    public GiftData getGiftByPosition(float x, float y, float z) {
        for (GiftData gift : gifts) {
            if (gift.getX() == x && gift.getY() == y && gift.getZ() == z) {
                return gift;
            }
        }
        return null;
    }
}

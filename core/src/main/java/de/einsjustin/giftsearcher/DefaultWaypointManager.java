package de.einsjustin.giftsearcher;

import de.einsjustin.giftsearcher.api.WaypointManager;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Singleton;
import net.labymod.addons.waypoints.WaypointService;
import net.labymod.addons.waypoints.Waypoints;
import net.labymod.addons.waypoints.waypoint.WaypointBuilder;
import net.labymod.addons.waypoints.waypoint.WaypointIcon;
import net.labymod.addons.waypoints.waypoint.WaypointMeta;
import net.labymod.addons.waypoints.waypoint.WaypointType;
import net.labymod.api.client.component.Component;
import net.labymod.api.models.Implements;
import net.labymod.api.util.Color;
import net.labymod.api.util.math.vector.FloatVector3;

@Singleton
@Implements(WaypointManager.class)
public class DefaultWaypointManager implements WaypointManager {

  private int giftCount = 0;
  private final Set<FloatVector3> locations = new HashSet<>();

  @Override
  public void createWaypoint(float x, float y, float z) {
    WaypointService service = Waypoints.references().waypointService();
    FloatVector3 location = new FloatVector3(x, y, z);
    if (this.locations.contains(location)) return;
    this.locations.add(location);
    int counter = ++this.giftCount;
    WaypointMeta meta = WaypointBuilder.create()
        .identifier(String.valueOf(counter))
        .title(Component.text("Gift #" + counter))
        .color(Color.YELLOW)
        .icon(WaypointIcon.getByPath("enderchest"))
        .type(WaypointType.SERVER_SESSION)
        .location(location.copy().add(0.5f, 0, 0.5f))
        .applyCurrentContext()
        .dimension(service.getDimension() != null ? service.getDimension() : "labymod:unknown")
        .build();
    service.add(meta);
    service.refresh();
  }
}

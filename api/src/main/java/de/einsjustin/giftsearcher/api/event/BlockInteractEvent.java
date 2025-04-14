package de.einsjustin.giftsearcher.api.event;

import net.labymod.api.event.Event;

public record BlockInteractEvent(int x, int y, int z) implements Event {

}

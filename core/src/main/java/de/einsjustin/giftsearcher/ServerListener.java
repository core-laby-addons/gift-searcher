package de.einsjustin.giftsearcher;

import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerLoginEvent;

public class ServerListener {

  private GiftAddon addon;

  public ServerListener(GiftAddon addon) {
    this.addon = addon;
  }

  @Subscribe
  public void onServerLogin(ServerLoginEvent event) {
    this.addon.currentServer(event.serverData().address().getHost());
  }

}

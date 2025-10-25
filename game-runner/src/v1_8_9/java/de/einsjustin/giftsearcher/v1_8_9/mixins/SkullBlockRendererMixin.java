package de.einsjustin.giftsearcher.v1_8_9.mixins;

import com.mojang.authlib.GameProfile;
import de.einsjustin.giftsearcher.GiftAddon;
import de.einsjustin.giftsearcher.api.GiftData;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.tileentity.TileEntitySkull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TileEntitySkullRenderer.class)
public abstract class SkullBlockRendererMixin {

  @Inject(
      method = "renderTileEntityAt(Lnet/minecraft/tileentity/TileEntitySkull;DDDFI)V",
      at = @At("HEAD")
  )
  private void renderTileEntityAt(TileEntitySkull tileEntitySkull, double distanceX, double distanceY, double distanceZ, float f1, int i1, CallbackInfo ci) {
    if(tileEntitySkull.getSkullType() != 3) return;
    GameProfile gameProfile = tileEntitySkull.getPlayerProfile();
    if (gameProfile == null) return;
    String name = gameProfile.getName();
    String currentServer = GiftAddon.getInstance().currentServer();
    if(!GiftAddon.getInstance().headOwners().containsKey(currentServer)) return;
    if (!GiftAddon.getInstance().headOwners().get(currentServer).contains(name)) return;
    GiftData gift = new GiftData((int) distanceX, (int) distanceY, (int) distanceZ);
    GiftAddon.references().giftController().addGift(gift);
  }

}

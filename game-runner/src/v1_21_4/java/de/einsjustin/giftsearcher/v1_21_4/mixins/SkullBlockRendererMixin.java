package de.einsjustin.giftsearcher.v1_21_4.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import de.einsjustin.giftsearcher.GiftAddon;
import de.einsjustin.giftsearcher.api.GiftData;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkullBlockRenderer.class)
public abstract class SkullBlockRendererMixin implements BlockEntityRenderer<SkullBlockEntity> {

  @Inject(
      method = "render(Lnet/minecraft/world/level/block/entity/SkullBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
      at = @At("HEAD")
  )
  private void on(SkullBlockEntity skullBlockEntity, float yaw, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay, CallbackInfo ci) {
    ResolvableProfile ownerProfile = skullBlockEntity.getOwnerProfile();
    if (ownerProfile == null) return;
    Optional<UUID> uuid = ownerProfile.id();
    if(uuid.isEmpty()) return;
    String currentServer = GiftAddon.getInstance().currentServer();
    if(!GiftAddon.getInstance().headOwners().containsKey(currentServer)) return;
    if(!GiftAddon.getInstance().headOwners().get(currentServer).contains(uuid.get().toString())) return;

    BlockPos blockPos = skullBlockEntity.getBlockPos();
    GiftData gift = new GiftData(blockPos.getX(), blockPos.getY(), blockPos.getZ());

    GiftAddon.references().giftController().addGift(gift);
  }

  @Override
  public boolean shouldRenderOffScreen(SkullBlockEntity skullBlockEntity) {
    String currentServer = GiftAddon.getInstance().currentServer();
    if(!GiftAddon.getInstance().headOwners().containsKey(currentServer)) return true;
    ResolvableProfile ownerProfile = skullBlockEntity.getOwnerProfile();
    if (ownerProfile == null) {
      return BlockEntityRenderer.super.shouldRenderOffScreen(skullBlockEntity);
    }
    Optional<UUID> uuid = ownerProfile.id();
    if(uuid.isEmpty()) {
      return BlockEntityRenderer.super.shouldRenderOffScreen(skullBlockEntity);
    }
    if (GiftAddon.getInstance().headOwners().get(currentServer).contains(uuid.get().toString())) {
      return BlockEntityRenderer.super.shouldRenderOffScreen(skullBlockEntity);
    }
    return true;
  }

}

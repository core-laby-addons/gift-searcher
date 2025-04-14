package de.einsjustin.giftsearcher.v1_21_4.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import de.einsjustin.giftsearcher.GiftAddon;
import de.einsjustin.giftsearcher.api.GiftData;
import java.util.Optional;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkullBlockRenderer.class)
public class SkullBlockRendererMixin {

  @Inject(
      method = "render(Lnet/minecraft/world/level/block/entity/SkullBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
      at = @At("HEAD")
  )
  private void on(SkullBlockEntity skullBlockEntity, float yaw, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay, CallbackInfo ci) {
    ResolvableProfile ownerProfile = skullBlockEntity.getOwnerProfile();
    if (ownerProfile == null) {
      return;
    }
    Optional<String> name = ownerProfile.name();
    if (name.isEmpty() || !name.get().equalsIgnoreCase("GommeHD")) {
      return;
    }

    BlockPos blockPos = skullBlockEntity.getBlockPos();
    GiftData gift = new GiftData(blockPos.getX(), blockPos.getY(), blockPos.getZ());

    GiftAddon.references().giftController().addGift(gift);
  }
}

package de.einsjustin.giftsearcher.v1_21_4.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import de.einsjustin.giftsearcher.GiftAddon;
import de.einsjustin.giftsearcher.api.Gift;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.client.world.object.AbstractWorldObject;
import net.labymod.v1_21_4.client.util.MinecraftUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Optional;

@Mixin(SkullBlockRenderer.class)
public class SkullBlockRendererMixin {

  @Inject(
      method = "render(Lnet/minecraft/world/level/block/entity/SkullBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
      at = @At("HEAD")
  )
  private void on(SkullBlockEntity skullBlockEntity, float yaw, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay, CallbackInfo ci) {

    ResolvableProfile ownerProfile = skullBlockEntity.getOwnerProfile();
    if (ownerProfile != null) {
      Optional<String> name = ownerProfile.name();
      if (name.isPresent()) {
        if (name.get().equalsIgnoreCase("GommeHD")) {
          BlockPos blockPos = skullBlockEntity.getBlockPos();

          Gift gift = new Gift(blockPos.getX(), blockPos.getY(), blockPos.getZ());

          // Glow



          GiftAddon.getGiftAddon().referenceStorage().giftController().addGift(gift);
        }
      }
    }
  }
}

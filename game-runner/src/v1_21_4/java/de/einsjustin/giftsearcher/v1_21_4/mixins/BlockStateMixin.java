package de.einsjustin.giftsearcher.v1_21_4.mixins;

import de.einsjustin.giftsearcher.GiftAddon;
import de.einsjustin.giftsearcher.api.event.BlockInteractEvent;
import net.labymod.api.Laby;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Optional;
import java.util.UUID;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateMixin {

  @Shadow
  public abstract boolean blocksMotion();

  @Inject(
      method = "useWithoutItem",
      at = @At("HEAD")
  )
  public void in(Level level, Player player, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir) {
    BlockPos blockPos = blockHitResult.getBlockPos();
    BlockEntity blockEntity = level.getBlockEntity(blockPos);
    if(blockEntity != null) {
      if(blockEntity instanceof SkullBlockEntity skullBlockEntity) {
        ResolvableProfile ownerProfile = skullBlockEntity.getOwnerProfile();
        if (ownerProfile != null) {
          Optional<UUID> uuid = ownerProfile.id();
          if(uuid.isPresent()) {
            uuid.ifPresent(s -> GiftAddon.getInstance().logger().info("Head Owner (UUID): " + s));
          }
        } else {
          GiftAddon.getInstance().logger().info("OwnerProfile is null");
        }
      }
    }
    Laby.fireEvent(new BlockInteractEvent(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
  }
}

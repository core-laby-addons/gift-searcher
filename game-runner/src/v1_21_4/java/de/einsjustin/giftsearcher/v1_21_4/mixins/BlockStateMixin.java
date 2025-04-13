package de.einsjustin.giftsearcher.v1_21_4.mixins;

import de.einsjustin.giftsearcher.api.event.BlockInteractEvent;
import net.labymod.api.Laby;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
    Laby.fireEvent(new BlockInteractEvent(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
  }
}

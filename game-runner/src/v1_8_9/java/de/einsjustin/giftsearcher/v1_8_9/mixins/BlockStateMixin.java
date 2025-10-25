package de.einsjustin.giftsearcher.v1_8_9.mixins;

import com.mojang.authlib.GameProfile;
import de.einsjustin.giftsearcher.GiftAddon;
import de.einsjustin.giftsearcher.api.event.BlockInteractEvent;
import net.labymod.api.Laby;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockStateMixin {

  @Inject(
      method = "onBlockActivated",
      at = @At("HEAD")
  )
  private void on(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float lvt_6_1_, float lvt_7_1_, float lvt_8_1_, CallbackInfoReturnable<Boolean> cir) {
    if(blockState != null) {
      TileEntity tileEntity = world.getTileEntity(blockPos);
      if(tileEntity != null) {
        if(tileEntity instanceof TileEntitySkull tileEntitySkull) {
          if(tileEntitySkull.getSkullType() == 3) {
            GameProfile gameProfile = tileEntitySkull.getPlayerProfile();
            if (gameProfile != null) {
              GiftAddon.getInstance().logger().info("Head Owner: " + gameProfile.getName());
            }
          }
        }
      }
    }
    Laby.fireEvent(new BlockInteractEvent(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
  }

}

/*import de.einsjustin.giftsearcher.GiftAddon;
import de.einsjustin.giftsearcher.api.event.BlockInteractEvent;
import java.util.Optional;
import net.labymod.api.Laby;
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
    BlockEntity blockEntity = level.getBlockEntity(blockPos);
    if(blockEntity != null) {
      if(blockEntity instanceof SkullBlockEntity skullBlockEntity) {
        ResolvableProfile ownerProfile = skullBlockEntity.getOwnerProfile();
        if (ownerProfile != null) {
          Optional<String> name = ownerProfile.name();
          if(name.isPresent()) {
            name.ifPresent(s -> GiftAddon.getInstance().logger().info("Head Owner: " + s));
          } else {
            GiftAddon.getInstance().logger().info("Name is empty");
          }
        } else {
          GiftAddon.getInstance().logger().info("OwnerProfile is null");
        }
      } else {
        GiftAddon.getInstance().logger().info("BlockEntity is not a SkullBlockEntity");
      }
    } else {
      GiftAddon.getInstance().logger().info("BlockEntity is null");
    }
    Laby.fireEvent(new BlockInteractEvent(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
  }
}*/

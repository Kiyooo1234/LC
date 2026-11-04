package com.example.fastclick.mixin;

import com.example.fastclick.FastClickClient;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public abstract class MultiPlayerGameModeMixin {
	// Delay between breaking one block and starting the next.
	@Shadow
	private int destroyDelay;

	@Inject(method = "continueDestroyBlock", at = @At("HEAD"))
	private void fastclick$reduceBreakDelay(BlockPos pos, Direction direction,
											CallbackInfoReturnable<Boolean> cir) {
		if (FastClickClient.enabled && destroyDelay > FastClickClient.delay) {
			destroyDelay = FastClickClient.delay;
		}
	}
}

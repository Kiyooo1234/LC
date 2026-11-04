package com.example.fastclick.mixin;

import com.example.fastclick.FastClickClient;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
	// Delay between attack swings when holding left click.
	@Shadow
	private int missTime;

	@Inject(method = "tick", at = @At("HEAD"))
	private void fastclick$reduceAttackDelay(CallbackInfo ci) {
		if (FastClickClient.enabled && missTime > FastClickClient.delay) {
			missTime = FastClickClient.delay;
		}
	}
}

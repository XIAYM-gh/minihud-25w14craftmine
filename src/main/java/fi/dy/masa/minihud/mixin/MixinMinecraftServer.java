package fi.dy.masa.minihud.mixin;

import fi.dy.masa.minihud.util.DebugInfoUtils;
import net.minecraft.server.GameInstance;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer {
    @Inject(method = "tick", at = @At("TAIL"))
    public void onServerTickPost(GameInstance gameInstance, BooleanSupplier booleanSupplier, CallbackInfo ci) {
        DebugInfoUtils.onServerTickEnd((MinecraftServer) (Object) this);
    }
}

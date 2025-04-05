package fi.dy.masa.minihud.mixin;

import fi.dy.masa.malilib.config.HudAlignment;
import fi.dy.masa.minihud.MiniHUD;
import fi.dy.masa.minihud.config.Configs;
import fi.dy.masa.minihud.util.VanillaWorldEffectMode;
import net.minecraft.aprilfools.WorldEffect;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(InGameHud.class)
public class MixinInGameHud {
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "method_70261", at = @At("HEAD"), cancellable = true)
    private void onRenderWorldEffects(CallbackInfo ci) {
        MiniHUD.deltaPosY = 0;

        if (!Configs.Generic.MAIN_RENDERING_TOGGLE.getBooleanValue() ||
                Configs.Generic.HUD_ALIGNMENT.getOptionListValue() != HudAlignment.TOP_LEFT) {
            return;
        }

        if (Configs.Generic.VANILLA_WORLD_EFFECT_MODE.getOptionListValue() == VanillaWorldEffectMode.REPLACE) {
            ci.cancel();
            return;
        }

        if (client.world == null) {
            return;
        }

        // Calculate how much should we move beneath
        int y = 0;
        if (client.isDemo()) {
            y += 15;
        }

        List<WorldEffect> effects = client.world.method_69125();
        int lastK = effects.size() - 1;

        int l = MathHelper.ceil(effects.size() / 10.0F);
        if (l <= 3) {
            y += l * 25;
        } else {
            y += (int)(50.0F / l * MathHelper.floor(lastK / 10.0F)) + 24;
        }

        MiniHUD.deltaPosY = y + 1;
    }
}

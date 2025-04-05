package fi.dy.masa.minihud.util;

import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import fi.dy.masa.malilib.util.StringUtils;

public enum VanillaWorldEffectMode implements IConfigOptionListEntry {
    MOVE_BENEATH("beneath", "minihud.label.vanilla_world_effect_mode.move_beneath"),
    REPLACE("replace", "minihud.label.vanilla_world_effect_mode.replace");

    private final String configString;
    private final String translationKey;

    VanillaWorldEffectMode(String configString, String translationKey) {
        this.configString = configString;
        this.translationKey = translationKey;
    }

    public static VanillaWorldEffectMode fromStringStatic(String name) {
        for (VanillaWorldEffectMode val : VanillaWorldEffectMode.values()) {
            if (val.configString.equalsIgnoreCase(name)) {
                return val;
            }
        }

        return VanillaWorldEffectMode.MOVE_BENEATH;
    }

    @Override
    public String getStringValue() {
        return this.configString;
    }

    @Override
    public String getDisplayName() {
        return StringUtils.translate(this.translationKey);
    }

    @Override
    public IConfigOptionListEntry cycle(boolean forward) {
        int id = this.ordinal();

        if (forward) {
            if (++id >= values().length) {
                id = 0;
            }
        } else {
            if (--id < 0) {
                id = values().length - 1;
            }
        }

        return values()[id % values().length];
    }

    @Override
    public VanillaWorldEffectMode fromString(String name) {
        return fromStringStatic(name);
    }
}

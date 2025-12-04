package dev.redstone.treefalling.config;

import me.fzzyhmstrs.fzzy_config.annotations.Comment;
import me.fzzyhmstrs.fzzy_config.config.Config;
import net.minecraft.util.Identifier;

import static dev.redstone.treefalling.Treefalling.MOD_ID;

public class TreefallingConfig extends Config {
    public TreefallingConfig() {
        super(Identifier.of(MOD_ID, "treefalling"));
    }

    @Comment("Enables physics-based Tree harvesting")
    @Name("Physics")
    public boolean Physics = true;

    @Comment("Enables Durability")
    @Name("Durability")
    public boolean Durability = true;

    @Comment("Enables Mining To Take Longer")
    @Name("Time Takes")
    public boolean TimeTakes = true;
}

package dev.redstone.treefalling.config;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;

public class TreefallingConfigs {

    public static TreefallingConfig TreefallingConfig = ConfigApiJava.registerAndLoadConfig(TreefallingConfig::new);

    public static void init() {}
}

package pl.ololjvNek.skycastle.variables;

import codecrafter47.bungeetablistplus.api.bukkit.Variable;
import org.bukkit.entity.Player;
import pl.ololjvNek.skycastle.Main;

public class WhatTopVariable extends Variable {

    public WhatTopVariable(String name) {
        super(name);
    }

    @Override
    public String getReplacement(Player player) {
        return (Main.isShowTopPoints() ? "&a&lPUNKTY" : "&e&lGWIAZDKI");
    }
}

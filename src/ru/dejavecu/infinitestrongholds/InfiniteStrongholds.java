package ru.dejavecu.infinitestrongholds;

import org.bukkit.plugin.java.JavaPlugin;


public class InfiniteStrongholds extends JavaPlugin {
  public void onEnable() {
    this.saveDefaultConfig();
    this.getServer().getPluginManager().registerEvents(new ChunkListener(this), this);
  }
}

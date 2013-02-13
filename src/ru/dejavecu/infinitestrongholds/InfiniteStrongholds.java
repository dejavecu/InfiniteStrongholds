package ru.dejavecu.infinitestrongholds;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;


public class InfiniteStrongholds extends JavaPlugin {
  public void onEnable() {
    this.saveDefaultConfig();
    ConfigurationSection worldsConfiguration = this.getConfig().getConfigurationSection("worlds");
    Set<String> worldList = worldsConfiguration.getKeys(false);
    Map<String, Double> densities = new HashMap<String, Double>();
    for (String world : worldList) {
      densities.put(world, worldsConfiguration.getDouble(world + ".density"));
    }
    
    this.getServer().getPluginManager().registerEvents(new ChunkListener(densities), this);
}
}

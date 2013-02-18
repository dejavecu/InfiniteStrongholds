package ru.dejavecu.infinitestrongholds;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import net.minecraft.server.v1_4_R1.World;


import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_4_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ChunkListener implements Listener {

  private Map<String, InfiniteStrongholdGenerator> strongholdGenerators = new HashMap<String, InfiniteStrongholdGenerator>();
  private Random random = new Random();

  public ChunkListener(JavaPlugin plugin) {
    ConfigurationSection worldsConfiguration = plugin.getConfig().getConfigurationSection("worlds");
    Set<String> worldList = worldsConfiguration.getKeys(false);
    for (String worldName : worldList) {
      Double density = worldsConfiguration.getDouble(worldName + ".density");
      strongholdGenerators.put(worldName, new InfiniteStrongholdGenerator(plugin, density));
    }

  }

  @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
  public void onChunkLoad(ChunkLoadEvent event) {
    String worldName = event.getWorld().getName(); 
    if (strongholdGenerators.containsKey(worldName)) {
      World world = ((CraftWorld)event.getWorld()).getHandle();
      strongholdGenerators.get(worldName).a(null, world, event.getChunk().getX(), event.getChunk().getZ(), null);
    }
  }

  @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
  public void onChunkPopulate(ChunkPopulateEvent event) {
    String worldName = event.getWorld().getName(); 
    if (strongholdGenerators.containsKey(worldName)) {
      random.setSeed(event.getChunk().getWorld().getSeed());    
      World world = ((CraftWorld)event.getWorld()).getHandle();
      strongholdGenerators.get(worldName).a(world, random, event.getChunk().getX(), event.getChunk().getZ());
    }
  }
}

package ru.dejavecu.infinitestrongholds;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.server.v1_4_R1.World;


import org.bukkit.craftbukkit.v1_4_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;

public class ChunkListener implements Listener {
   
  private Map<String, InfiniteStrongholdGenerator> strongholdGenerators = new HashMap<String, InfiniteStrongholdGenerator>();
  private Random random = new Random();

  public ChunkListener(Map<String, Double> densities) {
    for (String worldName : densities.keySet()) { 
      System.out.println(worldName);
      System.out.println(densities.get(worldName));
      strongholdGenerators.put(worldName, new InfiniteStrongholdGenerator(densities.get(worldName)));
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

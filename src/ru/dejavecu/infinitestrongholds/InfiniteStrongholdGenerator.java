package ru.dejavecu.infinitestrongholds;

import net.minecraft.server.v1_4_R1.*;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public class InfiniteStrongholdGenerator extends StructureGenerator {

  private JavaPlugin plugin;
  private ChunkCoordIntPair[] g;
  private Double density;
  static long modulo = (1000 / 16 ) * (1000 / 16) * 100;
  MessageDigest md = null;
  int total = 0;

  public InfiniteStrongholdGenerator(JavaPlugin plugin, Double density) {          
    this.density = density;
    this.plugin = plugin;
    try {
      md = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }

  protected boolean a(int chunkX, int chunkZ) {
    Long seed = this.c.getSeed();
    int d      =  (int) (density * 100);
    md.update(ByteBuffer.allocate(8).putLong(chunkX).array());
    md.update(ByteBuffer.allocate(8).putLong(chunkZ).array());
    md.update(ByteBuffer.allocate(8).putLong(seed).array());
    ByteBuffer buf = ByteBuffer.wrap(md.digest());  
    int result = buf.getInt();  
    long c = result % modulo;
    if (Math.abs(c) < d) {
      return true;
    } else return false;

  }

  protected List<ChunkPosition> p_() {
    ArrayList<ChunkPosition> arraylist = new ArrayList<ChunkPosition>();
    ChunkCoordIntPair[] achunkcoordintpair = this.g;
    int i = achunkcoordintpair.length;

    for (int j = 0; j < i; ++j) {
      ChunkCoordIntPair chunkcoordintpair = achunkcoordintpair[j];

      if (chunkcoordintpair != null) {
        arraylist.add(chunkcoordintpair.a(64));
      }
    }

    return arraylist;
  }

  protected StructureStart b(int i, int j) {
    ConfigurationSection componentsConfiguration = plugin.getConfig().getConfigurationSection("generation.components");
    WorldGenStronghold2Start worldgenstronghold2start = new WorldGenStronghold2Start(componentsConfiguration, this.c, this.b, i, j);

    for (; worldgenstronghold2start.b().isEmpty() || ((WorldGenStrongholdStart) worldgenstronghold2start.b().get(0)).b == null; 
        worldgenstronghold2start = new WorldGenStronghold2Start(componentsConfiguration, this.c, this.b, i, j)) {
      ;
    }

    return worldgenstronghold2start;
  }
}

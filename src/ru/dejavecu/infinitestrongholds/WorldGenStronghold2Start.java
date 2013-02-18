package ru.dejavecu.infinitestrongholds;

import net.minecraft.server.v1_4_R1.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.configuration.ConfigurationSection;

class WorldGenStronghold2Start extends StructureStart {

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public WorldGenStronghold2Start(ConfigurationSection componentsConfiguration, World world, Random random, int i, int j) {
    WorldGenStrongholdPieces.a();
    try {
      Field strongholdPiecesWeightsField = WorldGenStrongholdPieces.class.getDeclaredField("c");
      strongholdPiecesWeightsField.setAccessible(true);
      Constructor usualComponentConstructor = Class.forName("net.minecraft.server.v1_4_R1.WorldGenStrongholdPieceWeight").getDeclaredConstructors()[0];
      usualComponentConstructor.setAccessible(true);
      Constructor libraryComponentConstructor = Class.forName("net.minecraft.server.v1_4_R1.WorldGenStrongholdUnknown").getDeclaredConstructors()[0];
      libraryComponentConstructor.setAccessible(true);
      Constructor portalRoomComponentConstructor = Class.forName("net.minecraft.server.v1_4_R1.WorldGenStrongholdPiece2").getDeclaredConstructors()[0];
      portalRoomComponentConstructor.setAccessible(true);

      List b = new ArrayList();
      for (String componentName : componentsConfiguration.getKeys(false)) {
        Constructor constructor;
        if (componentName == "Library")
          constructor = libraryComponentConstructor;
        else
          if (componentName == "PortalRoom")
            constructor = portalRoomComponentConstructor;
          else
            constructor = usualComponentConstructor;
        Class componentClass;
        try {
          componentClass = Class.forName("net.minecraft.server.v1_4_R1.WorldGenStronghold" + componentName);
          int frequency = componentsConfiguration.getInt(componentName + ".frequency", 0);
          int maxinstances = componentsConfiguration.getInt(componentName + ".maxinstances", 0);
          b.add(constructor.newInstance(componentClass, frequency, maxinstances));
        } catch (ClassNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      strongholdPiecesWeightsField.set(null, b);

    } catch (SecurityException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NoSuchFieldException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InstantiationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    WorldGenStrongholdStart worldgenstrongholdstart = new WorldGenStrongholdStart(0, random, (i << 4) + 2, (j << 4) + 2);
    this.a.add(worldgenstrongholdstart);
    worldgenstrongholdstart.a(worldgenstrongholdstart, this.a, random);
    ArrayList arraylist = worldgenstrongholdstart.c;

    while (!arraylist.isEmpty()) {
      int k = random.nextInt(arraylist.size());
      StructurePiece structurepiece = (StructurePiece) arraylist.remove(k);

      structurepiece.a((StructurePiece) worldgenstrongholdstart, (List) this.a, random);
    }
    this.c();
    this.a(world, random, 10);
  }
}


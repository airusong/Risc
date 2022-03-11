package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class V1MapTest {
  @Test

  public void checkcontents() {
    V1Map<Character> map = new V1Map<>();
    assertEquals(map.myTerritories.containsKey("Narnia"), true);
    assertEquals(map.myTerritories.get("Narnia").getAdjacency().contains("Midemio"), true);
    assertEquals(map.myTerritories.get("Narnia").getAdjacency().contains("Elantris"), true);
  } 
  @Test 
  @SuppressWarnings("unchecked")
  public void test_serialization_basicMap() {
    V1Map<Character> mapOrg = new V1Map<Character>();
    V1Map<Character> mapNew = null;

    // Serialize the original class object
    try {
      FileOutputStream fo = new FileOutputStream("map.tmp");
      ObjectOutputStream so = new ObjectOutputStream(fo);
      so.writeObject(mapOrg);
      so.flush();
      so.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }

    // Deserialize in to new class object
    try {
      FileInputStream fi = new FileInputStream("map.tmp");
      ObjectInputStream si = new ObjectInputStream(fi);
      mapNew = (V1Map<Character>) si.readObject();
      si.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }

    assertEquals(mapOrg.myTerritories.containsKey("Narnia"), mapNew.myTerritories.containsKey("Narnia"));
  }

  @Test
  public void test_territoriesGroupedByOwner() {
    V1Map<Character> map = new V1Map<>();
    HashMap<String, ArrayList<String>> terrGroups = map.getOwnersTerritoryGroups();

    ArrayList<String> expectedGreenTerritories = new ArrayList<>();
    expectedGreenTerritories.add("Narnia");
    expectedGreenTerritories.add("Midemio");
    expectedGreenTerritories.add("Oz");
    ArrayList<String> expectedBlueTerritories = new ArrayList<>();
    expectedBlueTerritories.add("Elantris");
    expectedBlueTerritories.add("Roshar");
    expectedBlueTerritories.add("Scadnal");

    assertEquals(expectedGreenTerritories, terrGroups.get("Green"));
    assertEquals(expectedBlueTerritories, terrGroups.get("Blue"));
    
  }

}

package edu.duke.ece651.mp.server;
import java.util.ArrayList;
import java.util.HashMap;
/* construct a V1Map specified with Territoris in it
 * @param hashmap myTerritory, the key is the territory itself and the value is the list of adjancent territories
 *
 */
public class V1Map<T> implements Map<T>{
  final HashMap<Territory<T>,ArrayList<Territory<T>>> myTerritory;
  public V1Map(){
    this.myTerritory=new HashMap<>();
    myTerritory.put(new Territory<T>("Narnia"),new ArrayList<>());
  }
  
}

package edu.duke.ece651.mp.common;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class V1Map<T> implements Map<T>, Serializable {
  public HashMap<String, Territory<T>> myTerritories; // key=name, value=object itself
  //public HashMap<String,Function<String,Territory<Character>> mapCreationFun;
  /**
   * Constructor
   * construct a V1Map specified with Territoris in it
   * @param hashmap myTerritories, 
   * the key is the territory name 
   * the value is the territory object
   */
  public V1Map(){
    this.myTerritories = new HashMap<>();
    setMap();
    addAdjacency();
  }
  // public HashMap<String, Territory<T>> getMap(){
  //  return myTerritories;
  // }
  /**
   * function used in constructor to initialize the hashmap
   *  
   **/
  protected void setMap(){
    myTerritories.put("Narnia", new Territory<>("Narnia","Green",new ArrayList<String>()));
    myTerritories.put("Midemio", new Territory<>("Midemio","Green",new ArrayList<String>()));
    myTerritories.put("Oz", new Territory<T>("Oz","Green",new ArrayList<String>()));
    myTerritories.put("Elantris", new Territory<T>("Elantris","Blue",new ArrayList<String>()));
    myTerritories.put("Scadnal", new Territory<T>("Scandal","Blue",new ArrayList<String>()));
    myTerritories.put("Roshar", new Territory<T>("Roshar","Blue",new ArrayList<String>()));
  }
  /**
   * function used to initialize the adjacency information in the map
   **/
  protected void addAdjacency(){
    myTerritories.get("Narnia").addAdjacency("Midemio");
    myTerritories.get("Narnia").addAdjacency("Elantris");
    myTerritories.get("Midemio").addAdjacency("Narnia");
    myTerritories.get("Midemio").addAdjacency("Oz");
    myTerritories.get("Oz").addAdjacency("Midemio");
    myTerritories.get("Oz").addAdjacency("Roshar");
    myTerritories.get("Elantris").addAdjacency("Scadnal");
    myTerritories.get("Elantris").addAdjacency("Narnia");
    myTerritories.get("Scadnal").addAdjacency("Roshar");
    myTerritories.get("Scadnal").addAdjacency("Elantris");
    myTerritories.get("Roshar").addAdjacency("Oz");
    myTerritories.get("Roshar").addAdjacency("Scadnal");
  }
  /**
   * Write out the V1Map object for serialization
   * to the ObjectOutputStream s.
   * readObject depends on this data format.
   */
  private void writeObject(ObjectOutputStream s) throws IOException {
    // Call even if there is no default serializable fields.
    s.defaultWriteObject();

    // Hashmap is serializable
    s.writeObject(myTerritories);

    // otehr fields go here
    
  }

  
  /** Read in the V1Map object from the ObjectInputStream s.
   * Was written to by writeObject.
   * 
   * @serialData Read serializable fields, if any exist.
   */
  @SuppressWarnings({"unchecked"})
  private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException  {
    /* Call even if there is no default serializable fields.
     * Enables default serializable fields to be added in future versions
     * and skipped by this version which has no default serializable fields.
     */
    s.defaultReadObject();

    // restore the HashMap
    myTerritories = (HashMap< String, Territory<T> >)s.readObject();

    // other fields go here
  }
  
}

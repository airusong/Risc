package edu.duke.ece651.mp.common;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class V1Map<T> implements Map<T>, Serializable {
  public HashMap<String, Territory<T>> myTerritories; // key=name, value=object itself
  
  /**
   * Constructor
   * construct a V1Map specified with Territoris in it
   * @param hashmap myTerritories, 
   * the key is the territory itself and 
   * the value is the list of adjancent territories
   */
  public V1Map(){
    this.myTerritories = new HashMap<>();
    myTerritories.put("Narnia", new Territory<T>("Narnia"));
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

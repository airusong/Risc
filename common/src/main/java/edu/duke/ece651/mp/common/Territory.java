package edu.duke.ece651.mp.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/*
 * construct a Territory class
 * @param: name
 * @param: color: one color represents one player
 */
public class Territory<T> implements ITerritory<T>, Serializable {
  private String name;
  private String color;
  // public ArrayList<String> adjacentTerritories

    public Territory() {
    this.name = null;
   }
  
  public Territory(String name,String color){
    this.name=name;
    this.color=color;
  }
  public String getName(){
    return name;
  }
  public String getColor(){
    return color;
  }

  // public void addAdjacentTo

   /**
   * Write out the Territory object for serialization
   * to the ObjectOutputStream s.
   * readObject depends on this data format.
   * @serialData Write serializable fields, if any exist.
   * Write each field of the Territory Class
   */
  private void writeObject(ObjectOutputStream s) throws IOException {
    // Call even if there is no default serializable fields.
    s.defaultWriteObject();

    // save the name
    s.writeObject(name);

    // New added fields go here
  }

  /** Read in the territory object from the ObjectInputStream s.
   * Was written to by writeObject.
   * 
   * @serialData Read serializable fields, if any exist.
   */
  private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException  {
    /* Call even if there is no default serializable fields.
     * Enables default serializable fields to be added in future versions
     * and skipped by this version which has no default serializable fields.
     */
    s.defaultReadObject();

    // restore the name
    name = (String)s.readObject();

    // other fields go here
  }

}

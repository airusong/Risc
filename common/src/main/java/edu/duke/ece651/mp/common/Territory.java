package edu.duke.ece651.mp.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/*
 * construct a Territory class
 * @param: name

 * @param: color: one color represents one player
 * @param: adjacentTerritories
 * @param: unit: number
 */
public class Territory<T> implements IITerritory<T>, Serializable {
  private String name;
  private String color;
  private ArrayList<String> adjacentTerritories;
  private ArrayList<Unit> unit_list;
  private FoodResource food;
  private TechResource tech;
  private int size;

  public Territory(String name, String color, ArrayList<String> adjacentTerritories, int size) {
    this.name = name;
    this.color = color;
    this.adjacentTerritories = new ArrayList<>();
    this.unit_list = new ArrayList<Unit>();
    this.food = new FoodResource(0);
    this.tech = new TechResource(0);
    this.size = size;
  }

  public ArrayList<Unit> getUnitList() {
    return unit_list;
  }

  public String getName() {
    return name;
  }

  public String getColor() {
    return color;
  }

  public int getFoodNum() { return food.getResourceAmount();}

  public int getTechNum() { return tech.getResourceAmount();}

  /**
   * function to add adjacency to the territory
   */
  public void addAdjacency(String name) {
    adjacentTerritories.add(name);
  }

  /**
   * function to add resources to the territory
   */
  public void updateResource(Resource resource) {
    if(resource instanceof FoodResource){
      int new_num = food.getResourceAmount() + resource.getResourceAmount();
      setFoodResource(new_num);
    }
    else if(resource instanceof TechResource){
      int new_num = tech.getResourceAmount() + resource.getResourceAmount();
      setTechResource(new_num);
    }
  }

  public void setFoodResource(int num){
    food.setResourceAmount(num);
  }
  public void setTechResource(int num){
    tech.setResourceAmount(num);
  }

  /**
   * function to add Units to the territory
   */
  public void addUnit(Unit unit) {
    unit_list.add(unit);
  }

  /*
   * function to get the adjacency list
   */
  public ArrayList<String> getAdjacency() {
    return adjacentTerritories;
  }

  /**
   * Write out the Territory object for serialization
   * to the ObjectOutputStream s.
   * readObject depends on this data format.
   * 
   * @serialData Write serializable fields, if any exist.
   *             Write each field of the Territory Class
   */
  private void writeObject(ObjectOutputStream s) throws IOException {
    // Call even if there is no default serializable fields.
    s.defaultWriteObject();

    // save the name
    s.writeObject(name);

    // New added fields go here
  }

  /**
   * Read in the territory object from the ObjectInputStream s.
   * Was written to by writeObject.
   * 
   * @serialData Read serializable fields, if any exist.
   */
  private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
    /*
     * Call even if there is no default serializable fields.
     * Enables default serializable fields to be added in future versions
     * and skipped by this version which has no default serializable fields.
     */
    s.defaultReadObject();

    // restore the name
    name = (String) s.readObject();

    // other fields go here
  }

  /* Update Unit of Territory according to move order */
  public void updateUnit(String unit_type, int new_unit) {
    int index = hasUnitType(unit_type);
    if (index >= 0) {
      unit_list.set(index, new Unit(unit_type, new_unit));
    } else {
      unit_list.add(new Unit(unit_type, new_unit));
    }
  }

  // change the owner of the territory
  public void updateColor(String new_color) {
    this.color = new_color;
  }

  // check if the UnitType exists in the Territory
  public int hasUnitType(String unit_type) {
    for (int index = 0; index < unit_list.size(); index++) {
      if (unit_list.get(index).getUnitType().equals(unit_type)) {
        return index;
      }
    }
    return -1;
  }

  /*
   * @return the unit_num of specific UnitType
   */
  public int getUnit(String unit_type) {
    for (Unit unit : unit_list) {
      if (unit.getUnitType().equals(unit_type)) {
        return unit.getUnitNum();
      }
    }
    return 0;
  }

  /* get the cose of passing the Territory per unit. */
  public int getSize() {
    return this.size;
  }
}

package edu.duke.ece651.mp.common;

/*
 * construct a Territory class
 * @param: name
 */
public class Territory<T> implements ITerritory<T>{
  private final String name;
  public Territory(String name){
    this.name=name;
  }
  public String getName(){
    return name;
  }
}

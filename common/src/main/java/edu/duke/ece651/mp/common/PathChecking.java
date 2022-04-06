package edu.duke.ece651.mp.common;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
public class PathChecking<T> extends MoveChecking<T>{
  
  public PathChecking(MoveChecking<T> next){
    super(next);
  };

  public String checkMyRule(Map<T> map,String source,String destination, UnitType unit_type, int movingunits){
    Deque<Territory<T>> stack=new ArrayDeque<>();
    HashSet<Territory<T>> visited=new HashSet<>();
    Territory<T> start=map.getAllTerritories().get(source);
    stack.push(start);
    String colorID=start.getColor();
    while(!stack.isEmpty()){
      Territory<T> currTerritory=stack.pop();
      String currname=currTerritory.getName();
      if(currname.equals(destination)){
        return null;  
      }
      if(!visited.contains(currTerritory)){
        visited.add(currTerritory);
        for(String s:currTerritory.getAdjacency()){
          Territory<T> thisTerritory=map.getAllTerritories().get(s);
          if(thisTerritory.getColor().equals(colorID)) stack.push(thisTerritory);
        }
      }
    }
    return "no valid path exists";
  }

}

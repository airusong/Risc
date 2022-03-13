package edu.duke.ece651.mp.server;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;

import edu.duke.ece651.mp.common.Territory;
import edu.duke.ece651.mp.common.V1Map;
public class PathChecking<T> extends MoveChecking<T>{
  public PathChecking(MoveChecking<T> next){
    super(next);
  };
  public String checkMyRule(V1Map<T> map,String source,String destination,int movingunits){
    Deque<Territory<T>> stack=new ArrayDeque<>();
    HashSet<Territory<T>> visited=new HashSet<>();
    Territory<T> start=map.myTerritories.get(source);
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
          Territory<T> thisTerritory=map.myTerritories.get(s);
          if(thisTerritory.getColor().equals(colorID)) stack.push(thisTerritory);
        }
      }
    }
    return "no valid path exists";
  }
}

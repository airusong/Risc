package edu.duke.ece651.mp.server;
import edu.duke.ece651.mp.common.Map;
import edu.duke.ece651.mp.common.V1Map;
import edu.duke.ece651.mp.common.Territory;
import edu.duke.ece651.mp.common.ITerritory;

public class OwnerChecking<T> extends MoveChecking<T>{
  //  public OwnerChecking(){};
  @Override
  public String checkMyRule(V1Map<T> map,String source,String destination,int movingunit){
    Territory<T> s=map.myTerritories.get(source);
    Territory<T> d=map.myTerritories.get(destination);
    if(!s.getColor().equals(d.getColor())){
      return "not same onwer";
    }else if(s.getUnit()<movingunit){
      return "Insuffcient units";
    }else{
       return null;
    }
  }
  public OwnerChecking(MoveChecking<T> next){
    super(next);
  };
}

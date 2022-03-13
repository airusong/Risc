package edu.duke.ece651.mp.server;
import edu.duke.ece651.mp.common.Map;
import edu.duke.ece651.mp.common.V1Map;

public abstract class MoveChecking<T> {
  private final MoveChecking<T> next;
  public MoveChecking(MoveChecking<T> next){
    this.next=next;
  }
  protected abstract String checkMyRule(V1Map<T>map,String source,String destination,int movingunits);
  public String checkMoving(V1Map<T>map,String source,String destination,int movingunits){
    if (checkMyRule(map,source,destination,movingunits)!=null) {
      return checkMyRule(map,source,destination,movingunits);
    }
    if(next!=null){
      return next.checkMoving(map,source,destination,movingunits);
    }
    return null;
  }
}

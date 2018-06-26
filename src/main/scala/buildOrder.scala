import scala.collection.mutable.Queue
import BroodWarUnits.{Domus, Unitas}
import bwapi.{Game, Player, UnitType}

class buildOrder(game:Game){
  private var kiwi: Queue[Unitas]= Queue()
  kiwi.enqueue(new Domus(game,UnitType.Terran_Barracks))
  kiwi.enqueue(new Domus(game,UnitType.Terran_Supply_Depot))
  kiwi.enqueue(new Domus(game,UnitType.Terran_Supply_Depot))

  def canDo():Option[Unitas]={
    if(game.canMake(kiwi.front.me.getType)){
      return Some(kiwi.dequeue())
    }else{
      return None
    }
  }

}

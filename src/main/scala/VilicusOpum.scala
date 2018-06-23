import BroodWarUnits.BWAPIConnection
import bwapi._

import scala.collection.JavaConverters._
/** The resource controller class.
  *
  * It manages how many resources are there, how many buildings
  * exist and any new request for new buildings. Not sure if we need this but it looks cool.
  */
class VilicusOpum(gameCons: Game, selfCons: Player)  extends BWAPIConnection{
  //connect(gameCons,selfCons) //this will run on instantiation
  def getUnits() ={
    print("Resources\n")
    val allUnits = self.getUnits.asScala
    val gameUnits = game.neutral.getUnits.asScala
    for(i <- allUnits){
      if(i.getType==UnitType.Terran_Command_Center){
        print("moar workers\n")
        i.train(UnitType.Terran_SCV)
      }
    }

  }
}

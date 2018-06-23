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
  val allUnits = self.getUnits.asScala
  val gameUnits = game.neutral.getUnits.asScala

  def getUnits() ={ //Appele touts frames
    print("Resources\n")
    //build order things
    if(self.minerals() >= 100){
      buildBuilding(UnitType.Terran_Refinery)
    }

  }

  def trainUnit(unitType: UnitType) ={
    for(i <- allUnits){
      if(i.getType==UnitType.Terran_Command_Center && i.isTraining == false){
        print("moar workers\n")
        i.train(unitType)
      }
    }
  }

  def buildBuilding(unitType: UnitType)={
    for(i <- allUnits){
      if(i.getType==UnitType.Resource_Vespene_Geyser){
        print("refinary\n")
        for(u <- allUnits){
          print("all units")
          if(u.getType == UnitType.Terran_SCV && u.isConstructing){
            print("found worker")
            u.build(unitType,i.getTilePosition)
          }
        }
      }
    }
  }

}

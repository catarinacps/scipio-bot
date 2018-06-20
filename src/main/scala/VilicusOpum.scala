import BroodWarUnits.BWAPIConnection
import bwapi._
/** The resource controller class.
  *
  * It manages how many resources are there, how many buildings
  * exist and any new request for new buildings. Not sure if we need this but it looks cool.
  */
class VilicusOpum(gameCons: Game, selfCons: Player)  extends BWAPIConnection{
  connect(gameCons,selfCons) //this will run on instantiation
  def getUnits() ={
    print("flip")
  }
}

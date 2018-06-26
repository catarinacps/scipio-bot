import BroodWarUnits.Militum.Soldier
import BroodWarUnits.Operarius.Operario
import BroodWarUnits._
import bwapi.{Unit => ScUnit, _}
import bwta.BWTA

import scala.collection.JavaConverters._

class Scipio extends DefaultBWListener {
    val mirror = new Mirror()
    var game: Game = _
    var self: Player = _
    var military: DuxMilitum = _
    var workers: DuxOperariorum = _
    var resources: DuxOpum = _
    var startPos: TilePosition = _
    var whatToBuild: BuildOrder = _


    def run(): Unit = {
        mirror.getModule.setEventListener(this)
        mirror.startGame()
    }

    override def onUnitCreate(unit: ScUnit): Unit = {
        System.out.println("New unit " + unit.getType)
        if(whatToBuild.isLocked)
          whatToBuild.unlockBuildOrder()
    }

    override def onStart(): Unit = {
        game = mirror.getGame
        self = game.self()

        startPos = self.getStartLocation

        //Use BWTA to analyze map
        //This may take a few minutes if the map is processed first time!
        System.out.println("Analyzing map...")
        BWTA.readMap()
        BWTA.analyze()
        System.out.println("Map data ready")
        military = new DuxMilitum()
        workers = new DuxOperariorum()
        resources = new DuxOpum(startPos)
        whatToBuild = new BuildOrder(game)
        //if we are not doing any setup for the controllers we might as well rework the constructors
    }

    override def onFrame(): Unit = {
        //game.setTextSize(10);
        game.drawTextScreen(10, 10, "Playing as " + self.getName + " - " + self.getRace)
        val ownUnits = self.getUnits.asScala
        val neutralUnits = game.neutral.getUnits.asScala
        var next: Option[Unitas] = None

        print("On frame\n")

        this.updateFrame()

      try{
        next = whatToBuild.canDo(self)
      }catch {
        case aaa: Throwable =>
          print(aaa)
      }

        if (next.isDefined) {
            println("Im defined!")
            next.get match {
                case organic: Homo => organic match {
                    case operario: Operario => //its a worker
                        workers.trainUnit(operario)
                    case militum: Soldier => //its a militum
                        //to be implemented
                    case _ =>
                        //
                }
                case building =>
                    //Its a domus
                    print("Biudi biudinguis\n")
                  try{
                    resources.buildBuilding(building.ut, workers.getGatheringWorkers.remove(0))
                  }catch{
                    case e: Throwable =>
                      e.printStackTrace()
                  }
            }
        }
         print("\n" + self.minerals + "\n")
        military.update(ownUnits, neutralUnits)
      try{
        workers.update(ownUnits, neutralUnits)

      }catch{
        case e: Throwable =>

          e.printStackTrace()
      }
      resources.update(ownUnits, neutralUnits)
    }

    def updateFrame(): Unit = {
        military.updateFrame(game, self)
        workers.updateFrame(game, self)
        resources.updateFrame(game, self)
    }
}

object Scipio {
    def main(args: Array[String]): Unit = {
        new Scipio().run()
    }
}

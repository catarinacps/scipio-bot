import BroodWarUnits.Military.Soldier
import BroodWarUnits.Workers.Worker
import BroodWarUnits._
import Controllers.{MilitaryController, WorkerController, BuildingController, BuildOrder}
import bwapi.{Unit => ScUnit, _}
import bwta.BWTA

import scala.collection.JavaConverters._

class Scipio extends DefaultBWListener {
    val mirror = new Mirror()
    var game: Game = _
    var self: Player = _
    var military: MilitaryController = _
    var workers: WorkerController = _
    var resources: BuildingController = _
    var startPos: TilePosition = _
    var whatToBuild: BuildOrder = _


    def run(): Unit = {
        mirror.getModule.setEventListener(this)
        mirror.startGame()
    }

    override def onUnitCreate(unit: ScUnit): Unit = {
        System.out.println("New unit " + unit.getType)
        if (whatToBuild.isLocked)
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
        military = new MilitaryController()
        workers = new WorkerController()
        resources = new BuildingController(startPos)
        whatToBuild = new BuildOrder(game)
        //if we are not doing any setup for the controllers we might as well rework the constructors
    }

    override def onFrame(): Unit = {
        //game.setTextSize(10);
        game.drawTextScreen(10, 10, "Playing as " + self.getName + " - " + self.getRace)

        val ownUnits = self.getUnits.asScala
        val neutralUnits = game.neutral.getUnits.asScala
        var next: Option[Unit] = None

        print("On frame\n")

        this.updateFrame()

        try {
            next = whatToBuild.canDo(self)
        } catch {
            case e: Throwable =>
                e.printStackTrace()
        }

        if (next.isDefined) {
            next.get match {
                case organic: Person => organic match {
                    case operario: Worker =>
                        workers.trainUnit(operario)
                    case militum: Soldier =>
                    // no military unit creation
                    case _ =>
                    //
                }
                case building =>
                    try {
                        resources.buildBuilding(building.ut, workers.getGatheringWorkers.remove(0))
                    } catch {
                        case e: Throwable =>
                            e.printStackTrace()
                    }
            }
        }

        military.update(ownUnits, neutralUnits)
        try {
            workers.update(ownUnits, neutralUnits)

        } catch {
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

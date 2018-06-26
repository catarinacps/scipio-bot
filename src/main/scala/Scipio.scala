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

        next = whatToBuild.canDo

        if (next.isDefined) {
            if (next.get.isInstanceOf[Homo]) {
                if (next.get.isInstanceOf[Operario]) { //its a worker
                    workers.trainUnit(next.get.asInstanceOf[Operario].me.getType)
                } else { //its a militum
                    //to be implemented
                }
            } else {
                //Its a domus
                resources.buildBuilding(next.get.me.getType, workers.getGatheringWorkers.remove(0))
            }
        }

        military.update(ownUnits, neutralUnits)
        workers.update(ownUnits, neutralUnits) //we have to reconnect because there's no such thing as "pass by reference" here ;(
        //resources.update(ownUnits, neutralUnits, workers.getGatheringWorkers) //each module must require the game and player handles to update its data
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

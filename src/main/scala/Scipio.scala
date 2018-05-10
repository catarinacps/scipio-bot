import Jobs._
import bwapi.{Unit => ScUnit, _}
import bwta.BWTA

import scala.collection.JavaConverters._
import scala.collection.mutable._

class Scipio extends DefaultBWListener {
    val mirror = new Mirror()
    var game: Game = _
    var self: Player = _
    var enemy: Player = _

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
        enemy = game.enemy()

        //Use BWTA to analyze map
        //This may take a few minutes if the map is processed first time!
        System.out.println("Analyzing map...")
        BWTA.readMap()
        BWTA.analyze()
        System.out.println("Map data ready")

        Scipio.initializeGameVariables(self)
        BuildOrder.orderList = BuildOrder.initializeOrders
    }

    override def onFrame(): Unit = {
        Workers.idleWorkers = Scipio.getIdleWorkers(self)

        Production.minerals = Scipio.getCurrentMinerals(self)
        Production.gas = Scipio.getCurrentGas(self)

        Scipio.mineralList = Scipio.getMineralsInSight(game)
        Scipio.geyserList = Scipio.getGeysersInSight(game)
        Scipio.commandCenterList = Scipio.getCommandCenters(self)

        /*Scipio.commandCenterList = self.getUnits.asScala
            .filter(u => u.getType == UnitType.Terran_Command_Center && !u.isTraining)*/


        if (!Scipio.vespeneRefinery) {
            Scipio.vespeneRefinery = game.neutral.getUnits.asScala
                .exists(_.getType.isRefinery)
        }

        if (Workers.idleWorkers.nonEmpty) {
            val separatedIdle = Workers.separateIdleWorkers(Workers.idleWorkers, 3 - Workers.gasWorkers.size, (mineral, gas))
            val newMineralWorkers = separatedIdle.filter(_._2 == mineral).map(_._1)
            val newGasWorkers = separatedIdle.filter(_._2 == gas).map(_._1)

            val workerController = Workers.controller(Scipio.mineralList, Scipio.geyserList)
            val gatherMinerals = workerController(Jobs.mineral)
            val gatherGas = workerController(Jobs.gas)

            gatherMinerals(newMineralWorkers)
            gatherGas(newGasWorkers)

            Workers.miningWorkers = Workers.miningWorkers ::: newMineralWorkers
            Workers.gasWorkers = Workers.gasWorkers ::: newGasWorkers
        }

        Production.issueBuildingOrders(game, Some(BuildOrder.orderList))
    }
}

object Scipio {
    var startLocation: Position = _

    var geyserList: Buffer[ScUnit] = _
    var mineralList: Buffer[ScUnit] = _

    var commandCenterList: Buffer[ScUnit] = _

    var vespeneRefinery: Boolean = _

    // The following functions are not referentially transparent at all
    def getIdleWorkers(self: Player): List[ScUnit] = self.getUnits.asScala
        .filter(u => u.getType.isWorker && u.isIdle)
        .toList

    def getMineralsInSight(game: Game): Buffer[ScUnit] = game.neutral.getUnits.asScala
        .filter(_.getType.isMineralField)

    def getGeysersInSight(game: Game): Buffer[ScUnit] = game.neutral.getUnits.asScala
        .filter(_.getType == bwapi.UnitType.Resource_Vespene_Geyser)

    def getCommandCenters(self: Player): Buffer[ScUnit] = self.getUnits.asScala
        .filter(_.getType == UnitType.Terran_Command_Center)

    def getCurrentMinerals(self: Player): Int = self.minerals

    def getCurrentGas(self: Player): Int = self.gas

    def initializeGameVariables(self: Player): Unit = {
        Scipio.startLocation = self.getStartLocation.toPosition
        Production.minerals = 100
    }

    // end of not referentially transparent functions

    def main(args: Array[String]): Unit =
        new Scipio().run()
}

import bwapi.{Unit => ScUnit, _}

import scala.collection.JavaConverters._
import scala.collection.mutable.Buffer

object Production {
    var minerals: Int = _
    var gas: Int = _

    private var work: Int = 0
    private var safety: Int = 0

    /*def update(game: Game, player: Player, maybeOrders: Option[Buffer[UnitType]]) {
        //val numberOfWorkers: Int = player.getUnits.asScala.filter(_.getType.isWorker).size
        def currentlyBuilding(): Int = player.getUnits.asScala.count(_.getRemainingBuildTime > 0)
        //game.drawTextScreen(10, 10, "Number of workers: " + numberOfWorkers)
        if (currentlyBuilding < work) { //finished building something so it's 100% safe to check resources (probably)
            minerals = player.minerals()
            println("Wow it actually got here...")
            safety = 0
        } else {
            safety += 1
            if (safety > 100) { //maybe its safe to check resources cuz it's been a while
                safety = -100
                minerals = player.minerals()
            }
        }
        work = currentlyBuilding()
        //if safety < 0 there may be resources discrepancies
        maybeOrders match {
            case Some(orders) => issueBuildingOrders(game, player, orders)
            case None => //if there are no orders it just updates resources
        }
    }*/

    def issueBuildingOrders(game: Game, orders: Option[List[UnitType]]): List[UnitType] = orders match {
        case None => Nil
        case Some(orderList) =>
            val plausibleBuildOrder = getPossibleBuildingOrders(orderList)
            val ccOrders = getCommandCenterOrders(plausibleBuildOrder)
            val buildingOrders = getBuildingOrders(plausibleBuildOrder)
            val workersToBuild = Workers.miningWorkers.take(buildingOrders.size)

            WorkerManager.trainUnits(Scipio.commandCenterList.head, ccOrders)
            Workers.build(BuildingManager.getBuildPosition(game, buildingOrders), workersToBuild)

            return removeFromOrderedList[UnitType](orderList, plausibleBuildOrder)
    }

    private def getCommandCenterOrders(orders: List[UnitType]): List[UnitType] = orders match {
        case Nil => Nil
        case head :: tail => head match {
            case UnitType.Terran_SCV => head :: getCommandCenterOrders(tail)
            case _ => getCommandCenterOrders(tail)
        }
    }

    private def getBuildingOrders(orders: List[UnitType]): List[UnitType] = orders match {
        case Nil => Nil
        case head :: tail => head match {
            case UnitType.Buildings => head :: getBuildingOrders(tail)
            case _ => getBuildingOrders(tail)
        }
    }

    private def removeFromOrderedList[A](orders: List[A], removeList: List[A]): List[A] = orders match {
        case Nil => Nil
        case head :: tail => head match {
            case removeList.head => removeFromOrderedList(tail, removeList.tail)
            case _ => head :: removeFromOrderedList(tail, removeList)
        }
    }

    private def getPossibleBuildingOrders(orders: List[UnitType]): List[UnitType] = orders match {
        case Nil => Nil
        case head :: tail => if (canMakeUnit(head)) {
            minerals -= head.mineralPrice
            gas -= head.gasPrice
            head :: getPossibleBuildingOrders(tail)
        } else getPossibleBuildingOrders(tail)
    }

    private def canMakeUnit(unit: UnitType): Boolean = {
        return (minerals - unit.mineralPrice > 0) && (gas - unit.gasPrice > 0)
    }

    /*def issueBuildingOrders(game: Game, player: Player, orders: Buffer[UnitType]): Unit = {
        val workerManager = new WorkerManager(game, player)
        val buildingManager = new BuildingManager(game, player)
        var nextBuild: UnitType = UnitType.None

        def somethingIsWrong(): Unit = {
            issueBuildingOrders(game, player, orders)
            orders.prepend(nextBuild)
        }

        if (orders.nonEmpty) {
            nextBuild = orders.head
            orders trimStart 1 //pop head
            game.drawTextScreen(10, 230, "Next build: " + nextBuild.toString)
            if (game.canMake(nextBuild)) { //sometimes this works
                nextBuild match {
                    case UnitType.Terran_SCV => if (minerals >= 50) {
                        workerManager.addWorker()
                        minerals -= 50
                        work += 1
                    } else somethingIsWrong()
                    case UnitType.Terran_Refinery => if (minerals >= 100) {
                        println("Rubberband man incoming!")
                        BuildingManager.add(nextBuild)
                        minerals -= 100
                        work += 1
                    } else somethingIsWrong()
                    //{println("Hey all, prepare yourselves for the rubberband man");buildingManager.add(nextBuild); ordered = true}
                    //if(minerals < 100 && ordered == true){ done=true; ordered = false}
                    case UnitType.Terran_Supply_Depot => if (minerals >= 100) {
                        println("MOAR SPACE")
                        BuildingManager.add(nextBuild)
                        minerals -= 100
                        work += 1
                    } else somethingIsWrong()
                    case _ => println("yay")
                }
                if (safety >= 0) //if it isn't safe it will only try one order
                    issueBuildingOrders(game, player, orders)
            }
            else
                somethingIsWrong()
        }
    }*/
}

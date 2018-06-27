package Controllers

import bwapi.{Unit => ScUnit}

import scala.collection.mutable.Buffer

/** The soldier controller class.
  *
  * It manages how many soldier are active, how many soldiers
  * are being trained and any new request for new soldiers. And patrols.
  */

class MilitaryController extends BWAPIConnection {

    //connect(gameCons,selfCons) //this will run on instantiation
    /*
    def startPatrolAt(building: Domus):Unit = { //assigns a unit to patrol this building
        //TODO: this
    }*/

    def update(ownUnits: Buffer[ScUnit], neutralUnits: Buffer[ScUnit]): Unit = {
        //print("Military\n")
    }
}

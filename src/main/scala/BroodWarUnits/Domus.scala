package BroodWarUnits

import bwapi.{Game, TilePosition, UnitType, Unit => ScUnit}

abstract class Domus(worker: ScUnit, startPos: TilePosition, unitType: UnitType, gameCons:Game) extends Unitas(gameCons){
  var pos: TilePosition = game.getBuildLocation(unitType, startPos, 100)
  var myself: ScUnit = _
  print("getId ")
  print(worker.getID)
  print("\n")
  worker.build(UnitType.Terran_Barracks, pos)

}

package vehicle.model

import vehicle.CarImpl
import vehicle.MakeOfCar
import vehicle.interfaces.IRacer
import vehicle.interfaces.IRacingCar

class RacingCar(
        capacity: Int,
        override val makeOfCar: MakeOfCar
) : CarImpl<IRacer>(capacity), IRacingCar
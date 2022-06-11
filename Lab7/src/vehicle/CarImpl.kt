package vehicle

import vehicle.interfaces.ICar
import vehicle.interfaces.IPerson

abstract class CarImpl<Passenger : IPerson>(capacity: Int) :
        ICar<Passenger>, VehicleImpl<Passenger>(capacity)
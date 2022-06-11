package vehicle.model

import vehicle.VehicleImpl
import vehicle.interfaces.IBus
import vehicle.interfaces.IPerson

class Bus(capacity: Int) : VehicleImpl<IPerson>(capacity), IBus
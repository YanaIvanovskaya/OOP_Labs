package vehicle

import vehicle.interfaces.IPerson
import vehicle.interfaces.IVehicle
import vehicle.interfaces.VehicleException

abstract class VehicleImpl<Passenger : IPerson>(
        private val capacity: Int
) : IVehicle<Passenger> {

    final override val placeCount: Int
        get() = capacity - passengerCount

    final override val passengerCount: Int
        get() = mPassengers.size

    private val mPassengers = mutableListOf<Passenger>()

    @Throws(VehicleException::class)
    override fun removePassenger(index: Int) {
        if (mPassengers.size > index) {
            mPassengers.removeAt(index)
        } else throw VehicleException.NoSuchPassenger()
    }

    @Throws(VehicleException::class)
    override fun getPassenger(index: Int): Passenger {
        if (mPassengers.size > index) {
            return mPassengers[index]
        } else throw VehicleException.NoSuchPassenger()
    }

    @Throws(VehicleException::class)
    override fun addPassenger(passenger: Passenger) {
        if (!isFull()) {
            mPassengers.add(passenger)
        } else throw VehicleException.NoPlaceException()
    }

    override fun isEmpty(): Boolean {
        return mPassengers.isEmpty()
    }

    override fun isFull(): Boolean {
        return mPassengers.size == capacity
    }

    override fun removeAllPassengers() {
        mPassengers.clear()
    }

}
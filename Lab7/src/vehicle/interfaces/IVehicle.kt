package vehicle.interfaces

interface IVehicle<Passenger> : IBasicVehicle {
    fun removePassenger(index: Int)
    fun addPassenger(passenger: Passenger)
    fun getPassenger(index: Int): Passenger
}

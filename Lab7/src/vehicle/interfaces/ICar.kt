package vehicle.interfaces

import vehicle.MakeOfCar

interface ICar<Passenger : IPerson> : IVehicle<Passenger> {
    val makeOfCar: MakeOfCar
}

interface IRacingCar : ICar<IRacer>

interface IPoliceCar : ICar<IPoliceMan>

interface ITaxi : ICar<IPerson>
sealed class VehicleException : Throwable() {
    class NoPlaceException : VehicleException()
    class NoSuchPassenger : VehicleException()
}
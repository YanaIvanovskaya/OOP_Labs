package vehicle.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import vehicle.MakeOfCar
import vehicle.interfaces.VehicleException
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class CarTest {

    private val testTaxi = Taxi(5, MakeOfCar.KIA)

    @BeforeEach
    fun setUp() {
        testTaxi.removeAllPassengers()
    }

    @Test
    fun `must throw exception if add passenger in full vehicle`() {
        testTaxi.addPassenger(Person("Jack1"))
        testTaxi.addPassenger(Person("Jack2"))
        testTaxi.addPassenger(Person("Jack3"))
        testTaxi.addPassenger(Person("Jack4"))
        testTaxi.addPassenger(Person("Jack5"))

        assertThrows<VehicleException.NoPlaceException> {
            testTaxi.addPassenger(Person("Jack6"))
        }
    }

    @Test
    fun `when add passenger then count of place must decrease and count of passengers must increase`() {
        testTaxi.addPassenger(Person("Jack1"))

        assertTrue { testTaxi.passengerCount == 1 }
        assertTrue { testTaxi.placeCount == 4 }
    }

    @Test
    fun `when add first passenger then vehicle is already not empty`() {
        assertTrue { testTaxi.isEmpty() }

        testTaxi.addPassenger(Person("Jack1"))

        assertFalse { testTaxi.isEmpty() }
    }

    @Test
    fun `when add last passenger then vehicle became full`() {
        assertFalse { testTaxi.isFull() }
        testTaxi.addPassenger(Person("Jack1"))
        testTaxi.addPassenger(Person("Jack2"))
        testTaxi.addPassenger(Person("Jack3"))
        testTaxi.addPassenger(Person("Jack4"))
        testTaxi.addPassenger(Person("Jack5"))

        assertTrue { testTaxi.isFull() }
        assertFalse { testTaxi.isEmpty() }
    }

    @Test
    fun `when remove passenger then count of place must increase and count of passengers must decrease`() {
        testTaxi.addPassenger(Person("Jack1"))
        testTaxi.addPassenger(Person("Jack2"))
        testTaxi.removePassenger(0)

        assertTrue { testTaxi.passengerCount == 1 }
        assertTrue { testTaxi.placeCount == 4 }
    }


    @Test
    fun `must throw exception when passenger at selected place is not exist`() {
        testTaxi.addPassenger(Person("Jack1"))

        assertThrows<VehicleException.NoSuchPassenger> {
            testTaxi.getPassenger(2)
        }
        Assertions.assertDoesNotThrow {
            testTaxi.getPassenger(0)
        }
        assertEquals("Jack1", testTaxi.getPassenger(0).name)
    }

    @Test
    fun `must throw exception when remove passenger that not exist`() {
        testTaxi.addPassenger(Person("Jack1"))

        assertThrows<VehicleException.NoSuchPassenger> {
            testTaxi.removePassenger(2)
        }
        Assertions.assertDoesNotThrow {
            testTaxi.removePassenger(0)
        }
    }

    @Test
    fun `when remove all passengers then vehicle became empty`() {
        assertTrue { testTaxi.isEmpty() }

        testTaxi.addPassenger(Person("Jack1"))
        testTaxi.addPassenger(Person("Jack2"))
        testTaxi.removeAllPassengers()

        assertTrue { testTaxi.isEmpty() }
        assertTrue { testTaxi.passengerCount == 0 }
        assertTrue { testTaxi.placeCount == 5 }
    }

    @Test
    fun `test situation`() {
        //Полицейский Джон Смит из Северо-западного полицейского участка
        // садится в свой служебный пятиместный полицейский автомобиль марки Ford.
        val policeCar = PoliceCar(capacity = 5, makeOfCar = MakeOfCar.FORD)
        val policemanJohn = PoliceMan("John Smith", "NorthWest police station")
        policeCar.addPassenger(policemanJohn)
        assertTrue { policeCar.getPassenger(0) == policemanJohn }

        // К нему в автомобиль садится его знакомый коп Джим Кларк из Юго-восточного полицейского участка.
        val policemanJim = PoliceMan("Jim Clark", "SouthEast police station")
        policeCar.addPassenger(policemanJim)
        assertTrue { policeCar.getPassenger(1) == policemanJim }
        assertTrue { policeCar.passengerCount == 2 }

        // Программа должна вывести имена полицейских, находящихся в упомянутой полицейской машине,
        // а также имена их полицейских участков.
        for (i in 0 until policeCar.passengerCount) {
            policeCar.getPassenger(i).also {
                println("Policeman ${it.name} from ${it.departmentName}")
            }
        }

        //Упомянутые полицейские ссорятся.
        // В сердцах полицейский Джим Кларк выходит из упомянутой машины своего теперь уже бывшего друга
        policeCar.removePassenger(1)
        assertTrue { policeCar.passengerCount == 1 }
        assertTrue { policeCar.getPassenger(0) == policemanJohn }

        // и останавливает двухместное такси марки Тойота, которым управляет выходец из Индии Раджа Ганди,
        // везущий гонщика Михаэля Шумахера на стадион, на котором через 15 минут должен начаться чемпионат мира.
        val taxi = Taxi(capacity = 2, makeOfCar = MakeOfCar.TOYOTA)
        val taxiDriver = Person("Raja Gandhi")
        val racer = Racer("Michael Schumacher", 12)
        taxi.addPassenger(taxiDriver)
        taxi.addPassenger(racer)
        assertTrue { taxi.isFull() }

        // «Убедив» таксиста при помощи своего табельного оружия покинуть машину, полицейский Джим садится
        // в такси на место водителя и соглашается подвезти своего любимого гонщика до стадиона.
        taxi.removePassenger(0)
        taxi.addPassenger(policemanJim)
        assertTrue { taxi.isFull() }

        //Таксист пытается вернуться в свой автомобиль, но при попытке сесть в машину,
        // ловит исключение std::logic_error, которое как бы говорит ему о нелогичности такого поступка,
        // т.к. в машине нет свободных мест.
        assertThrows<VehicleException.NoPlaceException> {
            taxi.addPassenger(taxiDriver)
        }
    }

}
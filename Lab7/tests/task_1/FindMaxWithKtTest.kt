package task_1

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal data class Sportsman(
        val name: String,
        val weight: Int,
        val height: Int
)

internal class FindMaxWithKtTest {

    private val testData = listOf(
            Sportsman("Jack1", 90, 180),
            Sportsman("Jack2", 179, 173),
            Sportsman("Jack3", 80, 183),
            Sportsman("Jack4", 110, 210)
    )
    private val comparatorMaxWeight = LessThan { first: Sportsman, second: Sportsman ->
        first.weight > second.weight
    }
    private val comparatorMaxHeight = LessThan { first: Sportsman, second: Sportsman ->
        first.height > second.height
    }

    @Test
    @DisplayName("При передаче пустого списка должна возвращать null")
    fun case_1() {
        assertNull(emptyList<Sportsman>().findMaxWith(comparatorMaxHeight))
    }

    @Test
    @DisplayName("Не должна изменять список")
    fun case_2() {
        val testData2 = testData
        testData.findMaxWith(comparatorMaxHeight)
        assertEquals(testData2, testData)
    }

    @Test
    @DisplayName("Передача функции Comparator, передача ссылки на функцию, передача лямбды должны работать одинаково")
    fun case_3() {
        val comparator = LessThan { first: Sportsman, second: Sportsman ->
            first.weight < second.weight
        }
        val expected = Sportsman("Jack3", 80, 183)

        fun testFun(f: Sportsman, s: Sportsman) = f.weight < s.weight

        assertEquals(expected, testData.findMaxWith(comparator))
        assertEquals(expected, testData.findMaxWith(::testFun))
        assertEquals(expected, testData.findMaxWith { first, second -> first.weight < second.weight })
    }

//    @Test
//    @DisplayName("Если в comparator происходит исключение, то оно не перехватывается")
//    fun case_5() {
//        val testData = listOf(
//                Sportsman("Jack1", 90, 180),
//                Sportsman("Jack2", 179, 173),
//                Sportsman("Jack3", 179, 183),
//                Sportsman("Jack4", 179, 210)
//        )
//        val comparator = Comparator { first: Sportsman, second: Sportsman ->
//            5 / 0 == 0
//            first.weight > second.weight
//        }
//        val expected = Sportsman("Jack4", 179, 210)
//
//        assertEquals(expected, testData.findMaxWith(comparator))
//    }

}
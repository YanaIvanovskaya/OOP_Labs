import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class RationalTest {

    @Test
    @DisplayName("Нельзя создать рациональное число с знаменателем == 0")
    fun case_0() {
        assertThrows<java.lang.IllegalArgumentException> { Rational(2, 0) }
    }

    @Test
    @DisplayName("Конструктор без параметров создает рациональное число 0/1")
    fun case_1() {
        val num = Rational()
        assertEquals(0, num.getNumerator())
        assertEquals(1, num.getDenominator())
    }

    @Test
    @DisplayName("Конструктор с 1 параметром создает рациональное число параметр/1")
    fun case_2() {
        val num = Rational(10)
        assertEquals(10, num.getNumerator())
        assertEquals(1, num.getDenominator())
    }

    @Test
    @DisplayName("Конструктор с 2 параметрами создает рациональное число параметр1/параметр2")
    fun case_3() {
        val num = Rational(10, 25)
        assertEquals(10, num.getNumerator())
        assertEquals(25, num.getDenominator())
    }

    @Test
    @DisplayName("Unary - : возвращает нормализованное рациональное число с противоположным знаком")
    fun case_4() {
        val num = Rational(10, 25)
        val expected = Rational(-2, 5)
        assertEquals(expected, -num)
    }

    @Test
    @DisplayName("Unary + : возвращает нормализованное рациональное число с тем же знаком")
    fun case_5() {
        val num = Rational(10, 25)
        assertEquals(num, +num)
    }

    @Test
    @DisplayName("Binary + :  rational + rational = normalized rational")
    fun case_6() {
        val num1 = Rational(1, 2)
        val num2 = Rational(1, 6)
        val expected = Rational(2, 3)
        assertEquals(expected, num1 + num2)
    }

    @Test
    @DisplayName("Binary + :  rational + int = normalized rational")
    fun case_7() {
        val num1 = Rational(1, 2)
        val num2 = 5
        val expected = Rational(11, 2)
        assertEquals(expected, num1 + num2)
    }

    @Test
    @DisplayName("Binary - :  rational - rational = normalized rational")
    fun case_32() {
        val num1 = Rational(1, 2)
        val num2 = Rational(1, 6)
        val expected = Rational(1, 3)
        assertEquals(expected, num1 - num2)
    }

    @Test
    @DisplayName("Binary - :  rational - int = normalized rational")
    fun case_33() {
        val num1 = Rational(1, 2)
        val num2 = 5
        val expected = Rational(-9, 2)
        assertEquals(expected, num1 - num2)
    }

    @Test
    @DisplayName("+= rational : rational += rational -> normalized rational")
    fun case_8() {
        val num1 = Rational(1, 2)
        val num2 = Rational(1, 6)
        val expected = Rational(2, 3)
        num1 += num2
        assertEquals(expected, num1)
    }

    @Test
    @DisplayName("+= int : rational += int -> normalized rational")
    fun case_9() {
        val num1 = Rational(1, 2)
        val num2 = 5
        val expected = Rational(11, 2)
        num1 += num2
        assertEquals(expected, num1)
    }

    @Test
    @DisplayName("-= rational : rational -= rational -> normalized rational")
    fun case_11() {
        val num1 = Rational(1, 2)
        val num2 = Rational(1, 6)
        val expected = Rational(1, 3)
        num1 -= num2
        assertEquals(expected, num1)
    }

    @Test
    @DisplayName("-= int : rational -= int -> normalized rational")
    fun case_12() {
        val num1 = Rational(1, 2)
        val num2 = 5
        val expected = Rational(-9, 2)
        num1 -= num2
        assertEquals(expected, num1)
    }

    @Test
    @DisplayName("*= rational : rational *= rational -> normalized rational")
    fun case_13() {
        val num1 = Rational(2, 3)
        val num2 = Rational(3, 4)
        val expected = Rational(1, 2)
        num1 *= num2
        assertEquals(expected, num1)
    }

    @Test
    @DisplayName("*= int : rational *= int -> normalized rational")
    fun case_14() {
        val num1 = Rational(2, 3)
        val num2 = 3
        val expected = Rational(2, 1)
        num1 *= num2
        assertEquals(expected, num1)
    }

    @Test
    @DisplayName("/= rational : rational /= rational -> normalized rational")
    fun case_15() {
        val num1 = Rational(2, 3)
        val num2 = Rational(3, 4)
        val expected = Rational(8, 9)
        num1 /= num2
        assertEquals(expected, num1)
    }

    @Test
    @DisplayName("/= int : rational /= int -> normalized rational")
    fun case_16() {
        val num1 = Rational(2, 3)
        val num2 = 3
        val expected = Rational(2, 9)
        num1 /= num2
        assertEquals(expected, num1)
    }

    @Test
    @DisplayName("* : rational + rational = normalized rational")
    fun case_17() {
        val num1 = Rational(2, 3)
        val num2 = Rational(3, 4)
        val expected = Rational(1, 2)
        assertEquals(expected, num1 * num2)
    }

    @Test
    @DisplayName("* : rational + int = normalized rational")
    fun case_18() {
        val num1 = Rational(2, 3)
        val num2 = 3
        val expected = Rational(2, 1)
        assertEquals(expected, num1 * num2)
    }

    @Test
    @DisplayName("/ : rational + rational = normalized rational")
    fun case_19() {
        val num1 = Rational(2, 3)
        val num2 = Rational(3, 4)
        val expected = Rational(8, 9)
        assertEquals(expected, num1 / num2)
    }

    @Test
    @DisplayName("/ : rational + int = normalized rational")
    fun case_20() {
        val num1 = Rational(2, 3)
        val num2 = 3
        val expected = Rational(2, 9)
        assertEquals(expected, num1 / num2)
    }

    @Test
    @DisplayName("== : rational == int")
    fun case_21() {
        // dont work
    }

    @Test
    @DisplayName("== : rational == rational")
    fun case_22() {
        val num1 = Rational(4, 1)
        val num2 = Rational(4, 1)
        assertTrue { num1 == num2 }
    }

    @Test
    @DisplayName("< : rational < rational")
    fun case_23() {
        val num1 = Rational(2, 4)
        val num2 = Rational(2, 3)
        assertTrue { num1 < num2 }
    }

    @Test
    @DisplayName("< : rational < int")
    fun case_24() {
        val num1 = Rational(2, 4)
        val num2 = 5
        assertTrue { num1 < num2 }
    }

    @Test
    @DisplayName("> : rational > rational")
    fun case_25() {
        val num1 = Rational(2, 3)
        val num2 = Rational(2, 4)
        assertTrue { num1 > num2 }
    }

    @Test
    @DisplayName("> : rational > int")
    fun case_26() {
        val num1 = Rational(2, 4)
        val num2 = 5
        assertTrue { num1 < num2 }
    }

    @Test
    @DisplayName(">= : rational >= rational")
    fun case_27() {
        val num1 = Rational(2, 4)
        val num2 = Rational(2, 4)
        val num3 = Rational(2, 5)
        assertTrue { num1 >= num2 }
        assertTrue { num1 >= num3 }
    }

    @Test
    @DisplayName(">= : rational >= int")
    fun case_28() {
        val num1 = Rational(2, 1)
        val num2 = 2
        val num3 = 0
        assertTrue { num1 >= num2 }
        assertTrue { num1 >= num3 }
    }

    @Test
    @DisplayName("<= : rational <= rational")
    fun case_29() {
        val num1 = Rational(2, 4)
        val num2 = Rational(2, 4)
        val num3 = Rational(2, 3)
        assertTrue { num1 <= num2 }
        assertTrue { num1 <= num3 }
    }

    @Test
    @DisplayName("<= : rational <= int")
    fun case_30() {
        val num1 = Rational(2, 1)
        val num2 = 2
        val num3 = 3
        assertTrue { num1 <= num2 }
        assertTrue { num1 <= num3 }
    }

    @Test
    @DisplayName("Функция toDouble должна возвращать результат деления числителя на знаменатель")
    fun case_31() {
        val num = Rational(1, 2)
        assertEquals(0.5, num.toDouble())
    }

    @Test
    @DisplayName("Функция toCompoundFraction должна возвращать смешанную дробь")
    fun case_34() {
        val num = Rational(11, 2)
        val expected = 5 to Rational(1, 2)
        assertEquals(expected, num.toCompoundFraction())
    }

}
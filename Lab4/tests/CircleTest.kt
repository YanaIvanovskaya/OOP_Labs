import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import shape.Circle
import java.awt.Color
import kotlin.math.PI

internal class CircleTest {

    private val testShape = Circle(
        radius = 10.0,
        center = Point(12.0, 60.0),
        outlineColor = Color.RED,
        fillColor = Color.GRAY
    )

    @Test
    @DisplayName("Вычисление периметра (длины окружности)")
    fun case_1() {
        val expected = 2 * 10.0 * PI
        Assertions.assertEquals(expected, testShape.getPerimeter())
    }

    @Test
    @DisplayName("Вычисление площади")
    fun case_2() {
        val expected = PI * 10.0 * 10.0
        Assertions.assertEquals(expected, testShape.getArea())
    }

    @Test
    @DisplayName("Рисование")
    fun case_3() {

    }

}
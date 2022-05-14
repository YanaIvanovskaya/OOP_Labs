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
    private val mockCanvas = MockCanvas()

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
        testShape.draw(mockCanvas)
        val expected = listOf(
            "fillCircle center=${Point(12.0, 60.0)} radius=${10.0} fillColor=${Color.GRAY}",
            "drawCircle center=${Point(12.0, 60.0)} radius=${10.0} outlineColor=${Color.RED}"
        )
        Assertions.assertEquals(expected, mockCanvas.getDrawingResult())
    }

}
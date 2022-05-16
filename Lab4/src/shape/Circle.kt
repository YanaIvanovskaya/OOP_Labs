package shape

import ICanvas
import ISolidShape
import Point
import java.awt.Color
import kotlin.math.PI
import kotlin.math.pow

class Circle(
        private val outlineColor: Color,
        private val fillColor: Color,
        private val center: Point,
        private val radius: Double
) : ISolidShape() {

    override fun getFillColor() = fillColor
    override fun getOutlineColor() = outlineColor

    override fun getArea(): Double {
        return PI * radius.pow(2)
    }

    override fun getPerimeter(): Double {
        return 2 * radius * PI
    }

    override fun draw(canvas: ICanvas) {
        canvas.fillCircle(center, radius, fillColor)
        canvas.drawCircle(center, radius, outlineColor)
    }

    override fun stringify(): String {
        return "Circle (center=(${center.x},${center.y}), radius=$radius)"
    }

}

package shape

import ICanvas
import ICanvasDrawable
import ISolidShape
import Point
import java.awt.Color
import kotlin.math.PI

data class Circle(
    private val outlineColor: Color,
    private val fillColor: Color,
    private val center: Point,
    private val radius: Double
) : ISolidShape, ICanvasDrawable {

    override fun getFillColor() = fillColor
    override fun getOutlineColor() = outlineColor

    override fun getArea(): Double {
        return PI * radius * radius
    }

    override fun getPerimeter(): Double {
        return 2 * radius * PI
    }

    override fun draw(canvas: ICanvas) {
       // canvas.drawCircle(center, radius, outlineColor)
        canvas.fillCircle(center, radius, outlineColor)
    }

    override fun toString(): String {
        return "shape.Circle (center=(${center.x},${center.y}, radius=$radius)"
    }

}

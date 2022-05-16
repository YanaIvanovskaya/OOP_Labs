package shape

import ICanvas
import ISolidShape
import Point
import java.awt.Color
import java.util.*
import kotlin.math.abs
import kotlin.math.sqrt

class Triangle(
        private val outlineColor: Color,
        private val fillColor: Color,
        private val vertex1: Point,
        private val vertex2: Point,
        private val vertex3: Point
) : ISolidShape() {

    private val mPerimeter = DEFAULT
    private val mArea = DEFAULT
    private val mPoints = Vector<Point>().apply {
        addAll(listOf(vertex1, vertex2, vertex3))
    }

    override fun getFillColor() = fillColor
    override fun getOutlineColor() = outlineColor

    override fun getArea(): Double {
        return if (mArea == DEFAULT) {
            0.5 * abs(
                    (vertex2.x - vertex1.x) * (vertex3.y - vertex1.y) -
                            (vertex3.x - vertex1.x) * (vertex2.y - vertex1.y)
            )
        } else mArea
    }

    override fun getPerimeter(): Double {
        return if (mPerimeter == DEFAULT) {
            val vector12 = Point(vertex2.x - vertex1.x, vertex2.y - vertex1.y)
            val vector23 = Point(vertex3.x - vertex2.x, vertex3.y - vertex2.y)
            val vector31 = Point(vertex1.x - vertex3.x, vertex1.y - vertex3.y)

            val len12 = sqrt(vector12.x * vector12.x + vector12.y * vector12.y)
            val len23 = sqrt(vector23.x * vector23.x + vector23.y * vector23.y)
            val len31 = sqrt(vector31.x * vector31.x + vector31.y * vector31.y)
            len12 + len23 + len31
        } else mPerimeter
    }

    override fun draw(canvas: ICanvas) {
        canvas.fillPolygon(mPoints, fillColor)
        canvas.drawPolygon(mPoints, outlineColor)
    }

    override fun stringify(): String {
        return "Triangle (vertex1=(${vertex1.x},${vertex1.y})," +
                " vertex2=(${vertex2.x},${vertex2.y})," +
                " vertex3=(${vertex3.x},${vertex3.y}))"
    }

}
package shape

import ICanvas
import ISolidShape
import Point
import distanceTo
import java.awt.Color
import java.util.*
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class Triangle(
        private val outlineColor: Color,
        private val fillColor: Color,
        private val vertex1: Point,
        private val vertex2: Point,
        private val vertex3: Point
) : ISolidShape {

    private var mPerimeter: Double? = null
    private var mArea: Double? = null
    private val mPoints = Vector<Point>().apply {
        addAll(listOf(vertex1, vertex2, vertex3))
    }

    override fun getFillColor() = fillColor
    override fun getOutlineColor() = outlineColor

    override fun getArea(): Double {
        return if (mArea == null) {
            0.5 * abs((vertex2.x - vertex1.x) * (vertex3.y - vertex1.y) -
                    (vertex3.x - vertex1.x) * (vertex2.y - vertex1.y)
            ).also { mArea = it }
        } else mArea ?: 0.0
    }

    override fun getPerimeter(): Double {
        return if (mPerimeter == null) {
            val len12 = vertex1.distanceTo(vertex2)
            val len23 = vertex2.distanceTo(vertex3)
            val len31 = vertex3.distanceTo(vertex1)
            (len12 + len23 + len31).also { mPerimeter = it }
        } else mPerimeter ?: 0.0
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

    override fun toString(): String {
        return stringify()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Triangle) {
            other.vertex1 == vertex1
                    && other.vertex2 == vertex2
                    && other.vertex3 == vertex3
                    && other.outlineColor == outlineColor
                    && other.fillColor == fillColor
        } else false
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + outlineColor.hashCode()
        result = 31 * result + fillColor.hashCode()
        result = 31 * result + vertex1.hashCode()
        result = 31 * result + vertex2.hashCode()
        result = 31 * result + vertex3.hashCode()
        return result
    }
}
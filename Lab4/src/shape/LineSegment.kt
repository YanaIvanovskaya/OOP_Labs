package shape

import ICanvas
import IShape
import Point
import distanceTo
import java.awt.Color
import kotlin.math.pow
import kotlin.math.sqrt

class LineSegment(
        private val outlineColor: Color,
        private val start: Point,
        private val end: Point
) : IShape {

    override fun getOutlineColor() = outlineColor

    override fun getArea(): Double {
        return getPerimeter()
    }

    override fun getPerimeter(): Double {
        return start.distanceTo(end)
    }

    override fun draw(canvas: ICanvas) {
        canvas.drawLine(start, end, outlineColor)
    }

    override fun stringify(): String {
        return "Line (start=(${start.x},${start.y}), end=(${end.x},${end.y}))"
    }

    override fun toString(): String {
        return stringify()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is LineSegment) {
            other.start == start && other.end == end && other.outlineColor == outlineColor
        } else false
    }

    override fun hashCode(): Int {
        var result = outlineColor.hashCode()
        result = 31 * result + start.hashCode()
        result = 31 * result + end.hashCode()
        return result
    }

}

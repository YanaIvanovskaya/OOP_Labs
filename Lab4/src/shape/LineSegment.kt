package shape

import ICanvas
import IShape
import Point
import java.awt.Color
import kotlin.math.pow
import kotlin.math.sqrt

class LineSegment(
        private val outlineColor: Color,
        private val start: Point,
        private val end: Point
) : IShape() {

    private val mPerimeter = DEFAULT

    override fun getOutlineColor() = outlineColor

    override fun getArea(): Double {
        return if (mPerimeter == DEFAULT) {
            sqrt((start.x - end.x).pow(2) + (start.y - end.y).pow(2))
        } else mPerimeter
    }

    override fun getPerimeter(): Double {
        return if (mPerimeter == DEFAULT) {
            sqrt((start.x - end.x).pow(2) + (start.y - end.y).pow(2))
        } else mPerimeter
    }

    override fun draw(canvas: ICanvas) {
        canvas.drawLine(start, end, outlineColor)
    }

    override fun stringify(): String {
        return "Line (start=(${start.x},${start.y}), end=(${end.x},${end.y}))"
    }

}

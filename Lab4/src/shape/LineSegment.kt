package shape

import ICanvas
import ICanvasDrawable
import IShape
import Point
import java.awt.Color

data class LineSegment(
    private val outlineColor: Color,
    private val start: Point,
    private val end: Point
) : IShape, ICanvasDrawable {

    override fun getOutlineColor() = outlineColor
    override fun getArea() = 0.0
    override fun getPerimeter() = 0.0

    override fun draw(canvas: ICanvas) {
        canvas.drawLine(start, end, outlineColor)
    }

    override fun toString(): String {
        return "Line (start=(${start.x},${start.y}), end=(${end.x},${end.y}))"
    }

}

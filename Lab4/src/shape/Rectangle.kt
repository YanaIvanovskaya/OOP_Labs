package shape

import ICanvas
import ICanvasDrawable
import ISolidShape
import Point
import java.awt.Color
import java.util.*

data class Rectangle(
    private val outlineColor: Color,
    private val fillColor: Color,
    private val leftTop: Point,
    private val width: Double,
    private val height: Double
) : ISolidShape, ICanvasDrawable {

    private val mPoints = Vector<Point>()

    override fun getFillColor() = fillColor
    override fun getOutlineColor() = outlineColor

    override fun getArea(): Double {
        return width * height
    }

    override fun getPerimeter(): Double {
        return (2 * width) + (2 * height)
    }

    override fun draw(canvas: ICanvas) {
        if (mPoints.isEmpty()) {
            val rightTop = Point(leftTop.x + width, leftTop.y)
            val leftBottom = Point(leftTop.x, leftTop.y - height)
            val rightBottom = Point(leftTop.x + width, leftTop.y - height)
            mPoints.add(leftTop)
            mPoints.add(rightTop)
            mPoints.add(leftBottom)
            mPoints.add(rightBottom)
        }
        canvas.fillPolygon(mPoints, fillColor)
        canvas.drawPolygon(mPoints, outlineColor)
    }

    override fun toString(): String {
        return "shape.Rectangle (leftTop=(${leftTop.x},${leftTop.y}), width=$width, height=$height)"
    }
}

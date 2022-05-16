package shape

import ICanvas
import ISolidShape
import Point
import java.awt.Color
import java.util.*

class Rectangle(
        private val outlineColor: Color,
        private val fillColor: Color,
        private val leftTop: Point,
        private val width: Double,
        private val height: Double
) : ISolidShape() {

    private val mPoints = Vector<Point>()

    override fun getFillColor() = fillColor
    override fun getOutlineColor() = outlineColor

    override fun getArea(): Double {
        return width * height
    }

    override fun getPerimeter(): Double {
        return 2 * (width + height)
    }

    override fun draw(canvas: ICanvas) {
        if (mPoints.isEmpty()) {
            val rightTop = Point(leftTop.x + width, leftTop.y)
            val leftBottom = Point(leftTop.x, leftTop.y - height)
            val rightBottom = Point(leftTop.x + width, leftTop.y - height)
            mPoints.add(leftTop)
            mPoints.add(rightTop)
            mPoints.add(rightBottom)
            mPoints.add(leftBottom)
        }
        canvas.fillPolygon(mPoints, fillColor)
        canvas.drawPolygon(mPoints, outlineColor)
    }

    override fun stringify(): String {
        return "Rectangle (leftTop=(${leftTop.x},${leftTop.y}), width=$width, height=$height)"
    }
}

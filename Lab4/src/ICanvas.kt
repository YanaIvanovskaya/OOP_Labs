import java.awt.Color
import java.util.*

interface ICanvas {

    fun drawLine(
        from: Point,
        to: Point,
        color: Color
    )

    fun fillCircle(
        center: Point,
        radius: Double,
        fillColor: Color
    )

    fun drawCircle(
        center: Point,
        radius: Double,
        outlineColor: Color
    )

    fun fillPolygon(
        points: Vector<Point>,
        fillColor: Color
    )

    fun drawPolygon(
        points: Vector<Point>,
        outlineColor: Color
    )

    fun clear()

}
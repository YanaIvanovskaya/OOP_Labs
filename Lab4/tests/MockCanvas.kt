import java.awt.Color
import java.util.*

internal class MockCanvas : ICanvas {

    private val mDrawingResult = mutableListOf<String>()

    override fun drawLine(from: Point, to: Point, color: Color) {
        mDrawingResult.add("drawLine from=$from to=$to color=$color")
    }

    override fun fillCircle(center: Point, radius: Double, fillColor: Color) {
        mDrawingResult.add("fillCircle center=$center radius=$radius fillColor=$fillColor")
    }

    override fun drawCircle(center: Point, radius: Double, outlineColor: Color) {
        mDrawingResult.add("drawCircle center=$center radius=$radius outlineColor=$outlineColor")
    }

    override fun fillPolygon(points: Vector<Point>, fillColor: Color) {
        mDrawingResult.add("fillPolygon points=$points fillColor=$fillColor")
    }

    override fun drawPolygon(points: Vector<Point>, outlineColor: Color) {
        mDrawingResult.add("drawPolygon points=$points outlineColor=$outlineColor")
    }

    override fun clear() {
        mDrawingResult.clear()
    }

    fun getDrawingResult() = mDrawingResult

}
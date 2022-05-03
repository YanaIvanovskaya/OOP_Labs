import java.awt.Color
import java.util.*

internal class MockCanvas : ICanvas {

    private val mDrawingResult = mutableListOf<String>()

    override fun drawLine(from: Point, to: Point, color: Color) {
        mDrawingResult.clear()
        mDrawingResult.add("")
    }

    override fun fillCircle(center: Point, radius: Double, fillColor: Color) {
        TODO("Not yet implemented")
    }

    override fun drawCircle(center: Point, radius: Double, outlineColor: Color) {
        TODO("Not yet implemented")
    }

    override fun fillPolygon(points: Vector<Point>, fillColor: Color) {
        TODO("Not yet implemented")
    }

    override fun drawPolygon(points: Vector<Point>, outlineColor: Color) {
        TODO("Not yet implemented")
    }

    override fun clear() {
        TODO("Not yet implemented")
    }

}
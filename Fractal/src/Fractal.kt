import javax.swing.JFrame

fun main() {
    Fractal().draw()
}

class Fractal {

    private val mDraw by lazy {
        Draw("Fractal").apply {
            setCanvasSize(CANVAS_WIDTH.toInt(), CANVAS_HEIGHT.toInt())
            setXscale(-SCALE, SCALE)
            setYscale(-SCALE, SCALE)
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
            penRadius = 0.002
        }
    }


    fun draw() {
        mDraw.point(500.0, 500.0)


        var realTemp: Double
        var imagTemp: Double
        var realTemp2: Double
        var arg: Double
        var iterations: Int


        var imagCoord = 1.2


        while (imagCoord >= -1.2) {

            var realCoord = -0.6
            while (realCoord <= 1.77) {
                iterations = 0
                realTemp = realCoord
                imagTemp = imagCoord
                arg = (realCoord * realCoord) + (imagCoord * imagCoord)
                while ((arg < 4) && (iterations < 40)) {
                    realTemp2 = (realTemp * realTemp) - (imagTemp * imagTemp) - realCoord;
                    imagTemp = (2 * realTemp * imagTemp) - imagCoord
                    realTemp = realTemp2
                    arg = (realTemp * realTemp) + (imagTemp * imagTemp)
                    iterations += 1
                    mDraw.point(imagCoord, iterations.toDouble())
//                    println("imagCoord $imagCoord realCoord $realCoord ")
                }

                when (iterations % 4) {
                    0 -> print(".")
                    1 -> print("o");
                    2 -> print("0")
                    3 -> print("@")
                }

                realCoord += 0.03
            }
            imagCoord -= 0.05
        }


    }


    companion object {
        private const val CANVAS_HEIGHT = 800.0
        private const val SCALE = 2.0
        private const val CANVAS_WIDTH = 800.0
    }

}
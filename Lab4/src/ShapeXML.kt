sealed class ShapeXML(val tag: String) {

    abstract val attrs: Map<String, Boolean>

    val countOfRequired
        get() = attrs.filter { it.value }.count()

    object Circle : ShapeXML(CIRCLE) {
        const val centerX = "center_x"
        const val centerY = "center_y"
        const val radius = "radius"

        override val attrs = mapOf(
            centerX to true,
            centerY to true,
            radius to true,
            FILL_COLOR to false,
            OUTLINE_COLOR to false
        )
    }

    object Rectangle : ShapeXML(RECTANGLE) {
        const val leftTopX = "left_top_x"
        const val leftTopY = "left_top_y"
        const val width = "width"
        const val height = "height"

        override val attrs = mapOf(
            leftTopX to true,
            leftTopY to true,
            width to true,
            height to true,
            FILL_COLOR to false,
            OUTLINE_COLOR to false
        )
    }

    object Triangle : ShapeXML(TRIANGLE) {
        const val vertex1X = "vertex1_x"
        const val vertex1Y = "vertex1_y"
        const val vertex2X = "vertex2_x"
        const val vertex2Y = "vertex2_y"
        const val vertex3X = "vertex3_x"
        const val vertex3Y = "vertex3_y"

        override val attrs = mapOf(
            vertex1X to true,
            vertex1Y to true,
            vertex2X to true,
            vertex2Y to true,
            vertex3X to true,
            vertex3Y to true,
            FILL_COLOR to false,
            OUTLINE_COLOR to false
        )
    }

    object Line : ShapeXML(LINE) {
        const val startX = "start_x"
        const val startY = "start_y"
        const val endX = "end_x"
        const val endY = "end_y"

        override val attrs = mapOf(
            startX to true,
            startY to true,
            endX to true,
            endY to true,
            OUTLINE_COLOR to false
        )
    }

    companion object {
        const val CIRCLE = "circle"
        const val RECTANGLE = "rectangle"
        const val TRIANGLE = "triangle"
        const val LINE = "line"
        const val FILL_COLOR = "fill_color"
        const val OUTLINE_COLOR = "outline_color"
        val availableShapes = listOf(CIRCLE, RECTANGLE, TRIANGLE, LINE)

        fun getShapeByTag(tag: String): ShapeXML {
            return when (tag) {
                CIRCLE -> Circle
                RECTANGLE -> Rectangle
                TRIANGLE -> Triangle
                LINE -> Line
                else -> throw IllegalArgumentException("Tag $tag is not shape tag")
            }
        }
    }

}
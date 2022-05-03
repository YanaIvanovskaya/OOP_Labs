data class Attribute(
    val name: String,
    val isRequired: Boolean
)

sealed class ShapeXML(val tag: String) {

    abstract val attrs: List<Attribute>

    val countOfRequired
        get() = attrs.filter { it.isRequired }.count()

    object Circle : ShapeXML(CIRCLE) {
        const val centerX = "center_x"
        const val centerY = "center_y"
        const val radius = "radius"

        override val attrs = listOf(
            Attribute(centerX, true),
            Attribute(centerY, true),
            Attribute(radius, true),
            Attribute(FILL_COLOR, false),
            Attribute(OUTLINE_COLOR, false)
        )
    }

    object Rectangle : ShapeXML(RECTANGLE) {
        const val leftTopX = "left_top_x"
        const val leftTopY = "left_top_y"
        const val width = "width"
        const val height = "height"

        override val attrs = listOf(
            Attribute(leftTopX, true),
            Attribute(leftTopY, true),
            Attribute(width, true),
            Attribute(height, true),
            Attribute(FILL_COLOR, false),
            Attribute(OUTLINE_COLOR, false)
        )
    }

    object Triangle : ShapeXML(TRIANGLE) {
        const val vertex1X = "vertex1_x"
        const val vertex1Y = "vertex1_y"
        const val vertex2X = "vertex2_x"
        const val vertex2Y = "vertex2_y"
        const val vertex3X = "vertex3_x"
        const val vertex3Y = "vertex3_y"

        override val attrs = listOf(
            Attribute(vertex1X, true),
            Attribute(vertex1Y, true),
            Attribute(vertex2X, true),
            Attribute(vertex2Y, true),
            Attribute(vertex3X, true),
            Attribute(vertex3Y, true),
            Attribute(FILL_COLOR, false),
            Attribute(OUTLINE_COLOR, false)
        )
    }

    object Line : ShapeXML(LINE) {
        const val startX = "start_x"
        const val startY = "start_y"
        const val endX = "end_y"
        const val endY = "end_y"

        override val attrs = listOf(
            Attribute(startX, true),
            Attribute(startY, true),
            Attribute(endX, true),
            Attribute(endY, true),
            Attribute(FILL_COLOR, false)
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
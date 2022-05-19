import org.w3c.dom.Document
import org.w3c.dom.NamedNodeMap
import org.w3c.dom.Node
import org.xml.sax.InputSource
import shape.Circle
import shape.LineSegment
import shape.Rectangle
import shape.Triangle
import java.awt.Color
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

class ParserException(val description: String) : Throwable()

class ShapeXMLParser : IShapeParser {

    override fun parse(source: String): List<IShape> {
        if (source.isEmpty()) {
            return listOf()
        }

        val document = createXMLDocument(source)

        val rootNode = document.firstChild
        if (rootNode.nodeName != "shapes") {
            throw ParserException("Expected root element <shapes></shapes>")
        }

        val shapes = mutableListOf<IShape>()
        val childNodes = rootNode.childNodes
        for (index in 0 until childNodes.length) {
            val child = childNodes.item(index)
            if (child.nodeType !in listOf(Node.TEXT_NODE, Node.COMMENT_NODE)) {
                shapes.add(createShapeFromNode(child))
            }
        }
        return shapes
    }

    private fun createXMLDocument(source: String): Document {
        try {
            val docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            val xml = InputSource(StringReader(source))
            return docBuilder.parse(xml)
        } catch (ex: Exception) {
            throw ParserException("The source is not valid XML")
        }
    }

    private fun createShapeFromNode(node: Node): IShape {
        val attrs = node.attributes

        val shapeXML = if (node.nodeName !in ShapeXML.availableShapes) {
            throw ParserException("Unexpected tag: ${node.nodeName}")
        } else ShapeXML.getShapeByTag(node.nodeName)

        ensureRequiredAttributes(attrs, shapeXML)
        ensureExpectedAttributes(attrs, shapeXML)

        val outlineColor = getOutlineColor(attrs)
        val fillColor = getFillColor(attrs)

        return when (shapeXML) {
            ShapeXML.Circle -> createCircle(attrs, outlineColor, fillColor)
            ShapeXML.Rectangle -> createRectangle(attrs, outlineColor, fillColor)
            ShapeXML.Triangle -> createTriangle(attrs, outlineColor, fillColor)
            ShapeXML.Line -> createLine(attrs, outlineColor)
        }
    }

    private fun ensureRequiredAttributes(attrs: NamedNodeMap, shapeXML: ShapeXML) {
        if (attrs.length < shapeXML.countOfRequired) {
            val msg =
                    "Tag <${shapeXML.tag}> expected ${shapeXML.countOfRequired} required attributes: " +
                            "${shapeXML.attrs.filter { it.value }.keys}"
            throw ParserException(msg)
        }
    }

    private fun ensureExpectedAttributes(attrs: NamedNodeMap, shapeXML: ShapeXML) {
        val expectedAttrs = shapeXML.attrs.keys
        for (index in 0 until attrs.length) {
            val attr = attrs.item(index)
            if (attr.nodeName !in expectedAttrs) {
                throw ParserException("Unexpected attribute for shape ${shapeXML.tag} - ${attr.nodeName}")
            }
        }
    }

    private fun getOutlineColor(attrs: NamedNodeMap): Color {
        var hexColor = ""
        return runCatching {
            hexColor = attrs.getAttributeByName(ShapeXML.OUTLINE_COLOR)
                    ?: return@runCatching DEFAULT_OUTLINE_COLOR
            Color.decode(hexColor)
        }.getOrElse { throw ParserException("Incorrect outline color - $hexColor") }
    }

    private fun getFillColor(attrs: NamedNodeMap): Color {
        var hexColor = ""
        return runCatching {
            hexColor = attrs.getAttributeByName(ShapeXML.FILL_COLOR)
                    ?: return@runCatching DEFAULT_FILL_COLOR
            Color.decode(hexColor)
        }.getOrElse { throw ParserException("Incorrect fill color - $hexColor") }
    }

    private fun createTriangle(
            attrs: NamedNodeMap,
            outlineColor: Color,
            fillColor: Color
    ) = Triangle(
            outlineColor = outlineColor,
            fillColor = fillColor,
            vertex1 = createPoint(attrs, ShapeXML.Triangle.vertex1X, ShapeXML.Triangle.vertex1Y),
            vertex2 = createPoint(attrs, ShapeXML.Triangle.vertex2X, ShapeXML.Triangle.vertex2Y),
            vertex3 = createPoint(attrs, ShapeXML.Triangle.vertex3X, ShapeXML.Triangle.vertex3Y)
    )

    private fun createLine(
            attrs: NamedNodeMap,
            outlineColor: Color
    ) = LineSegment(
            start = createPoint(attrs, ShapeXML.Line.startX, ShapeXML.Line.startY),
            end = createPoint(attrs, ShapeXML.Line.endX, ShapeXML.Line.endY),
            outlineColor = outlineColor
    )

    private fun createRectangle(
            attrs: NamedNodeMap,
            outlineColor: Color,
            fillColor: Color
    ) = Rectangle(
            outlineColor = outlineColor,
            fillColor = fillColor,
            leftTop = createPoint(attrs, ShapeXML.Rectangle.leftTopX, ShapeXML.Rectangle.leftTopY),
            width = attrs.getDoubleAttributeByName(ShapeXML.Rectangle.width),
            height = attrs.getDoubleAttributeByName(ShapeXML.Rectangle.height)
    )

    private fun createCircle(
            attrs: NamedNodeMap,
            outlineColor: Color,
            fillColor: Color
    ) = Circle(
            center = createPoint(attrs, ShapeXML.Circle.centerX, ShapeXML.Circle.centerY),
            outlineColor = outlineColor,
            fillColor = fillColor,
            radius = attrs.getDoubleAttributeByName(ShapeXML.Circle.radius)
    )

    private fun createPoint(
            attrs: NamedNodeMap,
            nameX: String,
            nameY: String
    ) = Point(
            x = attrs.getDoubleAttributeByName(nameX),
            y = attrs.getDoubleAttributeByName(nameY)
    )

    private fun NamedNodeMap.getAttributeByName(name: String): String? {
        return getNamedItem(name)?.nodeValue
    }

    private fun NamedNodeMap.getDoubleAttributeByName(name: String): Double {
        return runCatching {
            getAttributeByName(name)?.toDouble() ?: 0.0
        }.getOrElse { throw ParserException("Attribute $name expected type number") }
    }

    companion object {
        private val DEFAULT_FILL_COLOR = Color(0, 0, 0, 0)
        private val DEFAULT_OUTLINE_COLOR = Color.BLACK
    }

}


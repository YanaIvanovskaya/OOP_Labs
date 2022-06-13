fun main() {
    val controller = CalculatorController(
            calculator = Calculator(),
            parser = InstructionParser()
    )

    controller.start()
}
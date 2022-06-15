fun main() {
    val controller = CalculatorController(
            calculator = Calculator(),
            parser = InstructionParser()
    )


    val data = mutableListOf(
            "let x = 1",
            "let x1 = 1"
    )
    for (i in 1..1000000) {
        data.add("fn x${i + 1} = x$i + x")
    }
    data.add("print x1000000")
//    data.forEach {
//        println(it)
//    }
    val time = System.currentTimeMillis()
    data.forEach { controller.calculate(it) }

    println("Time: ${(System.currentTimeMillis() - time) / 1000 / 60} min")

}
fun main() {
    val controller = CalculatorController(
            calculator = Calculator(),
            parser = InstructionParser()
    )

//    val fib = listOf(
//            "let v0 = 0",
//            "let v1 = 1",
//            "fn fib0 = v0",
//            "fn fib1 = v1",
//            "fn fib2 = fib1 + fib0",
//            "fn fib3 = fib2 + fib1",
//            "fn fib4 = fib3 + fib2",
//            "fn fib5 = fib4 + fib3",
//            "fn fib6 = fib5 + fib4",
//            "printfns",
//            "let v0 = 1",
//            "let v1 = 1",
//            "printfns"
//    )
//    fib.forEach { controller.calculate(it) }
//    controller.start()

    val data = mutableListOf(
            "let x = 1",
            "let x1 = 1"
    )
    for (i in 1..10000) {
        data.add("fn x${i + 1} = x$i + x")
    }
    data.add("print x10000")
//    data.forEach {
//        println(it)
//    }
    data.forEach { controller.calculate(it) }

}
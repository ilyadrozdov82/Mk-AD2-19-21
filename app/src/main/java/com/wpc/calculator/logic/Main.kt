var result: Number = 0

fun main() {
    print("Input:")
    scanning()
    loop()
}

fun loop() {
    print("Output:$result\nGoing:")

    val addExpression: String? = readLine()
    scanning("$result$addExpression")
    loop()
}

fun scanning(expression: String? = readLine()) {
    result = ParsToCalc().calculate(expression.toString())
}
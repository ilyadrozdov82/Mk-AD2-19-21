class ParsToCalc {
    private val add = Addition()
    private val sub = Subtraction()
    private val div = Division()
    private val mul = Multiplication()

    private fun separate(expression: String): List<String> {
        val incomMathExpression =
            expression.filter { !it.isWhitespace() }.replace("*", " * ").replace("/", " / ").replace("+", " + ")
                .replace("-", " - ").replace("(", " ( ").replace(")", " ) ").replace("  ", " ").trim()
        return incomMathExpression.split(" ")
    }

    fun calculate(expression: String): Double {
        val operators = separate(expression)
        val operationQueue = mutableListOf<Any>()

        val nestedOperation = mutableListOf<Any>()
        var openNestedOperations = 0

        val zeroPriorityIterator = operators.iterator()
        for ((position) in zeroPriorityIterator.withIndex()) {

            if (operators[position] == '('.toString() || nestedOperation.isNotEmpty()) {
                if (operators[position] == '('.toString()) openNestedOperations++
                if (operators[position] == ')'.toString()) openNestedOperations--
                nestedOperation.add(operators[position])

                if (operators[position] == ')'.toString() && openNestedOperations == 0) {
                    nestedOperation.removeFirst()
                    nestedOperation.removeLast()

                    val nestedStringOperation = nestedOperation.joinToString(separator = " ")
                    val result: Any = calculate(nestedStringOperation)
                    operationQueue.add(result)
                    nestedOperation.clear()
                }
            } else {
                operationQueue.add(operators[position])
            }
        }

        var passOperatorCounter = 0
        val tempList = mutableListOf<Any>()

        val firstPriorityIterator = operationQueue.iterator()
        for ((index) in firstPriorityIterator.withIndex()) {

            if (passOperatorCounter > 0) {
                passOperatorCounter--
                continue
            }

            val next = index + 1

            if (operationQueue[index] == '*'.toString()) {
                val value = tempList.removeLastOrNull()
                val result = mul.execute(
                    value.toString().toDouble(), operationQueue[next].toString().toDouble()
                )
                tempList.add(result)
                passOperatorCounter++
            } else if (operationQueue[index] == '/'.toString()) {
                if (operationQueue[next].toString().toDouble() == 0.0) println("You can't divide by zero")

                val value = tempList.removeLastOrNull()
                val result = div.execute(value.toString().toDouble(), operationQueue[next].toString().toDouble())
                tempList.add(result)
                passOperatorCounter++
            } else {
                tempList.add(operationQueue[index])
            }
        }

        operationQueue.clear()
        operationQueue.addAll(tempList)

        var result: Double = operationQueue.removeFirst().toString().toDouble()

        val secondPriorityIterator = operationQueue.iterator()
        for ((index) in secondPriorityIterator.withIndex()) {
            if ((index + 1) % 2 > 0 && operationQueue.count() > index + 1) {
                val nextOperator = index + 1
                if (operationQueue[index] == '+'.toString()) {
                    result = add.execute(result, operationQueue[nextOperator].toString().toDouble())
                } else if (operationQueue[index] == '-'.toString()) {
                    result = sub.execute(
                        result, operationQueue[nextOperator].toString().toDouble()
                    )
                }
            }
        }
        return result
    }
}



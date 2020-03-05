import kotlin.random.Random

private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

sealed class Operation(open val regex: Regex) {
    class ReplaceLetters(override val regex: Regex = "[a-zA-Z]".toRegex()): Operation(regex);
    class ReplaceNumber(override val regex: Regex = "[0-9]".toRegex()): Operation(regex);
}

data class ReplaceOperation(
    val source: String,
    val operand: String,
    val operation: Operation
) {
    val target: String = source.replace(operation.regex, operand)
    val count = target.count { it.toString() == operand }
}

fun main(args: Array<String>) {
    println("Please, enter some random number")

    (1..(readLine()?.toInt() ?: 0))
        .map { Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")
        .let { generatedString ->
            println("Generated the string \"$generatedString\"")
            println("Now, enter some random symbol, please")
            ReplaceOperation(generatedString, readLine() ?: "", Operation.ReplaceLetters())
        }
        .let { operation ->
            println("Replaced: ${operation.target}")
            println("Now, enter another random symbol, please")
            Pair(
                operation,
                ReplaceOperation(operation.target, readLine() ?: "", Operation.ReplaceNumber())
            )

        }
        .let { (prevOperation: ReplaceOperation, newOperation: ReplaceOperation) ->
            println("Result string: ${newOperation.target}")
            println("Replaced letters: ${prevOperation.count}")
            println("Replaced numbers: ${newOperation.count}")
        }
        .apply {
            println("----------------------------------")
        }

}
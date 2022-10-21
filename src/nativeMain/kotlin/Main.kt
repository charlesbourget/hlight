import platform.posix.exit

const val ANSI_RED_BACKGROUND = "\u001B[41m"
const val ANSI_RESET = "\u001B[0m"

fun main(args: Array<String>) {
    if (args.size != 1) {
        println("ERROR: Program only accepts 1 positional argument")
        usage()
        exit(1)
    }

    if (args[0] == "-h") {
        usage()
        exit(0)
    }

    val pattern = args[0]
    val regex = Regex(pattern)

    while (true) {
        try {
            val line = readln()

            if (regex.containsMatchIn(line)) {
                println(ANSI_RED_BACKGROUND + line + ANSI_RESET)
            } else {
                println(line)
            }
        } catch (e: RuntimeException) {
            exit(0)
        }
    }
}

fun usage() {
    println("Usage: colorer PATTERN")
}
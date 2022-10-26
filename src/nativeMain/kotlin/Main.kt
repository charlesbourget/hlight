import platform.posix.exit

const val ANSI_RED_BACKGROUND = "\u001B[41m"
const val ANSI_GREEN_BACKGROUND = "\u001B[42m"
const val ANSI_YELLOW_BACKGROUND = "\u001B[43m"
const val ANSI_BLUE_BACKGROUND = "\u001B[44m"
const val ANSI_PURPLE_BACKGROUND = "\u001B[45m"
const val ANSI_CYAN_BACKGROUND = "\u001B[46m"
const val ANSI_WHITE_BACKGROUND = "\u001B[47m"
const val ANSI_RESET = "\u001B[0m"

data class Pattern(val regex: Regex, val color: String)

fun main(args: Array<String>) {
    val colors = listOf(
        ANSI_RED_BACKGROUND,
        ANSI_GREEN_BACKGROUND,
        ANSI_YELLOW_BACKGROUND,
        ANSI_BLUE_BACKGROUND,
        ANSI_PURPLE_BACKGROUND,
        ANSI_CYAN_BACKGROUND,
        ANSI_WHITE_BACKGROUND
    )

    validateArgs(args, colors)

    val patterns = List(args.size) { i -> Pattern(Regex(args[i]), colors[i]) }

    while (true) {
        try {
            var outputLine = readln()

            for (pattern in patterns) {
                if (pattern.regex.containsMatchIn(outputLine)) {
                    outputLine = pattern.color + outputLine + ANSI_RESET
                    break
                }
            }

            println(outputLine)
        } catch (e: RuntimeException) {
            exit(0)
        }
    }
}

fun usage() {
    println("Usage: hlight PATTERN")
}

fun validateArgs(args: Array<String>, colors: List<String>) {
    if (args.isEmpty()) {
        println("ERROR: Program needs at least 1 positional argument")
        usage()
        exit(1)
    }

    if (args.contains("-h") || args.contains("--help")) {
        usage()
        exit(0)
    }

    if (args.size > colors.size) {
        println("ERROR: Too much patterns. The limit is ${colors.size}")
        usage()
        exit(1)
    }
}
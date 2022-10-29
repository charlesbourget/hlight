import platform.posix.exit

var useConfiguration = false

fun main(args: Array<String>) {
    validateArgs(args, getNumColors())

    val inputPatterns: List<String> = when(useConfiguration) {
        true -> Configuration().getPatterns()
        false -> args.asList()
    }

    val patterns = List(inputPatterns.size) { i -> Pattern(Regex(inputPatterns[i]), colors[i]) }

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
    println("""Usage:
        Using command line arguments: 
            hlight PATTERNS
        or using config file in ~/.config/hlight/hlight.json
            hlight -c
        """)
}

fun validateArgs(args: Array<String>, numColors: Int) {
    if (args.isEmpty()) {
        println("ERROR: Program needs at least 1 positional argument")
        usage()
        exit(1)
    }

    if (args.contains("-h") || args.contains("--help")) {
        usage()
        exit(0)
    }

    if (args.contains("-c") || args.contains("--config")) {
        useConfiguration = true
    }

    if (args.size > numColors) {
        println("ERROR: Too much patterns. The limit is $numColors")
        usage()
        exit(1)
    }
}
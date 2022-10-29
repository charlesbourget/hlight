import kotlinx.cinterop.toKString
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import platform.posix.exit
import platform.posix.getenv


@Serializable
data class FileConfiguration(val patterns: List<String>)

class Configuration() {
    private var configurationFilePath: String = defaultConfigurationFilePath
    private var fileConfiguration: FileConfiguration? = null

    init {
        this.fileConfiguration = readFileConfiguration()
    }

    fun getPatterns(): List<String> {
        return fileConfiguration?.patterns ?: listOf()
    }

    private fun readFileConfiguration(): FileConfiguration {
        val configurationFileContent: String

        try {
            configurationFileContent = readFileToString(configurationFilePath)
            return Json.decodeFromString(configurationFileContent)
        } catch(e: IllegalArgumentException) {
            createConfigurationFile()
        }

        return FileConfiguration(listOf())
    }

    private fun createConfigurationFile() {
        val fileConfiguration = FileConfiguration(patterns = listOf())
        writeAllText(configurationFilePath, Json.encodeToString(fileConfiguration))
       println("No configuration file found. New empty file was created")
       exit(0)
    }

    private companion object {
        const val CONFIGURATION_FILE_NAME = "hlight"
        const val CONFIGURATION_DIR_NAME = ".config/hlight"
        const val JSON_FILE_EXTENSION = "json"
        val HOME_DIR = getenv("HOME")?.toKString()
        val defaultConfigurationFilePath = "$HOME_DIR/$CONFIGURATION_DIR_NAME/$CONFIGURATION_FILE_NAME.$JSON_FILE_EXTENSION"

    }
}
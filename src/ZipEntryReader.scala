import java.io.{BufferedReader, File, InputStreamReader, IOException}
import java.util.zip.ZipFile

/**
 * A class for reading individual text files line by line from a zip file (just what you would need for reading a
 * compressed CSV file!).
 * @param zipPath the filename of the zip file
 * @param entryName the name of the file to read within the zip file
 */
class ZipEntryReader(zipPath: String, entryName: String) extends AutoCloseable:

  private val zipFile = ZipFile(File(zipPath), ZipFile.OPEN_READ)
  private val zipEntry = zipFile.getEntry(entryName)

  if zipEntry == null then
    throw IOException(s"No entry named '$entryName' in zip file!")

  private val entryReader = BufferedReader(InputStreamReader(zipFile.getInputStream(zipEntry)))

  /** Close this ZipEntryReader (and the zip file that it is reading from) */
  override def close(): Unit =
    zipFile.close()

  /** Reads a single line from the file, or returns None if there are no lines remaining */
  def readLine(): Option[String] = Option(entryReader.readLine)

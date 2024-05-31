import java.io.{BufferedReader, File, InputStreamReader, IOException}
import java.util.zip.ZipFile

class ZipEntryReader(zipPath: String, entryName: String) extends AutoCloseable:

  private val zipFile = ZipFile(File(zipPath), ZipFile.OPEN_READ)
  private val zipEntry = zipFile.getEntry(entryName)

  if zipEntry == null then
    throw IOException(s"No entry named '$entryName' in zip file!")

  private val entryReader = BufferedReader(InputStreamReader(zipFile.getInputStream(zipEntry)))

  override def close(): Unit =
    zipFile.close()

  def readLine(): Option[String] = Option(entryReader.readLine)

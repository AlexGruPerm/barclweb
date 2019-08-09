package models

import java.text.SimpleDateFormat
import java.util.Date

trait CommonFuncs {

  def convertLongToDate(l: Long): Date = new Date(l)

  //http://tutorials.jenkov.com/java-internationalization/simpledateformat.html
  // Pattern Syntax
  val DATE_FORMAT = "dd.MM.yyyy HH:mm:ss"

  val DATETIME_FORMAT_CASS = "yyyy.MM.dd HH:mm:ss"
  val DATE_FORMAT_CASS = "yyyy.MM.dd"

  /**
    * When we convert unix_timestamp to String representation of date and time is using same TimeZone.
    * Later we can adjust it with :
    *
    * val format = new SimpleDateFormat()
    * format.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME, "UTC"))
    * val dateAsString = format.format(date)
    *
    */
  def getDateAsString(d: Date): String = {
    val dateFormat = new SimpleDateFormat(DATE_FORMAT)
    dateFormat.format(d)
  }

  def getDateAsStringCass(d: Date): String = {
    val dateFormat = new SimpleDateFormat(DATE_FORMAT_CASS)
    dateFormat.format(d)
  }

  def getDateTimeAsStringCass(d: Date): String = {
    val dateFormat = new SimpleDateFormat(DATETIME_FORMAT_CASS)
    dateFormat.format(d)
  }

}

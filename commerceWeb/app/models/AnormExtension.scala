package models

import org.joda.time._
import org.joda.time.format._
import anorm._

object AnormExtension {
  val dateFormatGeneration: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss:SS");

  implicit def rowToDateTime: Column[DateTime] = Column.nonNull { (value, meta) =>
    val MetaDataItem(qualified, nullable, clazz) = meta
    value match {
      case ts: java.sql.Timestamp => Right(new DateTime(ts.getTime))
      case d: java.sql.Date => Right(new DateTime(d.getTime))
      case str: java.lang.String => Right(dateFormatGeneration.parseDateTime(str))
      case _ => Left(TypeDoesNotMatch("Cannot convert " + value + ":" + value.asInstanceOf[AnyRef].getClass))
    }
  }

  implicit val dateTimeToStatement = new ToStatement[DateTime] {
    def set(s: java.sql.PreparedStatement, index: Int, aValue: DateTime): Unit = {
      s.setTimestamp(index, new java.sql.Timestamp(aValue.withMillisOfSecond(0).getMillis()))
    }
  }

  implicit def rowToLocalTime: Column[LocalTime] = Column.nonNull { (value, _) =>
    value match {
      case t: java.sql.Time => Right(new LocalTime(t.getTime))
      case str: java.lang.String => Right(new LocalTime(dateFormatGeneration.parseLocalTime(str)))
      case x => Left(TypeDoesNotMatch(x.getClass.toString))
    }
  }

  implicit def localTimeToStatement = new ToStatement[LocalTime] {
    def set(s: java.sql.PreparedStatement, index: Int, aValue: LocalTime) {
      s.setTime(index, new java.sql.Time(aValue.toDateTimeToday.getMillis))
    }
  }

  implicit def rowToLocalDate: Column[LocalDate] = Column.nonNull { (value, _) =>
    value match {
      case d: java.sql.Date => Right(new LocalDate(d.getTime))
      case str: java.lang.String => Right(new LocalDate(dateFormatGeneration.parseLocalDate(str)))
      case x => Left(TypeDoesNotMatch(x.getClass.toString))
    }
  }

  implicit def localDateToStatement = new ToStatement[LocalDate] {
    def set(s: java.sql.PreparedStatement, index: Int, aValue: LocalDate) {
      s.setDate(index, new java.sql.Date(aValue.toDateTimeAtStartOfDay.getMillis))
    }
  }
}
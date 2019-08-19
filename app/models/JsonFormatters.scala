package models

import play.api.libs.json.{Json, Writes}

object JsonFormatters {
  implicit val residentWrites = new Writes[TickerFailBwsCnt] {
    def writes(thisTickerFail: TickerFailBwsCnt) = //Json.toJson()
      Json.obj("tickerID" -> thisTickerFail.tickerId, "failcnt" -> thisTickerFail.failCnt)
  }
}

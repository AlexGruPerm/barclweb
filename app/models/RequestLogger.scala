package models

import play.api.Logger
import play.api.mvc.{AnyContent, Request}


object RequestLogger extends  CommonFuncs {
  val log: Logger = Logger(this.getClass())

  def saveLog(req : Request[AnyContent]) :String = {
    val currentUtx = System.currentTimeMillis
    val currentDateTime :String = getDateAsString(convertLongToDate(currentUtx))

    val clientSessionId: String = req.session.get("uid")
      .map(id => id.take(10))
      .getOrElse((new scala.util.Random(31)).nextString(10))

    log.info("---------------------------------------------------")
    log.info("currentUtx = "+currentUtx)
    log.info("datetime = "+currentDateTime)
    log.info("request.remoteAddress="+req.remoteAddress)
    log.info("host = "+req.host)
    log.info("clientSessionId = "+clientSessionId)
    log.info("---------------------------------------------------")

   clientSessionId
  }

  trait Factory {
    def apply(): RequestLogger.type
  }
}

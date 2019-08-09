package models


import app.Global
import play.api.Logger
import play.api.mvc.{AnyContent, Request}
import play.api.routing.Router



object RequestLogger extends  CommonFuncs {
  val log: Logger = Logger(this.getClass())

  def getUserToken(username :String) :String ={
    val tokenGenerator = new BearerTokenGenerator
    tokenGenerator.generateMD5Token(username)
  }

  val sess  = Global.sessInstance

  def saveLog(req : Request[AnyContent]) :String = {
    val currentUtx = System.currentTimeMillis
    val currentDate :String = getDateAsStringCass(convertLongToDate(currentUtx))
    val currentDateTime :String = getDateTimeAsStringCass(convertLongToDate(currentUtx))
    val clientSessionIdOpt: Option[String] = req.session.get("uid")
    val clientSessionId: String = clientSessionIdOpt match {
      case Some(uid) => {
        log.info("UID found in session, is = "+uid)
        uid
      }
      case None => {
        log.info("There is no UID in session, generate new.")
        getUserToken(currentUtx.toString)
      }
    }
    //log.info("saveLog ")

    val handler = req.attrs(Router.Attrs.HandlerDef)
    val routerPath = handler.path
    val routerController = handler.controller
    val routerMethod = handler.method
    val routerModifiers = handler.modifiers

    /*
    log.info("---------------------------------------------------")
    //partition key by ddate
    //clustering key by ts desc
    log.info("date = "+currentDate)
    log.info("currentUtx = "+currentUtx)
    log.info("datetime = "+currentDateTime)
    log.info("clientSessionId = "+clientSessionId)
    log.info("request.remoteAddress="+req.remoteAddress)
    log.info("routerPath = "+routerPath)
    log.info("routerController = "+ routerController);
    log.info("routerMethod = "+ routerMethod);
    log.info("routerModifiers = "+ routerModifiers);
*/
    sess.saveClientLog(clientRow(clientSessionId, req.remoteAddress, routerPath, routerController, routerMethod))
    //log.info("---------------------------------------------------")

    clientSessionId
  }

  trait Factory {
    def apply(): RequestLogger.type
  }
}

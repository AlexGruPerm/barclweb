package models


import play.api.Logger
import play.api.libs.typedmap.{TypedKey, TypedMap}
import play.api.mvc.{AnyContent, Request, RequestHeader}
import play.api.routing.{HandlerDef, Router}



object RequestLogger extends  CommonFuncs {
  val log: Logger = Logger(this.getClass())

  def getUserToken(username :String) :String ={
    val tokenGenerator = new BearerTokenGenerator
    tokenGenerator.generateMD5Token(username)
  }

  def saveLog(req : Request[AnyContent]) :TypedMap = {
    val currentUtx = System.currentTimeMillis
    val currentDateTime :String = getDateAsString(convertLongToDate(currentUtx))

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


    log.info("saveLog ")

    val handler = req.attrs(Router.Attrs.HandlerDef)

    val routerPath = handler.path
    val routerController = handler.controller
    val routerMethod = handler.method
    val routerModifiers = handler.modifiers

    log.info("---------------------------------------------------")
    //partition key by ddate
    //clustering key by ts desc
    log.info("datetime = "+currentDateTime)
    log.info("currentUtx = "+currentUtx)
    log.info("clientSessionId = "+clientSessionId)
    log.info("request.remoteAddress="+req.remoteAddress)
    log.info("routerPath = "+routerPath)
    log.info("routerController = "+ routerController);
    log.info("routerMethod = "+ routerMethod);
    log.info("routerModifiers = "+ routerModifiers);

    //maybe create tables with COUNTERS
    //ddate,userid,cnt
    //userid,cnt
    //
    log.info("---------------------------------------------------")

    val newAttrs :TypedMap = req.attrs
    req.addAttr(TypedKey.apply[String]("uid"),clientSessionId)
    newAttrs.+(TypedKey.apply[String]("uid") -> clientSessionId)
  }

  trait Factory {
    def apply(): RequestLogger.type
  }
}

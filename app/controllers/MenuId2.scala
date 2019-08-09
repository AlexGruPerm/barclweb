package controllers

import app.Global
import javax.inject._
import play.api.Logger
import play.api.mvc._

@Singleton
class MenuId2 @Inject()(cc: ControllerComponents)(implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {
  val log: Logger = Logger(this.getClass())
  val thisCntrlMid :Int = 2
  log.info("Constructor "+this.getClass.getName)

  def midAction = Action {implicit request =>
    log.info(this.getClass.getName+" mid2")
    val uid :String = Global.reqLog.saveLog(request)

    Ok(views.html.mid2("mid"+thisCntrlMid.toString,thisCntrlMid))
      .withSession(request.session + ("mid" -> thisCntrlMid.toString) + ("uid" -> uid))
  }

}


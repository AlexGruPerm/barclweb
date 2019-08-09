package controllers

import app.Global
import javax.inject._
import play.api.Logger
import play.api.mvc._

@Singleton
class MenuId4 @Inject()(cc: ControllerComponents)(implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {
  val log: Logger = Logger(this.getClass())
  val thisCntrlMid :Int = 4
  log.info("Constructor "+this.getClass.getName)

  def midAction = Action {implicit request =>
    log.info(this.getClass.getName+" mid4")
    val uid :String = Global.reqLog.saveLog(request)

    Ok(views.html.mid4("mid"+thisCntrlMid.toString,thisCntrlMid))
      .withSession(request.session + ("mid" -> thisCntrlMid.toString) + ("uid" -> uid))
  }

}


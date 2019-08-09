package controllers

import app.Global
import javax.inject._
import play.api.Logger
import play.api.mvc._

@Singleton
class MenuId3 @Inject()(cc: ControllerComponents)(implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {
  val log: Logger = Logger(this.getClass())
  val thisCntrlMid :Int = 3
  log.info("Constructor "+this.getClass.getName)

  def midAction = Action {implicit request =>
    log.info(this.getClass.getName+" mid3")
    val uid :String = Global.reqLog.saveLog(request)

    Ok(views.html.mid3("mid"+thisCntrlMid.toString,thisCntrlMid))
      .withSession(request.session + ("mid" -> thisCntrlMid.toString) + ("uid" -> uid))
  }

}


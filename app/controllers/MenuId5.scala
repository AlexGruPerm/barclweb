package controllers

import app.Global
import javax.inject._
import play.api.Logger
import play.api.libs.typedmap.TypedMap
import play.api.mvc._

@Singleton
class MenuId5 @Inject()(cc: ControllerComponents)(implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {
  val log: Logger = Logger(this.getClass())
  log.info("Constructor "+this.getClass.getName)

  def midAction = Action {implicit request =>
    log.info(this.getClass.getName+" mid5")
    val newAttrs :TypedMap = Global.reqLog.saveLog(request)
    request.withAttrs(newAttrs)
    Ok(views.html.mid5("mid5",5)).withSession(request.session + ("mid" -> "5"))
  }

}


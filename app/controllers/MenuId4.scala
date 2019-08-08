package controllers

import javax.inject._
import play.api.Logger
import play.api.mvc._

@Singleton
class MenuId4 @Inject()(cc: ControllerComponents)(implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {
  val log: Logger = Logger(this.getClass())
  log.info("Constructor "+this.getClass.getName)

  def midAction = Action {implicit request =>
    log.info(this.getClass.getName+" mid4")
    Ok(views.html.mid4("mid4",4)).withSession(request.session + ("mid" -> "4"))
  }

}


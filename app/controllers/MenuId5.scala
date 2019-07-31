package controllers

import javax.inject._
import play.api.Logger
import play.api.mvc._

@Singleton
class MenuId5 @Inject()(cc: ControllerComponents)(implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {
  val log: Logger = Logger(this.getClass())
  log.info("Constructor "+this.getClass.getName)

  def midAction = Action {
    log.info(this.getClass.getName+" mid5")
    Ok(views.html.mid5("mid5",5))
  }

}

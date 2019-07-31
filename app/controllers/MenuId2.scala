package controllers

import javax.inject._
import play.api.Logger
import play.api.mvc._

@Singleton
class MenuId2 @Inject()(cc: ControllerComponents)(implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {
  val log: Logger = Logger(this.getClass())
  log.info("Constructor "+this.getClass.getName)

  def midAction = Action {
    log.info(this.getClass.getName+" mid2")
    Ok(views.html.mid2("mid2",2))
  }

}


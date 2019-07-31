package controllers

import javax.inject._
import play.api.Logger
import play.api.mvc._

@Singleton
class MenuId3 @Inject()(cc: ControllerComponents)(implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {
  val log: Logger = Logger(this.getClass())
  log.info("Constructor "+this.getClass.getName)

  def midAction = Action {
    log.info(this.getClass.getName+" mid3")
    Ok(views.html.mid3("mid3",3))
  }

}

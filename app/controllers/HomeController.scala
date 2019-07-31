package controllers

import javax.inject._
import play.api.Logger
import play.api.mvc._

@Singleton
class HomeController @Inject()(cc: ControllerComponents) (implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {
  val log: Logger = Logger(this.getClass())
  log.info("Constructor "+this.getClass.getName)

  def index = Action {
    log.info(this.getClass.getName+" index")
    Redirect(routes.MenuId1.midAction())
    /*
    Ok(views.html.index("mid1",1))
    */
  }

}

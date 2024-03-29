package controllers

import app.Global
import javax.inject._
import models.CommonFuncs
import play.api.Logger
import play.api.mvc._

@Singleton
class HomeController @Inject()(cc: ControllerComponents) (implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) with CommonFuncs {
  val log: Logger = Logger(this.getClass())
  log.info("Constructor "+this.getClass.getName)

  def index = Action { implicit request =>
    log.info(this.getClass.getName+" index")
    val uid :String = Global.reqLog.saveLog(request)
    val lastMenuId :Int = request.session.get("mid")
      .map(id => id.toIntOption match {
        case Some(id) => id
      }).getOrElse(0)

    //redirect to last visited menu ID.
    Redirect(
      lastMenuId match {
        case 1 => routes.MenuId1.midAction
        case 2 => routes.MenuId2.midAction
        case 3 => routes.MenuId3.midAction
        case 4 => routes.MenuId4.midAction
        case 5 => routes.MenuId5.midAction
        case _ => routes.MenuId2.midAction
      }
    )
    /*
    Ok(views.html.index("mid1",1))
    */
  }

}

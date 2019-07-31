package controllers

import app.Global
import javax.inject._
import models.Ticker
import play.api.Logger
import play.api.mvc._

@Singleton
class MenuId1 @Inject()(cc: ControllerComponents)(implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {
  val log: Logger = Logger(this.getClass())
  log.info("Constructor "+this.getClass.getName)

  def midAction = Action {
    log.info(this.getClass.getName+" mid1")

    val sess  = Global.sessInstance
    val tickersFrx :Seq[Ticker] = sess.tickersDict

    Ok(views.html.mid1("mid1",1,tickersFrx))
  }

}


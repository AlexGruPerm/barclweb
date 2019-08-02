
package views.html

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import play.api.mvc._
import play.api.data._

object menu extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[Int,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(activeMenuId :Int):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {

def /*4.2*/menuCont/*4.10*/ = {{Seq(
                  (1,"Menu1"),
                  (2,"Menu2"),
                  (3,"Menu3"),
                  (4,"Menu4"),
                  (5,"Menu5")
                 )}};
Seq[Any](_display_(/*2.2*/require(Seq(1,2,3,4,5).contains(activeMenuId),"Incorrect menuId")),format.raw/*2.67*/("""

"""),format.raw/*10.20*/("""

"""),_display_(/*12.2*/for((k,v) <- menuCont.sortBy(elm => elm._1)) yield /*12.46*/ {_display_(Seq[Any](format.raw/*12.48*/("""
  """),_display_(/*13.4*/if(k == activeMenuId)/*13.25*/{_display_(Seq[Any](format.raw/*13.26*/("""
    """),format.raw/*14.5*/("""<div>"""),_display_(/*14.11*/v),format.raw/*14.12*/("""</div>
  """)))}/*15.4*/else/*15.8*/{_display_(Seq[Any](format.raw/*15.9*/("""
    """),format.raw/*16.5*/("""<div><a href="/mid"""),_display_(/*16.24*/k),format.raw/*16.25*/("""">"""),_display_(/*16.28*/v),format.raw/*16.29*/("""</a></div>
  """)))}),format.raw/*17.4*/("""
""")))}),format.raw/*18.2*/("""
"""))
      }
    }
  }

  def render(activeMenuId:Int): play.twirl.api.HtmlFormat.Appendable = apply(activeMenuId)

  def f:((Int) => play.twirl.api.HtmlFormat.Appendable) = (activeMenuId) => apply(activeMenuId)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: 2019-08-02T15:46:53.669
                  SOURCE: /home/gdev/data/home/data/PROJECTS/barclweb/app/views/menu.scala.html
                  HASH: 91d275400a485fad835d9365d4e4ce8b76d04739
                  MATRIX: 725->1|822->90|838->98|1050->22|1135->87|1165->281|1194->284|1254->328|1294->330|1324->334|1354->355|1393->356|1425->361|1458->367|1480->368|1508->378|1520->382|1558->383|1590->388|1636->407|1658->408|1688->411|1710->412|1754->426|1786->428
                  LINES: 21->1|25->4|25->4|32->2|32->2|34->10|36->12|36->12|36->12|37->13|37->13|37->13|38->14|38->14|38->14|39->15|39->15|39->15|40->16|40->16|40->16|40->16|40->16|41->17|42->18
                  -- GENERATED --
              */
          
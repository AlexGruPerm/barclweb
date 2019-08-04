
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
Seq[Any](format.raw/*1.21*/("""
"""),_display_(/*2.2*/require(Seq(1,2,3,4,5).contains(activeMenuId),"Incorrect menuId")),format.raw/*2.67*/("""

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
                  DATE: 2019-08-04T18:21:32.999
                  SOURCE: C:/barclweb/app/views/menu.scala.html
                  HASH: 2711495921cce7a1c892db5f780d4b96b7b99795
                  MATRIX: 725->1|822->93|838->101|1057->20|1085->23|1170->88|1202->290|1233->295|1293->339|1333->341|1364->346|1394->367|1433->368|1466->374|1499->380|1521->381|1550->392|1562->396|1600->397|1633->403|1679->422|1701->423|1731->426|1753->427|1798->442|1831->445
                  LINES: 21->1|25->4|25->4|32->1|33->2|33->2|35->10|37->12|37->12|37->12|38->13|38->13|38->13|39->14|39->14|39->14|40->15|40->15|40->15|41->16|41->16|41->16|41->16|41->16|42->17|43->18
                  -- GENERATED --
              */
          
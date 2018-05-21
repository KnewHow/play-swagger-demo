package controllers

import javax.inject._
import play.api.mvc._
import play.swagger.api._
import controllers.example._
import play.api.libs.json._
import org.joda.time._

@Singleton
class ApiController @Inject()(cc: ControllerComponents) (implicit assetsFinder: AssetsFinder)
    extends AbstractController(cc) {


  def api = Action {
    val apis = List(
      scoreApi(),
      bookApi()
    ).mkString("[", ",", "]")
    println(s"${DateTime.now()} ->\n ${apis}")
    Ok(apis)
  }

  private def scoreApi():String = {
    import controllers.score._
    val apis = List(
      getControllerJson("scoreInvite", PlaySwagger.playApi[ScoreInviteApi]().mkString("[", ",", "]")),
      getControllerJson("score", PlaySwagger.playApi[ScoreApi]().mkString("[", ",", "]"))
    ).mkString("[", ",", "]")

    "{" + "\"" + "score" +  "\"" + ":" + apis + "}"
  }

  private def bookApi():String = {
    import controllers.book._
    val apis = List(
      getControllerJson("book",  PlaySwagger.playApi[BookApi]().mkString("[", ",", "]")),
      getControllerJson("bookBorrow",  PlaySwagger.playApi[BorrowBookApi]().mkString("[", ",", "]"))
    ).mkString("[", ",", "]")

    "{" + "\"" + "book" +  "\"" + ":" + apis + "}"
  }

   private def getControllerJson(name: String, json:String):String = {
    "{" + "\"" + name  +  "\"" + ":" + json + "}"
  }
}

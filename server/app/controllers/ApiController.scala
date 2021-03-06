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
    import controllers.person._
    PersonApi.getApi
    val apis = List(
      scoreApi,
      bookApi,
      residentApi
    ).mkString("[", ",", "]")
    println(s"${DateTime.now()} ->\n ${apis}")
    Ok(apis)
  }

  private def scoreApi:String = {
    import controllers.score._
    val apis = List(
      getControllerJson("scoreInvite", PlaySwagger.playApi[ScoreInviteApi]().mkString("[", ",", "]")),
      getControllerJson("score", PlaySwagger.playApi[ScoreApi]().mkString("[", ",", "]"))
    ).mkString("[", ",", "]")

    "{" + "\"" + "score" +  "\"" + ":" + apis + "}"
  }

  private def bookApi:String = {
    import controllers.book._
    val apis = List(
      getControllerJson("book",  PlaySwagger.playApi[BookApi]().mkString("[", ",", "]")),
      getControllerJson("bookBorrow",  PlaySwagger.playApi[BorrowBookApi]().mkString("[", ",", "]"))
    ).mkString("[", ",", "]")

    "{" + "\"" + "book" +  "\"" + ":" + apis + "}"
  }

  private def residentApi:String = {
    import controllers.resident._
    val apis = List(
       getControllerJson("居民信息获取",  PlaySwagger.playApi[ResidentApi]().mkString("[", ",", "]"))
    ).mkString("[", ",", "]")
    "{" + "\"" + "居民信息接口" +  "\"" + ":" + apis + "}"
  }

  // private def personApi:String = {
  //   import controllers.person._
  //   val apis = List(
  //     getControllerJson("person",PersonApi.getApi)
  //   ).mkString("[", ",", "]")

  //   "{" + "\"" + "person" +  "\"" + ":" + apis + "}"
  // }

   private def getControllerJson(name: String, json:String):String = {
    "{" + "\"" + name  +  "\"" + ":" + json + "}"
  }
}

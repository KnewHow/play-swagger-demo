package controllers.api

import javax.inject._

import play.api.mvc._
import swagger.api._
import controllers._
import play.api.libs.json._

@Singleton
class ApiController @Inject()(cc: ControllerComponents) (implicit assetsFinder: AssetsFinder)
    extends AbstractController(cc) {


  def api = Action {
    val apis = PlaySwagger.playApi[ExampleController]() ++ PlaySwagger.playApi[ExampleController2]()
    Ok(apis.mkString("[", ",", "]"))
  }
}

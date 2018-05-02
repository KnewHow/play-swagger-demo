package myRoute

import javax.inject._
import controllers._
import play.api.mvc._
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class MyRoute @Inject()(controller:ExampleController) extends SimpleRouter {
  override def routes: Routes = {
    case GET(p"/lala") => controller.exampleGetAction
  }
}

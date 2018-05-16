package myRoute

import play.api.mvc._
import com.dripower.play.swa._
import play.swagger.api.PlaySwagger
import controllers.example._
import controllers._
import javax.inject.{ Inject, Provider, Singleton }
import play.api.ApplicationLoader
import play.api.http.HttpConfiguration
import play.api.inject._
import play.api.inject.guice.{ GuiceApplicationLoader, GuiceableModule }
import play.api.mvc._
import play.api.routing.Router.Routes
import play.api.routing.sird._
import play.api.routing.{ Router, SimpleRouter }
import controllers.score._
import controllers.book._



class MyRoute @Inject()(ba: BookApi, bba: BorrowBookApi, sa: ScoreApi, sia: ScoreInviteApi) extends SimpleRouter {
  val rs = PlaySwagger.routes[BookApi](ba) ++  PlaySwagger.routes[BorrowBookApi](bba) ++  PlaySwagger.routes[ScoreApi](sa) ++  PlaySwagger.routes[ScoreInviteApi](sia)

  override def routes: Routes = {
    case r =>{
      getAction(r.path)
    }

  }

  private def getAction(path:String) = {
    val p = path.substring(1,path.length)
    import shapeless._
    rs.get(p) match {
      case Some(r) => {
        matchAction(r(HNil))
      }
    }
  }

  private def matchAction(action:Any):SwaAction[_,_] = {
    action match {
      case m: SwaAction[_, _] => m
    }
  }

  private def getMethod(c:HomeController) = {
    import scala.reflect.runtime.{universe => ru}
    val rm =  ru.runtimeMirror(getClass.getClassLoader).reflect(c)
    val m = rm.symbol.typeSignature.member(ru.newTermName("index"))

    rm.reflectMethod(m.asMethod)() match {
      case m: SwaAction[_, _] => m
    }

  }
}

@Singleton
class ScalaRoutesProvider @Inject()(playSimpleRouter: MyRoute, httpConfig: HttpConfiguration) extends Provider[Router] {

  lazy val get = playSimpleRouter.withPrefix(httpConfig.context)

}

class ScalaGuiceAppLoader extends GuiceApplicationLoader {

  protected override def overrides(context: ApplicationLoader.Context): Seq[GuiceableModule] = {
    super.overrides(context) :+ (bind[Router].toProvider[ScalaRoutesProvider]: GuiceableModule)
  }

}

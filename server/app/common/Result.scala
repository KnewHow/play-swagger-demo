package common.results
import play.swagger.response._

object CommonResults {
  def successful[T](t: T):ResBody[T] = {
    ResBody[T](200,t)
  }

   def failure[T](t: T):ResBody[T] = {
    ResBody[T](400,t)
  }
}

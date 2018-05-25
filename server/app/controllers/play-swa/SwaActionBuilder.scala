package com.dripower.play.swa

import play.api.mvc._
import play.api.http.Writeable
import scala.concurrent._
import dripower.validate._
import play.api.libs.json.Json

trait SwaAction[A, R] extends Action[A]

trait PostSwaAction[A,R] extends SwaAction[A,R]
trait GetSwaAction[A,R] extends SwaAction[A,R]

class SwaActionBuilder[Req[_], A] private(
  builder: ActionBuilder[Req, A]) {

  def async[A1, Res](_parser: BodyParser[A1])(body : Req[A1] => Future[Res])(
    implicit _writeable: Writeable[Res],
    _ec: ExecutionContext): SwaAction[A1, Res] = new SwaAction[A1, Res] {
    def executionContext = _ec
    def parser = _parser
    def apply(request: Request[A1]) = {
      builder.apply(parser).invokeBlock(request, (ra: Req[A1]) => body(ra).map(Results.Ok(_)))
    }
  }

  def asyncPost[A1, Res](_parser: BodyParser[A1])(body : Req[A1] => Future[Res])(
    implicit _writeable: Writeable[Res],
    _ec: ExecutionContext): PostSwaAction[A1, Res] = new PostSwaAction[A1, Res] {
    def executionContext = _ec
    def parser = _parser
    def apply(request: Request[A1]) = {
      def validateAccess = {
        builder.apply(parser).invokeBlock(request, (ra: Req[A1]) => body(ra).map(Results.Ok(_)))
      }

      def validateFailure(code: Int, msg: String) = {
        Results.Ok(
          Json.obj(
            "errcode" -> Json.toJson(code),
            "errmsg"  -> Json.toJson(msg)
          )
        )
      }
      val bg = request.body
      println(s"requestlala -> ${bg}")
      bg match {
        case b: BaseValidate  => {
          println("match successful")
          val rs = b.validator
          if(rs.nonEmpty) {
            println("validate failure")
            Future.successful(validateFailure(400,rs(0).left.get))
          } else {
            println("validate success")
            validateAccess
          }
        }
        case _ => {
          println("match failure")
          validateAccess
        }
      }

    }
  }

  def asyncGet[A1, Res](_parser: BodyParser[A1])(body : Req[A1] => Future[Res])(
    implicit _writeable: Writeable[Res],
    _ec: ExecutionContext): GetSwaAction[A1, Res] = new GetSwaAction[A1, Res] {
    def executionContext = _ec
    def parser = _parser
    def apply(request: Request[A1]) = {
      builder.apply(parser).invokeBlock(request, (ra: Req[A1]) => body(ra).map(Results.Ok(_)))
    }
  }


  def async[Res](body: Req[A] => Future[Res])(implicit _writeable: Writeable[Res],
    _ec: ExecutionContext): SwaAction[A, Res] = async(builder.parser)(body)
}

object SwaActionBuilder {
  def apply[Req[_], A, Res](builder: ActionBuilder[Req, A]) = {
    new SwaActionBuilder(builder)
  }
}

package controllers.score

import akka.util.ByteString
import play.api.mvc._
import javax.inject._
import play.api.libs.json._
import play.api.http.Writeable
import scala.concurrent._
import com.dripower.play.swa._
import org.joda.time._
import play.swagger.annotation._
import scala.annotation.meta
import play.api.libs.json.Json

case class ScoreGet(
  @(FieldAnnotation @meta.getter)(descrip="用户 openid")
    openid: String,
  @(FieldAnnotation @meta.getter)(descrip="公众号的微信 id")
    weixinId: String
)

case class Score(
  @(FieldAnnotation @meta.getter)(descrip="用户的 openid")
    openid: String,
  @(FieldAnnotation @meta.getter)(descrip="公众号的微信 id")
    weixinId: String,
  @(FieldAnnotation @meta.getter)(descrip="用户当前拥有积分")
    score: Long,
  @(FieldAnnotation @meta.getter)(descrip="积分记录创建时间")
    gmtCreate: DateTime,
  @(FieldAnnotation @meta.getter)(descrip="积分记录修改时间")
    gmtModified: DateTime
)

case class ScoreUpdateForm(
  @(FieldAnnotation @meta.getter)(descrip="用户的 openid")
    openid: String,
  @(FieldAnnotation @meta.getter)(descrip="公众号的微信 id")
    weixinId: String,
  @(FieldAnnotation @meta.getter)(descrip="积分增减数量，正数表示加积分，负数表示减积分")
  score: Long
)

object ScoreGet {
  implicit val format = Json.format[ScoreGet]
}
object ScoreUpdateForm {
  implicit val format = Json.format[ScoreUpdateForm]
}
object Score {
  implicit val scoreWriteable = Writeable((s: Score) =>
    ByteString(s.toString), Some("text/plain")
  )
}

@Singleton
class ScoreApi @Inject() (val controllerComponents: ControllerComponents) (implicit ec: ExecutionContext) extends BaseController {

  val Swa = SwaActionBuilder(Action)

  // @ActionAnnotation(descrip="查询用户积分")
  // def queryScore: PostSwaAction [ScoreGet, Score] = Swa.asyncPost[ScoreGet,Score](parse.json[ScoreGet]) { req =>
  //   val scoreGet = req.body
  //   Future.successful {
  //     Score(
  //       scoreGet.openid,
  //       scoreGet.weixinId,
  //       1234L,
  //       DateTime.now(),
  //       DateTime.now()
  //     )
  //   }
  // }



  // @ActionAnnotation(descrip="更新用户积分")
  // def updateScore: PostSwaAction[ScoreUpdateForm, Score] = Swa.asyncPost[ScoreUpdateForm, Score](parse.json[ScoreUpdateForm]) { req =>
  //   val suf = req.body
  //   Future.successful {
  //     Score(
  //       suf.openid,
  //       suf.weixinId,
  //       1234L,
  //       DateTime.now(),
  //       DateTime.now()
  //     )
  //   }
  // }
}

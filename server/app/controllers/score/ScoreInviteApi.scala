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

case class ScoreInviteRecord(
  @(FieldAnnotation @meta.getter)(descrip="公众号的微信 id")
    weixinId: String,
  @(FieldAnnotation @meta.getter)(descrip="邀请人的 openid")
    inviteOpenid: String,
  @(FieldAnnotation @meta.getter)(descrip="被邀请人的 openid")
    invitedOpenid: String,
  @(FieldAnnotation @meta.getter)(descrip="邀请规则 id")
    inviteRuleId: Long,
  @(FieldAnnotation @meta.getter)(descrip="积分记录创建时间")
    gmtCreate: DateTime,
  @(FieldAnnotation @meta.getter)(descrip="积分记录修改时间")
    gmtModified: DateTime
)
case class ScoreInviteQueryFrom(
  @(FieldAnnotation @meta.getter)(descrip="公众号的微信 id")
    weixinId: String,
  @(FieldAnnotation @meta.getter)(descrip="邀请人 openid")
    openid: String,
)

case class ScoreInviteQueryResults(
  @(FieldAnnotation @meta.getter)(descrip="积分邀请查询结果")
    results: List[ScoreInviteRecord]
)

case class AddInviteRecord(
   @(FieldAnnotation @meta.getter)(descrip="公众号的微信 id")
     weixinId: String,
  @(FieldAnnotation @meta.getter)(descrip="邀请人的 openid")
    inviteOpenid: String,
  @(FieldAnnotation @meta.getter)(descrip="被邀请人的 openid")
    invitedOpenid: String,
)

object ScoreInviteQueryFrom {
  implicit val format = Json.format[ScoreInviteQueryFrom]
}

object AddInviteRecord {
  implicit val format = Json.format[AddInviteRecord]
}

object ScoreInviteRecord {
  implicit val scoreInviteWriteable = Writeable((si: ScoreInviteRecord) =>
    ByteString(si.toString), Some("text/plain")
  )
}
object ScoreInviteQueryResults {
   implicit val scoreInviteWriteable = Writeable((sr: ScoreInviteQueryResults) =>
    ByteString(sr.toString), Some("text/plain")
  )
}

@Singleton
class ScoreInviteApi @Inject() (val controllerComponents: ControllerComponents) (implicit ec: ExecutionContext) extends BaseController {

  val Swa =  SwaActionBuilder(Action)

  // @ActionAnnotation(descrip="查询用户积分邀请记录")
  // def queryScoreInviteRecords: PostSwaAction[ScoreInviteQueryFrom, ScoreInviteRecord] = Swa.asyncPost[ScoreInviteQueryFrom, ScoreInviteRecord](parse.json[ScoreInviteQueryFrom]) { req =>
  //   val sqr = req.body
  //   val rs =
  //       ScoreInviteRecord(
  //         sqr.weixinId,
  //         sqr.openid,
  //         "lisi",
  //         123L,
  //         DateTime.now(),
  //         DateTime.now()
  //       )
  //   Future.successful {
  //     rs
  //   }
  // }

  //  @ActionAnnotation(descrip="添加用户积分邀请记录")
  // def addInviteRecord: PostSwaAction[AddInviteRecord, ScoreInviteRecord] = Swa.asyncPost[AddInviteRecord,ScoreInviteRecord](parse.json[AddInviteRecord]) {req =>
  //   var air = req.body
  //   Future.successful {
  //     ScoreInviteRecord(
  //       air.weixinId,
  //       air.inviteOpenid,
  //       air.invitedOpenid,
  //       123L,
  //       DateTime.now(),
  //       DateTime.now()
  //     )
  //   }
  // }
}

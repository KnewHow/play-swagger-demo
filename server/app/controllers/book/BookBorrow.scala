package controllers.book

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
import play.swagger.response._
import common._


case class BorrowRecord(
  @(FieldAnnotation @meta.getter)(descrip="借书记录 id")
    id:String,
  @(FieldAnnotation @meta.getter)(descrip="书籍的id")
    bid: String,
  @(FieldAnnotation @meta.getter)(descrip="学生的id")
    sid: String,
  @(FieldAnnotation @meta.getter)(descrip="借书状态，true 已还 false 未还")
    status: Boolean,
  @(FieldAnnotation @meta.getter)(descrip="过期时间")
    expiredTime: DateTime,
  @(FieldAnnotation @meta.getter)(descrip="创建时间")
    gmtCreate: DateTime,
  @(FieldAnnotation @meta.getter)(descrip="修改时间")
    gmtModified: DateTime
)

case class CreateBorrowRecord(
  @(FieldAnnotation @meta.getter)(descrip="书籍的id")
    bid: String,
  @(FieldAnnotation @meta.getter)(descrip="学生的id")
    sid: String,
)

case class ReturnBook(
   @(FieldAnnotation @meta.getter)(descrip="书籍的id")
      bid: String,
  @(FieldAnnotation @meta.getter)(descrip="学生的id")
    sid: String,
)

object CreateBorrowRecord {
  implicit val format = Json.format[CreateBorrowRecord]
}

object ReturnBook {
  implicit val format = Json.format[ReturnBook]
}

object BorrowRecord {
  implicit val BorrowRecordWrites = new Writes[BorrowRecord] {
    def writes(b: BorrowRecord) = Json.obj(
      "id" -> b.id,
      "bid" -> b.bid,
      "sid" -> b.sid,
      "status" -> b.status,
      "expiredTime" -> b.expiredTime.toString,
      "gmtCreate" -> b.gmtCreate.toString,
      "gmtModified" -> b.gmtModified.toString
    )
  }

  implicit val bookBorrowWriteable = Writeable((r: ResBody[BorrowRecord]) =>
    ByteString(
      Json.stringify(
        Json.obj(
          "code" -> r.code,
          "data" -> Json.obj(
            JsonTool.getCaseClassName(r.data) -> Json.toJson(r.data)
          )
        )
      )
    ), Some("application/json")
  )
}

class BorrowBookApi @Inject() (val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext) extends BaseController {

  val Swa = SwaActionBuilder(Action)


  @ActionAnnotation(descrip="还书接口")
  def returnBook: PostSwaAction[ReturnBook, ResBody[BorrowRecord]] = Swa.asyncPost[ReturnBook,  ResBody[BorrowRecord]](parse.json[ReturnBook]) {req =>
    val rb = req.body
    val r = ResBody[BorrowRecord](
      200,
      BorrowRecord(
        "1",
        rb.bid,
        rb.sid,
        true,
        DateTime.now(),
        DateTime.now(),
        DateTime.now()
      )
    )
    Future.successful {
      r
    }
  }
}

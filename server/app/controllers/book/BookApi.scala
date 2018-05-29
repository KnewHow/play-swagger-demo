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
import dripower.validate._
import scala.collection.immutable.List
import scala.util._

case class GetBook(
   @(FieldAnnotation @meta.getter)(descrip="书籍的 id")
     id: String
) extends BaseValidate {
  override def validator = {
    List(Validator.maxLength(id, 4, Option("书籍的ID长度最多不得超过4为"))).filter(r => r.isLeft)
  }
}

case class Book (
   @(FieldAnnotation @meta.getter)(descrip="书籍的 id")
     id: String,
   @(FieldAnnotation @meta.getter)(descrip="书籍的名称")
     name: String,
   @(FieldAnnotation @meta.getter)(descrip="书籍的出版社")
     publish: String,
  @(FieldAnnotation @meta.getter)(descrip="书籍的作者")
    author: String
)

case class AddBookForm(
  @(FieldAnnotation @meta.getter)(descrip="书籍的名称")
     name: String,
  @(FieldAnnotation @meta.getter)(descrip="书籍的出版社")
     publish: String,
  @(FieldAnnotation @meta.getter)(descrip="书籍的作者")
    author: String
) extends BaseValidate {
  override def validator = {
    List(
      validateAuthor,
      Validator.maxLength(publish, 10)
    ).filter(r => r.isLeft)
  }

  private def validateAuthor:Either[String, Boolean] = {
    if (author.contains("ygh")) {
      Right(true)
    } else {
      Left("the author must be ygh")
    }
  }
}

object GetBook {
  implicit val format = Json.format[GetBook]
}

object AddBookForm {
  implicit val format = Json.format[AddBookForm]
}

object Book {
  implicit val bookWrites = Json.writes[Book]

  implicit val bookWriteable =  Writeable((b: Book) =>
    ByteString(
      Json.stringify(
        Json.obj(
          "code" -> 200,
          "data" -> Json.toJson(b)
        )
       )
    ), Some("application/json")
  )
}

class BookApi @Inject() (val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext) extends BaseController {

  val Swa =  SwaActionBuilder(Action)

  @ActionAnnotation(descrip="根据图书id查询图书信息")
  def queryBook:PostSwaAction[GetBook, Book] = Swa.asyncPost[GetBook, Book](parse.json[GetBook]) {req =>
    val bg = req.body
    Future.successful{
      Book(
        bg.id,
        "dive into kotlin",
        "dripower",
        "机械工业出版社"
      )
    }
  }

  @ActionAnnotation(descrip="添加图书信息")
  def addBook: PostSwaAction[AddBookForm, Book] = Swa.asyncPost[AddBookForm, Book](parse.json[AddBookForm]) {req =>
    val abf = req.body
    Future.successful {
       Book(
         "aodcbjasbxsdbaj",
         abf.name,
         abf.publish,
         abf.author
      )
    }
  }
}

object BookApi {
  def getApi = {
    import play.swagger.api._
     PlaySwagger.playApi[BookApi]().mkString("[", ",", "]")
  }
}

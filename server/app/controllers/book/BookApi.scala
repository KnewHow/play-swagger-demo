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

case class GetBook(
   @(FieldAnnotation @meta.getter)(descrip="书籍的 id")
     id: String
)

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
)

object GetBook {
  implicit val format = Json.format[GetBook]
}

object AddBookForm {
  implicit val format = Json.format[AddBookForm]
}

object Book {
  implicit val bookWriteable = Writeable((b: Book) =>
    ByteString(b.toString), Some("text/plain")
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

package controllers.example

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

case class Car(
  @(FieldAnnotation @meta.getter)(descrip="汽车ID")
    id:Long,
  @(FieldAnnotation @meta.getter)(descrip="汽车标志")
    logo:String
)
case class PersonGet(
  @(FieldAnnotation @meta.getter)(descrip="人的ID")
  id: Long
)
case class Person(
  @(FieldAnnotation @meta.getter)(descrip="人的ID")
    id: Long,
   @(FieldAnnotation @meta.getter)(descrip="人的名字")
     name: String,
  @(FieldAnnotation @meta.getter)(descrip="人的年龄")
    age: Int,
  @(FieldAnnotation @meta.getter)(descrip="人所属的汽车")
    car: Car,
  @(FieldAnnotation @meta.getter)(descrip="测试时间戳")
    level1ExpiredAt: Option[DateTime] = None,
  @(FieldAnnotation @meta.getter)(descrip="人的头像")
    avatar: Option[String] = None,
  @(FieldAnnotation @meta.getter)(descrip="创建时间")
    gmtCreate:DateTime = DateTime.now(),
  @(FieldAnnotation @meta.getter)(descrip="修改时间")
     gmtModified:DateTime = DateTime.now(),
  @(FieldAnnotation @meta.getter)(descrip="所属的朋友")
    friends:List[Friend] = List()
)
case class Friend(
  @(FieldAnnotation @meta.getter)(descrip="朋友的ID")
    id:Long,
  @(FieldAnnotation @meta.getter)(descrip="朋友的名称")
    name:String,
  @(FieldAnnotation @meta.getter)(descrip="关系")
    relation:String
)
case class Ids(ids:List[Long],
  op:Option[String]
)

object PersonGet {
  implicit val format = Json.format[PersonGet]
}

object Ids {
  implicit val format = Json.format[Ids]
}


object Person {
  implicit val personWriteable = Writeable((p: Person) => ByteString(p.toString), Some("text/plain"))

  implicit val personListWriteable = Writeable((ps: List[Person]) => ByteString(ps.toString), Some("text/plain"))
}

@Singleton
class ExampleController  @Inject() (val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext) extends BaseController {

  val Swa = SwaActionBuilder(Action)

  @ActionAnnotation(descrip="不是正式的请求")
  def noFormatAction:PostSwaAction [PersonGet, Person] = Swa.asyncPost[PersonGet,Person](parse.json[PersonGet]) { req =>
    val personGet = req.body
    Future.successful(Person(personGet.id, "foo", 1,Car(1L,"奔驰")))
  }

  @ActionAnnotation(descrip="测试 POST 请求")
  def examplePostAction:PostSwaAction [PersonGet, Person] = Swa.asyncPost[PersonGet,Person](parse.json[PersonGet]) { req =>
    val personGet = req.body
    Future.successful(Person(personGet.id, "foo", 1,Car(1L,"奔驰")))
  }


   @ActionAnnotation(descrip="测试 GET 请求")
   def exampleGetAction:GetSwaAction[PersonGet, List[Person]] = Swa.asyncGet[PersonGet,List[Person]](parse.json[PersonGet]) { req =>
    val personGet = req.body
    Future.successful(List(Person(personGet.id, "foo", 1,Car(1L,"奔驰"))))
   }

  // def getPersonLists:GetSwaAction[Ids,List[Person]] = Swa.asyncGet[Ids,List[Person]](parse.json[Ids]) { req =>
  //   val ids = req.body.ids
  //   import scala.collection.mutable.ListBuffer
  //   val results = new ListBuffer[Person]()
  //   for(id <- ids) {
  //     results += (Person(id, "foo", 1,Car(1L,"奔驰")))
  //   }
  //   Future.successful(results.toList)
  // }
}

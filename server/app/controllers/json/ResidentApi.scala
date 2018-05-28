package controllers.resident

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

case class ResidentGet(name: String)
case class Resident(name: String, age: Int, role: Option[String])

object ResidentGet {
  implicit val format = Json.format[ResidentGet]
}
object Resident {

  // implicit val residentWrites = new Writes[Resident] {
  //   def writes(resident: Resident) = Json.obj(
  //     "name" -> resident.name,
  //     "age" -> resident.age,
  //     "role" -> resident.role
  //   )
  // }

  implicit val residentWrites = Json.writes[Resident]

  implicit val residentWriteable = Writeable((r: Resident) =>
    ByteString(
      Json.stringify(Json.toJson(r))
    ), Some("text/plain")
  )
  // implicit val residentWriteable = Json.writes[Resident]
}
class ResidentApi @Inject() (val controllerComponents : ControllerComponents)(implicit ec: ExecutionContext) extends BaseController {
  val Swa = SwaActionBuilder(Action)

  @ActionAnnotation("获取居民信息")
  def get:PostSwaAction[ResidentGet, Resident] = Swa.asyncPost[ResidentGet, Resident](parse.json[ResidentGet]) {req =>
    val rg = req.body
    val r = Resident(rg.name, 12, Option("teacher"))
    Future.successful(
      r
    )
  }
}

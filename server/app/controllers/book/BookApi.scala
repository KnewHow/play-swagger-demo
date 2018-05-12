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

case class Book

package common

object JsonTool {
  def getCaseClassName[T](t: T): String = {
    val allName = t.getClass.getName
    val s = allName.split("\\.").last
    s.substring(0,1).toLowerCase + s.substring(1, s.length)
  }
}

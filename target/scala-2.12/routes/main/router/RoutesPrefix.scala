// @GENERATOR:play-routes-compiler
// @SOURCE:/home/ygh/project/play-swa-demo/conf/routes
// @DATE:Mon Apr 30 15:38:40 CST 2018


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}

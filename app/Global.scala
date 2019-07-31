package app

import models.{CassConnectException, CassSessionInstance}
import org.slf4j.LoggerFactory

object Global {
  val log = LoggerFactory.getLogger(getClass.getName)
  val sessInstance :CassSessionInstance.type  =
    try {
      CassSessionInstance
    } catch {
      case e: com.datastax.oss.driver.api.core.servererrors.SyntaxError => {
        log.error("CassSessionInstance[0] EXCEPTION SyntaxError msg="+e.getMessage+" cause="+e.getCause)
        throw e
      }
      case e: CassConnectException => {
        log.error("CassSessionInstance[1] EXCEPTION CassConnectException msg="+e.getMessage+" cause="+e.getCause)
        throw e
      }
      case e : com.datastax.oss.driver.api.core.DriverTimeoutException =>
        log.error("CassSessionInstance[2] EXCEPTION DriverTimeoutException msg="+e.getMessage+" cause="+e.getCause)
        throw e
      case e : java.lang.ExceptionInInitializerError =>
        log.error("CassSessionInstance[3] EXCEPTION ExceptionInInitializerError msg="+e.getMessage+" cause="+e.getCause)
        throw e
      case e: Throwable =>
        log.error("CassSessionInstance[4] EXCEPTION Throwable msg="+e.getMessage+" cause="+e.getCause)
        throw e
    }
  require(!sessInstance.sess.isClosed, "Cassandra session must be opened.")

}

import cats.effect._
import cats.syntax.all._
import com.comcast.ip4s._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.ember.server._
import org.http4s.implicits._
import org.http4s.server.Router

import scala.concurrent.duration._

/** http4sを使ったテストサーバ。http://localhost:8080/hello/world にアクセスするとメッセージが返るだけのサーバ。
  * ~reStart をsbt上で実行すると、コード修正時にホットリロードしてくれるようになる。
  */
object Main extends IOApp {
  val helloWorldService = HttpRoutes
    .of[IO] { case GET -> Root / "hello" / name =>
      Ok(s"Hello, $name...") // ここを試しに書き換えてみると、高速にリロードされるはず
    }
    .orNotFound

  def run(args: List[String]): IO[ExitCode] =
    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(helloWorldService)
      // デフォルトだと30秒待ってしまうので速攻で終了させる。
      // 開発環境では即座に、本番環境では長めに待つといった構成をすると丁寧
      .withShutdownTimeout(Duration(1, "second"))
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)
}

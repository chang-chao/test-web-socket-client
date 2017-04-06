package chao.demo.websocket.client;

import org.apache.commons.lang3.time.StopWatch;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket(maxTextMessageSize = 64 * 1024)
public class WebsocketClientEndpoint {
  Session userSession = null;
  private StopWatch stopWatch = StopWatch.createStarted();
  private static int nextClientId = 0;
  private int clientId = nextClientId++;

  @OnWebSocketConnect
  public void onOpen(Session userSession) {
    stopWatch.stop();
    long time = stopWatch.getTime();
    System.out.println("#" + clientId + "\t" + "OK\t" + time);
    this.userSession = userSession;
  }

  @OnWebSocketClose
  public void onClose(Session userSession, int statusCode, String reason) {
    this.userSession = null;
  }

  @OnWebSocketError
  public void onError(Session session, Throwable thr) {
    stopWatch.stop();
    long time = stopWatch.getTime();
    System.out.println("#" + clientId + "\t" + "NG\t" + time + "\t" + thr.getMessage());
  }

}
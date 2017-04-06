package chao.demo.websocket.client;

import java.net.URI;
import java.util.concurrent.Future;

import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

public class WebSocketClientApp {

  public static void main(String[] args) {
    try {
      SslContextFactory sslContextFactory = new SslContextFactory();
      sslContextFactory.setTrustAll(true); // The magic
      // String prfix = "ws://localhost:8080/api";
      String prefix = "wss://192.168.1.203:8443/api-pi";

      String devId = "03212660-06AC-4CB2-A5E9-44F81BEF24BE";
      String token = "8c56ff00-216b-40b8-8935-591e2ce701d7";
      for (int i = 0; i < 100; i++) {

        WebSocketClient client = new WebSocketClient(sslContextFactory);
        client.start();
        connectAndClose(client, devId, token, prefix);
        try {
          client.stop();
        } catch (Exception e) {
          e.printStackTrace();
        }

        Thread.sleep(1000);
      }

    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
    }
  }

  private static void connectAndClose(WebSocketClient client, String devId, String token,
      String prfix) {
    try {
      String destUri = prfix + "/fm/contact/exchange?devId=" + devId + "&token=" + token;

      URI wsUri = new URI(destUri);
      ClientUpgradeRequest request = new ClientUpgradeRequest();
      WebsocketClientEndpoint socket = new WebsocketClientEndpoint();
      Future<Session> session = client.connect(socket, wsUri, request);
      Session s2 = session.get();
      s2.close();

    } catch (Exception e) {
    }
  }
}
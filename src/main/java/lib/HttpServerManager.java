package lib;

import com.sun.net.httpserver.HttpServer;
import org.openqa.selenium.net.PortProber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Kateryna
 * Class creates instance of http server on localhost
 **/
public class HttpServerManager {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(HttpServerManager.class);


    private HttpServer server;
    private String serverUrl;



    public   HttpServerManager() throws IOException {


        Path path1 = Paths.get("src/test/resources/login.html");
        byte[] bytes1 = Files.readAllBytes(path1);

        var port = PortProber.findFreePort();
        server= HttpServer.create(new InetSocketAddress(port), 0);
        server.setExecutor(null);
        server.createContext("/", httpExchange -> {
            httpExchange.sendResponseHeaders(200, 0);
            try (OutputStream out = httpExchange.getResponseBody()) {
                out.write(bytes1);
                out.flush();
                httpExchange.close();
            }
        });
        server.start();
        serverUrl = String.format("http://localhost:%d", port);
        LOGGER.info("Server is started on "+String.format("http://localhost:%d", port));

    }

    public HttpServer getServer() {
        return server;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void stopServer(){
        server.stop(0);
        LOGGER.info("Server is stopped");

    }

}

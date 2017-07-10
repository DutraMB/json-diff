/*
 * The MIT License
 *
 * Copyright 2017 Marcio Branquinho Dutra <mdutra at gmail dot com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package dutramb.jsondiff;

import dutramb.jsondiff.log.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 * The {@code Main} class is responsible to assemble 
 * the service URI, based on some environment variables and
 * start the HTTP Server.
 * 
 * @author Marcio Branquinho Dutra
 */
public class Main {

    public static final Optional<String> host;
    public static final Optional<String> port;
    public static final String BASE_URI;

    static {
        host = Optional.ofNullable(System.getenv("JSONDIFF_HOSTNAME"));
        port = Optional.ofNullable(System.getenv("JSONDIFF_PORT"));
        BASE_URI = "http://" + host.orElse("localhost") + ":" + port.orElse("8080") + "/json-diff/";
    }

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources.
     *
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        HttpServer server = null;
        try {
            // create a resource config that scans for JAX-RS resources and providers
            // in dutramb.jsondiff package
            final ResourceConfig rc = new ResourceConfig().packages("dutramb.jsondiff");

            rc.register(JacksonFeature.class);
            // create and start a new instance of grizzly http server
            // exposing the Jersey application at BASE_URI
            server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

            Logger.info(String.format("JSON Diff app is available at %s... \nPress Ctrl+C to stop the service.", BASE_URI), Main.class);

        } catch (Exception e) {
            Logger.severe("HttpServer was not created. Check json-diff environment variables.", Main.class, e, "BASE_URI", BASE_URI);
        }
        return server;
    }

    /**
     * Everything starts here!
     *
     * @param args No args are expected.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        startServer();
    }
}

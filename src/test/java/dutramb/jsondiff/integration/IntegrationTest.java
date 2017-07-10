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
package dutramb.jsondiff.integration;

import dutramb.jsondiff.Main;
import dutramb.jsondiff.model.Diff;
import dutramb.jsondiff.model.Input;
import dutramb.jsondiff.model.Output;
import dutramb.jsondiff.model.type.Result;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Provides integration tests.
 * 
 * @author Marcio Branquinho Dutra
 */
public class IntegrationTest {

    private HttpServer server;
    private WebTarget target;

    /**
     * Starts up the http server before any action to perform integration tests.
     */
    @Before
    public void startup() throws Exception {
        server = Main.startServer();
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);
    }

    /**
     * Shutdowns the http server after any integration tests have completed.
     */
    @After
    public void shutdown() throws Exception {
        server.shutdownNow();
    }

    /**
     * Tests left endpoint request.
     */
    @Test
    public void testInsertLeft() {
        Integer id = 1;
        Response output = this.target.path("/v1/diff/" + id + "/left").request().post(Entity.json(new Input(id, "YWMvZGMhIQ==")));

        assertEquals("Insert left wrong return", Response.Status.CREATED.getStatusCode(), output.getStatus());
    }

    /**
     * Tests right endpoint request.
     */
    @Test
    public void testInsertRight() {
        Integer id = 1;
        Response output = this.target.path("/v1/diff/" + id + "/right").request().post(Entity.json(new Input(id, "YWMvZGMhIQ==")));

        assertEquals("Insert right wrong return", Response.Status.CREATED.getStatusCode(), output.getStatus());
    }

    /**
     * Tests the whole process for equal inputs.
     * The whole process comprehends the insertion on both sides and the request by the result.
     */
    @Test
    public void testEqualResult() {
        Integer id = 1;

        Response responseLeft = this.target.path("/v1/diff/" + id + "/left").request().post(Entity.json(new Input(id, "YWMvZGMhIQ==")));
        assertEquals("Insert Left wrong return", Response.Status.CREATED.getStatusCode(), responseLeft.getStatus());

        Response responseRight = this.target.path("/v1/diff/" + id + "/right").request().post(Entity.json(new Input(id, "YWMvZGMhIQ==")));
        assertEquals("Insert Right wrong return", Response.Status.CREATED.getStatusCode(), responseRight.getStatus());

        Response responseResult = this.target.path("/v1/diff/" + id.toString()).request().get();
        assertEquals("Result wrong return", Response.Status.OK.getStatusCode(), responseResult.getStatus());

        Output output = (Output) responseResult.readEntity(Output.class);
        assertEquals("Output wrong id", id, output.getId());
        assertEquals("Output wrong result type", Result.EQUAL, output.getResult());
        assertNull("diffList should be null", output.getDiffList());
    }

    /**
     * Tests the whole process for different size inputs.
     * The whole process comprehends the insertion on both sides and the request by the result.
     */
    @Test
    public void testDifferentSizeResult() {
        Integer id = 1;

        Response responseLeft = this.target.path("/v1/diff/" + id + "/left").request().post(Entity.json(new Input(id, "YWMvZGMhIQ==")));
        assertEquals("Insert Left wrong return", Response.Status.CREATED.getStatusCode(), responseLeft.getStatus());

        Response responseRight = this.target.path("/v1/diff/" + id + "/right").request().post(Entity.json(new Input(id, "YWMvZGM==")));
        assertEquals("Insert Right wrong return", Response.Status.CREATED.getStatusCode(), responseRight.getStatus());

        Response responseResult = this.target.path("/v1/diff/" + id.toString()).request().get();
        assertEquals("Result wrong return", Response.Status.OK.getStatusCode(), responseResult.getStatus());

        Output output = (Output) responseResult.readEntity(Output.class);
        assertEquals("Output wrong id", id, output.getId());
        assertEquals("Output wrong result type", Result.DIFFERENT_SIZE, output.getResult());
        assertNull("diffList should be null", output.getDiffList());
    }

    /**
     * Tests the whole process for different inputs.
     * The whole process comprehends the insertion on both sides and the request by the result.
     */
    @Test
    public void testDifferentResult() {
        Integer id = 1;

        Response responseLeft = this.target.path("/v1/diff/" + id + "/left").request().post(Entity.json(new Input(id,   "YWMvZGM==")));
        assertEquals("Insert Left wrong return", Response.Status.CREATED.getStatusCode(), responseLeft.getStatus());

        Response responseRight = this.target.path("/v1/diff/" + id + "/right").request().post(Entity.json(new Input(id, "YUMhZGM==")));
        assertEquals("Insert Right wrong return", Response.Status.CREATED.getStatusCode(), responseRight.getStatus());

        Response responseResult = this.target.path("/v1/diff/" + id.toString()).request().get();
        assertEquals("Result wrong return", Response.Status.OK.getStatusCode(), responseResult.getStatus());

        Diff[] listDiffExpected = {new Diff(1,1), new Diff(3,1)};
        Output output = (Output) responseResult.readEntity(Output.class);
        assertEquals("Output wrong id", id, output.getId());
        assertEquals("Output wrong result type", Result.DIFFERENT, output.getResult());
        assertNotNull("diffList shouldn't be null", output.getDiffList());
        assertArrayEquals("iffList is wrong", listDiffExpected, output.getDiffList().toArray());
        
    }
}

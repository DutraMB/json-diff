/**
 * MIT License
 *
 * Copyright (c) [2017] [Marcio Branquinho Dutra]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dutramb.jsondiff.ws;

import dutramb.jsondiff.log.Logger;
import dutramb.jsondiff.logic.Comparator;
import dutramb.jsondiff.model.Input;
import dutramb.jsondiff.model.Output;
import dutramb.jsondiff.repository.MapRepository;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The {@code DiffWS} provides all service endpoints starting at '/v1/diff'.
 *
 * @author Marcio Branquinho Dutra
 */
@Path("/v1/diff")
public class DiffWS {

    /**
     * Left endpoint.
     *
     * @param id Operation id.
     * @param input JSON-based data input
     * @return HTTP Response: 201 - Created for success.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/left")
    public Response left(@PathParam("id") Integer id, Input input) {
        Logger.info("Request on left", this.getClass(), "id", id, "input", input);
        input.setId(id);
        MapRepository.getInstance().insertLeftInput(input);
        return Response.status(Response.Status.CREATED).build();
    }

    /**
     * Right endpoint.
     *
     * @param id Operation id.
     * @param input JSON-based data input
     * @return HTTP Response: 201 - Created for success.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/right")
    public Response right(@PathParam("id") Integer id, Input input) {
        Logger.debug("Request on right", this.getClass(), "id", id, "input", input);
        input.setId(id);
        MapRepository.getInstance().insertRightInput(input);
        return Response.status(Response.Status.CREATED).build();
    }

    /**
     * Result endpoint. It provides a JSON with the comparison's result.
     *
     * @param id Operation id
     * @return HTTP Response: 401 - Not Found if the operation id doesn't exist
     * or has just only one side information (just left or right).
     * <p>
     * 200 - OK for success. It returns 3 types of JSON according to
     * the comparison between data received on Left and Right.
     *
     * <p> Equal:
     * <blockquote><pre>
     *  {"id":1,
     *   "result":"EQUAL"
     *  }
     * </pre></blockquote>
     *
     * <p> Different sizes:
     * <blockquote><pre>
     *  {"id":1,
     *   "result":"DIFFERENT_SIZE"
     *  }
     * </pre></blockquote>
     *
     * <p> Same sizes, but different contents:
     * <blockquote><pre>
     *  {"id":1,
     *   "result":"DIFFERENT",
     *   "diffList":[
     *      {"offset":2,
     *       "length":1
     *      }
     *    ]
     *  }
     * </pre></blockquote>
     *
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response result(@PathParam("id") Integer id) {
        Output output = new Comparator().compare(id);

        Logger.debug("Request on result", this.getClass(), "id", id, "output", output);

        if (output == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Result not found for id=[" + id + "]").build();
        } else {
            return Response.ok(output).build();
        }
    }
}

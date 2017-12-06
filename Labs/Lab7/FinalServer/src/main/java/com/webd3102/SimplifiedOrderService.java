package com.webd3102;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/")
public class SimplifiedOrderService {

    @POST
    @Path("/show")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response DisplayOrder(String input) {

        System.out.println(input);

        return Response.status(201).entity("OK").build();
    }


}

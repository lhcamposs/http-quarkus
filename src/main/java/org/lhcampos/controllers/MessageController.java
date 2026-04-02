package org.lhcampos.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.lhcampos.model.Message;
import org.lhcampos.services.MessageService;

import java.util.Optional;

@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageController {

    @Inject
    MessageService messageService;

    @GET
    public Response getMessages() {
        return Response.ok(messageService.getMessages()).build();
    }

    @GET
    @Path("/{id}")
    public Response getMessageById(Long id) {
        Optional<Message> message = messageService.getMessageById(id);

        if (message.isPresent()) {
            return Response.ok(message.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response createMessage(Message message) {
        Message newMessage = messageService.createMessage(message);
        return Response.status(Response.Status.CREATED).entity(newMessage).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMessage(@PathParam("id") Long id) {
        boolean removed = messageService.deleteMessage(id);

        if (removed) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}

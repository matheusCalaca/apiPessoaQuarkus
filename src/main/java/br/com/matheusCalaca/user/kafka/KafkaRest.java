package br.com.matheusCalaca.user.kafka;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/kafka")
public class KafkaRest {


    @Inject
    @Channel("user-stream")
    Publisher<String> mensagemConsumer;

    @Inject
    @Channel("user-stream")
    Emitter<String> mensagemEmitter;

    @POST
    @Path("{mensagem}")
    public void insertMensagemKafka(@PathParam("mensagem") String mensagem) {
        System.out.println("generated mensagem");
        mensagemEmitter.send(mensagem);
    }


    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType("text/plain")
    public Publisher<String> stream() {
        System.out.println("reader mensagem");
        return mensagemConsumer;
    }
}

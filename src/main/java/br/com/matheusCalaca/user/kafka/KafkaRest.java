package br.com.matheusCalaca.user.kafka;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.matheusCalaca.user.model.User;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;

@Path("/kafka")
public class KafkaRest {


    @Inject
    @Channel("user-stream")
    Publisher<User> mensagemConsumer;

    @Inject
    @Channel("user-stream")
    Emitter<User> mensagemEmitter;

//    @POST
//    public void insertMensagemKafka(User user) {
//        mensagemEmitter.send(user);
//    }
//
//
//    @GET
//    @Path("/stream")
//    @Produces(MediaType.SERVER_SENT_EVENTS)
//    @SseElementType("text/plain")
//    public Publisher<User> stream() {
//        return mensagemConsumer;
//    }
}

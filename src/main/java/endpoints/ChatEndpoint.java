package endpoints;

import coders.MessageDecoder;
import coders.MessageEncoder;
import entities.Message;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

@ServerEndpoint(value = "/chat", decoders = {MessageDecoder.class}, encoders = {MessageEncoder.class})
public class ChatEndpoint {
    private static List<Session> sessions = new LinkedList<>();
    private static List<Message> messages = new LinkedList<>();
    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("onopen sessionId: "+session.getId());
        messages.forEach(message -> {
            try {
                session.getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });
    }
    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }
    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }
    @OnMessage
    public void onMessage(Session session, Message message) {
        messages.add(message);
        broadcastMessage(message);
    }
    private void broadcastMessage(Message message) {
        sessions.forEach(session -> {
            try {
                session.getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

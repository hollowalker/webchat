package endpoints;

import coders.DotDecoder;
import coders.DotEncoder;
import entities.Dot;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@ServerEndpoint(value = "/paint", decoders = {DotDecoder.class}, encoders = {DotEncoder.class})
public class PaintEndpoint {
    private static List<Session> sessions = new LinkedList<>();
    private static List<Dot> dots = new LinkedList<>();
    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("onopen sessionId: "+session.getId());
        dots.forEach(dot -> {
            try {
                session.getBasicRemote().sendObject(dot);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });
    }
    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }
    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }
    @OnMessage
    public void onMessage(Session session, Dot dot) {
        dots.add(dot);
        broadcast(session, dot);
    }
    private void broadcast(Session curSession, Dot dot) {
        sessions.forEach(
            session -> {
                if (session != curSession) {
                    try {
                        session.getBasicRemote().sendObject(dot);
                    } catch (IOException | EncodeException e) {
                        e.printStackTrace();
                    }
                }
            }
        );
    }
}

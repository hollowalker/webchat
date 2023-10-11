package entities;

import enums.MessageType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Message {
    private String sender;
    private String text;
    private MessageType type;
    @Override
    public String toString() {
        return "sender: " + sender + "; text: " + text + "; type:" + type;
    }
}

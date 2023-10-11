package coders;

import com.google.gson.Gson;
import entities.Dot;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class DotDecoder implements Decoder.Text<Dot> {
    Gson gson = new Gson();
    @Override
    public Dot decode(String s) throws DecodeException {
        return gson.fromJson(s, Dot.class);
    }

    @Override
    public boolean willDecode(String s) {
        return s != null;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}

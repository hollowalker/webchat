package coders;

import com.google.gson.Gson;
import entities.Dot;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class DotEncoder implements Encoder.Text<Dot> {
    Gson gson = new Gson();
    @Override
    public String encode(Dot dot) throws EncodeException {
        return gson.toJson(dot);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}

package com.example.endassigment.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

public class ElementDeserializer extends StdDeserializer<Element> {

    public ElementDeserializer() { this(null); }
    public ElementDeserializer(Class<?> vc) { super(vc); }

    @Override
    public Element deserialize(JsonParser p, DeserializationContext context) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        String type = node.get("type").asText();

        Element element = ElementFactory.createElement(type);

        if (element != null) {
            ObjectMapper mapper = (ObjectMapper) p.getCodec();
            return mapper.readerForUpdating(element).readValue(node);
        }
        return null;
    }
}
package com.github.nikammerlaan.aoc.days.day13;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.util.List;

public class PacketDeserializer extends JsonDeserializer<Packet> {

    @Override
    public Packet deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        ObjectMapper objectMapper = (ObjectMapper) p.getCodec();
        JsonNode tree = p.readValueAsTree();

        if(tree.isInt()) {
            return new IntPacket(tree.intValue());
        } else if(tree.isArray()) {
            var children = objectMapper.convertValue(tree, new TypeReference<List<Packet>>() {});
            return new ListPacket(children);
        } else {
            throw new IllegalStateException();
        }
    }

}

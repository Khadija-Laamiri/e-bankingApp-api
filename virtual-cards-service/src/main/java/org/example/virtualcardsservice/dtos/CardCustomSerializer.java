package org.example.virtualcardsservice.dtos;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.stripe.model.issuing.Card;

import java.io.IOException;

public class CardCustomSerializer extends JsonSerializer<Card> {
    @Override
    public void serialize(Card card, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        // Serialize only the fields you need
        gen.writeStringField("id", card.getId());
        gen.writeStringField("status", card.getStatus());
        // Add other fields as required
        gen.writeEndObject();
    }
}

package cz.cvut.fit.anteater.business.mapping;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class PageSerializer extends JsonSerializer<Page<?>> {

	@Override
	public void serialize(Page<?> value, JsonGenerator generator,
			SerializerProvider serializers) throws IOException,
			JsonProcessingException {
		generator.writeStartObject();
		generator.writeObjectField("content", value.getContent());
		generator.writeNumberField("number", value.getNumber());
		generator.writeNumberField("size", value.getSize());
		generator.writeNumberField("totalElements", value.getTotalElements());
		generator.writeNumberField("totalPages", value.getTotalPages());
		generator.writeEndObject();
	}
}

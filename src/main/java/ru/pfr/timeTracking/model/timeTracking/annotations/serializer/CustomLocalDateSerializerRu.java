package ru.pfr.timeTracking.model.timeTracking.annotations.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Преобразует LocalDate в строку даты в русском формате
 */
public class CustomLocalDateSerializerRu extends StdSerializer<LocalDate> {

    private static final DateTimeFormatter formatterRu
            = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    protected CustomLocalDateSerializerRu(Class<LocalDate> t) {
        super(t);
    }

    protected CustomLocalDateSerializerRu() {
        this(null);
    }

    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(formatterRu.format(localDate));
    }
}

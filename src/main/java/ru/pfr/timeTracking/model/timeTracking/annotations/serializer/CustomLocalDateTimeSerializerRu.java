package ru.pfr.timeTracking.model.timeTracking.annotations.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Преобразует LocalDateTime в строку даты в русском формате
 */
public class CustomLocalDateTimeSerializerRu extends StdSerializer<LocalDateTime> {

    private static final DateTimeFormatter formatterRu
            //= DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    protected CustomLocalDateTimeSerializerRu(Class<LocalDateTime> t) {
        super(t);
    }

    protected CustomLocalDateTimeSerializerRu() {
        this(null);
    }

    @Override
    public void serialize(LocalDateTime localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(formatterRu.format(localDate));
    }
}

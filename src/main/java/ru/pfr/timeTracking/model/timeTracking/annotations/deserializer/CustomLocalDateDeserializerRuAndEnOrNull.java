package ru.pfr.timeTracking.model.timeTracking.annotations.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Преобразует строку даты (в русском и американском формате) в LocalDate
 * при ошибке возвращает null
 */
public class CustomLocalDateDeserializerRuAndEnOrNull extends StdDeserializer<LocalDate> {

    private static final DateTimeFormatter formatterEn
            = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final DateTimeFormatter formatterRu
            = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public CustomLocalDateDeserializerRuAndEnOrNull() {
        this(null);
    }

    public CustomLocalDateDeserializerRuAndEnOrNull(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String date = jsonParser.getText();
        try {
            return LocalDate.parse(date, formatterEn);
        } catch (DateTimeParseException e) {
            try {
                return LocalDate.parse(date, formatterRu);
            } catch (DateTimeParseException e2) {
                return null;
            }
        }
    }

}

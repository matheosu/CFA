package br.edu.unigranrio.ect.si.cfa.ws.rs.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarDeserializer extends StdDeserializer<Calendar> {

    private static final long serialVersionUID = 4308006064509390775L;

    public CalendarDeserializer() {
        this(null);
    }

    protected CalendarDeserializer(Class<Calendar> vc) {
        super(vc);
    }

    @Override
    public Calendar deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String dateText = jsonParser.getText();
        if (dateText != null) {
            TemporalAccessor parse = DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(dateText);
            return GregorianCalendar.from(ZonedDateTime.from(parse));
        }
        return null;
    }
}

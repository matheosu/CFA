package br.edu.unigranrio.ect.si.cfa.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.GregorianCalendar;

@FacesConverter("calendar-converter")
public class CalendarConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s != null && !s.isEmpty()) {
            TemporalAccessor parse = DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(s);
            return GregorianCalendar.from(ZonedDateTime.from(parse));
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o != null && o instanceof Calendar) {
            Calendar c = (Calendar) o;
            LocalDateTime localDateTime = c.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(localDateTime);
        }
        return "";
    }
}

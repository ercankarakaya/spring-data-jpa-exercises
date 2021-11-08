package com.ercan.utilities;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CustomDateUtil {

    public static class CalendarToDateStringSerializer extends JsonSerializer<Calendar> {
        @Override
        public void serialize(Calendar value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String formatted = sdf.format(value.getTime());
                gen.writeString(formatted);
            }
        }

    }


    public static String calendarToString(Calendar calendar){
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(calendar.getTime());
    }

}


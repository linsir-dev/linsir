
package com.linsir.core.serial.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.linsir.core.util.V;
import com.linsir.core.tool.utils.D;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * jackson yyyy-MM-dd格式参数转换为LocalDateTime
 * @author : uu
 * @version : v1.0
 * @Date 2023/9/18  15:05
 */
public class LocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {
    private static final long serialVersionUID = 8758976191733673106L;

    public LocalDateTimeDeserializer() {
        super(LocalDateTime.class);
    }
    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        String dateString = p.readValueAs(String.class);
        dateString = D.formatDateTimeString(dateString);
        if(V.isEmpty(dateString)) {
            return null;
        }
        if(dateString.length() <= D.FORMAT_DATE_Y4MD.length()) {
            return LocalDate.parse(dateString, D.FORMATTER_DATE_Y4MD).atStartOfDay();
        }
        return LocalDateTime.parse(dateString, D.FORMATTER_DATETIME_Y4MDHMS);
    }
}

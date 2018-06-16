package it.digitalgarage.marketplace.commons.fe.formatter;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class BigDecimalJsonSerializer extends JsonSerializer<BigDecimal> {


    @Override
    public void serialize(BigDecimal bigDecimal, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(bigDecimal != null) {
            String format = bigDecimal.toPlainString();
            if (format != null) {
                format = format.replace(".", ",");
            }
            jsonGenerator.writeString(format);
        }
    }
}

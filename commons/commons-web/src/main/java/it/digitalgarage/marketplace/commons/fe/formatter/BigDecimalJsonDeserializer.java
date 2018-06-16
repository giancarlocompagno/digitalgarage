package it.digitalgarage.marketplace.commons.fe.formatter;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public class BigDecimalJsonDeserializer extends JsonDeserializer<BigDecimal> {

    private static final Pattern bigDecimal = Pattern.compile("^[0-9]+(\\.[0-9]+){0,1}$");

    @Override
    public BigDecimal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if(jsonParser != null) {
            String s = jsonParser.getValueAsString();
            String toParse = null;
            if (s != null) {
                toParse = s.replace(",", ".");
                if (bigDecimal.matcher(toParse).matches()) {
                    try {
                        return new BigDecimal(toParse);
                    }catch (Exception e){
                        //  deserializationContext.handleUnexpectedToken(BigDecimal.class, jsonParser);
                        throw InvalidFormatException.from(jsonParser,"Il valore inserito non e' un numerico valido.",s,BigDecimal.class);
//                                throw new JsonProcessingException("Il valore inserito non è un numerico valido.",jsonParser.getCurrentLocation()) {};
                    }
                } else {
                    throw InvalidFormatException.from(jsonParser,"Il valore inserito non e' un numerico valido.",s,BigDecimal.class);
//                            deserializationContext.handleUnexpectedToken(BigDecimal.class, jsonParser);
                }
            }
        }
        return null;
    }
}

package hello.typeconverter.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Slf4j
public class IntegerToStringConverter implements Converter<Integer, String> {
    @Nullable
    @Override
    public String convert(Integer source) { //파라미터로 integer들어오고 반환값이 스트링.
        log.info("convert source: {}", source);
        return String.valueOf(source);
    }
}

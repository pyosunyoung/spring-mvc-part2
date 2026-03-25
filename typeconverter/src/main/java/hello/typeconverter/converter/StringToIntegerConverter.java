package hello.typeconverter.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Slf4j
public class StringToIntegerConverter implements Converter<String, Integer> { //s는 파라미터 t는 반환값
    @Nullable
    @Override
    public Integer convert(String source) {
        log.info("convert source: {}", source);
        Integer integer = Integer.valueOf(source); // 문자를 숫자로 바꿔주는 것.
        return integer;
    }
}

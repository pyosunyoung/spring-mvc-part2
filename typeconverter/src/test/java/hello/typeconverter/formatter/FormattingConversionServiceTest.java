package hello.typeconverter.formatter;

import hello.typeconverter.converter.IpPortToStringConverter;
import hello.typeconverter.converter.StringToIpPortConverter;
import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

public class FormattingConversionServiceTest {

    @Test
    void formattingConversionService() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(); // 컨버터랑, 포메터 같이 사용 가능
        //컨버터 등록
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());
        //포멧터 등록
        conversionService.addFormatter(new MyNumberFormatter());

        //컨버터 사용
        IpPort ipPort = conversionService.convert("127.0.0.1:8080", IpPort.class);
        Assertions.assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1", 8080));

        //포맷터 사용
        Assertions.assertThat(conversionService.convert(1000, String.class)).isEqualTo("1,000");
        Assertions.assertThat(conversionService.convert("1,000", Long.class)).isEqualTo(1000L);

    }
}

//FormattingConversionService 는 ConversionService 관련 기능을 상속받기 때문에 결과적으로 컨버터도
//포맷터도 모두 등록할 수 있다. 그리고 사용할 때는 ConversionService 가 제공하는 convert 를 사용하면 된다.

//포맷터를 지원하는 컨버전 서비스를 사용하면 컨버전 서비스에 포맷터를 추가할 수 있다. 내부에서 어댑터 패턴을 사용
//해서 Formatter 가 Converter 처럼 동작하도록 지원한다.
//FormattingConversionService 는 포맷터를 지원하는 컨버전 서비스이다.

package hello.itemservice.message;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    private MessageSource ms;

    @Test
    void helloMessage(){
        String result = ms.getMessage("hello", null, null);//1번쨰  코드(키값느낌?, 2번쨰 arg, 3번째 locale), message.properties에서 가져옴
        Assertions.assertThat(result).isEqualTo("안녕");
    }

//    @Test
//    void notFoundMessageCode(){ // 해당 메시지가 없을 때, 예외 처리
//       Assertions.assertThatThrownBy(()->ms.getMessage("no_code", null, null))
//               .isInstanceOf(NoSuchMessageException.class);
//
//    }

    @Test
    void notFoundMessageCodeDefaultMessage(){ // 해당 메시지가 없을 때, 예외 처리
        String result = ms.getMessage("no_code", null, "기본 메시지", null);
        Assertions.assertThat(result).isEqualTo("기본 메시지");

    }

    @Test
    void argumentMessage(){
        String message = ms.getMessage("hello.name", new Object[]{"Spring"}, null);
        Assertions.assertThat(message).isEqualTo("안녕 Spring"); // hello.name + Spring이 들어간것.
    }

    @Test
    void defaultLang(){
        Assertions.assertThat(ms.getMessage("hello", null, null)).isEqualTo("안녕"); //기본값은 korea이기 떄문에 정상으로 돌아감
        Assertions.assertThat(ms.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
    }

    @Test
    void enLang(){
        Assertions.assertThat(ms.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
    }

}

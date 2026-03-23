package hello.login.web.argumentResolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME) // 실제 에노테이션 동작 전까지 남아있어야 해서 넣어줌
public @interface Login {
}

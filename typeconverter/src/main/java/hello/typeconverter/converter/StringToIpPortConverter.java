package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Slf4j
public class StringToIpPortConverter implements Converter<String, IpPort> {
    @Nullable
    @Override
    public IpPort convert(String source) {
        log.info("convert source: {}", source);
        ////127.0.0.1:8080 같은 문자가 들어오면 IpPort라는 객체로 바꾸고 싶은것.
        String[] split = source.split(":");
        String ip = split[0]; //127.0.0.1
        int port = Integer.parseInt(split[1]);//8080

        return new IpPort(ip, port);
    }
}

package example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // Interface, Class, Enum
@Retention(RetentionPolicy.SOURCE) // 컴파일 타임에 쓰고 바이트 코드에서는 더 이상 필요 없기 때문
public @interface Bepi {
}

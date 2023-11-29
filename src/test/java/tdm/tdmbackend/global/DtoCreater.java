package tdm.tdmbackend.global;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public abstract class DtoCreater {

    public static <T> T create(Class<T> dtoClass, Object... input) {
        try {
            final Constructor<T> constructor = dtoClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            final T dto = constructor.newInstance();
            Field[] fields = dtoClass.getDeclaredFields();
            for (int i = 0; i < input.length; i++) {
                fields[i].setAccessible(true);
                fields[i].set(dto,input[i]);
            }
            return dto;
        } catch (NoSuchMethodException|InvocationTargetException|InstantiationException|IllegalAccessException e) {
            // todo: 예외처리 만들기
            throw new TestException("Dto 생성에 실패하였습니다.");
        }
    }
}

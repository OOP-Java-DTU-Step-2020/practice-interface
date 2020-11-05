package org.itstep.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import static org.itstep.TestUtil.isPublicNoStaticNoFinalNoAbstractClass;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EchoCommandTest {

    public static final String CLASS_NAME = "org.itstep.command.EchoCommand";

    public static final Class<?> CLAZZ;

    static {
        Class<?> cls;
        try {
            cls = Class.forName(CLASS_NAME);
        } catch (ClassNotFoundException ex) {
            cls = null;
        }
        CLAZZ = cls;
    }

    @Test
    @DisplayName("Проверка наличия класса EchoCommand")
    void classExists() {
        isPublicNoStaticNoFinalNoAbstractClass(CLAZZ, CLASS_NAME);
    }

    @Test
    @DisplayName("Класс наследуется от Command")
    void implementsCommand() {
        Optional<Class<?>> o = Arrays.stream(CLAZZ.getInterfaces()).findFirst();
        assertTrue(o.isPresent());
        assertEquals("Command", o.get().getSimpleName());
    }

    @Test
    @DisplayName("Выполнение метода execute()")
    void execute() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object echo = CLAZZ.getDeclaredConstructor().newInstance();
        String actual = null;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            System.setOut(new PrintStream(out));
            Method execute = CLAZZ.getDeclaredMethod("execute", String[].class);
            execute.invoke(echo, new Object[]{new String[]{"Hello", "World"}});
            actual = out.toString().trim();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.setOut(null);
        }
        assertEquals("Hello World", actual);
    }
}
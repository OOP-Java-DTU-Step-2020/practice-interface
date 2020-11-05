package org.itstep.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.itstep.TestUtil.isPublicNoStaticNoFinalNoAbstractClass;
import static org.junit.jupiter.api.Assertions.*;

class NowCommandTest {

    public static final String CLASS_NAME = "org.itstep.command.NowCommand";

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
    @DisplayName("Проверка наличия класса NowCommand")
    void classExists() {
        isPublicNoStaticNoFinalNoAbstractClass(CLAZZ, CLASS_NAME);
    }
    @Test
    @DisplayName("Класс наследуется от Command")
    void implementsCommand(){
        Optional<Class<?>> o = Arrays.stream(CLAZZ.getInterfaces()).findFirst();
        assertTrue(o.isPresent());
        assertEquals("Command", o.get().getSimpleName());
    }


    @Test
    void execute() {
    }
}
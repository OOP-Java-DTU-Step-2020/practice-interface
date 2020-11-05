package org.itstep.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.itstep.TestUtil.isPublicNoStaticNoFinalNoAbstractInterface;

class CommandTest {
    public static final String CLASS_NAME = "org.itstep.command.Command";

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
    @DisplayName("Проверка наличия интерфейса Command")
    void classExists() {
        isPublicNoStaticNoFinalNoAbstractInterface(CLAZZ, CLASS_NAME);
    }
}
package org.itstep;

import org.junit.jupiter.api.Assertions;

import java.lang.reflect.*;
import java.util.Random;
import java.util.stream.Stream;

public class TestUtil {
    public final static Random rnd = new Random();

    public static String randomString() {
        return Stream.generate(() -> rnd.nextInt('z' - 'a' + 1) + 'a')
                .limit(rnd.nextInt(20) + 20)
                .map(Object::toString)
                .reduce(String::concat).orElse("");
    }

    public static Object getExpected(Class<?> clazz) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> cityClass = null;

        if (int.class.equals(clazz)) {
            return rnd.nextInt(800_000);
        } else if (String.class.equals(clazz)) {
            return randomString();
        } else if (cityClass.equals(clazz)) {
            Constructor<?> ctor = clazz.getDeclaredConstructor();
            Object obj = ctor.newInstance();
            Method setMethod = cityClass.getDeclaredMethod("setInhabitants", int.class);
            setMethod.invoke(obj, getExpected(int.class));
            return obj;
        } else if(long.class.equals(clazz)) {
            return rnd.nextLong();
        } else if(byte.class.equals(clazz)) {
            return (byte)rnd.nextInt(100);
        } else if(double.class.equals(clazz)) {
            return rnd.nextDouble();
        }
        return null;
    }

    public static void isPublicNoStaticNoFinalNoAbstractClass(Class<?> clazz, String className) {
        Assertions.assertNotNull(clazz, "Класс " + className + " не найден");
        Assertions.assertTrue(Modifier.isPublic(clazz.getModifiers()), "Класс должен быть публичным");
        Assertions.assertFalse(Modifier.isFinal(clazz.getModifiers()), "Класс не должен быть final");
        Assertions.assertFalse(Modifier.isAbstract(clazz.getModifiers()), "Класс не должен быть абстрактным");
        Assertions.assertFalse(Modifier.isInterface(clazz.getModifiers()), "Должен быть класс, а не интерфейс");
    }

    public static void isPublicNoStaticNoFinalNoAbstractInterface(Class<?> clazz, String className) {
        Assertions.assertNotNull(clazz, "Интерфейс " + className + " не найден");
        Assertions.assertTrue(Modifier.isPublic(clazz.getModifiers()), "Интерфейс должен быть публичным");
        Assertions.assertTrue(Modifier.isAbstract(clazz.getModifiers()), "Интерфейс должен быть абстрактным");
        Assertions.assertTrue(Modifier.isInterface(clazz.getModifiers()), "Должен быть интерфейс, а не класс");
    }

    public static void hasPrivateNoStaticField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Field declaredField = clazz.getDeclaredField(fieldName);
        Assertions.assertTrue(Modifier.isPrivate(declaredField.getModifiers()), "Поле должно быть приватными");
        Assertions.assertFalse(Modifier.isStatic(declaredField.getModifiers()), "Поле не должно быть статическими");
        Assertions.assertFalse(Modifier.isFinal(declaredField.getModifiers()), "Поле не должно быть final");
    }

    public static void hasPrivateNoStaticField(Class<?> clazz, String fieldName, Class<?> fieldType) throws NoSuchFieldException {
        hasPrivateNoStaticField(clazz, fieldName);
        Field declaredField = clazz.getDeclaredField(fieldName);
        Assertions.assertEquals(fieldType, declaredField.getType(), "Поле должно быть типа " + declaredField);
    }

    public static void hasPublicNoStaticMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        Method declaredMethod = clazz.getDeclaredMethod(methodName, parameterTypes);
        Assertions.assertTrue(Modifier.isPublic(declaredMethod.getModifiers()), "Метод должен быть публичными");
        Assertions.assertFalse(Modifier.isStatic(declaredMethod.getModifiers()), "Метод не должен быть статическими");
        Assertions.assertFalse(Modifier.isFinal(declaredMethod.getModifiers()), "Метод не должен быть final");
        Assertions.assertFalse(Modifier.isAbstract(declaredMethod.getModifiers()), "Метод не должен быть абстрактным");
    }

    public static void hasPublicNoStaticMethod(Class<?> clazz, Class<?> returnType, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        hasPublicNoStaticMethod(clazz, methodName, parameterTypes);
        Method declaredMethod = clazz.getDeclaredMethod(methodName, parameterTypes);
        Assertions.assertEquals(returnType, declaredMethod.getReturnType(), "Метод " + methodName +
                " должен возвращать " + returnType.getSimpleName());
    }

    public static void getAndSetCheck(Class<?> clazz, Class<?> argClazz, String fieldName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        Object obj = clazz.getDeclaredConstructor().newInstance();

        String setterName = String.format("set%c%s", Character.toUpperCase(fieldName.charAt(0)), fieldName.substring(1));
        String getterName = String.format("get%c%s", Character.toUpperCase(fieldName.charAt(0)), fieldName.substring(1));

        Method setter = clazz.getDeclaredMethod(setterName, argClazz);
        Method getter = clazz.getDeclaredMethod(getterName);

        Object expected = TestUtil.getExpected(argClazz);

        setter.invoke(obj, expected);

        Object actual = getter.invoke(obj);
        Assertions.assertEquals(expected, actual);
    }
}
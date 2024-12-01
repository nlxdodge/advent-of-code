package nl.nlxdodge.util;

import java.lang.reflect.InvocationTargetException;

public class DayCaller {
    
    public static Day call(String dayNumber) {
        try {
            Class<?> clazz = Class.forName("nl.nlxdodge.days.Day" + dayNumber);
            return (Day) clazz.getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException |
                 ClassNotFoundException e) {
            throw new RuntimeException("Day Class for day %s has not been made yet!".formatted(dayNumber));
        }
    }
}

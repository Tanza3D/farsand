package com.farsand.farsand.protocol.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Retrieves fields and methods by signature, not just name.
 *
 * @author Kristian
 */
public class FuzzyReflection {

    /**
     * Matches a Minecraft object.
     */
    public static String MINECRAFT_OBJECT = "net\\.minecraft(\\.\\w+)+";

    // The class we're actually representing
    private Class<?> source;

    // Whether or not to lookup private members
    private boolean forceAccess;

    public FuzzyReflection(Class<?> source, boolean forceAccess) {
        this.source = source;
        this.forceAccess = forceAccess;
    }

    /**
     * Retrieves a fuzzy reflection instance from a given class.
     * @param source - the class we'll use.
     * @return A fuzzy reflection instance.
     */
    public static FuzzyReflection fromClass(Class<?> source) {
        return fromClass(source, false);
    }

    /**
     * Retrieves a fuzzy reflection instance from a given class.
     * @param source - the class we'll use.
     * @param forceAccess - whether or not to override scope restrictions.
     * @return A fuzzy reflection instance.
     */
    public static FuzzyReflection fromClass(Class<?> source, boolean forceAccess) {
        return new FuzzyReflection(source, forceAccess);
    }

    /**
     * Retrieves a fuzzy reflection instance from an object.
     * @param reference - the object we'll use.
     * @return A fuzzy reflection instance that uses the class of the given object.
     */
    public static FuzzyReflection fromObject(Object reference) {
        return new FuzzyReflection(reference.getClass(), false);
    }

    /**
     * Retrieves a fuzzy reflection instance from an object.
     * @param reference - the object we'll use.
     * @param forceAccess - whether or not to override scope restrictions.
     * @return A fuzzy reflection instance that uses the class of the given object.
     */
    public static FuzzyReflection fromObject(Object reference, boolean forceAccess) {
        return new FuzzyReflection(reference.getClass(), forceAccess);
    }

    /**
     * Retrieves the underlying class.
     */
    public Class<?> getSource() {
        return source;
    }

    /**
     * Retrieves a method by looking at its name.
     * @param nameRegex -  regular expression that will match method names.
     * @return The first method that satisfies the regular expression.
     */
    public Method getMethodByName(String nameRegex) {

        Pattern match = Pattern.compile(nameRegex);

        for (Method method : getMethods()) {
            if (match.matcher(method.getName()).matches()) {
                // Right - this is probably it.
                return method;
            }
        }

        throw new RuntimeException("Unable to find a method with the pattern " +
                nameRegex + " in " + source.getName());
    }

    /**
     * Retrieves a method by looking at the parameter types only.
     * @param name - potential name of the method. Only used by the error mechanism.
     * @param args - parameter types of the method to find.
     * @return The first method that satisfies the parameter types.
     */
    public Method getMethodByParameters(String name, Class<?>... args) {

        // Find the correct method to call
        for (Method method : getMethods()) {
            if (ArrayUtils.isEquals(method.getParameterTypes(), args)) {
                return method;
            }
        }

        // That sucks
        throw new RuntimeException("Unable to find " + name + " in " + source.getName());
    }

    /**
     * Retrieves a field by name.
     * @param nameRegex - regular expression that will match a field name.
     * @return The first field to match the given expression.
     */
    public Field getFieldByName(String nameRegex) {

        Pattern match = Pattern.compile(nameRegex);

        for (Field field : getFields()) {
            if (match.matcher(field.getName()).matches()) {
                // Right - this is probably it.
                return field;
            }
        }

        // Looks like we're outdated. Too bad.
        throw new RuntimeException("Unable to find a field with the pattern " +
                nameRegex + " in " + source.getName());
    }

    /**
     * Retrieves a field by type.
     * <p>
     * Note that the type is matched using the full canonical representation, i.e.:
     * <ul>
     *     <li>java.util.List</li>
     *     <li>net.comphenix.xp.ExperienceMod</li>
     * </ul>
     * @param typeRegex - regular expression that will match the field type.
     * @return The first field with a type that matches the given regular expression.
     */
    public Field getFieldByType(String typeRegex) {

        Pattern match = Pattern.compile(typeRegex);

        // Like above, only here we test the field type
        for (Field field : getFields()) {
            if (match.matcher(field.getType().getName()).matches()) {
                return field;
            }
        }

        // Looks like we're outdated. Too bad.
        throw new RuntimeException("Unable to find a field with the type " +
                typeRegex + " in " + source.getName());
    }

    /**
     * Retrieves all private and public fields in declared order (after JDK 1.5).
     * @return Every field.
     */
    public Set<Field> getFields() {
        // We will only consider private fields in the declared class
        if (forceAccess)
            return setUnion(source.getDeclaredFields(), source.getFields());
        else
            return setUnion(source.getFields());
    }

    /**
     * Retrieves all private and public methods in declared order (after JDK 1.5).
     * @return Every method.
     */
    public Set<Method> getMethods() {
        // We will only consider private methods in the declared class
        if (forceAccess)
            return setUnion(source.getDeclaredMethods(), source.getMethods());
        else
            return setUnion(source.getMethods());
    }

    // Prevent duplicate fields
    private static <T> Set<T> setUnion(T[]... array) {
        Set<T> result = new LinkedHashSet<T>();

        for (T[] elements : array) {
            for (T element : elements) {
                result.add(element);
            }
        }

        return result;
    }

    /**
     * Retrieves whether or not not to override any scope restrictions.
     * @return TRUE if we override scope, FALSE otherwise.
     */
    public boolean isForceAccess() {
        return forceAccess;
    }

    /**
     * Sets whether or not not to override any scope restrictions.
     * @param forceAccess - TRUE if we override scope, FALSE otherwise.
     */
    public void setForceAccess(boolean forceAccess) {
        this.forceAccess = forceAccess;
    }
}

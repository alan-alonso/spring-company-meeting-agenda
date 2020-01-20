package br.alan.springcompanymeetingagenda.utils;

import java.beans.PropertyDescriptor;
import java.util.stream.Stream;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * BeanUtilsExtension
 */
public class BeanUtilsExtension extends BeanUtils {


    /**
     * Return all class field names whose values are null. Such array can be used for merging two
     * objects while preserve non-null properties.
     * 
     * @param source source object whose field values will be evaluated
     * @return null valued field names
     */
    public static String[] getNullPropertyNames(Object source) {
        if (source == null) {
            return new String[] {};
        }
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        return Stream.of(pds).filter(pd -> src.getPropertyValue(pd.getName()) == null)
                .map(PropertyDescriptor::getName).toArray(String[]::new);
    }
}

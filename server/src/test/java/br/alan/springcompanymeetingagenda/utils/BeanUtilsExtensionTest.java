package br.alan.springcompanymeetingagenda.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import br.alan.springcompanymeetingagenda.domain.ResourceType;

/**
 * BeanUtilsExtensionTest
 * 
 * Test class for {@link BeanUtilsExtension}.
 * 
 * @author Alan Alonso
 */
public class BeanUtilsExtensionTest {

    @DisplayName("getNullPropertyNames should return correct null field names from ResourceType object")
    @Test
    void getNullPropertyNamesTestNonNullObj() {
        // arrange
        ResourceType resourceType = ResourceType.builder().id(null).name("name").createdDate(null)
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now())).modifiedBy("modifiedBy").createdBy("createdBy").build();

        // act
        String[] nullProps = BeanUtilsExtension.getNullPropertyNames(resourceType);

        // assert
        assertArrayEquals(new String[] {"createdDate", "id"}, nullProps);
    }

    @DisplayName("getNullPropertyNames should handle null values")
    @Test
    void getNullPropertyNamesTestNull() {
        // act
        String[] nullProps = BeanUtilsExtension.getNullPropertyNames(null);

        // assert
        assertArrayEquals(new String[] {}, nullProps);
    }
}

package guru.springframework.sfg_recipe_project.converters;

import guru.springframework.sfg_recipe_project.commands.UnitOfMeasureCommand;
import guru.springframework.sfg_recipe_project.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureTest {

    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = new Long(1L);

    UnitOfMeasureCommandToUnitOfMeasure converter;

    @BeforeEach
    void setUp() throws Exception {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    void setNullParameter() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    void convert() throws Exception {
        //given
        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setId(LONG_VALUE);
        command.setDescription(DESCRIPTION);

        //when
        UnitOfMeasure uom = converter.convert(command);

        //then
        assertNotNull(uom);
        assertEquals(LONG_VALUE, uom.getId());
        assertEquals(DESCRIPTION, uom.getDescription());
    }
}
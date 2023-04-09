package classes;

import org.example.classes.Store;
import org.junit.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StoreTest {

    @Test
    public void testGettersAndSetters() {
        List<String> pickers = Arrays.asList("Picker 1", "Picker 2", "Picker 3");
        LocalTime pickingStartTime = LocalTime.of(9, 0, 0);
        LocalTime pickingEndTime = LocalTime.of(17, 0, 0);

        Store store = new Store(pickers, pickingStartTime, pickingEndTime);

        assertEquals(pickers, store.getPickers());
        assertEquals(pickingStartTime.toSecondOfDay(), store.getPickingStartTime());
        assertEquals(pickingEndTime.toSecondOfDay(), store.getPickingEndTime());

        List<String> newPickers = Arrays.asList("Picker 4", "Picker 5");
        int newPickingStartTime = LocalTime.of(10, 0, 0).toSecondOfDay();
        int newPickingEndTime = LocalTime.of(18, 0, 0).toSecondOfDay();

        store.setPickers(newPickers);
        store.setPickingStartTime(newPickingStartTime);
        store.setPickingEndTime(newPickingEndTime);

        assertEquals(newPickers, store.getPickers());
        assertEquals(newPickingStartTime, store.getPickingStartTime());
        assertEquals(newPickingEndTime, store.getPickingEndTime());
    }
}
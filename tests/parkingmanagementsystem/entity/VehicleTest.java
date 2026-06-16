package parkingmanagementsystem.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Vehicle Entity Tests")
class VehicleTest {

    @Test
    @DisplayName("Constructor sets all fields correctly")
    void constructorSetsFields() {
        Vehicle v = new Vehicle("V001", "John", "Car", "DHK-1234", "A-1", "10:00");
        assertEquals("V001", v.getVehicleId());
        assertEquals("John", v.getDriverName());
        assertEquals("Car", v.getVehicleType());
        assertEquals("DHK-1234", v.getNumberPlate());
        assertEquals("A-1", v.getParkingSlot());
        assertEquals("10:00", v.getEntryTime());
    }

    @Test
    @DisplayName("All setters update fields")
    void settersUpdateFields() {
        Vehicle v = new Vehicle("V001", "John", "Car", "DHK-1234", "A-1", "10:00");
        v.setVehicleId("V999");
        v.setDriverName("Jane");
        v.setVehicleType("Bike");
        v.setNumberPlate("CTG-5678");
        v.setParkingSlot("B-2");
        v.setEntryTime("14:30");

        assertEquals("V999", v.getVehicleId());
        assertEquals("Jane", v.getDriverName());
        assertEquals("Bike", v.getVehicleType());
        assertEquals("CTG-5678", v.getNumberPlate());
        assertEquals("B-2", v.getParkingSlot());
        assertEquals("14:30", v.getEntryTime());
    }

    @Test
    @DisplayName("toLine produces comma-separated representation")
    void toLineFormat() {
        Vehicle v = new Vehicle("V001", "John", "Car", "DHK-1234", "A-1", "10:00");
        assertEquals("V001,John,Car,DHK-1234,A-1,10:00", v.toLine());
    }

    @Test
    @DisplayName("fromLine parses valid CSV line")
    void fromLineValid() {
        Vehicle v = Vehicle.fromLine("V001,John,Car,DHK-1234,A-1,10:00");
        assertNotNull(v);
        assertEquals("V001", v.getVehicleId());
        assertEquals("John", v.getDriverName());
        assertEquals("Car", v.getVehicleType());
        assertEquals("DHK-1234", v.getNumberPlate());
        assertEquals("A-1", v.getParkingSlot());
        assertEquals("10:00", v.getEntryTime());
    }

    @Test
    @DisplayName("fromLine returns null for null input")
    void fromLineNull() {
        assertNull(Vehicle.fromLine(null));
    }

    @Test
    @DisplayName("fromLine returns null for empty string")
    void fromLineEmpty() {
        assertNull(Vehicle.fromLine(""));
    }

    @Test
    @DisplayName("fromLine returns null for whitespace-only string")
    void fromLineWhitespace() {
        assertNull(Vehicle.fromLine("   "));
    }

    @Test
    @DisplayName("fromLine returns null for line with wrong number of fields")
    void fromLineWrongFieldCount() {
        assertNull(Vehicle.fromLine("V001,John,Car"));
        assertNull(Vehicle.fromLine("V001,John,Car,DHK-1234,A-1,10:00,extra"));
    }

    @Test
    @DisplayName("fromLine handles fields with empty values")
    void fromLineEmptyFields() {
        Vehicle v = Vehicle.fromLine(",,,,, ");
        assertNotNull(v);
        assertEquals("", v.getVehicleId());
        assertEquals(" ", v.getEntryTime());
    }

    @Test
    @DisplayName("toRow returns correct Object array")
    void toRowFormat() {
        Vehicle v = new Vehicle("V001", "John", "Car", "DHK-1234", "A-1", "10:00");
        Object[] row = v.toRow();
        assertEquals(6, row.length);
        assertEquals("V001", row[0]);
        assertEquals("John", row[1]);
        assertEquals("Car", row[2]);
        assertEquals("DHK-1234", row[3]);
        assertEquals("A-1", row[4]);
        assertEquals("10:00", row[5]);
    }

    @Test
    @DisplayName("toLine and fromLine are inverse operations (round-trip)")
    void roundTrip() {
        Vehicle original = new Vehicle("V100", "Alice", "Bike", "RAJ-9999", "B-3", "08:15");
        String line = original.toLine();
        Vehicle parsed = Vehicle.fromLine(line);

        assertNotNull(parsed);
        assertEquals(original.getVehicleId(), parsed.getVehicleId());
        assertEquals(original.getDriverName(), parsed.getDriverName());
        assertEquals(original.getVehicleType(), parsed.getVehicleType());
        assertEquals(original.getNumberPlate(), parsed.getNumberPlate());
        assertEquals(original.getParkingSlot(), parsed.getParkingSlot());
        assertEquals(original.getEntryTime(), parsed.getEntryTime());
    }
}

package parkingmanagementsystem.fileio;

import org.junit.jupiter.api.*;
import parkingmanagementsystem.entity.Vehicle;

import java.io.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for VehicleFileIO.
 *
 * VehicleFileIO uses the relative path "parkingmanagementsystem/fileio/vehicles.txt",
 * so the test runner's working directory must contain that directory structure.
 * The run-tests.sh script sets the working directory to a temp folder that provides this.
 */
@DisplayName("VehicleFileIO Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VehicleFileIOTest {

    private static final String FILE_PATH = "parkingmanagementsystem/fileio/vehicles.txt";

    @BeforeEach
    void resetFile() throws Exception {
        Path p = Path.of(FILE_PATH);
        Files.createDirectories(p.getParent());
        Files.deleteIfExists(p);
        Files.createFile(p);
    }

    @AfterAll
    static void cleanupFile() throws Exception {
        Files.deleteIfExists(Path.of(FILE_PATH));
        Files.deleteIfExists(Path.of("parkingmanagementsystem/fileio/temp.txt"));
    }

    @Test
    @DisplayName("createFileIfNotExists creates the file")
    void createFileIfNotExists() throws Exception {
        Files.deleteIfExists(Path.of(FILE_PATH));
        assertFalse(Files.exists(Path.of(FILE_PATH)));

        VehicleFileIO.createFileIfNotExists();
        assertTrue(Files.exists(Path.of(FILE_PATH)));
    }

    @Test
    @DisplayName("addVehicle writes vehicle to file")
    void addVehicle() throws Exception {
        Vehicle v = new Vehicle("V001", "John", "Car", "DHK-1234", "A-1", "10:00");
        VehicleFileIO.addVehicle(v);

        String content = Files.readString(Path.of(FILE_PATH)).trim();
        assertEquals("V001,John,Car,DHK-1234,A-1,10:00", content);
    }

    @Test
    @DisplayName("countRecords returns correct count")
    void countRecords() throws Exception {
        assertEquals(0, VehicleFileIO.countRecords());

        VehicleFileIO.addVehicle(new Vehicle("V001", "A", "Car", "P1", "A-1", "10:00"));
        assertEquals(1, VehicleFileIO.countRecords());

        VehicleFileIO.addVehicle(new Vehicle("V002", "B", "Bike", "P2", "A-2", "11:00"));
        assertEquals(2, VehicleFileIO.countRecords());
    }

    @Test
    @DisplayName("vehicleIdExists returns true for existing ID")
    void vehicleIdExistsTrue() throws Exception {
        VehicleFileIO.addVehicle(new Vehicle("V001", "A", "Car", "P1", "A-1", "10:00"));
        assertTrue(VehicleFileIO.vehicleIdExists("V001"));
    }

    @Test
    @DisplayName("vehicleIdExists returns false for non-existing ID")
    void vehicleIdExistsFalse() throws Exception {
        VehicleFileIO.addVehicle(new Vehicle("V001", "A", "Car", "P1", "A-1", "10:00"));
        assertFalse(VehicleFileIO.vehicleIdExists("V999"));
    }

    @Test
    @DisplayName("plateExists returns true for existing plate (case-insensitive)")
    void plateExistsTrue() throws Exception {
        VehicleFileIO.addVehicle(new Vehicle("V001", "A", "Car", "DHK-1234", "A-1", "10:00"));
        assertTrue(VehicleFileIO.plateExists("dhk-1234"));
        assertTrue(VehicleFileIO.plateExists("DHK-1234"));
    }

    @Test
    @DisplayName("plateExists returns false for non-existing plate")
    void plateExistsFalse() throws Exception {
        assertFalse(VehicleFileIO.plateExists("NONEXISTENT"));
    }

    @Test
    @DisplayName("driverNameExists returns true (case-insensitive)")
    void driverNameExistsTrue() throws Exception {
        VehicleFileIO.addVehicle(new Vehicle("V001", "John", "Car", "P1", "A-1", "10:00"));
        assertTrue(VehicleFileIO.driverNameExists("john"));
        assertTrue(VehicleFileIO.driverNameExists("JOHN"));
    }

    @Test
    @DisplayName("driverNameExists returns false for unknown driver")
    void driverNameExistsFalse() throws Exception {
        assertFalse(VehicleFileIO.driverNameExists("Nobody"));
    }

    @Test
    @DisplayName("isSlotOccupied returns true when slot is taken")
    void isSlotOccupiedTrue() throws Exception {
        VehicleFileIO.addVehicle(new Vehicle("V001", "A", "Car", "P1", "A-1", "10:00"));
        assertTrue(VehicleFileIO.isSlotOccupied("A-1"));
    }

    @Test
    @DisplayName("isSlotOccupied returns false when slot is free")
    void isSlotOccupiedFalse() throws Exception {
        assertFalse(VehicleFileIO.isSlotOccupied("A-1"));
    }

    @Test
    @DisplayName("getAvailableSlot returns first free slot")
    void getAvailableSlotFirst() throws Exception {
        assertEquals("A-1", VehicleFileIO.getAvailableSlot());
    }

    @Test
    @DisplayName("getAvailableSlot skips occupied slots")
    void getAvailableSlotSkips() throws Exception {
        VehicleFileIO.addVehicle(new Vehicle("V001", "A", "Car", "P1", "A-1", "10:00"));
        assertEquals("A-2", VehicleFileIO.getAvailableSlot());
    }

    @Test
    @DisplayName("getAvailableSlot returns null when all slots full")
    void getAvailableSlotAllFull() throws Exception {
        String[] slots = {"A-1", "A-2", "A-3", "B-1", "B-2", "B-3"};
        for (int i = 0; i < slots.length; i++) {
            VehicleFileIO.addVehicle(new Vehicle("V00" + i, "D" + i, "Car", "P" + i, slots[i], "10:00"));
        }
        assertNull(VehicleFileIO.getAvailableSlot());
    }

    @Test
    @DisplayName("deleteVehicle removes vehicle and returns true")
    void deleteVehicleSuccess() throws Exception {
        VehicleFileIO.addVehicle(new Vehicle("V001", "A", "Car", "P1", "A-1", "10:00"));
        VehicleFileIO.addVehicle(new Vehicle("V002", "B", "Bike", "P2", "A-2", "11:00"));

        assertTrue(VehicleFileIO.deleteVehicle("V001"));
        assertEquals(1, VehicleFileIO.countRecords());
        assertFalse(VehicleFileIO.vehicleIdExists("V001"));
        assertTrue(VehicleFileIO.vehicleIdExists("V002"));
    }

    @Test
    @DisplayName("deleteVehicle returns false for non-existing ID")
    void deleteVehicleNotFound() throws Exception {
        VehicleFileIO.addVehicle(new Vehicle("V001", "A", "Car", "P1", "A-1", "10:00"));
        assertFalse(VehicleFileIO.deleteVehicle("V999"));
        assertEquals(1, VehicleFileIO.countRecords());
    }

    @Test
    @DisplayName("getAllVehicles returns all records as 2D array")
    void getAllVehicles() throws Exception {
        VehicleFileIO.addVehicle(new Vehicle("V001", "A", "Car", "P1", "A-1", "10:00"));
        VehicleFileIO.addVehicle(new Vehicle("V002", "B", "Bike", "P2", "A-2", "11:00"));

        Object[][] rows = VehicleFileIO.getAllVehicles();
        assertEquals(2, rows.length);
        assertEquals("V001", rows[0][0]);
        assertEquals("V002", rows[1][0]);
    }

    @Test
    @DisplayName("getAllVehicles returns empty array when no records")
    void getAllVehiclesEmpty() {
        Object[][] rows = VehicleFileIO.getAllVehicles();
        assertEquals(0, rows.length);
    }

    @Test
    @DisplayName("searchVehicles finds matching vehicles by keyword")
    void searchVehiclesFound() throws Exception {
        VehicleFileIO.addVehicle(new Vehicle("V001", "John", "Car", "DHK-1234", "A-1", "10:00"));
        VehicleFileIO.addVehicle(new Vehicle("V002", "Jane", "Bike", "CTG-5678", "A-2", "11:00"));

        Object[][] results = VehicleFileIO.searchVehicles("John");
        assertEquals(1, results.length);
        assertEquals("V001", results[0][0]);
    }

    @Test
    @DisplayName("searchVehicles is case-insensitive")
    void searchVehiclesCaseInsensitive() throws Exception {
        VehicleFileIO.addVehicle(new Vehicle("V001", "John", "Car", "DHK-1234", "A-1", "10:00"));

        Object[][] results = VehicleFileIO.searchVehicles("john");
        assertEquals(1, results.length);
    }

    @Test
    @DisplayName("searchVehicles returns empty array when no match")
    void searchVehiclesNotFound() throws Exception {
        VehicleFileIO.addVehicle(new Vehicle("V001", "John", "Car", "DHK-1234", "A-1", "10:00"));

        Object[][] results = VehicleFileIO.searchVehicles("NoMatch");
        assertEquals(0, results.length);
    }

    @Test
    @DisplayName("searchVehicles matches on vehicle ID")
    void searchByVehicleId() throws Exception {
        VehicleFileIO.addVehicle(new Vehicle("V001", "John", "Car", "DHK-1234", "A-1", "10:00"));
        VehicleFileIO.addVehicle(new Vehicle("V002", "Jane", "Bike", "CTG-5678", "A-2", "11:00"));

        Object[][] results = VehicleFileIO.searchVehicles("V002");
        assertEquals(1, results.length);
        assertEquals("V002", results[0][0]);
    }

    @Test
    @DisplayName("searchVehicles matches on number plate")
    void searchByPlate() throws Exception {
        VehicleFileIO.addVehicle(new Vehicle("V001", "John", "Car", "DHK-1234", "A-1", "10:00"));

        Object[][] results = VehicleFileIO.searchVehicles("DHK");
        assertEquals(1, results.length);
    }
}

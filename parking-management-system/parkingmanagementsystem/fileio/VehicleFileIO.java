package parkingmanagementsystem.fileio;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import parkingmanagementsystem.entity.Vehicle;

public class VehicleFileIO {

    private static final String FILE_NAME = "parkingmanagementsystem/fileio/vehicles.txt";
    private static final String TEMP_FILE = "parkingmanagementsystem/fileio/temp.txt";
    private static final String[] SLOTS   = { "A-1", "A-2", "A-3", "B-1", "B-2", "B-3" };

    public static void createFileIfNotExists() throws IOException {
        File f = new File(FILE_NAME);
        if (!f.exists()) f.createNewFile();
    }

    // ── shared file-scan utilities ──────────────────────────────────

    /**
     * Reads every Vehicle record from the file and returns those that
     * satisfy the given predicate.
     */
    private static List<Vehicle> readVehicles(Predicate<Vehicle> filter) {
        List<Vehicle> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                Vehicle v = Vehicle.fromLine(line);
                if (v != null && filter.test(v)) {
                    result.add(v);
                }
            }
        } catch (IOException ignored) {}
        return result;
    }

    /** Returns true when at least one record matches the predicate. */
    private static boolean anyMatch(Predicate<Vehicle> filter) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                Vehicle v = Vehicle.fromLine(line);
                if (v != null && filter.test(v)) return true;
            }
        } catch (IOException ignored) {}
        return false;
    }

    private static Object[][] toRowArray(List<Vehicle> vehicles) {
        Object[][] rows = new Object[vehicles.size()][6];
        for (int i = 0; i < vehicles.size(); i++) {
            rows[i] = vehicles.get(i).toRow();
        }
        return rows;
    }

    // ── public API (now delegates to shared utilities) ──────────────

    public static String getAvailableSlot() {
        for (String slot : SLOTS) {
            if (!isSlotOccupied(slot)) return slot;
        }
        return null;
    }

    public static boolean isSlotOccupied(String slot) {
        return anyMatch(v -> v.getParkingSlot().equals(slot));
    }

    public static boolean vehicleIdExists(String vehicleId) {
        return anyMatch(v -> v.getVehicleId().equals(vehicleId));
    }

    public static boolean plateExists(String numberPlate) {
        return anyMatch(v -> v.getNumberPlate().equalsIgnoreCase(numberPlate));
    }

    public static boolean driverNameExists(String driverName) {
        return anyMatch(v -> v.getDriverName().equalsIgnoreCase(driverName));
    }

    public static int countRecords() {
        return readVehicles(v -> true).size();
    }

    public static void addVehicle(Vehicle v) throws IOException {
        try (PrintWriter pw = new PrintWriter(
                new BufferedWriter(new FileWriter(FILE_NAME, true)))) {
            pw.println(v.toLine());
        }
    }

    public static boolean deleteVehicle(String vehicleId) throws IOException {
        File inputFile = new File(FILE_NAME);
        File tempFile  = new File(TEMP_FILE);
        boolean found  = false;

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                Vehicle v = Vehicle.fromLine(line);
                if (v != null && v.getVehicleId().equals(vehicleId)) {
                    found = true;
                    continue;
                }
                bw.write(line);
                bw.newLine();
            }
        }

        if (found) {
            if (!inputFile.delete() || !tempFile.renameTo(inputFile))
                throw new IOException("Could not finalise delete operation.");
        } else {
            tempFile.delete();
        }
        return found;
    }

    public static Object[][] getAllVehicles() {
        return toRowArray(readVehicles(v -> true));
    }

    public static Object[][] searchVehicles(String keyword) {
        String kw = keyword.toLowerCase();
        return toRowArray(readVehicles(v -> matches(v, kw)));
    }

    private static boolean matches(Vehicle v, String kw) {
        return v.getVehicleId().toLowerCase().contains(kw)
            || v.getDriverName().toLowerCase().contains(kw)
            || v.getNumberPlate().toLowerCase().contains(kw);
    }
}

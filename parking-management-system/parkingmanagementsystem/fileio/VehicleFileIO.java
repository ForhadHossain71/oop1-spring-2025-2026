package parkingmanagementsystem.fileio;

import java.io.*;
import parkingmanagementsystem.entity.Vehicle;

public class VehicleFileIO {

    private static final String FILE_NAME = "parkingmanagementsystem/fileio/vehicles.txt";
    private static final String TEMP_FILE = "parkingmanagementsystem/fileio/temp.txt";
    private static final String[] SLOTS   = { "A-1", "A-2", "A-3", "B-1", "B-2", "B-3" };

    public static void createFileIfNotExists() throws IOException {
        File f = new File(FILE_NAME);
        if (!f.exists()) f.createNewFile();
}

    public static String getAvailableSlot() throws IOException {
        for (String slot : SLOTS) {
            if (!isSlotOccupied(slot)) return slot;
        }
        return null;
    }

    public static boolean isSlotOccupied(String slot) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                Vehicle v = Vehicle.fromLine(line);
                if (v != null && v.getParkingSlot().equals(slot)) return true;
            }
        }
        return false;
    }

    public static boolean vehicleIdExists(String vehicleId) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                Vehicle v = Vehicle.fromLine(line);
                if (v != null && v.getVehicleId().equals(vehicleId)) return true;
            }
        }
        return false;
    }

    public static boolean plateExists(String numberPlate) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                Vehicle v = Vehicle.fromLine(line);
                if (v != null && v.getNumberPlate().equalsIgnoreCase(numberPlate)) return true;
            }
        }
        return false;
    }

    public static boolean driverNameExists(String driverName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                Vehicle v = Vehicle.fromLine(line);
                if (v != null && v.getDriverName().equalsIgnoreCase(driverName)) return true;
            }
        }
        return false;
    }

    public static int countRecords() throws IOException {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (Vehicle.fromLine(line) != null) count++;
            }
        }
        return count;
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

    public static Object[][] getAllVehicles() throws IOException {
        int total       = countRecords();
        Object[][] rows = new Object[total][6];
        int idx         = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null && idx < total) {
                Vehicle v = Vehicle.fromLine(line);
                if (v != null) {
                    Object[] row = v.toRow();
                    for (int c = 0; c < 6; c++) rows[idx][c] = row[c];
                    idx++;
                }
            }
        }
        return rows;
    }


    public static Object[][] searchVehicles(String keyword) throws IOException {
        String kw = keyword.toLowerCase();

        int matchCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                Vehicle v = Vehicle.fromLine(line);
                if (v != null && matches(v, kw)) matchCount++;
            }
        }

        Object[][] results = new Object[matchCount][6];
        int idx = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null && idx < matchCount) {
                Vehicle v = Vehicle.fromLine(line);
                if (v != null && matches(v, kw)) {
                    Object[] row = v.toRow();
                    for (int c = 0; c < 6; c++) results[idx][c] = row[c];
                    idx++;
                }
            }
        }
        return results;
    }

    private static boolean matches(Vehicle v, String kw) {
        return v.getVehicleId().toLowerCase().contains(kw) || v.getDriverName().toLowerCase().contains(kw) || v.getNumberPlate().toLowerCase().contains(kw);
    }
}

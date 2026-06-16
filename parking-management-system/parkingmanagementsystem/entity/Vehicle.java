package parkingmanagementsystem.entity;

public class Vehicle {

    private String vehicleId;
    private String driverName;
    private String vehicleType;
    private String numberPlate;
    private String parkingSlot;
    private String entryTime;

    public Vehicle(String vehicleId, String driverName, String vehicleType, String numberPlate, String parkingSlot, String entryTime) {
        this.vehicleId   = vehicleId;
        this.driverName  = driverName;
        this.vehicleType = vehicleType;
        this.numberPlate = numberPlate;
        this.parkingSlot = parkingSlot;
        this.entryTime   = entryTime;
    }

    public String getVehicleId(){ 
        return vehicleId; 
    }
    public String getDriverName(){ 
        return driverName; 
    }
    public String getVehicleType(){ 
        return vehicleType; 
    }
    public String getNumberPlate(){
        return numberPlate; 
    }
    public String getParkingSlot(){
        return parkingSlot; 
    }
    public String getEntryTime(){
         return entryTime; 
        }

    public void setVehicleId(String vehicleId){
         this.vehicleId   = vehicleId; 
        }
    public void setDriverName(String driverName){
         this.driverName  = driverName; 
        }
    public void setVehicleType(String vehicleType){ 
        this.vehicleType = vehicleType; 
    }
    public void setNumberPlate(String numberPlate){ 
        this.numberPlate = numberPlate; 
    }
    public void setParkingSlot(String parkingSlot){
         this.parkingSlot = parkingSlot; 
        }
    public void setEntryTime(String entryTime){
         this.entryTime   = entryTime; 
        }

    public String toLine() {
        return escape(vehicleId) + "," + escape(driverName) + "," + escape(vehicleType)
             + "," + escape(numberPlate) + "," + escape(parkingSlot) + "," + escape(entryTime);
    }

    public static Vehicle fromLine(String line) {
        if (line == null || line.trim().isEmpty())
            return null;
        String[] data = splitCsv(line);
        if (data.length != 6)
            return null;
        return new Vehicle(data[0], data[1], data[2], data[3], data[4], data[5]);
    }

    private static String escape(String field) {
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }

    private static String[] splitCsv(String line) {
        java.util.List<String> fields = new java.util.ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (inQuotes) {
                if (c == '"' && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else if (c == '"') {
                    inQuotes = false;
                } else {
                    current.append(c);
                }
            } else {
                if (c == '"') {
                    inQuotes = true;
                } else if (c == ',') {
                    fields.add(current.toString());
                    current.setLength(0);
                } else {
                    current.append(c);
                }
            }
        }
        fields.add(current.toString());
        return fields.toArray(new String[0]);
    }

    public Object[] toRow() {
        return new Object[]{ vehicleId, driverName, vehicleType, numberPlate, parkingSlot, entryTime };
    }
}

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
        return vehicleId + "," + driverName + "," + vehicleType + "," + numberPlate + "," + parkingSlot + "," + entryTime;
    }

    public static Vehicle fromLine(String line) {
        if (line == null || line.trim().isEmpty())
            return null;
        String[] data = line.split(",", -1);
        if (data.length != 6)
            return null;
        return new Vehicle(data[0], data[1], data[2], data[3], data[4], data[5]);
    }

    public Object[] toRow() {
        return new Object[]{ vehicleId, driverName, vehicleType, numberPlate, parkingSlot, entryTime };
    }
}

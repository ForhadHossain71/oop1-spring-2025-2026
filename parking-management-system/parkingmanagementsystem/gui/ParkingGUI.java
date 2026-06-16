package parkingmanagementsystem.gui;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import parkingmanagementsystem.entity.Vehicle;
import parkingmanagementsystem.fileio.VehicleFileIO;

public class ParkingGUI extends JFrame {
    private JTextField vehicleIdField;   
    private JTextField driverNameField; 
    private JComboBox<String> vehicleTypeCombo;
    private JComboBox<String> districtCombo;    
    private JComboBox<String> seriesCombo;      
    private JTextField plateNum1;        
    private JTextField plateNum2;       
    private JTextField parkingSlotField; 
    private JTextField entryTimeDisplay; 
    private JTextField searchField;    
    private JTable table;
    private DefaultTableModel tableModel;

    public ParkingGUI() {
        setTitle("OOP1");
        setSize(960, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(6, 6));

        JLabel titleLabel = new JLabel("PARKING MANAGEMENT SYSTEM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 6, 0));

        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Vehicle"));
        searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JButton searchBtn = new JButton("Search");
        searchBtn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchBtn,   BorderLayout.EAST);

        JPanel northPanel = new JPanel(new BorderLayout(4, 4));
        northPanel.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
        northPanel.add(titleLabel,  BorderLayout.NORTH);
        northPanel.add(searchPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 8, 8));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(6, 8, 6, 4),
                BorderFactory.createTitledBorder("Actions")));

        JButton entryBtn   = new JButton("Vehicle Entry");
        JButton exitBtn    = new JButton("Vehicle Exit");
        JButton viewAllBtn = new JButton("View All");
        JButton clearBtn   = new JButton("Clear");
        JButton deleteBtn  = new JButton("Delete");

        Font btnFont = new Font("SansSerif", Font.PLAIN, 13);
        entryBtn.setFont(btnFont);
        exitBtn.setFont(btnFont);
        viewAllBtn.setFont(btnFont);
        clearBtn.setFont(btnFont);
        deleteBtn.setFont(btnFont);

        buttonPanel.add(entryBtn);
        buttonPanel.add(exitBtn);
        buttonPanel.add(viewAllBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(deleteBtn);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(6, 4, 6, 8),
                BorderFactory.createTitledBorder("Vehicle Information")));

        Font labelFont = new Font("SansSerif", Font.PLAIN, 13);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 13);

        GridBagConstraints lc = new GridBagConstraints();
        lc.gridx   = 0;
        lc.anchor  = GridBagConstraints.WEST;
        lc.insets  = new Insets(6, 8, 6, 6);

        GridBagConstraints fc = new GridBagConstraints();
        fc.gridx    = 1;
        fc.fill     = GridBagConstraints.HORIZONTAL;
        fc.weightx  = 1.0;
        fc.gridwidth = GridBagConstraints.REMAINDER;
        fc.insets   = new Insets(6, 0, 6, 8);

        lc.gridy = 0; fc.gridy = 0;
        JLabel idLabel = new JLabel("Vehicle ID (8 digits):");
        idLabel.setFont(labelFont);
        vehicleIdField = new JTextField();
        vehicleIdField.setFont(fieldFont);
        formPanel.add(idLabel,        lc);
        formPanel.add(vehicleIdField, fc);

        lc.gridy = 1; fc.gridy = 1;
        JLabel nameLabel = new JLabel("Owner Name:");
        nameLabel.setFont(labelFont);
        driverNameField = new JTextField();
        driverNameField.setFont(fieldFont);
        formPanel.add(nameLabel,        lc);
        formPanel.add(driverNameField,  fc);

        lc.gridy = 2; fc.gridy = 2;
        JLabel typeLabel = new JLabel("Vehicle Type:");
        typeLabel.setFont(labelFont);
        vehicleTypeCombo = new JComboBox<>(new String[]{ "Bike", "Car" });
        vehicleTypeCombo.setFont(fieldFont);
        formPanel.add(typeLabel,       lc);
        formPanel.add(vehicleTypeCombo, fc);

        lc.gridy = 3;
        JLabel plateLabel = new JLabel("Number Plate:");
        plateLabel.setFont(labelFont);
        formPanel.add(plateLabel, lc);

        String[] districts = { "Dhaka", "Chittagong", "Rajshahi", "Sylhet", "Khulna", "Barishal", "Rangpur", "Mymensingh"};
        String[] series = { "A", "BHA", "LA", "CHA", "HA", "KA"};

        districtCombo = new JComboBox<>(districts);
        seriesCombo   = new JComboBox<>(series);
        plateNum1     = new JTextField("",   4); 
        plateNum1.setPreferredSize(new Dimension(80, 28));
        plateNum2     = new JTextField("", 6);
        plateNum2.setPreferredSize(new Dimension(120, 28));   

        districtCombo.setFont(fieldFont);
        seriesCombo.setFont(fieldFont);
        plateNum1.setFont(fieldFont);
        plateNum2.setFont(fieldFont);

        JLabel dashLabel = new JLabel("-");
        dashLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JPanel platePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        platePanel.add(districtCombo);
        platePanel.add(new JLabel("-"));
        platePanel.add(seriesCombo);
        platePanel.add(new JLabel("-"));
        platePanel.add(plateNum1);
        platePanel.add(dashLabel);
        platePanel.add(plateNum2);

        GridBagConstraints plateFc = new GridBagConstraints();
        plateFc.gridx    = 1;
        plateFc.gridy    = 3;
        plateFc.fill     = GridBagConstraints.HORIZONTAL;
        plateFc.weightx  = 1.0;
        plateFc.gridwidth = GridBagConstraints.REMAINDER;
        plateFc.insets   = new Insets(6, 0, 6, 8);
        formPanel.add(platePanel, plateFc);

        lc.gridy = 4; fc.gridy = 4;
        JLabel slotLabel = new JLabel("Parking Slot:");
        slotLabel.setFont(labelFont);
        parkingSlotField = new JTextField("Auto Generated");
        parkingSlotField.setFont(fieldFont);
        parkingSlotField.setEditable(false);
        parkingSlotField.setBackground(new Color(238, 238,  238));
        formPanel.add(slotLabel,       lc);
        formPanel.add(parkingSlotField, fc);

        lc.gridy = 5; fc.gridy = 5;
        JLabel timeLabel = new JLabel("Entry Time:");
        timeLabel.setFont(labelFont);
        entryTimeDisplay = new JTextField("Auto Generated");
        entryTimeDisplay.setFont(fieldFont);
        entryTimeDisplay.setEditable(false);
        entryTimeDisplay.setBackground(new Color(238, 238, 238));
        formPanel.add(timeLabel,        lc);
        formPanel.add(entryTimeDisplay, fc);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 6, 6));
        centerPanel.add(buttonPanel);
        centerPanel.add(formPanel);

        String[] columns = { "Vehicle ID", "Owner Name", "Vehicle Type", "Number Plate", "Parking Slot", "Entry Time" };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(22);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 8, 8, 8),
                BorderFactory.createTitledBorder("Parked Vehicles")));
        scrollPane.setPreferredSize(new Dimension(0, 210));

        add(northPanel,  BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(scrollPane,  BorderLayout.SOUTH);

        entryBtn.addActionListener(e   -> vehicleEntry());
        exitBtn.addActionListener(e    -> vehicleExit());
        deleteBtn.addActionListener(e  -> deleteVehicle());
        viewAllBtn.addActionListener(e -> { searchField.setText(""); viewAll(); });
        clearBtn.addActionListener(e   -> clearFields());
        searchBtn.addActionListener(e  -> searchVehicle());
        searchField.addActionListener(e -> searchVehicle());

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                vehicleIdField.setText(String.valueOf(tableModel.getValueAt(row, 0)));
                driverNameField.setText(String.valueOf(tableModel.getValueAt(row, 1)));
                vehicleTypeCombo.setSelectedItem(tableModel.getValueAt(row, 2));
                parkingSlotField.setText(String.valueOf(tableModel.getValueAt(row, 4)));
                entryTimeDisplay.setText(String.valueOf(tableModel.getValueAt(row, 5)));

                String fullPlate = String.valueOf(tableModel.getValueAt(row, 3));
                parsePlateIntoFields(fullPlate);
            }
        });

        try {
            VehicleFileIO.createFileIfNotExists();
        } catch (IOException ex) {
            showError("Error creating data file: " + ex.getMessage());
        }

        refreshSlot();
        viewAll();

        setLocationRelativeTo(null); 
        setVisible(true);
    }


    private String buildNumberPlate() {
        String district = (String) districtCombo.getSelectedItem();
        String series   = (String) seriesCombo.getSelectedItem();
        String n1       = plateNum1.getText().trim(); 
        String n2       = plateNum2.getText().trim();
        return district + "-" + series + "-" + n1 + "-" + n2;
    }


    private void parsePlateIntoFields(String fullPlate) {
        String[] parts = fullPlate.split("-", 4);
        if (parts.length == 4) {
            districtCombo.setSelectedItem(parts[0]);
            seriesCombo.setSelectedItem(parts[1]);
            plateNum1.setText(parts[2]);
            plateNum2.setText(parts[3]);
        } else {
            plateNum1.setText("");
            plateNum2.setText(fullPlate);
        }
    }
    private void refreshSlot() {
        String slot = VehicleFileIO.getAvailableSlot();
        parkingSlotField.setText(slot != null ? slot : "FULL - No Slot Available");
    }

    private boolean isValidId(String id) {
        if (id.isEmpty()) {
            showError("Vehicle ID is required!");
            return false;
        }
        if (!id.matches("\\d{8}")) {
            showError("Vehicle ID must be exactly 8 digits (numbers only)." + System.lineSeparator() + "Minimum: 8 digits  |  Maximum: 8 digits" + System.lineSeparator() + "Example: 20012005");
            return false;
        }
        return true;
    }

    private boolean isValidName(String name) {
        if (name.isEmpty()) {
            showError("Owner Name is required!");
            return false;
        }
        if (!name.matches("[A-Za-z]+(\\s[A-Za-z]+)*")) {
            showError("Owner Name must contain letters only (no digits or symbols)."+ System.lineSeparator()+ "Example: Forhad Hossain");
            return false;
        }
        return true;
    }
    private boolean isValidPlateNumbers(String n1, String n2) {
        if (n1.isEmpty() || n2.isEmpty()) {
            showError("Both number plate number fields are required!" + System.lineSeparator() + "Format: District - Series - NN - NNNN" +  System.lineSeparator()+ "Example: Dhaka - LA - 11 - 2233");
            return false;
        }
        if (!n1.matches("\\d{2}")) {
            showError("First plate number must be exactly 2 digits."+ System.lineSeparator()+"Example: 11");
            return false;
        }
        if (!n2.matches("\\d{4}")) {
            showError("Second plate number must be exactly 4 digits."+ System.lineSeparator()+ "Example: 2233");
            return false;
        }
        return true;
    }

    private void vehicleEntry() {
        String id   = vehicleIdField.getText().trim();
        String name = toTitleCase(driverNameField.getText().trim());
        String type = (String) vehicleTypeCombo.getSelectedItem();
        String n1   = plateNum1.getText().trim();
        String n2   = plateNum2.getText().trim();

        if (id.isEmpty() || name.isEmpty() || n1.isEmpty() || n2.isEmpty()) {
            showError("All fields are required!");
            return;
        }
        if (!isValidId(id))                return;
        if (!isValidName(name))            return;
        if (!isValidPlateNumbers(n1, n2))  return;

        String plate = buildNumberPlate();

        // Check parking capacity
        String slot = VehicleFileIO.getAvailableSlot();
        if (slot == null) {
            showError("Parking lot is FULL! All 6 slots are occupied."+ System.lineSeparator()+ "A vehicle must exit before a new one can enter.");
            return;
        }

        // Duplicate checks
        if (VehicleFileIO.vehicleIdExists(id)) {
            showError("Duplicate Vehicle ID! A vehicle with ID " + id + " is already parked.");
            return;
        }
        if (VehicleFileIO.driverNameExists(name)) {
            showError("Duplicate Owner Name!\"" + name + "\" already has a vehicle parked."+ System.lineSeparator() + "The same owner cannot park two vehicles at once.");
            return;
        }
        if (VehicleFileIO.plateExists(plate)) {
            showError("Duplicate Number Plate!\nA vehicle with plate " + plate + " is already parked.");
            return;
        }

        String entryTime = new SimpleDateFormat("dd-MM-yyyy hh:mm a").format(new Date());
        int    entryFee  = type.equals("Bike") ? 30 : 40;

        Vehicle v = new Vehicle(id, name, type, plate, slot, entryTime);
        try {
            VehicleFileIO.addVehicle(v);
            showInfo("Vehicle Entry Successful!\n\n"
                   + "Vehicle ID   : " + id        +System.lineSeparator()
                   + "Owner Name   : " + name      +System.lineSeparator()
                   + "Vehicle Type : " + type      + System.lineSeparator()
                   + "Number Plate : " + plate     + System.lineSeparator()
                   + "Parking Slot : " + slot      + System.lineSeparator()
                   + "Entry Time   : " + entryTime + System.lineSeparator()
                   + "Entry Fee    : " + entryFee  + " TK");
            clearFields();
            viewAll();
        } catch (IOException ex) {
            showError("Error saving record: " + ex.getMessage());
        }
    }

    private void vehicleExit() {
        int row = table.getSelectedRow();
        if (row < 0) {
            showError("Please select a vehicle from the table to process exit.");
            return;
        }

        String id       = String.valueOf(tableModel.getValueAt(row, 0));
        String name     = String.valueOf(tableModel.getValueAt(row, 1));
        String type     = String.valueOf(tableModel.getValueAt(row, 2));
        String plate    = String.valueOf(tableModel.getValueAt(row, 3));
        String slot     = String.valueOf(tableModel.getValueAt(row, 4));
        String entryStr = String.valueOf(tableModel.getValueAt(row, 5));

        long totalMinutes = calcMinutesParked(entryStr);
        long hours        = Math.max(1, (totalMinutes + 59) / 60);
        int  entryFee     = type.equals("Bike") ? 30 : 40;
        long hourlyCharge = hours * 10;
        long totalCharge  = entryFee + hourlyCharge;

        int confirm = JOptionPane.showConfirmDialog(this,
                " VEHICLE EXIT SUMMARY\n\n"
              + "Vehicle ID   : " + id            + System.lineSeparator()
              + "Owner Name   : " + name          + System.lineSeparator()
              + "Vehicle Type : " + type          + System.lineSeparator()
              + "Number Plate : " + plate         + System.lineSeparator()
              + "Parking Slot : " + slot          + System.lineSeparator()
              + "Entry Time   : " + entryStr      + System.lineSeparator()
              + "Duration     : " + hours         + " hour(s)\n\n"
              + "Entry Fee    : " + entryFee      + " TK\n"
              + "Hourly Fee   : " + hourlyCharge  + " TK  (" + hours + " hr x 10 TK)\n"
              + "TOTAL CHARGE : " + totalCharge   + " TK\n\n"
              + "Confirm vehicle exit and collect payment?",
                "Vehicle Exit - Confirm",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            boolean deleted = VehicleFileIO.deleteVehicle(id);
            if (deleted) {
                showInfo("Vehicle exited successfully!\nTotal charged: " + totalCharge + " TK");
                clearFields();
                viewAll();
            } else {
                showError("Vehicle ID not found in database!");
            }
        } catch (IOException ex) {
            showError("Error processing exit: " + ex.getMessage());
        }
    }

    /**
     * Delete — administrative removal of a selected record with confirmation.
     */
    private void deleteVehicle() {
        int row = table.getSelectedRow();
        if (row < 0) {
            showError("Please select a vehicle from the table to delete.");
            return;
        }

        String id   = String.valueOf(tableModel.getValueAt(row, 0));
        String name = String.valueOf(tableModel.getValueAt(row, 1));

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this record?\n"+ System.lineSeparator()+ "Vehicle ID : " + id   + System.lineSeparator() + "Owner Name : " + name,
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            boolean deleted = VehicleFileIO.deleteVehicle(id);
            if (deleted) {
                showInfo("Record deleted successfully!");
                clearFields();
                viewAll();
            } else {
                showError("Vehicle ID not found in database!");
            }
        } catch (IOException ex) {
            showError("Error deleting record: " + ex.getMessage());
        }
    }

    private void populateTable(Object[][] rows) {
        tableModel.setRowCount(0);
        for (Object[] r : rows) {
            if (r[0] != null) tableModel.addRow(r);
        }
    }

    private void searchVehicle() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            showError("Please enter a Vehicle ID, Owner Name, or Number Plate to search.");
            return;
        }

        Object[][] results = VehicleFileIO.searchVehicles(keyword);
        populateTable(results);

        if (results.length == 0)
            showInfo("No matching vehicle found for: \"" + keyword + "\"");
    }

    private void viewAll() {
        populateTable(VehicleFileIO.getAllVehicles());
        refreshSlot();
    }

    private void clearFields() {
        vehicleIdField.setText("");
        driverNameField.setText("");
        vehicleTypeCombo.setSelectedIndex(0);
        districtCombo.setSelectedIndex(0);
        seriesCombo.setSelectedIndex(0);
        plateNum1.setText("11");
        plateNum2.setText("2233");
        table.clearSelection();
        refreshSlot();
        entryTimeDisplay.setText("Auto Generated");
    }

    private String toTitleCase(String input) {
        if (input == null || input.isEmpty()) return input;
        String[] words = input.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)));
                sb.append(word.substring(1).toLowerCase());
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

    private long calcMinutesParked(String entryStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            Date entryDate = sdf.parse(entryStr);
            long diffMs = new Date().getTime() - entryDate.getTime();
            return diffMs / (1000 * 60);
        } catch (Exception ex) {
            return 60;
        }
    }

    private void showInfo(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Information",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentAdmissionSystem extends JFrame {

    private JTextField txtAdmissionNo;
    private JTextField txtName;
    private JTextField txtAge;
    private JTextField txtPhone;
    private JTextField txtEmail;

    private JComboBox<String> cmbGender;
    private JComboBox<String> cmbCourse;

    private JTable studentTable;
    private DefaultTableModel tableModel;

    public StudentAdmissionSystem() {

        setTitle("Student Admission Management System");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Heading
        JLabel heading = new JLabel(
                "STUDENT ADMISSION MANAGEMENT SYSTEM",
                SwingConstants.CENTER
        );

        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setForeground(new Color(25, 65, 120));
        heading.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        add(heading, BorderLayout.NORTH);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 12));
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));

        txtAdmissionNo = new JTextField();
        txtName = new JTextField();
        txtAge = new JTextField();
        txtPhone = new JTextField();
        txtEmail = new JTextField();

        cmbGender = new JComboBox<>(
                new String[]{"Select Gender", "Male", "Female", "Other"}
        );

        cmbCourse = new JComboBox<>(
                new String[]{
                        "Select Course",
                        "B.E. Information Technology",
                        "B.E. Computer Science",
                        "B.Tech Information Technology",
                        "B.Sc Computer Science",
                        "BCA",
                        "BBA"
                }
        );

        formPanel.add(new JLabel("Admission Number:"));
        formPanel.add(txtAdmissionNo);

        formPanel.add(new JLabel("Student Name:"));
        formPanel.add(txtName);

        formPanel.add(new JLabel("Age:"));
        formPanel.add(txtAge);

        formPanel.add(new JLabel("Gender:"));
        formPanel.add(cmbGender);

        formPanel.add(new JLabel("Phone Number:"));
        formPanel.add(txtPhone);

        formPanel.add(new JLabel("Email:"));
        formPanel.add(txtEmail);

        formPanel.add(new JLabel("Course:"));
        formPanel.add(cmbCourse);

        mainPanel.add(formPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {
                "Admission No",
                "Name",
                "Age",
                "Gender",
                "Phone",
                "Email",
                "Course"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(25);
        studentTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(
                BorderFactory.createTitledBorder("Admission Records")
        );

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 12, 10)
        );

        JButton btnAdd = new JButton("Add Student");
        JButton btnSearch = new JButton("Search Student");
        JButton btnDelete = new JButton("Delete Student");
        JButton btnClear = new JButton("Clear Fields");
        JButton btnView = new JButton("View All");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnView);

        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        btnAdd.addActionListener(e -> addStudent());
        btnSearch.addActionListener(e -> searchStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnClear.addActionListener(e -> clearFields());
        btnView.addActionListener(e -> viewAllStudents());

        // Configure table column widths
        int[] widths = {110, 150, 50, 80, 120, 180, 200};

        for (int i = 0; i < widths.length; i++) {
            studentTable.getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(widths[i]);
        }

        setVisible(true);
    }

    // Add student
    private void addStudent() {

        String admissionNo = txtAdmissionNo.getText().trim();
        String name = txtName.getText().trim();
        String ageText = txtAge.getText().trim();
        String gender = (String) cmbGender.getSelectedItem();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();
        String course = (String) cmbCourse.getSelectedItem();

        if (admissionNo.isEmpty() || name.isEmpty()
                || ageText.isEmpty() || phone.isEmpty()
                || email.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all student details."
            );

            return;
        }

        if (gender == null || gender.equals("Select Gender")
                || course == null || course.equals("Select Course")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a gender and course."
            );

            return;
        }

        int age;

        try {
            age = Integer.parseInt(ageText);

            if (age < 1 || age > 120) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid age."
                );
                return;
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Age must be a number."
            );

            return;
        }

        if (!phone.matches("[0-9]{10}")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid 10-digit phone number."
            );
            return;
        }

        if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid email address."
            );
            return;
        }

        // Check for duplicate admission number
        for (int i = 0; i < tableModel.getRowCount(); i++) {

            String existingNo =
                    tableModel.getValueAt(i, 0).toString();

            if (existingNo.equalsIgnoreCase(admissionNo)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Admission number already exists."
                );

                return;
            }
        }

        tableModel.addRow(new Object[]{
                admissionNo,
                name,
                age,
                gender,
                phone,
                email,
                course
        });

        JOptionPane.showMessageDialog(
                this,
                "Student admitted successfully!"
        );

        clearFields();
    }

    // Search student
    private void searchStudent() {

        String admissionNo =
                txtAdmissionNo.getText().trim();

        if (admissionNo.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Enter an admission number to search."
            );
            return;
        }

        for (int i = 0; i < tableModel.getRowCount(); i++) {

            String existingNo =
                    tableModel.getValueAt(i, 0).toString();

            if (existingNo.equalsIgnoreCase(admissionNo)) {

                studentTable.setRowSelectionInterval(i, i);

                studentTable.scrollRectToVisible(
                        studentTable.getCellRect(i, 0, true)
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Student found: "
                                + tableModel.getValueAt(i, 1)
                );

                return;
            }
        }

        JOptionPane.showMessageDialog(
                this,
                "Student not found."
        );
    }

    // Delete student
    private void deleteStudent() {

        int selectedRow = studentTable.getSelectedRow();

        if (selectedRow == -1) {

            String admissionNo =
                    txtAdmissionNo.getText().trim();

            if (!admissionNo.isEmpty()) {

                for (int i = 0; i < tableModel.getRowCount(); i++) {

                    String existingNo =
                            tableModel.getValueAt(i, 0).toString();

                    if (existingNo.equalsIgnoreCase(admissionNo)) {
                        selectedRow = i;
                        break;
                    }
                }
            }
        }

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select a student row or enter an admission number."
            );

            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Do you want to delete this student record?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmation == JOptionPane.YES_OPTION) {

            tableModel.removeRow(selectedRow);

            JOptionPane.showMessageDialog(
                    this,
                    "Student record deleted successfully."
            );

            clearFields();
        }
    }

    // Clear fields
    private void clearFields() {

        txtAdmissionNo.setText("");
        txtName.setText("");
        txtAge.setText("");
        txtPhone.setText("");
        txtEmail.setText("");

        cmbGender.setSelectedIndex(0);
        cmbCourse.setSelectedIndex(0);

        studentTable.clearSelection();
    }

    // View all students
    private void viewAllStudents() {

        studentTable.clearSelection();

        if (tableModel.getRowCount() == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "No student records available."
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Total admitted students: "
                            + tableModel.getRowCount()
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                StudentAdmissionManagementLauncher::start
        );
    }

    private static class StudentAdmissionManagementLauncher {

        static void start() {
            new StudentAdmissionSystem();
        }
    }
}
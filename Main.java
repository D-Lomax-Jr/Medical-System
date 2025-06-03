import java.util.*;
import javax.swing.*;

class HospitalManagementSystem {
    private List<Patient> patients = new ArrayList<>();
    private HashMap<Integer, Doctor> doctors = new HashMap<>();
    private PriorityQueue<Patient> patientPriorityQueue = new PriorityQueue<>(Comparator.comparingInt(Patient::getPriority));
    private List<Appointment> appointments = new ArrayList<>();

    public void addPatient(int id, String name, int age, String diagnosis, int priority) {
        Patient patient = new Patient(id, name, age, diagnosis, priority);
        patients.add(patient);
        patientPriorityQueue.add(patient);
        //JOptionPane.showMessageDialog(null, "Patient added: " + name, "New Patient", JOptionPane.OK_OPTION);
        System.out.println("Patient added: " + name);
    }

    public void addDoctor(int id, String name, String specialization, String availableHours) {
        doctors.put(id, new Doctor(id, name, specialization, availableHours));
        //JOptionPane.showMessageDialog(null, "Doctor added: " + name, "New Doctor", JOptionPane.OK_OPTION);
        System.out.println("Doctor added: " + name);
    }

    public void assignPatientToDoctor(int patientId, int doctorId) {
        Patient patient = findPatientById(patientId);
        Doctor doctor = doctors.get(doctorId);

        if (patient == null) {
            JOptionPane.showMessageDialog(null, "Invalid patient ID: " + patientId, "Patient ID Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Invalid patient ID: " + patientId);
            return;
        }
        if (doctor == null) {
            JOptionPane.showMessageDialog(null, "Invalid doctor ID: " + doctorId, "Doctor ID Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Invalid doctor ID: " + doctorId);
            return;
        }

        // Create an appointment (no specific date/time given in this logic)
        Appointment appointment = new Appointment(patient, doctor);
        appointments.add(appointment);
        JOptionPane.showMessageDialog(null, "Assigned " + patient.getName() + " to " + doctor.getName(), "Appointment Creation", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("Assigned " + patient.getName() + " to " + doctor.getName());
    }

    public void deletePatient(int patientId) {
        Patient patient = findPatientById(patientId);
        if (patient != null) {
            // Remove the patient from the list and priority queue
            patients.remove(patient);
            patientPriorityQueue.remove(patient);

            // Remove all appointments associated with the patient
            appointments.removeIf(appointment -> appointment.toString().contains(patient.getName()));

            JOptionPane.showMessageDialog(null, "Patient " + patient.getName() + " and their appointments have been deleted.", "Deleted Patient", JOptionPane.OK_OPTION);
            System.out.println("Patient " + patient.getName() + " and their appointments have been deleted.");
            printAllPatients(); // Print updated patient list
        } else {
            JOptionPane.showMessageDialog(null, "Patient ID " + patientId + " not found.", "Unknown Patient ID", JOptionPane.ERROR_MESSAGE);
            System.out.println("Patient ID " + patientId + " not found.");
        }
    }

    public void searchPatient(int id) {
        Patient patient = findPatientById(id);
        if (patient != null) {
            JOptionPane.showMessageDialog(null, "Found: " + patient, "Patient Lookup", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Found: " + patient);
        } else {
            JOptionPane.showMessageDialog(null, "Patient not found.", "Unknown/Missing Patient", JOptionPane.OK_OPTION);
            System.out.println("Patient not found.");
        }
    }

    public void searchDoctor(int id) {
        Doctor doctor = doctors.get(id);
        if (doctor != null) {
            JOptionPane.showMessageDialog(null, "Found: " + doctor, "Doctor Lookup", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Found: " + doctor);
        } else {
            JOptionPane.showMessageDialog(null, "Doctor not found.", "Unknown/Missing Doctor", JOptionPane.OK_OPTION);
            System.out.println("Doctor not found.");
        }
    }

    public void processPatientQueue() {
        while (!patientPriorityQueue.isEmpty()) {
            Patient patient = patientPriorityQueue.poll();
            JOptionPane.showMessageDialog(null, "Processing patient: " + patient, "Patient Priority Processing", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Processing patient: " + patient);
        }
    }

    public void printAllPatients() {
        JOptionPane.showMessageDialog(null, "Listing Current Patients to Console", "Patient Priority Processing", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("Current Patients:");
        for (Patient patient : patients) {
            System.out.println(patient);
        }
    }

    private Patient findPatientById(int id) {
        for (Patient patient : patients) {
            if (patient.getId() == id) {
                return patient;
            }
        }
        return null;
    }

    public void displayAppointmentsByDoctor(int doctorId) {
        Doctor doctor = doctors.get(doctorId);
        if (doctor == null) {
            JOptionPane.showMessageDialog(null, "Doctor not found.", "Unknown/Missing Doctor", JOptionPane.OK_OPTION);
            System.out.println("Doctor not found.");
            return;
        }

        JOptionPane.showMessageDialog(null, "Appointment(s) for " + doctor.getName() + ".", "Appointment Management", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("\nAppointments for " + doctor.getName() + ":");
        boolean found = false;

        for (Appointment appointment : appointments) {
            if (appointment.toString().contains(doctor.getName())) { // Check if the appointment involves the given doctor
                JOptionPane.showMessageDialog(null, appointment, "Appointment Management", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(appointment);
                found = true;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(null, "No appointments found for " + doctor.getName(), "Appointment Management", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("No appointments found for " + doctor.getName());
        }
    }


    public static void main(String[] args) {
        HospitalManagementSystem hospital = new HospitalManagementSystem();
        Scanner scanner = new Scanner(System.in);

        // Adding doctors interactively
        JOptionPane.showMessageDialog(null, "Enter doctor details (type 'done' to finish):", "Doctor Addition Instructions", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("Enter doctor details (type 'done' to finish):");
        while (true) {
            System.out.print("\nEnter doctor ID: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) break;

            int id = Integer.parseInt(input);
            System.out.print("Enter doctor name: ");
            String name = scanner.nextLine();
            System.out.print("Enter specialization: ");
            String specialization = scanner.nextLine();
            System.out.print("Enter available hours: ");
            String availableHours = scanner.nextLine();

            JOptionPane.showMessageDialog(null, "Doctor added: " + name, "New Doctor", JOptionPane.INFORMATION_MESSAGE);

            hospital.addDoctor(id, name, specialization, availableHours);
        }

        // Adding patients interactively
        JOptionPane.showMessageDialog(null, "Enter patient details (type 'done' to finish):", "Patient Addition Instructions", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("\nEnter patient details (type 'done' to finish):");
        while (true) {
            System.out.print("\nEnter patient ID: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) break;

            int id = Integer.parseInt(input);
            System.out.print("Enter patient name: ");
            String name = scanner.nextLine();
            System.out.print("Enter patient age: ");
            int age = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter diagnosis: ");
            String diagnosis = scanner.nextLine();
            System.out.print("Enter priority (lower value = higher priority): ");
            int priority = Integer.parseInt(scanner.nextLine());

            JOptionPane.showMessageDialog(null, "Patient added: " + name, "New Patient", JOptionPane.INFORMATION_MESSAGE);

            hospital.addPatient(id, name, age, diagnosis, priority);
        }

        // Process patients by priority
        JOptionPane.showMessageDialog(null, "Processing patients by priority...", "Patient Priority Processing", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("\nProcessing patients by priority:");
        hospital.processPatientQueue();


        // Assigning patients to doctors
        JOptionPane.showMessageDialog(null, "Assign patients to doctors (type 'done' to finish):", "Appointment Creation", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("\nAssign patients to doctors (type 'done' to finish):");
        while (true) {
            System.out.print("\nEnter patient ID: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) break;

            int patientId = Integer.parseInt(input);
            System.out.print("Enter doctor ID: ");
            int doctorId = Integer.parseInt(scanner.nextLine());

            hospital.assignPatientToDoctor(patientId, doctorId);
        }

        // Displaying appointments
        System.out.println("\nAll appointments:");
        hospital.appointments.forEach(System.out::println);

        // Searching for patients and doctors
        JOptionPane.showMessageDialog(null, "Search for patients or doctors (type 'done' to finish):", "Appointment Management Instruction", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("\nSearch for patients or doctors (type 'done' to finish):");
        while (true) {
            System.out.print("\nEnter 'P {ID}' to search for a patient, 'D {ID}' for a doctor, or 'erase {ID}' to delete a patient: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) {
                JOptionPane.showMessageDialog(null, "Goodbye.", "Closing System", JOptionPane.INFORMATION_MESSAGE);
                break;
            }

            if (input.startsWith("P ")) {
                int patientId = Integer.parseInt(input.substring(2));
                hospital.searchPatient(patientId);
            } else if (input.startsWith("D ")) {
                int doctorId = Integer.parseInt(input.substring(2));
                hospital.searchDoctor(doctorId);
                hospital.displayAppointmentsByDoctor(doctorId); // Display the doctor's appointments
            } else if (input.startsWith("erase ")) {
                int patientId = Integer.parseInt(input.substring(6));
                hospital.deletePatient(patientId);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Input", "Appointment Management", JOptionPane.OK_OPTION);
                System.out.println("Invalid Input.");
            }
        }

        scanner.close();
    }
}
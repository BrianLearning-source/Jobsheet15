import java.util.Scanner;

public class OtherKRS {

    String studentName;
    String studentID;
    String courseCode;
    String courseName;
    int creditHours;

    public OtherKRS(String studentName, String studentID, String courseCode, String courseName, int creditHours) {
        this.studentName = studentName;
        this.studentID = studentID;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.creditHours = creditHours;
    }
}

class MonitoringSystem {
    private static final int MAX_CREDIT = 20; // Max credit limit per student
    private static KRS[] krsArray = new KRS[100]; // Array to store KRS data
    private static int krsCount = 0; // Counter to track the number of entries

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n=== Student KRS Monitoring System ===");
            System.out.println("1. Add KRS Data");
            System.out.println("2. Display Student KRS List");
            System.out.println("3. Analyze KRS Data");
            System.out.println("4. Exit");
            System.out.print("Choose menu: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (choice == 1) {
                addKRSData(scanner);
            } else if (choice == 2) {
                displayStudentKRS(scanner);
            } else if (choice == 3) {
                analyzeKRSData();
            } else if (choice == 4) {
                System.out.println("Thank you!");
                break;
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static int calculateTotalCreditHoursForStudent(String studentID) {
        int totalCredits = 0;
        for (int i = 0; i < krsCount; i++) {
            if (krsArray[i].studentID.equals(studentID)) {
                totalCredits += krsArray[i].creditHours;
            }
        }
        return totalCredits;
    }

    private static void addKRSData(Scanner scanner) {
        boolean addMoreCourses = true;
        String currentStudentID = "";
        String currentStudentName = "";

        while (addMoreCourses) {
            // Ask for student name and ID for each course
            System.out.print("Enter student name: ");
            String studentName = scanner.nextLine();
            System.out.print("Enter student ID (NIM): ");
            String studentID = scanner.nextLine();

            currentStudentID = studentID; // Store the current student ID for calculating total credit hours
            currentStudentName = studentName;

            int totalCreditHours = calculateTotalCreditHoursForStudent(studentID);

            // Check if the student has exceeded the max credit limit
            if (totalCreditHours >= MAX_CREDIT) {
                System.out.println("Cannot add more courses. The total credit hours for this student have already reached or exceeded the limit.");
                return;
            }

            // Course details
            System.out.print("Enter course code: ");
            String courseCode = scanner.nextLine();
            System.out.print("Enter course name: ");
            String courseName = scanner.nextLine();

            int creditHours;
            while (true) {
                System.out.print("Enter credit hours (1-3): ");
                creditHours = scanner.nextInt();
                if (creditHours >= 1 && creditHours <= 3) {
                    break;
                } else {
                    System.out.println("Invalid input. Credit hours must be between 1 and 3.");
                }
            }

            // Check if adding this course will exceed the total credit hours
            if (totalCreditHours + creditHours > MAX_CREDIT) {
                System.out.println("Cannot add this course. The total credit hours will exceed the maximum limit of " + MAX_CREDIT + " credit hours.");
            } else {
                // Add the course entry to the array
                krsArray[krsCount] = new KRS(studentName, studentID, courseCode, courseName, creditHours);
                krsCount++;
                System.out.println("KRS data added successfully!");
            }

            // Ask if the user wants to add another course
            System.out.print("Add another course? (y/n): ");
            String addCourse = scanner.next();
            scanner.nextLine();  // Consume the newline character

            if (addCourse.equalsIgnoreCase("n")) {
                addMoreCourses = false;

                // Display total credit hours for the student
                int finalTotalCredits = calculateTotalCreditHoursForStudent(currentStudentID);
                System.out.println("\nTotal credit hours: " + finalTotalCredits);
            }
        }
    }

    private static void displayStudentKRS(Scanner scanner) {
        System.out.print("Enter student ID (NIM): ");
        String nim = scanner.nextLine();
    
        boolean found = false;
        int totalCredits = 0;
    
        System.out.println("\nKRS List:");
        System.out.printf("%-10s %-15s %-15s %-25s %-10s\n", "NIM", "Name", "Course Code", "Course Name", "Credits");
    
        for (int i = 0; i < krsCount; i++) {
            if (krsArray[i].studentID.equals(nim)) {
                System.out.printf("%-10s %-15s %-15s %-25s %-10d\n",
                        krsArray[i].studentID, krsArray[i].studentName, krsArray[i].courseCode, krsArray[i].courseName, krsArray[i].creditHours);
                totalCredits += krsArray[i].creditHours;
                found = true;
            }
        }
    
        if (found) {
            System.out.println("\nTotal credit hours: " + totalCredits);
        } else {
            System.out.println("Student not found or no courses added.");
        }
    }

    private static void analyzeKRSData() {
        int count = 0;

        for (int i = 0; i < krsCount; i++) {
            int totalCredits = 0;
            for (int j = 0; j < krsCount; j++) {
                if (krsArray[i].studentID.equals(krsArray[j].studentID)) {
                    totalCredits += krsArray[j].creditHours;
                }
            }

            if (totalCredits < MAX_CREDIT) {
                count++;
            }
        }

        System.out.println("\n--- KRS Data Analysis ---");
        System.out.println("Number of students taking less than " + MAX_CREDIT + " credit hours: " + count);
    }
}

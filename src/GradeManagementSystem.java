import java.util.Scanner;

public class GradeManagementSystem {

    // Constants
    private static final int MAX_STUDENTS = 100;
    private static final int SUBJECT_COUNT = 5;

    // Arrays to store student names and marks
    private static String[] studentNames = new String[MAX_STUDENTS];
    private static double[][] studentMarks = new double[MAX_STUDENTS][SUBJECT_COUNT];
    private static int studentCount = 0;

    // Subjects
    private static final String[] subjects = {"Mathematics", "Science", "English", "History", "Computer"};

    // Scanner for input
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = getValidInt(1, 6);

            switch (choice) {
                case 1 -> addStudentMarks();
                case 2 -> viewAllStudents();
                case 3 -> calculateAverages();
                case 4 -> findTopPerformers();
                case 5 -> generateReport();
                case 6 -> {
                    running = false;
                    System.out.println("Thank you for using Grade Management System!");
                }
            }
        }
        scanner.close();
    }

    // Print menu
    private static void printMenu() {
        System.out.println("\n=== GRADE MANAGEMENT SYSTEM ===");
        System.out.println("1. Add Student Marks");
        System.out.println("2. View All Students");
        System.out.println("3. Calculate Averages");
        System.out.println("4. Find Top Performers");
        System.out.println("5. Generate Report");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    // Add student marks
    private static void addStudentMarks() {
        if (studentCount >= MAX_STUDENTS) {
            System.out.println("Maximum student limit reached!");
            return;
        }

        System.out.println("\n=== ADD STUDENT MARKS ===");
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        studentNames[studentCount] = name;

        System.out.println("\nEnter marks for 5 subjects (0-100):");
        for (int i = 0; i < SUBJECT_COUNT; i++) {
            System.out.print(subjects[i] + ": ");
            studentMarks[studentCount][i] = getValidMark();
        }

        studentCount++;
        System.out.println("✅ Student marks added successfully!");
    }

    // View all students
    private static void viewAllStudents() {
        if (studentCount == 0) {
            System.out.println("No students found!");
            return;
        }

        System.out.printf("%-20s %-12s %-12s %-12s %-12s %-12s %-12s\n",
                "Student Name", "Math", "Science", "English", "History", "Computer", "Average");
        System.out.println("-".repeat(100));

        for (int i = 0; i < studentCount; i++) {
            double avg = calculateStudentAverage(i);
            System.out.printf("%-20s %-12.2f %-12.2f %-12.2f %-12.2f %-12.2f %-12.2f\n",
                    studentNames[i],
                    studentMarks[i][0], studentMarks[i][1], studentMarks[i][2],
                    studentMarks[i][3], studentMarks[i][4],
                    avg);
        }
    }

    // Calculate average for one student
    private static double calculateStudentAverage(int index) {
        double sum = 0;
        for (int i = 0; i < SUBJECT_COUNT; i++) {
            sum += studentMarks[index][i];
        }
        return sum / SUBJECT_COUNT;
    }

    // Calculate averages and show grade
    private static void calculateAverages() {
        if (studentCount == 0) {
            System.out.println("No students found!");
            return;
        }

        System.out.println("\n=== STUDENT AVERAGES ===");
        for (int i = 0; i < studentCount; i++) {
            double avg = calculateStudentAverage(i);
            String grade = getGrade(avg);
            System.out.printf("%-20s: Average = %.2f, Grade = %s\n",
                    studentNames[i], avg, grade);
        }
    }

    // Get grade based on average
    private static String getGrade(double average) {
        if (average >= 90) return "A+";
        else if (average >= 80) return "A";
        else if (average >= 70) return "B";
        else if (average >= 60) return "C";
        else if (average >= 50) return "D";
        else return "F";
    }

    // Find top performers
    private static void findTopPerformers() {
        if (studentCount == 0) {
            System.out.println("No students found!");
            return;
        }

        double highestAverage = -1;
        for (int i = 0; i < studentCount; i++) {
            double avg = calculateStudentAverage(i);
            if (avg > highestAverage) highestAverage = avg;
        }

        System.out.println("\n=== TOP PERFORMERS ===");
        for (int i = 0; i < studentCount; i++) {
            double avg = calculateStudentAverage(i);
            if (avg == highestAverage) {
                System.out.printf("%s - Average: %.2f\n", studentNames[i], avg);
            }
        }
    }

    // Generate performance report
    private static void generateReport() {
        if (studentCount == 0) {
            System.out.println("No students found!");
            return;
        }

        System.out.println("\n=== PERFORMANCE REPORT ===");
        System.out.println("Total Students: " + studentCount);

        // Subject averages
        System.out.println("\n📊 SUBJECT AVERAGES:");
        for (int s = 0; s < SUBJECT_COUNT; s++) {
            double sum = 0;
            for (int i = 0; i < studentCount; i++) {
                sum += studentMarks[i][s];
            }
            double avg = sum / studentCount;
            System.out.printf("• %s: %.2f\n", subjects[s], avg);
        }

        // Grade distribution
        int[] gradeCounts = new int[6]; // A+, A, B, C, D, F
        for (int i = 0; i < studentCount; i++) {
            String grade = getGrade(calculateStudentAverage(i));
            switch (grade) {
                case "A+" -> gradeCounts[0]++;
                case "A"  -> gradeCounts[1]++;
                case "B"  -> gradeCounts[2]++;
                case "C"  -> gradeCounts[3]++;
                case "D"  -> gradeCounts[4]++;
                case "F"  -> gradeCounts[5]++;
            }
        }

        System.out.println("\n📈 GRADE DISTRIBUTION:");
        System.out.printf("• A+ Grade: %d\n• A Grade: %d\n• B Grade: %d\n• C Grade: %d\n• D Grade: %d\n• F Grade: %d\n",
                gradeCounts[0], gradeCounts[1], gradeCounts[2], gradeCounts[3], gradeCounts[4], gradeCounts[5]);
    }

    // Validate menu input
    private static int getValidInt(int min, int max) {
        int value;
        while (true) {
            try {
                value = scanner.nextInt();
                scanner.nextLine(); // consume newline
                if (value >= min && value <= max) return value;
                else System.out.printf("Enter number between %d and %d: ", min, max);
            } catch (Exception e) {
                System.out.print("Invalid input! Enter a number: ");
                scanner.nextLine(); // clear invalid input
            }
        }
    }

    // Validate marks input
    private static double getValidMark() {
        double mark;
        while (true) {
            try {
                mark = scanner.nextDouble();
                scanner.nextLine(); // consume newline
                if (mark >= 0 && mark <= 100) return mark;
                else System.out.print("Marks must be 0-100. Re-enter: ");
            } catch (Exception e) {
                System.out.print("Invalid input! Enter a number: ");
                scanner.nextLine();
            }
        }
    }
}

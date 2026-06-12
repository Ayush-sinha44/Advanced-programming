import java.util.*;
import java.util.stream.Collectors;


class Student {

    private int id;
    private String name;
    private List<String> courses;
    private Map<String, Integer> scores;

    public Student(int id, String name, List<String> courses, Map<String, Integer> scores) {
        this.id = id;
        this.name = name;
        this.courses = new ArrayList<>(courses);
        this.scores = new HashMap<>(scores);
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getCourses() {
        return courses;
    }

    public Map<String, Integer> getScores() {
        return scores;
    }


    public double getAverageScore() {

        if (scores.isEmpty()) return 0.0;

        double sum = scores.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();

        return sum / scores.size();
    }

    @Override
    public String toString() {
        return "ID: " + id +
                ", Name: " + name +
                ", Average: " + getAverageScore();
    }
}


class StudentPerformanceAnalyzer {


    public static List<Student> getTopNStudents(List<Student> students, int n) {

        return students.stream()

                .sorted(
                        Comparator.comparingDouble(Student::getAverageScore)
                                .reversed()
                )

                .limit(n)

                .collect(Collectors.toList());
    }

 
    public static Map<String, Double> getAverageScorePerCourse(List<Student> students) {

        Map<String, List<Integer>> courseScores = new HashMap<>();

        for (Student student : students) {

            for (String course : student.getCourses()) {

                int score = student.getScores().getOrDefault(course, 0);

                courseScores
                        .computeIfAbsent(course, k -> new ArrayList<>())
                        .add(score);
            }
        }

       
        return courseScores.entrySet()
                .stream()
                .collect(Collectors.toMap(

                        entry -> entry.getKey(),

                        entry -> entry.getValue()
                                .stream()
                                .mapToInt(Integer::intValue)
                                .average()
                                .orElse(0.0)
                ));
    }

  
    public static Set<String> getAllUniqueCourses(List<Student> students) {

        Set<String> uniqueCourses = new HashSet<>();

        for (Student student : students) {

            uniqueCourses.addAll(student.getCourses());
        }

        return uniqueCourses;
    }
}


public class Main {

    public static void main(String[] args) {

      
        List<Student> students = new ArrayList<>();

      
        students.add(
                new Student(
                        1,
                        "Ayush",
                        Arrays.asList("Math", "Physics", "Java"),
                        new HashMap<String, Integer>() {{
                            put("Math", 90);
                            put("Physics", 85);
                            put("Java", 95);
                        }}
                )
        );

    
        students.add(
                new Student(
                        2,
                        "Rahul",
                        Arrays.asList("Math", "Java"),
                        new HashMap<String, Integer>() {{
                            put("Math", 70);
                            put("Java", 80);
                        }}
                )
        );

       
        students.add(
                new Student(
                        3,
                        "Priya",
                        Arrays.asList("Physics", "Java"),
                        new HashMap<String, Integer>() {{
                            put("Physics", 88);
                            put("Java", 92);
                        }}
                )
        );


       
        System.out.println("Top 2 Students:");
        List<Student> topStudents =
                StudentPerformanceAnalyzer.getTopNStudents(students, 2);

        topStudents.forEach(System.out::println);


      
        System.out.println("\nAverage Score Per Course:");
        Map<String, Double> avgCourse =
                StudentPerformanceAnalyzer.getAverageScorePerCourse(students);

        avgCourse.forEach((course, avg) ->
                System.out.println(course + " : " + avg)
        );


      
        System.out.println("\nAll Unique Courses:");
        Set<String> courses =
                StudentPerformanceAnalyzer.getAllUniqueCourses(students);

        courses.forEach(System.out::println);
    }
}

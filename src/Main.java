import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    static List<Employee> employees = new ArrayList<>();

    public static void main(String[] args) throws IOException {


        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("Employee.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split(",");
                //  System.out.println(line);
                Employee employee = new Employee(
                        Integer.parseInt(tokens[0]), tokens[1], tokens[2],
                        Integer.parseInt(tokens[3]), Double.parseDouble(tokens[4]), Department.valueOf(tokens[5]),
                        Boolean.parseBoolean(tokens[6]), LocalDate.parse(tokens[7]), tokens[8], tokens[9],
                        Position.valueOf(tokens[10]), tokens[11]);
              //  System.out.println(employee);
                employees.add(employee);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        filterSortModify(employees);
        generateDepartmentSummary();
        generateNewEmployee();
        getUniqueEmployees();
        averageSalaryDepartment();
        longestServing();
        countEmployeeByPosition();
        employeeByDepartmentAndPosition();
    }

    public static void filterSortModify(List<Employee> employees) {
     //   System.out.println(employees);
       List<Employee> employeesFilter = employees.stream()
                .filter(employee -> Position.INTERN.equals(employee.getPosition()))
                .sorted(Comparator.comparing(Employee::getStartDate))
                .peek(
                        employee -> {
                            if (employee.getDepartment() == Department.IT && employee.getStartDate().isBefore(LocalDate.now().minusYears(5))) {
                                employee.setSalary(employee.getSalary() * 1.1);
                            }
                        })
                .collect(Collectors.toList());
        System.out.println(employeesFilter);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(" processed_employees.txt"))) {
            for (Employee employee : employees) {
                bufferedWriter.write(employee.toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void generateDepartmentSummary() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("department_summary.txt"))) {

            Map<Department, List<Employee>> departmentListMap = employees
                    .stream()
                    .collect(Collectors.groupingBy(Employee::getDepartment));
            departmentListMap.forEach((department, employees) -> {
                try {
                    bufferedWriter.write(department.name());
                    bufferedWriter.newLine();
                    for (Employee employee : employees) {
                        bufferedWriter.write(employee.toString());
                        bufferedWriter.newLine();
                    }


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void generateNewEmployee() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("new_employees.txt"));
            List<Employee> newEmployee = employees
                    .stream()
                    .filter(employee -> Period.between(employee.getStartDate(), LocalDate.now()).getYears() < 2)
                    .toList();
            for (Employee employee : newEmployee) {
                bufferedWriter.write(employee.toString());
                bufferedWriter.newLine();

            }bufferedWriter.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void getUniqueEmployees() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("unique_employees.txt"))) {
            {
                Map<Integer, List<Employee>> employeesDuplicateId = employees
                        .stream()
                        .collect(Collectors.groupingBy(Employee::getId));
                List<Employee> employeesToRemove = new ArrayList<>();
                employeesDuplicateId.forEach((id, employees1) -> {

                    try {
                        bufferedWriter.write(id.toString());
                        bufferedWriter.newLine();
                        for (Employee employee : employees1) {
                            bufferedWriter.write(employee.toString());
                            bufferedWriter.newLine();

                            employees.remove(employees1);
                            if (employees.size() > 1) {
                                employeesToRemove.addAll(employees.subList(1, employees.size()));
                            }
                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                });
                employees.removeAll(employeesToRemove);

                bufferedWriter.flush();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void averageSalaryDepartment() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("average_salary_by_department.txt"))) {
            Map<Department, Double> avarageSalaryByDepartment = employees.stream()
                    .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));
            avarageSalaryByDepartment.forEach((department, salary) -> {
                try {
                    bufferedWriter.write(department.name());
                    bufferedWriter.newLine();
                    bufferedWriter.write(avarageSalaryByDepartment.toString());
                    bufferedWriter.newLine();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void longestServing() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("longest_serving_employee.txt"))) {
            Employee employee = employees
                    .stream()
                    .min(Comparator.comparing(Employee::getStartDate)).orElse(null);

            if (employee != null) {
                bufferedWriter.write(employee.toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void countEmployeeByPosition() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("employees_by_position.txt"))) {
            Map<Position, Long> employee = employees
                    .stream()
                    .collect(Collectors.groupingBy(Employee::getPosition, Collectors.counting()));
            employee.forEach((position, count) -> {
                try {
                    bufferedWriter.write(position.name());
                    bufferedWriter.newLine();
                    bufferedWriter.write(count.toString());
                    bufferedWriter.newLine();


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            });
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static void employeeByDepartmentAndPosition() throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(" employees_by_department_and_position.txt"))) {
            Map<Department, Map<Position, List<Employee>>> departmentMap = employees
                    .stream()
                    .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.groupingBy(Employee::getPosition)));
            departmentMap.forEach((department, employeeList) -> {
                try {
                    bufferedWriter.write(department.name());
                    bufferedWriter.newLine();
                    employeeList.forEach((position, employees) -> {
                        try {
                            bufferedWriter.write(position.name());
                            bufferedWriter.newLine();
                            for (Employee employee : employees) {
                                bufferedWriter.write(employee.toString());


                            }

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    bufferedWriter.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        catch(
                IOException e)

        {
            throw new RuntimeException(e);
        }
    }
}
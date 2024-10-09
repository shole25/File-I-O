import java.time.LocalDate;

public class Employee {
    private int id;
    private String name;
    private String surname;
    private int age;
    private double salary;
    private Department department;
    private boolean isEmployeer;
    private LocalDate startDate;
    private String email;
    private String phoneNumber;
    private Position position;
    private String address;

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmployeer(boolean employeer) {
        isEmployeer = employeer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public Department getDepartment() {
        return department;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public boolean isEmployeer() {
        return isEmployeer;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Position getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public String getSurname() {
        return surname;
    }

    public Employee(int address, String age, String department, int email, double isEmployeer, Department name, boolean salary, LocalDate startDate, String surname, String position, Position phoneNumber, String id) {

    }
    @Override
    public String toString() {
        return "Employee{" +
                "address='" + address + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", department=" + department +
                ", isEmployeer=" + isEmployeer +
                ", startDate=" + startDate +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", position=" + position +
                '}';
    }
}

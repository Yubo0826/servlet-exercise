package exercise.student.model;

public class Student {
  private int id;
  private String name;
  private int age;

  // 构造函数和 getter/setter 方法
  public void setName(String name) {
    this.name = name;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public Integer getAge() {
    return this.age;
  }

  public Integer getId() {
    return this.id;
  }

  // toString 方法用于在日志中打印学生信息
  @Override
  public String toString() {
      return "Student{id=" + id + ", name='" + name + "', age=" + age + '}';
  }
}

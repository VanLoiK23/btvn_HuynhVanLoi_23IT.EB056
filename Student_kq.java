package Btvn;

public class Student_kq {
  private int age,sum;
  private Boolean isDigit;
public Student_kq(int age, int sum, Boolean isDigit) {
	super();
	this.age = age;
	this.sum = sum;
	this.isDigit = isDigit;
}
public int getAge() {
	return age;
}
public void setAge(int age) {
	this.age = age;
}
public int getSum() {
	return sum;
}
public void setSum(int sum) {
	this.sum = sum;
}
public Boolean getIsDigit() {
	return isDigit;
}
public void setIsDigit(Boolean isDigit) {
	this.isDigit = isDigit;
}
@Override
public String toString() {
	return "Student_kq [age=" + age + ", sum=" + sum + ", isDigit=" + isDigit + "]";
}
  
}

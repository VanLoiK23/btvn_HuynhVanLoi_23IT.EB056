package Btvn;

import java.time.LocalDate;
import java.util.Date;

public class Student {
   private int id;
   private String name;
   private String address;
   private LocalDate dateOfBirth;
   
   public Student() {
	   
   }

public Student(int id, String name, String address, LocalDate dateOfBirth) {
	super();
	this.id = id;
	this.name = name;
	this.address = address;
	this.dateOfBirth = dateOfBirth;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public LocalDate getDateOfBirth() {
	return dateOfBirth;
}

public void setDateOfBirth(LocalDate dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
}

@Override
public String toString() {
	return "Student [id=" + id + ", name=" + name + ", address=" + address + ", dateOfBirth=" + dateOfBirth + "]";
}

}
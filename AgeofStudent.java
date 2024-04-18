package Btvn;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AgeofStudent implements Runnable{
   private static int year,Id;
   private static int month;
   private static int day;
   private BlockingQueue<Model_tuoi> outputQueue;
   private BlockingQueue<Student> queue;
   private BlockingQueue<List<Student_kq>> kq;
   private static String gt;
   private static List<Student_kq> st_kq;
       public AgeofStudent() {
       }
       public AgeofStudent(BlockingQueue<Student> queue , BlockingQueue<Model_tuoi> outputQueue,BlockingQueue<List<Student_kq>> kq) {
           this.queue = queue;
           this.outputQueue=outputQueue;
           this.kq=kq;
       }

       @Override
       public void run() {
           try {
               while (true) {
                   Student student = queue.take();
                   if (student.getId() == -1) {
                       break;
                   }
                   getAge_and_mahoa(student.getId(),student.getName(),student.getAddress(),student.getDateOfBirth().toString());
                   outputQueue.put(new Model_tuoi(getYear(),getDay(),getMonth()));
                   int[] digits = giaima(gt);
                   int years = digits[0];
                   int months = digits[1];
                   int days = digits[2];
                   KiemtraTotalisPrime check = new KiemtraTotalisPrime(outputQueue);         
                   Boolean isDigit =check.isPrime(years+days+months);
                   Student_kq k=new Student_kq(years,(years+days+months),isDigit);
                   st_kq=new ArrayList<>();
                   st_kq.add(k);
                   kq.put(st_kq);
               }
           } catch (InterruptedException e) {
               
           }
       }
    public static synchronized void getAge_and_mahoa(int id, String name, String address, String dateOfBirth) {
    	LocalDate birthDate = LocalDate.parse(dateOfBirth);
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(birthDate, currentDate);
        System.out.println("Age of Student:"+name);
        Id=id;
        year=age.getYears();
        month=age.getMonths();
        day=age.getDays();
        System.out.println(age.getYears()+" years"
        		+"\nMonths:"+age.getMonths()
        		+"\nDays:"+age.getDays());
        String gia_tri_mahoa = mahoa(age.getYears(), age.getMonths(), age.getDays());
        System.out.println("Chuỗi số  mã hóa: " + gia_tri_mahoa);
        gt=gia_tri_mahoa;
    }
    //Ma hoa duoi dang chuoi Base64
    private static synchronized  String mahoa(int... digits) {
        String digitsString = arrayToString(digits);
        byte[] bytes = digitsString.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(bytes);
    }
    private static int[] giaima(String a) {
        byte[] bytes = Base64.getDecoder().decode(a);
        String toString = new String(bytes, StandardCharsets.UTF_8);
        String[] digitStrings = toString.split(",");
        int[] digits = new int[digitStrings.length];
        for (int i = 0; i < digitStrings.length; i++) {
            digits[i] = Integer.parseInt(digitStrings[i]);
        }
        return digits;
    }
    private static synchronized String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i != array.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    public synchronized int getId() {
    	return Id;
    }
    public synchronized int getYear() {
    	return year;
    }
    public synchronized int getMonth() {
    	return month;
    }    
    public synchronized int getDay() {
    	return day;
    }
}

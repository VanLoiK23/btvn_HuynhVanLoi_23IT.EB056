package Btvn;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Main {

    public static void main(String[] args)  {
        String filePath = "D:/Student.xml";

        BlockingQueue<Student> queue = new LinkedBlockingQueue<>();
        BlockingQueue<Model_tuoi> outputQueue=new LinkedBlockingQueue<>();
        BlockingQueue<List<Student_kq>> list = new LinkedBlockingQueue<>();
        Thread fileReaderThread = new Thread(new HamdocfileStudent_xml(filePath, queue));
    	fileReaderThread.start();
        try {
        	fileReaderThread.join();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread caculateAge = new Thread(new AgeofStudent(queue,outputQueue,list));
        Thread check= new Thread(new KiemtraTotalisPrime(outputQueue));
        caculateAge.start();
        try {
        	 check.start();
        	caculateAge.join();
        	
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

       
        Thread CretaeFile= new Thread(new TaofileKq_xml(list));
        CretaeFile.start();
        try {
            fileReaderThread.join();
            caculateAge.join();
            check.join();
            CretaeFile.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ferer");
    }
}
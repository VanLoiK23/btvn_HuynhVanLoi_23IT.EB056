package Btvn;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import org.xml.sax.SAXException;

public class TaofileKq_xml implements Runnable{
    private BlockingQueue<List<Student_kq>> kq;
    public TaofileKq_xml() {
    	
    }

    public TaofileKq_xml(BlockingQueue<List<Student_kq>> kq) {
        this.kq=kq;
    }

    @Override
    public void run() {
        try {
            while (true) {
            	List<Student_kq> k= kq.take();                    
                if (k == null) {
                    break;
                }
                Tao_kq_xml(k,"D:/kq.xml");                
            }
        } catch (InterruptedException e) {
            
        }
    }
    public static void Tao_kq_xml(List<Student_kq> st, String filePath) {
        try {
            Document doc;
            File file = new File(filePath);

            if (file.exists()) {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.parse(file);
            } else {
                doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            }

            Element rootElement;
            if (doc.getDocumentElement() != null) {
                rootElement = doc.getDocumentElement();
            } else {
                rootElement = doc.createElement("Students");
                doc.appendChild(rootElement);
            }
            //check trung lap
            Set<Integer> existingValues = new HashSet<>();

            if (st != null && !st.isEmpty()) {
                for (Student_kq kq : st) {
                    if (!existingValues.contains(kq.getAge())) {
                        Element studentElement = doc.createElement("Student");

                        Element ageElement = doc.createElement("age");
                        ageElement.setTextContent(String.valueOf(kq.getAge()));

                        Element sumElement = doc.createElement("sum");
                        sumElement.setTextContent(String.valueOf(kq.getSum()));

                        Element isDigitElement = doc.createElement("isDigit");
                        isDigitElement.setTextContent(String.valueOf(kq.getIsDigit()));

                        studentElement.appendChild(ageElement);
                        studentElement.appendChild(sumElement);
                        studentElement.appendChild(isDigitElement);

                        rootElement.appendChild(studentElement);

                        existingValues.add(kq.getAge());
                    }
                }
            } else {
            	Element emptyElement = doc.createElement("Empty");
                emptyElement.setTextContent("Danh sách rỗng");
                rootElement.appendChild(emptyElement);
                System.out.println("Danh sách kết quả rỗng. Ghi vào file XML với thông báo 'Danh sách rỗng'");
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | IOException | TransformerException e) {
            e.printStackTrace();
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        }
    }

    static private synchronized void Add(Document doc,Element Students,Student_kq st) {
	    Element Student = doc.createElement("Student");
	    Element age = doc.createElement("age");
		age.setTextContent(String.valueOf(st.getAge()));
		Element sum = doc.createElement("sum");
		sum.setTextContent(Integer.toString(st.getSum()));
		Element isDigit = doc.createElement("isDigit");
		isDigit.setTextContent(st.getIsDigit().toString());
		
		Student.appendChild(age);
		Student.appendChild(sum);
		Student.appendChild(isDigit);
		
		Students.appendChild(Student);
   }
}

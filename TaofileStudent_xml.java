package Btvn;

import java.time.LocalDate;
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

public class TaofileStudent_xml {
   public static void main(String[] args) {
    try {
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();
		
		Student st1 = new Student(1, "Gia Huy", "Tran Cao Van", LocalDate.of(2000, 1, 1));
		Student st2 = new Student(2, "Gia Han", "Tran Hung Dao", LocalDate.of(2001, 2, 2));
		Student st3 = new Student(3, "Tien Vu", "Dien Bien Phu", LocalDate.of(2005, 4, 13));


		Element Student = doc.createElement("Students");
		AddStudent(doc,Student,st1);
		AddStudent(doc,Student,st2);
		AddStudent(doc,Student,st3);
		doc.appendChild(Student);

		TransformerFactory tff = TransformerFactory.newInstance(); 
		try {
			Transformer tf = tff.newTransformer();
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult("D:/student.xml");
			tf.transform(source, result);
			System.out.println("Ghi file xml thành công");

		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
    } catch (ParserConfigurationException e) {
		e.printStackTrace();
	}
   }
   static private void AddStudent(Document doc,Element Students,Student st) {
	    Element Student = doc.createElement("Student");
	    Element id = doc.createElement("Id");
		id.setTextContent(String.valueOf(st.getId()));
		Element name = doc.createElement("Name");
		name.setTextContent(st.getName());
		Element address = doc.createElement("Address");
		address.setTextContent(st.getAddress());
		Element dateOfBirth = doc.createElement("DateOfBirth");
		dateOfBirth.setTextContent(st.getDateOfBirth().toString());

		Student.appendChild(id);
		Student.appendChild(name);
		Student.appendChild(address);
		Student.appendChild(dateOfBirth);
		
		Students.appendChild(Student);
   }
}

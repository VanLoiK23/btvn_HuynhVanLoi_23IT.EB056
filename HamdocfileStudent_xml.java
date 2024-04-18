package Btvn;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.BlockingQueue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class HamdocfileStudent_xml implements Runnable{
        private String filePath;
        private BlockingQueue<Student> queue;

        public HamdocfileStudent_xml(String filePath, BlockingQueue<Student> queue) {
            this.filePath = filePath;
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                File file = new File(filePath);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(file);

                Element rootNode = doc.getDocumentElement();

                NodeList studentNodes = rootNode.getElementsByTagName("Student");

                for (int i = 0; i < studentNodes.getLength(); i++) {
                    Node studentNode = studentNodes.item(i);
                    if (studentNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element studentElement = (Element) studentNode;
                        int id = Integer.parseInt(getTagValue(studentElement, "Id"));
                        String name = getTagValue(studentElement, "Name");
                        String address = getTagValue(studentElement, "Address");
                        String dateOfBirth = getTagValue(studentElement, "DateOfBirth");

                        Student student = new Student(id, name, address, LocalDate.parse(dateOfBirth));
                        queue.put(student);
                        NodeList childNodes = studentElement.getChildNodes();

                        for (int j = 0; j < childNodes.getLength(); j++) {
                            Node childNode = childNodes.item(j);
                            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element childElement = (Element) childNode;
                                System.out.println(childElement.getTagName() + ": " + childElement.getTextContent());
                            }
                        }
                    }
                   }
                queue.put(new Student(-1, "", "", LocalDate.MIN));
                } catch (ParserConfigurationException | SAXException | IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        private synchronized String getTagValue(Element element, String tagName) {
            NodeList nodeList = element.getElementsByTagName(tagName);
            if (nodeList.getLength() > 0) {
                return nodeList.item(0).getTextContent();
            } else {
                return null;
            }
        }
    }
package main.java;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataController {

    private List<Student> tableData;

    private boolean bName;
    private boolean bTitul;
    private boolean bType;
    private boolean bCategory;

    DataController(){
        tableData = new ArrayList<Student>();
    }

    private List<Student> FindTemplate(String name, int minTitul, int maxTitul, String type, String Category) {
        List<Student> temp = new ArrayList<>();
        for (Student tableDatum : tableData) {
            boolean bIsFits = (tableDatum.getName().equals(name) || bName)
                    && (tableDatum.getTitul() >= minTitul && tableDatum.getTitul() <= maxTitul || bTitul)
                    && (tableDatum.getType().equals(type) || bType)
                    && (tableDatum.getCategory().equals(Category) || bCategory);
            if(bIsFits){
                temp.add(tableDatum);
            }
        }
        return temp;
    }

    public List<Student> FindStudents(String name, int minTitul, int maxTitul, String type, String Category){

        bName = (name.equals(""));
        bTitul = (minTitul == 0 && maxTitul == 0);
        if(maxTitul < minTitul)maxTitul = minTitul;
        bType = (type.equals("All"));
        bCategory = (Category.equals("All"));

        return FindTemplate(name, minTitul, maxTitul, type, Category);
    }

    public int DeleteStudents(String name, int minTitul, int maxTitul, String type, String Category){
        bName = (name.equals(""));
        bTitul = (minTitul == 0 && maxTitul == 0);
        if(maxTitul < minTitul)maxTitul = minTitul;
        bType = (type.equals("All"));
        bCategory = (Category.equals("All"));

        List<Student> temp = FindTemplate(name, minTitul, maxTitul, type, Category);
        int amount = temp.size();
        tableData.removeAll(temp);
        return amount;
    }

    public void Add(Student student){
        tableData.add(student);
    }

    public void Read(String pathToFile) throws ParserConfigurationException, SAXException, IOException {
        Sax sax = new Sax();
        sax.parse(pathToFile);
        tableData = sax.getStudents();
    }

    public void Write(String pathToFile){
        Dom dom = new Dom();
        dom.setStuds(tableData, pathToFile);
        dom.setBooks();
    }

    public Student atIndex(int index){
        return tableData.get(index);
    }

    public boolean isExists(int index){
        return index < tableData.size();
    }

    public void setStudents(List<Student> data){
        tableData = data;
    }
}

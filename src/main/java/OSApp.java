import javax.sound.midi.Soundbank;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class OSApp {
    static HashMap<String, String> variables = new HashMap<>();
    public static Stack readDataFromFileOnDisk(String name) {
        boolean flag = true;
        String path = "src/resources/" + name + ".txt";
        try{
            Stack s = new Stack();
            String line = "";
            FileReader fileReader= new FileReader(path);
            BufferedReader br = new BufferedReader(fileReader);
            while ((line = br.readLine()) != null) {
                String[] currentLine = line.split(" ");
                                    // ***** The coming commented part will be used in parsing *****
                for(int i = 0; i < currentLine.length; i++){
                    s.push(currentLine[i]);
                    }
                }
            Stack result = new Stack();
            while(!s.isEmpty()){
                result.push(s.pop());
            }
            return result;
    }
        catch (FileNotFoundException f){
            f.printStackTrace();
            flag = false;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static void assign(String name, String value){
        variables.put(name, value);
    }

    public static void readFile(String file) throws IOException {
        Stack s = readDataFromFileOnDisk(file);
        String newFile="";
        while(!s.isEmpty()){
        if(s.peek().equals("assign")){
            s.pop();
            String name = (String) s.pop();
            if(s.peek().equals("readFile")){
                s.pop();
                String var = (String) s.pop();
               Stack x = readDataFromFileOnDisk(variables.get(var));
               while(!x.isEmpty()){
                   newFile += x.pop() + " ";
               }
               assign(name, newFile);
            }
            else if(s.peek().equals("input"))
            {
                s.pop();
                Scanner sc = new Scanner (System.in);
                String value = sc.nextLine();
                assign(name, value);
            }
        }
        else if(s.peek().equals("print")){
            s.pop();
            if(variables.containsKey(s.peek())){
                System.out.println(variables.get(s.pop()));
            }
            else{
                System.out.println(s.pop());
            }
        }
        else if(s.peek().equals("writeFile")){
            s.pop();
            writeDataToFileOnDisk(variables.get(s.pop()), variables.get(s.pop()));
        }
       }
    }
    public static void writeDataToFileOnDisk(String name, String data) throws IOException {
        try{
        FileWriter file = new FileWriter("src/resources/" + name + ".txt");
        file.write(data);
        file.close();
    } catch (IOException i) {
        i.printStackTrace();
    }
    }

    public static void main(String[] args) throws IOException {
        readFile("Program 1");
       // writeDataToFileOnDisk("Berkawy 2", "assign 2 3 print x y");
    }
}

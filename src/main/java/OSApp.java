import java.io.*;
import java.util.*;

public class OSApp {

    static HashMap<String, String> variables = new HashMap<>();

    public static Stack readDataFromFileOnDisk(String name) {
        String path = "src/resources/" + name + ".txt";
        try{
            Stack s = new Stack();
            String line = "";
            FileReader fileReader= new FileReader(path);
            BufferedReader br = new BufferedReader(fileReader);
            while ((line = br.readLine()) != null) {
                String[] currentLine = line.split(" ");
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
            catch (IOException f){
                f.printStackTrace();
            }

        return null;
    }

    public static String readLineByLine(String name) throws IOException {
        String path = "src/resources/" + name + ".txt";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null){
            stringBuilder.append(line);
            stringBuilder.append("\n");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        reader.close();
        return stringBuilder.toString();
    }

    public static void assign(String name, String value){
        variables.put(name, value);
    }

    public static void readFile(String file) throws IOException {
        Stack s = readDataFromFileOnDisk(file);
        String newFile="";
        int count = 0;
        boolean flag = false;
        while(!s.isEmpty()){
        if(s.peek().equals("assign")){
            if(count == 0){
                flag = true;
            }
            s.pop();
            String name = (String) s.pop();
            if(s.peek().equals("readFile")){
                s.pop();
                String var = (String) s.pop();
                newFile = readLineByLine(variables.get(var));
                assign(name, newFile);
            }
            else if(s.peek().equals("input"))
            {
                count++;
                s.pop();
                if(flag){
                    System.out.println("Please enter file name or a number:");
                }
                Scanner sc = new Scanner (System.in);
                String value = sc.nextLine();
                assign(name, value);
            }
        }
        else if(s.peek().equals("print")){
            count++;
            s.pop();
            if(variables.containsKey(s.peek())){
                System.out.println(variables.get(s.pop()));
            }
            else{
                System.out.println(s.pop());
            }
        }
        else if(s.peek().equals("writeFile")){
            count++;
            s.pop();
            writeDataToFileOnDisk(variables.get(s.pop()), variables.get(s.pop()));
        }
        else if(s.peek().equals("add")){
            count++;
            s.pop();
            String value = (String) variables.keySet().toArray()[0];
            String a = variables.get(s.pop());
            String b = variables.get(s.pop());
            int add = Integer.parseInt(a) + Integer.parseInt(b);
            variables.replace(value, Integer.toString(add));
        }
       }
    }

    public static void writeDataToFileOnDisk(String name, String data) throws IOException {
        try{
        String[] dataLine = {};
        FileWriter file = new FileWriter("src/resources/" + name + ".txt");
        for(int i=0; i<data.length(); i++) {
            dataLine = data.split(" ");
        }
        for(int i = 0; i < dataLine.length; i++){
            if(dataLine[i].equals("input")){
                file.write(dataLine[i] + "\n");
            }
            else if(dataLine[i].equals("print")){
                file.write(dataLine[i] + " " + dataLine[i+1] + "\n");
                i++;
            }
            else if(dataLine[i].equals("readFile")){
                file.write(dataLine[i] + " " + dataLine[i+1]  + "\n");
                i++;
            }
            else if(dataLine[i].equals("writeFile") || dataLine[i].equals("add")){
                file.write(dataLine[i] + " " + dataLine[i+1] + " " + dataLine[i+2] + "\n");
                i += 2;
            }
            else{
                file.write(dataLine[i] + " ");
            }
        }
        file.close();
    } catch (IOException i) {
        i.printStackTrace();
    }
    }

    public static void main(String[] args) throws IOException {
        readFile("Program 1");
        System.out.println("\n");
        readFile("Program 2");
        System.out.println("\n");
        readFile("Program 3");
    }
}

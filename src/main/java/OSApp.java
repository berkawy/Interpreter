package main.java;

import java.io.*;
import java.util.*;

public class OSApp {

    static int id =1 ;
    static Object Memory[] = new Object[4096];
    static int start = 0;
    static int process = 0;

    public static Stack readDataFromFileOnDisk(String name) {
        String path = "src/resources/" + name;
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

    public static void assign(Object end,Object start, String name, Object value){
        for (int start1 = (int)start; start1 < (int)end; start1++)
            if(Memory[start1]==null) {
                Memory[start1] = new Var(name, value);
                break;
            }
    }

    public static void allocateMemory(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            Memory[start++] = id++;
            Memory[start++] = State.NotRunning;
            Memory[start++] = 1;
            Memory[start++] = start -3;
            Memory[start] = start-4+49;
            Stack s = readDataFromFileOnDisk(fileEntry.getName());
            Memory[start+1] = s;
            start = start +46 ;
            process++;
            }
        }


    public static void readFile() throws IOException {
        allocateMemory(new File("src/resources"));
        String newFile="";
        int count = 0;
        boolean flag = false;
        while(process !=0) {
            for (int i = 0; i < start ; i = i + 50) {
                count = 0;
                if (Memory[i] == null) {
                    continue;
                }
                for(int j = 0 ; j<2&&!(((Stack) Memory[i+5]).isEmpty());j++){
                    (Memory[i+1])=State.Running;
                    Stack s = (Stack) Memory[i+5];
                    if(s.peek().equals("assign")){
                        if(count == 0){
                            flag = true;
                        }
                        s.pop();
                        String name = (String) s.pop();
                        if(s.peek().equals("readFile")){
                            s.pop();
                            String var = (String) s.pop();
                            for(int k = i+6;k<i+49;k++){
                                if(Memory[k] != null) {
                                    if (((Var) Memory[k]).name.equals(var)) {
                                        newFile = readLineByLine((String) ((Var) Memory[k]).value);
                                        break;
                                    }
                                }
                            }
                            assign(Memory[i+4],Memory[i+3],name, newFile);
                        }
                        else if(s.peek().equals("input"))
                        {
                            count++;
                            s.pop();
                            if(flag){
                                System.out.println("Please enter file name:");
                                flag = false;
                            }
                            Scanner sc = new Scanner (System.in);
                            String value = sc.nextLine();
                            assign(Memory[i+4],Memory[i+3],name, value);
                        }
                    }
                    else if(s.peek().equals("print")){
                        count++;
                        s.pop();
                        boolean flag1 =false;
                        for(int k = i+6;k<i+49;k++) {
                            if(Memory[k] != null) {
                                if (((Var) Memory[k]).name.equals(s.peek())) {
                                    System.out.println(((Var) Memory[k]).value);
                                    s.pop();
                                    flag1 = true;
                                    break;
                                }
                            }
                        }
                        if(!flag1){
                            System.out.println(s.pop());
                        }
                    }
                    else if(s.peek().equals("writeFile")){
                        count++;
                        s.pop();
                        Object value1 = null;
                        Object value2 = null;
                        for(int k = i+6;k<i+49;k++) {
                            if(Memory[k] != null) {
                                if (((Var) Memory[k]).name.equals(s.peek())) {
                                    value1 = ((Var) Memory[k]).value;
                                    s.pop();
                                    break;
                                }
                            }
                        }
                        for(int k = i+6;k<i+49;k++) {
                            if(Memory[k] != null) {
                                if (((Var) Memory[k]).name.equals(s.peek())) {
                                    value2 = ((Var) Memory[k]).value;
                                    s.pop();
                                    break;
                                }
                            }
                        }
                        writeDataToFileOnDisk((String) value1, (String) value2);
                    }
                    else if(s.peek().equals("add")){
                        count++;
                        s.pop();
                        String a = "";
                        String b = "";
                        int index = i+6;
                        for(int k = i+6;k<i+49;k++) {
                            if(Memory[k] != null) {
                                if (((Var) Memory[k]).name.equals(s.peek())) {
                                    a = (String) ((Var) Memory[k]).value;
                                    s.pop();
                                    index = k;
                                    break;
                                }
                            }
                        }
                        for(int k = i+6;k<i+49;k++) {
                            if(Memory[k] != null) {
                                if (((Var) Memory[k]).name.equals(s.peek())) {
                                    b = (String) ((Var) Memory[k]).value;
                                    s.pop();
                                    break;
                                }
                            }
                        }

                        int add = Integer.parseInt(a) + Integer.parseInt(b);
                        ((Var)(Memory[index])).value =Integer.toString(add);
                    }
                    if(s.isEmpty()){
                        Memory[i+1] =State.Ended;
                        process--;
                    }else{
                        Memory[i+1]=State.NotRunning;
                    }
                    Memory[i+3] = (int)Memory[i+3] +1;
                }
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
        readFile();
    }
}

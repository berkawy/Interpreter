import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class OSApp {
    public static BufferedReader readDataFromFileOnDisk(String path) {
        boolean flag = true;
        try{
            String line = "";
            FileReader fileReader= new FileReader(path);
            BufferedReader br = new BufferedReader(fileReader);
            while ((line = br.readLine()) != null) {
                String[] currentLine = line.split(" ");
                                    // ***** The coming commented part will be used in parsing *****
                //Stack s = new Stack();
                //for(int i = 0; i < currentLine.length; i++){
                   // s.push(currentLine[i]);
                //}
        }
            return br;
    }
        catch (IOException e){
            e.printStackTrace();
            flag = false;
        }

        return null;
    }

}

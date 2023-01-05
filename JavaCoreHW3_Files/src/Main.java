import java.io.*;
import java.time.ZonedDateTime;

public class Main {
    public static void main(String[] args) {

        //тест
        //creating a StringBuilder for logging
        StringBuilder logSB = new StringBuilder();

        //creating folders
        createFolder("Games", logSB);
        createFolder("Games/src", logSB);
        createFolder("Games/savegames", logSB);
        createFolder("Games/res", logSB);
        createFolder("Games/temp", logSB);

        createFolder("Games/src/test", logSB);
        createFolder("Games/src/main", logSB);

        createFolder("Games/res/drawables", logSB);
        createFolder("Games/res/vectors", logSB);
        createFolder("Games/res/icons", logSB);

        //creating files
        createFile("Games/temp/temp.txt", logSB);
        createFile("Games/src/main/Main.java", logSB);
        createFile("Games/src/main/Utils.java", logSB);

        //recording whole log
        byte[] bufferedBytes = logSB.toString().getBytes();
        try (FileOutputStream myOut = new FileOutputStream("Games/temp/temp.txt", false);
             BufferedOutputStream myBOS = new BufferedOutputStream(myOut)) {
            myBOS.write(bufferedBytes, 0, bufferedBytes.length);
        } catch (IOException myEx) {
            System.out.println(myEx.getMessage());
        }

    }

    private static void createFile(String fileName, StringBuilder logSB) {
        File myFile = new File(fileName);
        String logText = "";
        try {
            if (myFile.createNewFile())
                logText = ZonedDateTime.now() + " создан файл " + fileName + "\n";
            System.out.println(logText);
        } catch (IOException ex) {
            logText = ZonedDateTime.now() + " не удалось создать файл " + fileName + "\n" + ex.getMessage() + "\n";
            System.out.println(logText);
        }

        logSB.append(logText);

    }

    private static void createFolder(String folderName, StringBuilder logSB) {
        File myDir = new File(folderName);
        String logText = "";
        if (myDir.mkdir()) {
            logText = ZonedDateTime.now() + " создан каталог " + myDir.getAbsolutePath() + "\n";
            System.out.println(logText);
        } else {
            logText = ZonedDateTime.now() + " не удалось создать каталог " + folderName + "\n";
            System.out.println(logText);
        }
        logSB.append(logText);
    }

}

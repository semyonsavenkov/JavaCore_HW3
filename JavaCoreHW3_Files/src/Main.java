import java.io.*;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

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

        //creating gameProgress and saving it to a file
        GameProgress gameProgress =
                new GameProgress(94, 10, 2, 254.32);
        saveGame(gameProgress);

        GameProgress gameProgress2 =
                new GameProgress(85, 12, 3, 300.50);
        saveGame(gameProgress2);

        GameProgress gameProgress3 =
                new GameProgress(70, 15, 5, 500.50);
        saveGame(gameProgress3);

        //adding saves to a zip archive
        ArrayList listOfFiles = new ArrayList<String>();

        File savesDir = new File("Games/savegames");
        if (savesDir.isDirectory()) {
            for (File item : savesDir.listFiles()) {
                if (! item.getName().contains(".zip")) {
                    listOfFiles.add(item.getAbsolutePath());

                }
            }
            zipFiles(listOfFiles);

            //deleting non-zip files after archivation
            for (File item : savesDir.listFiles()) {
                if (! item.getName().contains(".zip")) {
                    item.delete();
                }
            }

            for (File item : savesDir.listFiles()) {
                if (item.getName().contains(".zip")) {
                    openZip(savesDir.getAbsolutePath(), item.getAbsolutePath());
                }
            }

            for (File item : savesDir.listFiles()) {
                if (item.getName().contains(".dat")) {
                    openProgress(item.getAbsolutePath());
                }
            }
        }
    }

    private static void createFile(String fileName, StringBuilder logSB) {
        File myFile = new File(fileName);
        String logText = "";
        try {
            if (myFile.createNewFile())
                logText = getStringDate() + " создан файл " + fileName + "\n";
            System.out.println(logText);
        } catch (IOException ex) {
            logText = getStringDate() + " не удалось создать файл " + fileName + "\n" + ex.getMessage() + "\n";
            System.out.println(logText);
        }

        logSB.append(logText);

    }

    private static void createFolder(String folderName, StringBuilder logSB) {
        File myDir = new File(folderName);
        String logText = "";
        if (myDir.mkdir()) {
            logText = getStringDate() + " создан каталог " + myDir.getAbsolutePath() + "\n";
            System.out.println(logText);
        } else {
            logText = getStringDate() + " не удалось создать каталог " + folderName + "\n";
            System.out.println(logText);
        }
        logSB.append(logText);
    }

    public static void saveGame (GameProgress currentGameProgress) {

        String formatedDate = getStringDate();

        try (FileOutputStream fos = new FileOutputStream("Games/savegames/save" + formatedDate +
                currentGameProgress.hashCode() +  ".dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(currentGameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(ArrayList<String> listOfFiles) {

        String formatedDate = getStringDate();

        try (ZipOutputStream zout = new ZipOutputStream(
                new FileOutputStream("Games/savegames/save" + formatedDate + "archivedsaves.zip"))
        ) {
            for (String currentFile : listOfFiles) {

                try (FileInputStream fis = new FileInputStream(currentFile)) {
                    ZipEntry entry = new ZipEntry(currentFile);
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (IOException exception) {
                    System.out.println(exception.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void openZip(String directoryPath, String fileZipPath) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(fileZipPath))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName(); // получим название файла // распаковка
                FileOutputStream fout = new FileOutputStream(name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage()); }
    }

    public static void openProgress (String path) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(gameProgress);
    }

    public static String getStringDate () {
        SimpleDateFormat myDF = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
        return myDF.format(new Date());
    }
}

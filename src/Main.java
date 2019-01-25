
import java.io.*;
import java.util.*;

public class Main {

    static int clonfile(File file1, File file2) throws IOException {
        try (BufferedInputStream onesfile1 = new BufferedInputStream(new FileInputStream(file1));
             BufferedInputStream onesfile2 = new BufferedInputStream(new FileInputStream(file2))) {

            while (true) {
                int fullfile1 = onesfile1.read();
                int fullfile2 = onesfile2.read();

                if (fullfile1 == -1 && fullfile2 == -1) return 0;

                if (fullfile1 != fullfile2) return fullfile1 - fullfile2;
            }
        }
    }

    public static List<File> allFailinallDirect(File all) {
        ArrayList<File> file = new ArrayList<>();
        File[] allFile = all.listFiles(new FileFilter() {
            @Override
            public boolean accept(File name) {
                return name.isDirectory();
            }
        });
        for (File allnewFile : allFile) {
            file.addAll(allFailinallDirect(allnewFile));
        }
        File[] allFile1 = all.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });
        file.addAll(Arrays.asList(allFile1));
        return file;

    }

    public static void main(String[] args) throws IOException {
        int clik=0;
        System.out.println("Enter the path to the folder where you want to search for files with the same content:");
        Scanner scan = new Scanner(System.in);
        String adres = scan.nextLine();

        List<File> files = allFailinallDirect(new File(adres));


        Set fast = new TreeSet<File>(new Comparator<File>() {
            @Override
            public int compare(File t1, File t2) {
                try {
                    return clonfile(t1, t2);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        try {
            for (File f : files) {
                if (!fast.contains(f)) {
                    fast.add(f);
                } else {
                    clik++;
                    File have = (File) ((TreeSet) fast).floor(f);
                    System.out.println("Files: \n" + f + "\n" + have +"\nHave the same content!");
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        if (clik==0) System.out.println("No matches found!");
    }
}
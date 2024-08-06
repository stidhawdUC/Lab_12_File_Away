import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import static java.lang.System.out;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import static java.nio.file.StandardOpenOption.CREATE;
import javax.swing.JFileChooser;
import java.util.Scanner;
public class DataSaver {
    public static void main(String[] args) {
        ArrayList<String> recs = new ArrayList<>(); // this will be where it all goes
        Scanner in = new Scanner(System.in);

        boolean readyToSave = false;
        System.out.println("Welcome to the DataSaver." +
                "\nBegin by entering one or more profiles.\nWhen done, it will be saved in a .csv.");
        do
        {
        String firstName = SafeInput.getNonZeroLenString(in, "Enter your first name");
        String lastName = SafeInput.getNonZeroLenString(in, "Enter your last name");
        String idNumber = SafeInput.getRegExString(in, "Enter your six-digit ID", "\\d{6}");
        String emailAddr = SafeInput.getRegExString(in, "Enter your email address", "^[a-zA-Z0-9._%±]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$");
        // Google tells me this will work as a RegEx pattern for an email, I hope it works.
        String birthYear = SafeInput.getRegExString(in, "Enter your date of birth", "\\d{4}");

        recs.add("" + firstName + ", " + lastName + ", " + idNumber +
                ", " + emailAddr + ", " + birthYear); // adding to the array list

        readyToSave = SafeInput.getYNConfirm(in, "Add another profile?");
        readyToSave = !readyToSave; // toggle boolean. "add another" is inverse to "ready to save"
        }while(!readyToSave);
        System.out.println("\nFinal list of profiles:");
        for (int i = 0; i < recs.size(); i++) System.out.printf("%3d | %s\n", i + 1, recs.get(i));
        //ask the user to name the file
        String fileName = SafeInput.getNonZeroLenString(in, "Enter .csv file name (without the extension)");
        String userNamedfilePath = "/src/" + fileName + ".csv"; //because I'm on Mac

//        System.out.println("File Name: " + userNamedfilePath);
//        System.exit(0);
//        used to test


        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + userNamedfilePath);
        try {
                OutputStream out =
                        new BufferedOutputStream(Files.newOutputStream(file, CREATE));
                BufferedWriter writer =
                        new BufferedWriter(new OutputStreamWriter(out));

                for(String rec : recs)
                {
                    writer.write(rec, 0, rec.length());
                    writer.newLine();
                }
                writer.close();
                System.out.println("Data saved!");
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }

    }
}
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import static java.lang.System.out;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import static java.nio.file.StandardOpenOption.CREATE;
import javax.swing.JFileChooser;
public class Main {
    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";
        ArrayList<String> lines = new ArrayList<>();

        //set up some fields for the delimiter
        final int FIELDS_LENGTH = 5;
        int wordCount = 0;
        int charCount = 0;
        int lineCount = 0;

        try {
            File workingDirectory = new File(System.getProperty("user.dir"));
            //this calls a toolkit to get the user's CWD, in this case the IDE's
            chooser.setCurrentDirectory(workingDirectory);
            //this gives the CWD info to the "chooser"
            //the rest of this is within an "if" in case the user closes -
            //the chooser without choosing.
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {   //what exactly is going on in the if condition above?
                //I know what it means, but not how those pieces create that condition.

                //the next 8 lines call what we need to read the file.
                selectedFile = chooser.getSelectedFile();
                //perfectly descriptive command - the chooser gets the selected file.
                Path file = selectedFile.toPath();
                //gives it to the Path.
                // this next part is "boilerplate" and upper-level java stuff I won't understand yet
                InputStream in =
                        new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(in));

                while (reader.ready()) // while the reader has something to read
                {
                    rec = reader.readLine(); // this is the actual line reading
                    charCount = charCount + rec.length();
                    lines.add(rec);
                    lineCount++; // advances our line tracker


                    out.printf("\nLine %4d %-60s ", lineCount, rec);
                }
                reader.close(); // do not forget to close the file. "seals it + flushes buffer"
                out.println("\nFile read! Processing...");

                String[] words;
                for(String l:lines) // now to count the words
                {
                    words = l.split(" ");
                    wordCount = wordCount + words.length;
                }
                    out.println("\nSpeedRead complete." +
                            "\n\nFile name: " + selectedFile +
                            "\nNumber of lines: " + lineCount +
                            "\nNumber of words: " + wordCount +
                            "\nNumber of characters: " + charCount +
                            "\n\nWhew!");





            }else out.println("File not chosen.  Restart program to choose again.");
        }
        catch(FileNotFoundException fnfe)
        {
            out.println("File not found, sorry.");
            fnfe.printStackTrace();
        }
        catch(IOException ioe)
        {

        }

    }
}
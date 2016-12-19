import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by songyilin on 2016/10/19 0019.
 */
public class Test3 {

    public static void main(String[] args) throws Exception{
        try {
            // Initiate ZipFile object with the path/name of the zip file.
            // Zip file may not necessarily exist. If zip file exists, then
            // all these files are added to the zip file. If zip file does not
            // exist, then a new zip file is created with the files mentioned
            /*File file1 = new File("f:\\test.xlsx");
            InputStream input = new FileInputStream(file1);

            byte[] byt = new byte[input.available()];


            InputStream input1 = new ByteArrayInputStream(byt);



            File file = new File("f:\\abc.xlsx");

            OutputStream output = new FileOutputStream(file);

            BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);

            bufferedOutput.write(byt);*/


            ZipFile zipFile = new ZipFile("f:\\tt.zip");



            // Build the list of files to be added in the array list
            // Objects of type File have to be added to the ArrayList
            ArrayList filesToAdd = new ArrayList();
            /*filesToAdd.add(file);*/
            filesToAdd.add(new File("f:\\aa\\test.xlsx"));

            // Initiate Zip Parameters which define various properties such
            // as compression method, etc. More parameters are explained in other
            // examples
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // set compression method to deflate compression

            // Set the compression level. This value has to be in between 0 to 9
            // Several predefined compression levels are available
            // DEFLATE_LEVEL_FASTEST - Lowest compression level but higher speed of compression
            // DEFLATE_LEVEL_FAST - Low compression level but higher speed of compression
            // DEFLATE_LEVEL_NORMAL - Optimal balance between compression level/speed
            // DEFLATE_LEVEL_MAXIMUM - High compression level with a compromise of speed
            // DEFLATE_LEVEL_ULTRA - Highest compression level but low speed
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

            // Now add files to the zip file
            // Note: To add a single file, the method addFile can be used
            // Note: If the zip file already exists and if this zip file is a split file
            // then this method throws an exception as Zip Format Specification does not
            // allow updating split zip files



            zipFile.createZipFile(filesToAdd, parameters);



            /*File file = new File("file.txt");

            InputStream input = new FileInputStream(file);

            byte[] byt = new byte[input.available()];


            InputStream input1 = new ByteArrayInputStream(byt);

            input.read(byt);
            input.close();*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

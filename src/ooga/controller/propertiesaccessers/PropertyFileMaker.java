package ooga.controller.propertiesaccessers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * this class is a very general class that makes a new properties file
 */
public class PropertyFileMaker {

  private final static String PROPERTIES_EXTENSION = ".properties";

  private BufferedWriter br;

  /**
   * creates a file with the given gile name (the file name must also
   * have the path of where the file wants to be created)
   * @param filename
   */
  public void createFile(String filename){
    try {
      br = new BufferedWriter(new FileWriter(filename + PROPERTIES_EXTENSION));
    } catch (IOException e) {
      return;
    }
  }

  /**
   * writes the val passed to the method to the properites file created
   * above
   * @param valToWrite
   */
  public void writeVals(String valToWrite)
      throws IOException {
    StringBuilder propertiesContent = new StringBuilder();
    propertiesContent.append(valToWrite + "\n");
    br.write(String.valueOf(propertiesContent));
  }

  public void closeFile() throws IOException {
    br.close();
  }


}

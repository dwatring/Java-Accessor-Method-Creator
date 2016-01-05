//Derek Watring 2016
//IMPORT variables into /PUTHERE/read.txt in this format:
//
// private int example1;
// private String example2;
// private static String example3;
//
// I've made it so you only need to copy and paste your variables as you would usually write them in
// DOCUMENTATION AVAILABLE AT: 

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class AccessorCreator {
	
	public static void main(String[] args) throws IOException{ //Checks for spaces, reads the file, creates new file, writes in new file, alerts when finished
		boolean withOrWithout = checkSpaces();
		List<String> lines = readText();
		File outputFile = createFile();
        writeToFile(lines, outputFile, withOrWithout);
        finishedAlert(withOrWithout, lines);
	}
	
	public static boolean checkSpaces(){
		boolean done = false;
		while(done == false){
		    System.out.println("Do you want spaces after every accessor function? (Y/N)");
		    Scanner checkSpaces = new Scanner(System.in);
		    String spacesInput = checkSpaces.nextLine();
		    if(spacesInput.equals("Y") || spacesInput.equals("N")){
		    	done = true;
			    checkSpaces.close();
			    if(spacesInput.equals("Y"))
			    	return true;
			    else return false;
			}
		    else System.out.println("Please enter \"Y\" or \"N\"");
		}
		return true; //Must include for compiler, never reached
	}
	
	public static List<String> readText() throws IOException{
		Path path = FileSystems.getDefault().getPath("PUTHERE", "read.txt");
		List<String> lines = Files.readAllLines(path);
		return lines;
	}
	
	public static File createFile() throws IOException{
		Path path2 = FileSystems.getDefault().getPath("PUTHERE", "output.txt");
		String path2String = path2.toString();
        File outputFile = new File(path2String);
        outputFile.createNewFile();
        return outputFile;
	}
	
	public static void writeToFile(List<String> lines, File outputFile, boolean spaces) throws IOException{
	    BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		for(int i=0;i<lines.size();i++){
			String input = (String) lines.get(i);
			input.toLowerCase();
			int dataTypeStartingPoint;
			if(input.contains("static"))
				dataTypeStartingPoint = input.indexOf(" ") + 7;
			else
				dataTypeStartingPoint = input.indexOf(" ");
			int seperationPoint = input.lastIndexOf(" ");
			String outputNameUpperCased = Character.toUpperCase(input.charAt(seperationPoint+1)) + input.substring(seperationPoint+2, input.length()-1);
			String outputNameLowerCased = input.substring(seperationPoint+1, input.length()-1);
			String dataType = input.substring(dataTypeStartingPoint+1, seperationPoint);
			String outputGetter = (String) "public " +  dataType + " get" + outputNameUpperCased + "(){" + System.lineSeparator()
					+ "		return this." + outputNameLowerCased + ";" + System.lineSeparator() 
					+ "}" + System.lineSeparator();
			String outputSetter = (String) "public void" + " set" + outputNameUpperCased + "(" + dataType + " " + outputNameLowerCased + "){" + System.lineSeparator()
					+ "		this." + outputNameLowerCased + " = " + outputNameLowerCased + ";" + System.lineSeparator()
					+ "}" + System.lineSeparator();
			if(spaces == true){
				outputGetter = outputGetter.concat(System.lineSeparator());
				outputSetter = outputSetter.concat(System.lineSeparator());
			}
			String total = outputGetter.concat(outputSetter);
		    writer.write(total);
		}
	    writer.close();
	}
	
	public static void finishedAlert(boolean withOrWithout, List<String> lines){
		String withOrWithoutText;
        if(withOrWithout == true)
        	withOrWithoutText = "with";
        	else withOrWithoutText = "without";
	    System.out.println("Finished " + lines.size() + " accessor methods " + withOrWithoutText + " spaces between them.");
	}
}

package Library;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import Canvas.C_Class;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CodeGenerate {
	private FileChooser code;
	private File file;
	private Stage owner;
	public CodeGenerate(Stage owner) {
		this.owner=owner;
		code = new FileChooser();
		FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Java files (*.java)", "*.java");
		code.getExtensionFilters().add(extension);
		
	}

	public void generateClass(C_Class data) {
		code.setInitialFileName(data.getLabel().getText());
		file = code.showSaveDialog(owner);
		if (file != null) {
			try {
				FileWriter out = new FileWriter(file);
				out.write("public class "+data.getLabel().getText()+"{\n");
				
				for(int i=0; i<data.getDatas().size(); i++){
					String raw=data.getDatas().get(i).get();
					
					String var=raw.substring(0, raw.indexOf(":"));
					String ret=raw.substring(raw.indexOf(":")+1,raw.length());
					
					out.write("public "+ret+" "+var+"; \n");
				}
				out.write("}");
				out.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

}

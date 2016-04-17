package Library;


import java.io.IOException;


public class Cmd {
	public Cmd() throws IOException, InterruptedException {
	
		
		
		
		String path="\"" +"D:\\Test Test\\Hello123456677"+ "\"";
		//path.replace(" ", "\\ ");
		System.out.println(" Path :" +path);
		
		Runtime.getRuntime().exec(new String[]{"cmd","/c","mkdir "+path});
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		new Cmd();
	}
}

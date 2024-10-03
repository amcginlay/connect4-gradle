package com.cynaptec.connectconsole;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ConnectFourTextStreamView {

	private Scanner scanner;
	private PrintStream out;
	
	public ConnectFourTextStreamView() {
		this(System.in, System.out);
	}

	public ConnectFourTextStreamView(InputStream in, OutputStream out) {
		this(in, new PrintStream(out));
	}
	
	public ConnectFourTextStreamView(InputStream in, PrintStream out) {
		this.scanner = new Scanner(in);
		this.out = out;
	}
	
	public void writeLine(String value) {
		out.println(value);
	}
	
	public String readLine() {
		String next = scanner.nextLine();
		return next;
	}

}

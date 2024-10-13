package com.cynaptec.connectconsole;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cynaptec.connect.ConnectFourPlayingServiceFactory;

public class ConnectFourTextStreamControllerTest {
			
	private ConnectFourPlayingServiceFactory modelFactory;
	private ConnectFourTextStreamView view;
	private ConnectFourTextStreamController controller;
	
	private ByteArrayOutputStream testOutput;

	@BeforeEach
	public void before() {
		modelFactory = new ConnectFourPlayingServiceFactory();
		testOutput = new ByteArrayOutputStream();
	}
	
	private ConnectFourTextStreamController buildController(String commandSequence) {
		StringBuilder sb = new StringBuilder();
		char[] turns = commandSequence.replaceAll(" ", "").toCharArray();
		for (int index = 0; index < turns.length; index++) {
			sb.append(Character.toString(turns[index]));
			sb.append(System.getProperty("line.separator"));
		}
		byte[] bytes = sb.toString().getBytes();
		ByteArrayInputStream testInput = new ByteArrayInputStream(bytes);
		view = new ConnectFourTextStreamView(testInput, testOutput);
		return new ConnectFourTextStreamController(modelFactory, view);		
	}
		
	@Test
	public void test_DisplayPlayingBoard_ReturnsEmptyBoardImage_WhenGameBegins() {
		controller = buildController("qn"); // "qn" ensures that game ends immediately
		controller.main();
		String[] result = testOutput.toString().split(System.getProperty("line.separator"));
		assertEquals(10, result.length);
		String emptyRow = new String(new char[7]).replace('\0', '.'); // 7 is the X dimension for Connect Four
		int row = 0;
		assertEquals(emptyRow, result[row++]); // as no pieces have been placed all rows should appear empty
		assertEquals(emptyRow, result[row++]); 
		assertEquals(emptyRow, result[row++]); 
		assertEquals(emptyRow, result[row++]); 
		assertEquals(emptyRow, result[row++]); 
		assertEquals(emptyRow, result[row++]);
		assertEquals("Player One: Next Move? (1-7 or Q)", result[result.length - 3]); // "last" line is user prompt
	}

	@Test
	public void test_DisplayPlayingBoard_ReturnsExpectedImage_When1QSequenceUsed() {
		controller = buildController("1qn");
		controller.main();
		String[] result = testOutput.toString().split(System.getProperty("line.separator"));
		assertEquals("O......", result[result.length - 5]);
	}

	@Test
	public void test_DisplayPlayingBoard_ReturnsExpectedImage_When1234QSequenceUsed() {
		controller = buildController("1234qn");
		controller.main();
		String[] result = testOutput.toString().split(System.getProperty("line.separator"));
		assertEquals("OXOX...", result[result.length - 5]);
	}

	@Test
	public void test_DisplayPlayingBoard_ReturnsExpectedImage_When4444QSequenceUsed() {
		controller = buildController("4444qn");
		controller.main();
		String[] result = testOutput.toString().split(System.getProperty("line.separator"));
		assertEquals("...X...", result[result.length - 8]);
		assertEquals("...O...", result[result.length - 7]);
		assertEquals("...X...", result[result.length - 6]);
		assertEquals("...O...", result[result.length - 5]);
	}
	
	@Test
	public void test_DisplayPlayingBoard_ReturnsExpectedImage_When1717171SequenceUsed() {
		controller = buildController("1717171n"); // NOTE P1 winning sequence entered so no (Q)uit required
		controller.main();
		String[] result = testOutput.toString().split(System.getProperty("line.separator"));
		assertEquals("0......", result[result.length - 7]);
		assertEquals("0.....X", result[result.length - 6]);
		assertEquals("0.....X", result[result.length - 5]);
		assertEquals("0.....X", result[result.length - 4]);
	}	

	@Test
	public void test_DisplayPlayingBoard_ReturnsExpectedImage_When71223373444QSequenceUsed() {
		controller = buildController("71223373444QN");
		controller.main();
		String[] result = testOutput.toString().split(System.getProperty("line.separator"));
		assertEquals(".......", result[result.length - 8]);
		assertEquals("..XO...", result[result.length - 7]);
		assertEquals(".XXX..O", result[result.length - 6]);
		assertEquals("XOOO..O", result[result.length - 5]);
	}

	@Test
	public void test_DisplayPlayingBoard_ReturnsExpectedImage_When712233734444SequenceUsed() {
		// NOTE same test as above but using one more piece to cause a winning sequence to emerge
		controller = buildController("712233734444n"); // NOTE P2 winning sequence entered so no (Q)uit required
		controller.main();
		String[] result = testOutput.toString().split(System.getProperty("line.separator"));
		assertEquals("...#...", result[result.length - 7]);
		assertEquals("..#O...", result[result.length - 6]);
		assertEquals(".#XX..O", result[result.length - 5]);
		assertEquals("#OOO..O", result[result.length - 4]);
	}
	
	@Test
	public void test_DisplayPlayingBoard_ReturnsExpectedImage_When122337374746464SequenceUsed() {
		controller = buildController("122337374746464n"); // NOTE P1 (multiple) winning sequence entered so no (Q)uit required
		controller.main();
		String[] result = testOutput.toString().split(System.getProperty("line.separator"));
		assertEquals("...0...", result[result.length - 7]);
		assertEquals("..00..X", result[result.length - 6]);
		assertEquals(".0O0.XX", result[result.length - 5]);
		assertEquals("0XX0.XX", result[result.length - 4]);
	}

	// TODO play again?

}

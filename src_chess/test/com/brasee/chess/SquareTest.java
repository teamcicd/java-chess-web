package com.brasee.chess;

import org.junit.Test;
import static org.junit.Assert.*;

public class SquareTest {

	@Test
	public void testCreateAValidBottomLeftSquare() {
		try {
			new Square("a1");
		}
		catch (Exception e) {
			fail("No Exception should be thrown, a1 is a valid square");
		}
	}
	
	@Test
	public void testCreateAValidTopRightSquare() {
		try {
			new Square("h8");
		}
		catch (Exception e) {
			fail("No Exception should be thrown, h8 is a valid square");
		}
	}
	
	@Test
	public void testThrowExceptionForNullSquareString() {
		try {
			new Square(null);
		}
		catch (InvalidSquareException e) {
			return;
		}
		fail("Should have thrown InvalidSquareException");
	}
	
	@Test
	public void testThrowExceptionForTooShortSquareString() {
		try {
			new Square("a");
		}
		catch (InvalidSquareException e) {
			return;
		}
		fail("Should have thrown InvalidSquareException");
	}
	
	@Test
	public void testThrowExceptionForTooLongSquareString() {
		try {
			new Square("a1 ");
		}
		catch (InvalidSquareException e) {
			return;
		}
		fail("Should have thrown InvalidSquareException");
	}
	
	@Test
	public void testThrowExceptionForInvalidFile() {
		try {
			new Square("i1");
		}
		catch (InvalidSquareException e) {
			return;
		}
		fail("Should have thrown InvalidSquareException");
	}
	
	@Test
	public void testThrowExceptionForInvalidRank() {
		try {
			new Square("a9");
		}
		catch (InvalidSquareException e) {
			return;
		}
		fail("Should have thrown InvalidSquareException");
	}
	
	@Test
	public void testSquareNotEqualToNull() {
		assertFalse(new Square("a1").equals(null));
	}
	
	@Test
	public void testSquareNotEqualToAString() {
		assertFalse(new Square("a1").equals("a1"));
	}
	
	@Test
	public void testSquareNotEqualToADifferentSquare() {
		assertFalse(new Square("a1").equals(new Square("a2")));
	}
	
	@Test
	public void testSquareEqualToTheSameSquare() {
		assertTrue(new Square("a1").equals(new Square("a1")));
	}
	
	@Test
	public void testSquaresInDiagonalPath1() {
		assertTrue(new Square("a3").inDiagonalPathWith(new Square("f8")));
	}
	
	@Test
	public void testSquaresInDiagonalPath2() {
		assertTrue(new Square("f8").inDiagonalPathWith(new Square("a3")));
	}
	
	@Test
	public void testSquaresNotInDiagonalPath() {
		assertFalse(new Square("a1").inDiagonalPathWith(new Square("h7")));
	}
	
	@Test
	public void testSquaresInSameRank1() {
		assertTrue(new Square("a3").inSameRankAs(new Square("h3")));
	}
	
	@Test
	public void testSquaresInSameRank2() {
		assertTrue(new Square("h3").inSameRankAs(new Square("a3")));
	}
	
	@Test
	public void testSquaresNotInSameRank() {
		assertFalse(new Square("a3").inSameRankAs(new Square("h4")));
	}
	
	@Test
	public void testSquaresInSameFile1() {
		assertTrue(new Square("a1").inSameFileAs(new Square("a8")));
	}
	
	@Test
	public void testSquaresInSameFile2() {
		assertTrue(new Square("a8").inSameFileAs(new Square("a1")));
	}
	
	@Test
	public void testSquaresNotInSameFile() {
		assertFalse(new Square("a1").inSameFileAs(new Square("b2")));
	}

}

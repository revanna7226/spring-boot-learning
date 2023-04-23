package com.revs.demo.junit;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

@DisplayName("Testing calculator Class")
@Description(value = "Youtube URL: https://youtu.be/Geq60OVyBPg")
public class CalculatorTest {

	Calculator underTest = new Calculator();
	
	@Test
	@DisplayName("Testing Add Method")
	void testAddMethod() {
		int n1 = 12;
		int n2 = 32;
		
		int result = underTest.add(n1, n2);
		
		assertThat(result).isEqualTo(44);
	}
	
	@Test
	@DisplayName("Testing Multiply Method")
	void testMultiplyMethod() {
		int n1 = 2;
		int n2 = 3;
		
		int result = underTest.multiply(n1, n2);
		
		assertThat(result).isEqualTo(6);
	}
	
	
	class Calculator {
		
		int add(int a, int b) {
			return a + b;
		}
		
		int multiply(int a, int b) {
			return a * b;
		}
		
	}
	
}

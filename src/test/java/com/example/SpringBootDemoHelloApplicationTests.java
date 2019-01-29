package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDemoHelloApplicationTests {
	public static void main(String[] args){
		float s=0f;
        System.out.println(s==0);
	}
	@Test
	public void contextLoads() {
//		String codeType = ((CodeType)Enum.valueOf(CodeType.class, "ServiceI")).getValue();
        System.out.println("123");
	}
	public static enum CodeType
	{
		serviceImpl("ServiceImpl"),
		service("ServiceI"), controller("Controller"), entity("Entity"), jsp(""), jspList("List");

		private String type;

		private CodeType(String type) { this.type = type; }

		public String getValue()
		{
			return this.type;
		}
	}
}

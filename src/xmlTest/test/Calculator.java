package xmlTest.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

	public int calcSum(String filePath) throws IOException {
//		BufferedReader br = null;
//		
//		try {
//			br = new BufferedReader(new FileReader(filePath));
//			int sum = 0;
//			String line = null;
//			while((line = br.readLine()) != null) {
//				sum += Integer.valueOf(line);
//			}
//			
//			return sum;
//		} catch(IOException e) {
//			throw e;
//		} finally {
//			if(br != null) {
//				try {br.close();} 
//				catch(IOException e) {System.out.println(e.getMessage());}
//			}
//		}
		
		//콜백에서 bufferedReader 생성만 담당
//		BufferedReaderCallback sumCallback = 
//			new BufferedReaderCallback() {
//				@Override
//				public int doSomethingWithReader(BufferedReader br) throws IOException {
//					Integer sum = 0;
//					String line = null;
//					while((line = br.readLine()) != null) {
//						sum += Integer.valueOf(line);
//					}
//					return sum;
//				}
//			
//		};
//		return fileReadTemplate(filePath, sumCallback);
		
		
		//콜백에서 bufferedReader 생성과 ,라인별 계산도 담당
		LineCallback sumCallback = 
			new LineCallback () {
				@Override
				public int doSomethingWithLine(String line, int value) {
					return value + Integer.valueOf(line);
				} 
		};
		return lineReadTemplte(filePath, sumCallback, 0);
	}
	
	public int calcMultiply(String numFilePath) throws IOException {
//		BufferedReaderCallback multiplyCallback= 
//			new BufferedReaderCallback () {
//				@Override
//				public int doSomethingWithReader(BufferedReader br) throws IOException {
//					Integer mul = 1;
//					String line = null;
//					
//					while((line = br.readLine()) != null) {
//						mul *= Integer.valueOf(line);
//					}
//					return mul;
//				}
//			
//		};
//		return fileReadTemplate(numFilePath, multiplyCallback);
		
		LineCallback multiplyCallback = 
			new LineCallback (){
				@Override
				public int doSomethingWithLine(String line, int value) {
					return value * Integer.valueOf(line);
				}
			
		};
		return lineReadTemplte(numFilePath, multiplyCallback, 1);
	}
	

	public int fileReadTemplate(String filePath, BufferedReaderCallback callback) {
		BufferedReader br = null;
		int ret = 0;
		try {
			br = new BufferedReader(new FileReader(filePath));
			ret = callback.doSomethingWithReader(br);
			
		} catch(IOException e) {
			System.out.println(e.getMessage());
		} finally {
			if(br != null) {
				try {br.close();} 
				catch(IOException e) {System.out.println(e.getMessage());}
			}
		}
		return ret;
	}
	
	public int lineReadTemplte(String filePath, LineCallback callback, int value) throws IOException {
		BufferedReader br = null;
		Integer res = 0;
		try {
			br = new BufferedReader(new FileReader(filePath));
			res = value;
			String line = null;
			while((line = br.readLine()) != null) {
				res = callback.doSomethingWithLine(line, res);
			}
		} catch(IOException e) {
			System.out.println(e.getMessage());
		} finally {
			if(br != null) {
				try {br.close();} 
				catch(IOException e) {System.out.println(e.getMessage());}
			}
		}
		
		return res;
	}

}

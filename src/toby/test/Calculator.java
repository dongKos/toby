package toby.test;

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
		
		//�ݹ鿡�� bufferedReader ������ ���
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
		
		
		//�ݹ鿡�� bufferedReader ������ ,���κ� ��굵 ���
		LineCallback<Integer> sumCallback = 
			new LineCallback<Integer> () {
				@Override
				public Integer doSomethingWithLine(String line, Integer value) {
					return value + Integer.valueOf(line);
				}
		};
		return lineReadTemplate(filePath, sumCallback, 0);
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
		
		LineCallback<Integer> multiplyCallback = 
			new LineCallback<Integer> (){
				@Override
				public Integer doSomethingWithLine(String line, Integer value) {
					return value * Integer.valueOf(line);
				}
		};
		return lineReadTemplate(numFilePath, multiplyCallback, 1);
	}
	
	public String concatenate(String filePath) throws IOException {
		LineCallback<String> concatenateCallback =
			new LineCallback<String> () {
				@Override
				public String doSomethingWithLine(String line, String value) {
					return value + line;
				}
		};
		return lineReadTemplate(filePath, concatenateCallback, "");
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
	
	public <T> T lineReadTemplate(String filePath, LineCallback callback, T value) throws IOException {
		BufferedReader br = null;
		T res = null;
		try {
			br = new BufferedReader(new FileReader(filePath));
			res = value;
			String line = null;
			while((line = br.readLine()) != null) {
				res = (T) callback.doSomethingWithLine(line, res);
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

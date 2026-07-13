package excelsheet;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class dataDriven {

	public ArrayList<String> getdata (String testcasename) throws IOException {
		
		ArrayList<String> a = new ArrayList<String>();

		FileInputStream fis = new FileInputStream("C:\\Users\\chand\\Downloads\\Testing excel\\SeleniumTest.xlsx");

		XSSFWorkbook workbook = new XSSFWorkbook(fis);

		int sheets = workbook.getNumberOfSheets();
		System.out.println("Total Sheets : " + sheets);

		for (int i = 0; i < sheets; i++) {

			if (workbook.getSheetName(i).equalsIgnoreCase("testdata")) {

				XSSFSheet sheet = workbook.getSheetAt(i);

				Iterator<Row> rows = sheet.iterator();

				Row firstrow = rows.next();

				Iterator<Cell> ce = firstrow.cellIterator();

				int k = 0;
				int column = 0;

				while (ce.hasNext()) {

					Cell value = ce.next();

					if (value.getStringCellValue().equalsIgnoreCase(testcasename)) {
						column = k;
					}

					k++;
				}

				System.out.println("TestCases column index = " + column);

				while (rows.hasNext()) {
					Row r = rows.next();
					if(r.getCell(column).getStringCellValue().equalsIgnoreCase(testcasename)) {
						Iterator<Cell> cv=r.cellIterator();
						while(cv.hasNext())
						{	
						System.out.println(cv.next().getStringCellValue());
						}
						
					}
				}
				
				
			}

		}

		workbook.close();
		fis.close();
		return a;
	}
public static void main(String[] args) throws IOException {
	
}
}



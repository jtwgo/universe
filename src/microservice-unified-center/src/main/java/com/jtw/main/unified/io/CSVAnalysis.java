package com.jtw.main.unified.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
/*
 * 文件规则
 * Microsoft的格式是最简单的。以逗号分隔的值要么是“纯粹的”（仅仅包含在括号之前），
 * 要么是在双引号之间（这时数据中的双引号以一对双引号表示）。
 * Ten Thousand,10000, 2710 ,,"10,000","It's ""10 Grand"", baby",10K
 * 这一行包含七个字段（fields）：
 *	Ten Thousand
 *	10000
 *	 2710 
 *	空字段
 *	10,000
 *	It's "10 Grand", baby
 *	10K
 * 每条记录占一行
 * 以逗号为分隔符
 * 逗号前后的空格会被忽略
 * 字段中包含有逗号，该字段必须用双引号括起来。如果是全角的没有问题。
 * 字段中包含有换行符，该字段必须用双引号括起来
 * 字段前后包含有空格，该字段必须用双引号括起来
 * 字段中的双引号用两个双引号表示
 * 字段中如果有双引号，该字段必须用双引号括起来
 * 第一条记录，可以是字段名
 */
 
public class CSVAnalysis {
	private InputStreamReader fr = null;
	private BufferedReader br = null;
 
	public CSVAnalysis(String f) throws IOException {
		fr = new InputStreamReader(new FileInputStream(f));
	}
 
	/**
	 * 解析csv文件 到一个list中
	 * 每个单元个为一个String类型记录，每一行为一个list。
	 * 再将所有的行放到一个总list中
	 * @return
	 * @throws IOException
	 */
	public List<List<String>> readCSVFile() throws IOException {
		br = new BufferedReader(fr);
		String rec = null;//一行
		String str;//一个单元格
		List<List<String>> listFile = new ArrayList<List<String>>();
		try {			
			//读取一行
			while ((rec = br.readLine()) != null) {
				Pattern pCells = Pattern
						.compile("(\"[^\"]*(\"{2})*[^\"]*\")*[^,]*,");
				Matcher mCells = pCells.matcher(rec);
				List<String> cells = new ArrayList<String>();//每行记录一个list
				//读取每个单元格
				while (mCells.find()) {
					str = mCells.group();
					str = str.replaceAll(
							"(?sm)\"?([^\"]*(\"{2})*[^\"]*)\"?.*,", "$1");
					str = str.replaceAll("(?sm)(\"(\"))", "$2");
					cells.add(str);
				}
				listFile.add(cells);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fr != null) {
				fr.close();
			}
			if (br != null) {
				br.close();
			}
		}
		return listFile;
	}
 
	public static void main(String[] args) throws Throwable {
		CSVAnalysis parser = new CSVAnalysis("D:\\jtw\\test.csv");
		List<List<String>> lists = parser.readCSVFile();
		System.out.println(lists);
		byte[] src = {1,2,3,4,5,6,6};
		ByteArrayInputStream in = new ByteArrayInputStream(src);
		byte[] buffer = new byte[1024];
		in.read(buffer,0,src.length);
		File file = new File("d:\\jtw\\output.txt");
		if (!file.exists())
		{
			file.createNewFile();
			file.setReadable(true);
			file.setWritable(true);
		}
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		fileOutputStream.write(buffer,0,src.length);
		fileOutputStream.flush();
		fileOutputStream.close();
		in.close();
	}
}
 
 

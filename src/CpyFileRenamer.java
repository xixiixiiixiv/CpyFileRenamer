import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CpyFileRenamer {
	public static void main(String args[]) {	
		String extdel = "\\..*$"; // 拡張子取得用正規表現
		Pattern ptnExt = Pattern.compile(extdel);
		
		File[] arry = new File(".").listFiles();
		for(File oFile : arry){
			Matcher m = ptnExt.matcher(oFile.getName());
			//System.out.println(oFile.getName());
			
			String extName = "";
			if (m.find()) extName = m.group(0); // 拡張子退避
			String oldFileName = m.replaceFirst(""); // 拡張子除去
			String newFileName = oldFileName.replaceFirst(" - コピー.*$|^コピー (|\\([0-9]+\\) )～ ",""); // 編集
			
			// ファイル名変更対象外の場合は次のファイルへ
			if (oldFileName.equals(newFileName)) continue;

			System.out.println("   " + oFile.getName());
			
			// ファイル名編集
			StringBuilder sbFileName = new StringBuilder();
			sbFileName.append(newFileName);
			sbFileName.append(new SimpleDateFormat("_yyyyMMddhhmmss").format(oFile.lastModified()));
			sbFileName.append(extName);
			newFileName = sbFileName.toString();
			
			// ファイル移動
			boolean isSuccess = false;
			if (new File(newFileName).exists()) {
				System.out.println("   既に同名のファイルが存在します。");
			} else {
				try
				{
					oFile.renameTo(new File(newFileName));
					isSuccess = true;
				}catch(SecurityException e){
					System.out.println("   ファイルのリネームに失敗しました。");
				}
			}
			System.out.println((isSuccess ? "○" : "×") + ">" + sbFileName.toString());
		}		
	}
}

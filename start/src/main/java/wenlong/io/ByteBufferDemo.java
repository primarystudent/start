package wenlong.io;

import java.io.File;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.FileSystems;
import java.nio.file.StandardOpenOption;
import java.util.zip.CRC32;

public class ByteBufferDemo {

	public static void main(String[] args) {
		System.out.println(checksumMappedFile(""));
	}

	public static long checksumMappedFile(String path) {
		try {
			File file = new File("");

			FileChannel fileChannel = FileChannel
					.open(FileSystems
							.getDefault()
							.getPath(
									"C:\\Users\\wangwenlong\\Desktop\\spring-boot-reference.pdf"),
							StandardOpenOption.READ);

			int size = (int)fileChannel.size();
			MappedByteBuffer buffer = fileChannel.map(MapMode.READ_ONLY, 0, size);
			CRC32 crc = new CRC32();
			for(int p=0;p<size;p++){
				int c = buffer.get(p);
				crc.update(c);
			}
			return crc.getValue();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}

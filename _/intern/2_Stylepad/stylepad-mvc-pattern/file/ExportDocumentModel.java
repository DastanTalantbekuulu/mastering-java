package file;

import java.io.File;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class ExportDocumentModel {
    public boolean saveAsToFile(File file, String content) {
        RandomAccessFile writer = null;
        FileChannel channel = null;
        ByteBuffer buff = null;
        try {
            writer = new RandomAccessFile(file, "rw");
            channel = writer.getChannel();
            buff = ByteBuffer.wrap(content.getBytes(StandardCharsets.UTF_8));
            channel.write(buff);
            channel.truncate(channel.position());
            return true;
        } catch (NotSerializableException nse) {
            System.out.println("NotSerializableException: " + nse);
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (channel != null) {
                    channel.close();
                }
                if (buff != null) {
                    buff.clear();
                }
            } catch (IOException ioe) {
                System.out.println("IOException: " + ioe);
            }
        }
        return false;
    }
}

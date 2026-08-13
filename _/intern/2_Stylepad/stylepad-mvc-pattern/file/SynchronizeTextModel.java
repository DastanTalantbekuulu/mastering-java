package file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class SynchronizeTextModel {
    private RandomAccessFile writer;
    private FileChannel channel;
    private ByteBuffer remainingContent;
    private File file;

    public SynchronizeTextModel(File file) {
        this.file = file;
    }

    public void write(String str, int position) {
        try {
            // Insertion
            channel.position(position);
            remainingContent = ByteBuffer.allocate((int) (channel.size() - position));
            channel.read(remainingContent);
            remainingContent.flip();

            channel.position(position);
            channel.write(ByteBuffer.wrap(str.getBytes()));

            channel.write(remainingContent);
            remainingContent.clear();
        } catch (IOException ioe) {
            System.out.println("Error during write operation: " + ioe.getMessage());
        }
    }

    public void write(int deleteLength, int position) {
        try {
            // Deletion
            long newFileSize = channel.size() - deleteLength;

            remainingContent = ByteBuffer.allocate((int) (channel.size() - (position + deleteLength)));
            channel.position(position + deleteLength);
            channel.read(remainingContent);
            remainingContent.flip();

            channel.position(position);
            channel.write(remainingContent);

            channel.truncate(newFileSize);
            remainingContent.clear();
        } catch (IOException ioe) {
            System.out.println("Error during write operation: " + ioe.getMessage());
        }
    }

    public boolean openFile() {
        try {
            writer = new RandomAccessFile(file, "rw");
            channel = writer.getChannel();
            return true;
        } catch (IOException ioe) {
            System.out.println(ioe);
            return false;
        }
    }

    public void closeFile() {
        try {
            if (writer != null) {
                writer.close();
            }
            if (channel != null) {
                channel.close();
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    public void setFile(File file) {
        this.file = file;
    }
}

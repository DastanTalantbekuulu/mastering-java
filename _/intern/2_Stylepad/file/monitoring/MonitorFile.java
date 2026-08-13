package monitoring;

import java.io.File;

public class MonitorFile {

    private long timeStamp;
    private File file;

    private boolean isFileUpdated( File file ) {
        this.file = file;
        this.timeStamp = file.lastModified();

        if( this.timeStamp != timeStamp ) {
            this.timeStamp = timeStamp;
            return true;
        }
        return false;
    }
}

package com.programs.challenges;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class DeleteFilesDemo {

	public static void main(String[] args) {
		Path path = Paths.get("/home/pranshu/Downloads/EMS");
		
		try {
			Files.list(path);
			Files.walkFileTree(path, new FileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					System.out.println("Deleting dir: " + dir);
                    if (exc == null) {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    } else {
                        throw exc;
                    }
				}

				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					System.out.println("Path to delete directory : "+path.toString());
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					System.out.println("Deleting file: " + file);
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
					System.err.println("Unable to delete file");
					return FileVisitResult.TERMINATE;
				}

				

			});
		} catch (IOException e) {
			System.err.println("Error :" + e);
		}
	}

}

package monitoring;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class FileWatcher3 {

    public static void main(String[] args) {
        // Укажите путь к отслеживаемому файлу
        Path filePath = Paths.get("file/example.txt");
        Path directory = filePath.getParent(); // Папка, где находится файл

        if (!Files.exists(filePath)) {
            System.out.println("Файл не найден: " + filePath);
            return;
        }

        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            // Регистрируем папку для отслеживания изменений
            directory.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            System.out.println("Отслеживание изменений для файла: " + filePath);

            while (true) {
                WatchKey key = watchService.take(); // Ожидаем события

                for (WatchEvent<?> event : key.pollEvents()) {
                    // Получаем имя измененного файла
                    Path changedFile = directory.resolve((Path) event.context());

                    // Проверяем, наш ли это файл
                    System.out.println("changedFile = " + changedFile);
                    if (changedFile.equals(filePath)) {
                        System.out.println("Файл изменен: " + changedFile);
                    }
                }

                // Сбрасываем ключ, чтобы продолжить отслеживание
                if (!key.reset()) {
                    System.out.println("Невозможно сбросить WatchKey. Завершаем отслеживание.");
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}


public interface Mediator {
    void prevLevel();

    void nextLevel();

    void startGame();

    void restartGame();

    void undo();

    void redo();

    void pauseMusic();

    void resumeMusic();
}

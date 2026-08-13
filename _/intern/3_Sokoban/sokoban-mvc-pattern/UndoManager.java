import java.util.ArrayList;

public class UndoManager {
    private ArrayList<UndoableMove> undoableMoves;
    private int index;

    public UndoManager() {
        undoableMoves = new ArrayList<UndoableMove>();
    }

    public void setupUndoManager(UndoableMove undoableMove) {
        undoableMoves.clear();
        index = 0;
        addUndoableMove(undoableMove);
    }

    public void addUndoableMove(UndoableMove undoableMove) {
        checkIndexPosition();

        if(!undoableMoves.isEmpty()) {
            index = index + 1;
        }
        undoableMoves.add(undoableMove);
    }

    private void checkIndexPosition() {
        if(index != undoableMoves.size() - 1) {
            removeTrailingElements();
        }
    }

    private void removeTrailingElements() {
        for(int i = undoableMoves.size() - 1; i > index; i--) {
            undoableMoves.remove(i);
        }
    }

    public UndoableMove undo() {
        if(undoableMoves.isEmpty()) {
            return null;
        }

        UndoableMove previousGameState = null;

        if(index != 0) {
          index = index - 1;
          previousGameState = undoableMoves.get(index);
        }

        return previousGameState;
    }

    public UndoableMove redo() {
        if(undoableMoves.isEmpty()) {
            return null;
        }

        UndoableMove previousGameState = null;

        if(index != undoableMoves.size() - 1) {
          index = index + 1;
          previousGameState = undoableMoves.get(index);
        }

        return previousGameState;
    }
}

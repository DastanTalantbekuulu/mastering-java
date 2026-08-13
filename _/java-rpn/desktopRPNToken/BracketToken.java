
public class BracketToken extends Token {

    private final boolean isOpenBracket;

    public BracketToken(String literal, boolean isOpenBracket) {
        super(literal);
        this.isOpenBracket = isOpenBracket;
    }

    public boolean getIsOpenBracket() {
        return isOpenBracket;
    }
}

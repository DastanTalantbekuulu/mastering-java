package stylepad;

import stylepad.model.Paragraph;
import stylepad.model.Run;

public class Book {
    public static Paragraph[] wonderland = new Paragraph[]{
            new Paragraph("title", new Run[]{ new Run("none", "ALICE'S ADVENTURES IN WONDERLAND")}),
            new Paragraph("author", new Run[]{new Run("none", "Lewis Carroll")}),
            new Paragraph("heading", new Run[]{new Run("alice", " ")}),
            new Paragraph("edition", new Run[]{new Run("none", "THE MILLENNIUM FULCRUM EDITION 3.0")}),
            new Paragraph("heading", new Run[]{new Run("none", "CHAPTER V")}),
            new Paragraph("subtitle", new Run[]{new Run("none", "Advice from a Caterpillar")}),
            new Paragraph("normal", new Run[]{new Run("none", " "),}),            new Paragraph("normal", new Run[]{
                    new Run("none","The Caterpillar and Alice looked at each other for some time in "
                                    + "silence:  at last the Caterpillar took the hookah out "
                                    + "of its mouth, and addressed her in a languid, sleepy "
                                   + "voice.")
            }),
            new Paragraph("normal", new Run[]{
                    new Run("cquote", "Who are YOU?  "),
                    new Run("none", "said the Caterpillar.")
            }),
            new Paragraph("normal",
                    new Run[]{
                            new Run("none",
                                    "This was not an encouraging opening for a conversation.  Alice "
                                            + "replied, rather shyly, "),
                            new Run("aquote",
                                    "I--I hardly know, sir, just at present--at least I know who I WAS "
                                            + "when I got up this morning, but I think I must have "
                                            + "been changed several times since then. "),}),
            new Paragraph("heading", new Run[]{
                    new Run("caterpillar", " ")
            }),
            new Paragraph("normal", new Run[]{
                    new Run("cquote", "What do you mean by that? "),
                    new Run("none", " said the Caterpillar sternly.  "),
                    new Run("cquote", "Explain yourself!"),}),
            new Paragraph("normal", new Run[]{
                    new Run("aquote", "I can't explain MYSELF, I'm afraid, sir"),
                    new Run("none", " said Alice, "),
                    new Run("aquote", "because I'm not myself, you see."),}),
            new Paragraph("normal", new Run[]{
                    new Run("cquote", "I don't see,"),
                    new Run("none", " said the Caterpillar."),}),
            new Paragraph("normal",
                    new Run[]{
                            new Run("aquote", "I'm afraid I can't put it more clearly,  "),
                            new Run("none", "Alice replied very politely, "),
                            new Run("aquote",
                                    "for I can't understand it myself to begin with; and being so many "
                                            + "different sizes in a day is very confusing."),}),
            new Paragraph("normal", new Run[]{
                    new Run("cquote", "It isn't,  "),
                    new Run("none", "said the Caterpillar.")
            }),
            new Paragraph("normal", new Run[]{
                    new Run("aquote", "Well, perhaps you haven't found it so yet,"),
                    new Run("none", " said Alice; "),
                    new Run("aquote",
                            "but when you have to turn into a chrysalis--you will some day, "
                                    + "you know--and then after that into a butterfly, I "
                                    + "should think you'll feel it a little queer, won't you?")
            }),
            new Paragraph("normal", new Run[]{
                    new Run("cquote", "Not a bit, "),
                    new Run("none", "said the Caterpillar.")
            }),
            new Paragraph("normal",
                    new Run[]{
                            new Run("aquote", "Well, perhaps your feelings may be different,"),
                            new Run("none", " said Alice; "),
                            new Run("aquote", "all I know is, it would feel very queer to ME."),
                    }),
            new Paragraph("normal", new Run[]{
                    new Run("cquote", "You!"),
                    new Run("none", " said the Caterpillar contemptuously.  "),
                    new Run("cquote", "Who are YOU?"),}),
            new Paragraph("normal", new Run[]{
                    new Run("normal",
                            "Which brought them back again to the beginning of the "
                                    + "conversation.  Alice felt a little irritated at the "
                                    + "Caterpillar's making such VERY short remarks, and she "
                                    + "drew herself up and said, very gravely, "),
                    new Run("aquote",
                            "I think, you ought to tell me who YOU are, first."),}),
            new Paragraph("normal", new Run[]{
                    new Run("cquote", "Why?  "),
                    new Run("none", "said the Caterpillar."),}),
            new Paragraph("heading", new Run[]{
                    new Run("hatter", " ")
            }),
            new Paragraph("normal", new Run[]{
                    new Run("none", " "),}),
            new Paragraph("normal", new Run[]{
                    new Run("none", " "),}),
            new Paragraph("normal", new Run[]{
                    new Run("none", " "),})
    };
        public  static Paragraph[] hello = new Paragraph[]{
                new Paragraph("title", new Run[]{
                        new Run("none", "Hello from Cupertino")
                }),
                new Paragraph("title", new Run[]{
                        new Run("none", "\u53F0\u5317\u554F\u5019\u60A8\u0021")
                }),
                new Paragraph("title", new Run[]{
                        new Run("none", "\u0391\u03B8\u03B7\u03BD\u03B1\u03B9\u0020"
                                + "\u03B1\u03C3\u03C0\u03B1\u03B6\u03BF\u03BD"
                                + "\u03C4\u03B1\u03B9\u0020\u03C5\u03BC\u03B1"
                                + "\u03C2\u0021")
                }),
                new Paragraph("title", new Run[]{
                        new Run("none", "\u6771\u4eac\u304b\u3089\u4eca\u65e5\u306f")
                }),
                new Paragraph("title", new Run[]{
                        new Run("none", "\u05e9\u05dc\u05d5\u05dd \u05de\u05d9\u05e8\u05d5"
                                + "\u05e9\u05dc\u05d9\u05dd")
                }),
                new Paragraph("title", new Run[]{
                        new Run("none", "\u0633\u0644\u0627\u0645")
                })
        };}

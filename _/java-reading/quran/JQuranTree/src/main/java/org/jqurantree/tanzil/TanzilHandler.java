package org.jqurantree.tanzil;

import java.util.ArrayList;
import java.util.List;

import org.jqurantree.arabic.ArabicText;
import org.jqurantree.orthography.Sura;
import org.jqurantree.orthography.Quran;
import org.jqurantree.orthography.Aya;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

class TanzilHandler extends DefaultHandler {

    private int suraNumber;
    private ArabicText suraName;
    private ArabicText bismillah;
    private static final String SURA_ELEMENT = "sura";
    private static final String AYA_ELEMENT = "aya";
    private final Sura[] suras = new Sura[Quran.getSuraCount()];
    private final List<Aya> ayas = new ArrayList<Aya>();

    public Sura[] getChapters() {
        return suras;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (localName.equals(SURA_ELEMENT)) {
            String value = attributes.getValue("index");
            suraNumber = Integer.parseInt(value);
            String suraNameText = attributes.getValue("name");
            suraName = ArabicText.fromUnicode(suraNameText);
        } else if (localName.equals(AYA_ELEMENT)) {
            String value = attributes.getValue("index");
            int ayaNumber = Integer.parseInt(value);
            String text = attributes.getValue("text");
            ayas.add(new Aya(suraNumber, ayaNumber, text));
            if (ayaNumber == 1) {
                String bismillahText = attributes.getValue("bismillah");
                bismillah = bismillahText != null ? ArabicText
                        .fromUnicode(bismillahText) : null;
            }
        }
    }

    public void endElement(String uri, String localName, String qName) {
        if (localName.equals(SURA_ELEMENT)) {
            int size = this.ayas.size();
            Aya[] ayas = new Aya[size];
            this.ayas.toArray(ayas);
            this.ayas.clear();
            suras[suraNumber - 1] = new Sura(suraNumber,
                    suraName, bismillah, ayas);
        }
    }
}

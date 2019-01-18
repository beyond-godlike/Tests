package com.example.dia.tests.ui;

import android.content.Context;
import com.example.dia.tests.data.Item;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Presenter {
    Context context;
    Presenter (Context c) {
        this.context = c;
    }


    private Item temp;
    private List<Item> itemList;

    private int totalQuestions = 0;


    void doParse(String filename)
    {
        XmlPullParserFactory pullParserFactory;
        XmlPullParser parser;

        try
        {
            pullParserFactory = XmlPullParserFactory.newInstance();
            parser = pullParserFactory.newPullParser();

            InputStream in_s = context.getAssets().open(filename);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            parseNewFile(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseNewFile(XmlPullParser parser) throws IOException, XmlPullParserException {
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT){
            {
                String name;
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        itemList = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equals("item")){
                            temp = new Item("", new ArrayList<String>(), "");
                        }
                        else if (temp != null)
                        {
                            if(name.equals("question"))
                            {
                                temp.setQuestion(parser.nextText());
                            }
                            else if(name.equals("answer"))
                            {
                                temp.addAnswers(parser.nextText());
                            }
                            else if (name.equals("correct"))
                            {
                                temp.setCorrect(parser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("item") && temp != null)
                        {
                            itemList.add(temp);
                            setTotalQuestions(getTotalQuestions() + 1);
                        }
                }
                eventType = parser.next();
            }
        }
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

}

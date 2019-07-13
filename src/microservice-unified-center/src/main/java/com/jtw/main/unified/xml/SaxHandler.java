package com.jtw.main.unified.xml;

import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SaxHandler extends DefaultHandler
{
    Book book = new Book();
    List<Book> books = new ArrayList<>();
    private String value = "";
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        System.out.println("sax解析开始。。。。。");
    }
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        System.out.println(books);
        System.out.println("sax解析结束。。。。。");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if ("book".equals(qName))
        {
            book = new Book();
            System.out.println("book的属性值：");
            for (int i = 0; i < attributes.getLength(); i++)
            {
                System.out.println(attributes.getQName(i) + ":" + attributes.getValue(i));
                if ("id".equals(attributes.getQName(i)))
                {
                    book.setId(attributes.getQName(i));
                }
            }
        }
        else if(!"bookstore".equals(qName) && !"book".equals(qName))
        {
            System.out.print("属性名：" + qName + ",");
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
         if("bookstore".equals(qName) || "book".equals(qName))
        {
            if ("book".equals(qName))
            {
                books.add(book);
                book = null;
            }
            return;
        }
        String setter = "set" + qName.substring(0, 1).toUpperCase() + qName.substring(1);

        try {
            Method method = book.getClass().getMethod(setter, String.class);
            method.invoke(book,value);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        value = new String(ch, start, length);
        if (StringUtils.isNotEmpty(value.trim()))
        System.out.println("属性值:" + value);
    }
}

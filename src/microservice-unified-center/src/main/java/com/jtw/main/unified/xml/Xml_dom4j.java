package com.jtw.main.unified.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Xml_dom4j {
public static void main(String[] args) {
    try {
            SAXReader sax=new SAXReader();
            FileInputStream fin=new FileInputStream(new File("d:\\jtw\\aa.xml"));
            Document doc=sax.read(fin);
            Element ele=doc.getRootElement();
            @SuppressWarnings("unchecked")
            List<Element> list=ele.elements();
            for (int i = 0; i < list.size(); i++)
            {
                Element element = list.get(i);
                if (element.getName().equals("book"))
                {
                    for (Object attribute : element.attributes())
                    {
                        if (attribute instanceof Attribute)
                        {
                            Attribute attribute1 = (Attribute) attribute;
                            String name = attribute1.getName();
                            System.out.println("book的属性：" + name + "=" +element.attributeValue(name));
                        }
                    }
                    List<Element> elements = element.elements();
                    for (Element element1 : elements)
                    {
                        if (!element.getName().equals("bookstore"))
                        {
                            System.out.println("节点属性：" + element1.getName() + ",节点值：" + element1.getStringValue());
                        }
                    }

                }

            }
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }
}
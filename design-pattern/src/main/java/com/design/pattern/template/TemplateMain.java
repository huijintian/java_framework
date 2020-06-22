package com.design.pattern.template;

/**
 * Created by mengtian on 2020/6/23
 */
public class TemplateMain {
    public static void main(String[] args) {
        AbstractDisplay ad = new CharDisplay('H');
        AbstractDisplay ad2 = new StringDisplay("Hello World");
        ad.display();
        ad2.display();
    }
}

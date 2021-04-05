package com.example.recyclerview_example;

import android.app.Person;

import java.util.ArrayList;
import java.util.List;

public class CloneFactory {
    private static CloneFactory sCloneFactory;
    private static List<Person> mPersonList;

    public class Person {

        private String name;
        private int age;
        private String adress;
        private boolean sex;



        public Person(String name, int age, String adress, boolean sex) {
            this.name = name;
            this.age = age;
            this.adress = adress;
            this.sex = sex;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getAdress() {
            return adress;
        }

        public void setAdress(String adress) {
            this.adress = adress;
        }

        public boolean isSex() {
            return sex;
        }

        public void setSex(boolean sex) {
            this.sex = sex;
        }
    }

    private CloneFactory() {
        mPersonList = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            if(i % 2 == 0){
                mPersonList.add(new Person("Иванов Иван клон#"+i, 25, "Москва", true));
            }else{
                mPersonList.add(new Person("Петрова Мария клон#"+i, 33, "Санкт-Петербург", false));
            }
        }

    }

    public static List<Person> getCloneList() {
        if(sCloneFactory == null){
            sCloneFactory = new CloneFactory();
        }
        return mPersonList;
    }
}

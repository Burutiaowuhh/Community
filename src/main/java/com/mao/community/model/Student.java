package com.mao.community.model;

public class Student {
    private String stuname;
    private int stuage;

    @Override
    public String toString() {
        return "Student{" +
                "stuname='" + stuname + '\'' +
                ", stuage=" + stuage +
                '}';
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public int getStuage() {
        return stuage;
    }

    public void setStuage(int stuage) {
        this.stuage = stuage;
    }
}

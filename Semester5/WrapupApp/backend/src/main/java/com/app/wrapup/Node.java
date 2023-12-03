package com.app.wrapup;

public class Node {
    private Integer id;
    private Integer left;
    private Integer top;

    public Node(Integer id, Integer left, Integer top) {
        this.id = id;
        this.left = left;
        this.top = top;
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getLeft() {
        return this.left;
    }

    public Integer getTop() {
        return this.top;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public String toString() {
        return "Node{" +
                "id='" + id + '\'' +
                ", left='" + left + '\'' +
                ", top='" + top + '\'' +
                '}';
    }
}


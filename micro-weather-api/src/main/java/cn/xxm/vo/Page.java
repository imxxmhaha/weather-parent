package cn.xxm.vo;

import java.io.Serializable;

public class Page implements Serializable {
    private static final long serialVersionUID = 1L;
    private int startLine;
    private int pageSize;
    private int currentPage;
    private int totalNumber;


    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Page(int currentPage, int pageSize) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.startLine = (currentPage - 1) * this.pageSize;
    }

    public Page() {
    }

    public int getStartLine() {
        return startLine;
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        this.startLine = (currentPage - 1) * this.pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        this.startLine = (currentPage - 1) * this.pageSize;
    }


    public static void main(String[] args) {
        Page page = new Page(3, 5);
        System.out.println(page.getStartLine());
    }
}
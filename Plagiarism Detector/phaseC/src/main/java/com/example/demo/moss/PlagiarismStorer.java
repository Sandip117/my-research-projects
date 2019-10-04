package com.example.demo.moss;

public class PlagiarismStorer {


    private String link; //the link to analysis
    private String file1; //the uri for first file detected for plagiarism
    private String file1Percent; //the percentage of plagiarism detected for file 1
    private String file2; //the uri for second file detected for plagiarism
    private String file2Percent; //the percentage of plagiarism detected for file 2


    //getters and setters

    public int getLinesCopied() {
        return linesCopied;
    }

    public void setLinesCopied(int linesCopied) {
        this.linesCopied = linesCopied;
    }

    private int linesCopied;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFile1() {
        return file1;
    }

    public void setFile1(String file1) {
        this.file1 = file1;
    }

    public String getFile1Percent() {
        return file1Percent;
    }

    public void setFile1Percent(String file1Percent) {
        this.file1Percent = file1Percent;
    }

    public String getFile2() {
        return file2;
    }

    public void setFile2(String file2) {
        this.file2 = file2;
    }

    public String getFile2Percent() {
        return file2Percent;
    }

    public void setFile2Percent(String file2Percent) {
        this.file2Percent = file2Percent;
    }
}

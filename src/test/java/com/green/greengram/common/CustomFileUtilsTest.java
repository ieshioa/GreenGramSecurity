package com.green.greengram.common;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CustomFileUtilsTest {

    @Test
    void deleteFolder() {
        String delFolder = "D:\\Students\\MJ\\download\\delFolder2";
        File del = new File(delFolder);
        del.delete();
    }

    @Test
    void deleteFolder2() {
        CustomFileUtils customFileUtils = new CustomFileUtils("");

        String delFolder = "D:/Students/MJ/download/delFolder";
        customFileUtils.deleteFolder(delFolder);
    }
}
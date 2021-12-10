package com.officelibrary.library.util;

import java.time.LocalDate;

public class LibraryUtil {

    private LibraryUtil() {
        throw new AssertionError("");
    }

    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

}

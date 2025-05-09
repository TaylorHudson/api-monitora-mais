package br.com.pj2.back.core.common.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RegexUtils {
    private static final String TEACHER_REGEX = "^\\d{7}$";
    private static final String STUDENT_REGEX = "^\\d{12}$";

    public static boolean isStudentRegistration(String registration) {
        return registration.matches(STUDENT_REGEX);
    }

    public static boolean isTeacherRegistration(String registration) {
        return registration.matches(TEACHER_REGEX);
    }
}
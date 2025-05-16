package com.iudigital.util;

public class FuncionarioUtil {

    public static String mapSexoToDbValue(String sexoCorto) {
        if (sexoCorto == null) return null;
        switch (sexoCorto) {
            case "M": return "MASCULINO";
            case "F": return "FEMENINO";
            case "O": return "OTRO";
            default: return null;
        }
    }

    public static String mapDbValueToSexo(String sexoBd) {
        if (sexoBd == null) return null;
        switch (sexoBd) {
            case "MASCULINO": return "M";
            case "FEMENINO": return "F";
            case "OTRO": return "O";
            default: return null;
        }
    }
}

package br.com.five.seven.food.infra.utils;

public abstract class FoodUtils {

    private FoodUtils() {

    }

    public static String limparString(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.replaceAll("[^0-9]", "");
    }
}

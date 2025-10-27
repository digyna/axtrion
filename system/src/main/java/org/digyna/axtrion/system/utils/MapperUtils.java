package org.digyna.axtrion.system.utils;

import lombok.experimental.UtilityClass;
import java.util.List;
import java.util.function.Function;

@UtilityClass
public class MapperUtils {
    /**
     * Aplica un mapper a cada elemento de la lista y devuelve la lista resultante.
     *
     * @param list   Lista de entrada
     * @param mapper Función de mapeo
     * @param <T>    Tipo de entrada
     * @param <R>    Tipo de salida
     * @return Lista mapeada
     */
    public <T, R> List<R> mapList(List<T> list, Function<T, R> mapper) {
        return list.stream()
                .map(mapper)
                .toList();
    }
}

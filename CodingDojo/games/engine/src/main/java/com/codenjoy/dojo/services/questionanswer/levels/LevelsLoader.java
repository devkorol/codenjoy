package com.codenjoy.dojo.services.questionanswer.levels;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.utils.ReflectUtils;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class LevelsLoader {

    private static List<Class<? extends Level>> classes;

    /**
     * Here Map of complexities is passed for more convenient way to set complexity for level in one place
     * rather than do it in every Algorithm class.
     *
     * @param complexities - contains complexity as key and Algorithm class and value.
     */
    public static List<Level> getAlgorithms(Map<Integer, Class<? extends Level>> complexities) {
        List<Class<? extends Level>> classes = loadClasses();

        List<Level> result = createLevels(classes);

        initComplexity(result, complexities);

        sortByComplexity(result);

        return result;
    }

    private static void initComplexity(List<Level> levels,
                                       Map<Integer, Class<? extends Level>> complexities) {
        int max = complexities.keySet().stream()
                .max(Integer::compareTo)
                .orElse(0);

        for (Level level : levels) {
            Optional<Integer> current = complexities.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(level.getClass()))
                    .map(Map.Entry::getKey)
                    .findFirst();
            level.setComplexity(current.orElse(++max));
        }
    }

    private static void sortByComplexity(List<Level> result) {
        result.sort(Comparator.comparingInt(Level::complexity));
    }

    private static List<Level> createLevels(List<Class<? extends Level>> classes) {
        List<Level> result = new LinkedList<>();
        for (Class<? extends Level> clazz : classes) {
            try {
                Level level = clazz.newInstance();
                result.add(level);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    private static List<Class<? extends Level>> loadClasses() {
        // лейзи загрузка
        if (classes != null) {
            return classes;
        }

        classes = new LinkedList<>();

        classes.addAll(ReflectUtils.findInPackage("com", Level.class));
        classes.addAll(ReflectUtils.findInPackage("org", Level.class));
        classes.addAll(ReflectUtils.findInPackage("net", Level.class));

        classes.remove(Level.class);
        classes.remove(WaitLevel.class);
        classes.remove(LevelsPoolImpl.class);
        classes.remove(AlgorithmLevelImpl.class);
        classes.remove(QuestionAnswerLevelImpl.class);
        classes = classes.stream()
                .filter(clazz -> !clazz.getName().contains("Test$"))
                .filter(clazz -> !clazz.getName().contains("TestLevel"))
                .filter(clazz -> !clazz.getName().contains("SampleAlgorithm"))
                .collect(toList());
        return classes;
    }

}

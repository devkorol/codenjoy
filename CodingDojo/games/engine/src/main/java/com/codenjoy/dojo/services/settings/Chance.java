package com.codenjoy.dojo.services.settings;

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

import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.printer.CharElement;

import java.util.*;

public class Chance<T extends CharElement> {

    public static final int MAX_PERCENT = 100;
    public static final int AUTO_VALUE = -1;
    public static final int DEFAULT_RESERVED_VALUE = 30;
    public static final SettingsReader.Key CHANCE_RESERVED = () -> "[Chance] Reserved for autogenerated";

    private Dice dice;
    private SettingsReader settings;

    private Map<T, Parameter<Integer>> input;
    private List<T> axis;

    public Chance(Dice dice, SettingsReader settings) {
        this.settings = settings;
        this.input = new LinkedHashMap<>();
        this.dice = dice;
        this.axis = new LinkedList<>();

        checkReservedForAuto();
    }

    public void checkReservedForAuto() {
        try {
            settings.integer(CHANCE_RESERVED);
        } catch (Exception e) {
            // если его не установили, сделаем это сами
            settings.integer(CHANCE_RESERVED, DEFAULT_RESERVED_VALUE);
        }

        settings.integerValue(CHANCE_RESERVED).onChange((old, updated) -> run());
    }

    public Chance<T> put(SettingsReader.Key name, T el) {
        Parameter<Integer> param = settings.integerValue(name);
        add(el, param);
        param.onChange((old, updated) -> run());
        return this;
    }

    private int countAuto() {
        return (int) input.values().stream()
                .filter(param -> param.getValue() == AUTO_VALUE)
                .count();
    }

    private int sum() {
        return input.values().stream()
                .mapToInt(param -> param.getValue())
                .filter(param -> param > 0)
                .sum();
    }

    private void checkParams() {
        int sum = sum();
        if (sum > MAX_PERCENT) {
            changeParams(sum, countAuto());
        }
    }

    private void changeParams(int sum, int auto) {
        int reserved = (auto == 0) ? 0 : reservedForAuto();

        input.forEach((el, param) -> {
            int value = param.getValue();
            if (value <= 0) return;

            value = value * (MAX_PERCENT - reserved) / sum;
            if (value <= 0) return;

            param.justSet(value);
        });

        checkParams();
    }

    private int reservedForAuto() {
        return settings.integer(CHANCE_RESERVED);
    }

    private void fillAxis(int autoRange) {
        input.forEach((el, param) -> addAxis(el, param, autoRange));
    }

    private void addAxis(T el, Parameter<Integer> param, int autoRange) {
        if (param.getValue() > 0) {
            axis.addAll(Collections.nCopies(param.getValue(), el));
        }

        if (param.getValue() == AUTO_VALUE) {
            axis.addAll(Collections.nCopies(autoRange, el));
        }
    }

    private int autoRange() {
        int count = countAuto();
        if (count == 0) {
            return 0;
        }

        return reservedForAuto() / count;
    }

    public T any() {
        if (axis.isEmpty()) {
            return null;
        }

        return axis.get(dice.next(axis.size()));
    }

    public List<T> axis() {
        return axis;
    }

    private void add(T el, Parameter<Integer> param) {
        input.put(el, param);
    }

    public Chance<T> run() {
        axis.clear();
        checkParams();
        fillAxis(autoRange());
        return this;
    }
}

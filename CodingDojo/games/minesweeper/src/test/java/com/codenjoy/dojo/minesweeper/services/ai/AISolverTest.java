package com.codenjoy.dojo.minesweeper.services.ai;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 - 2021 Codenjoy
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

import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.games.minesweeper.Board;
import com.codenjoy.dojo.minesweeper.services.ai.AISolver;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Direction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AISolverTest {

    private Dice dice;
    private Solver ai;

    @Before
    public void setup() {
        dice = mock(Dice.class);
        ai = new AISolver(dice);
    }

    private Board board(String board) {
        return (Board) new Board().forString(board);
    }

    private void asertAI(String board, String expected) {
        String actual = ai.get(board(board));
        assertEquals(expected, actual);
    }

    private void dice(Direction direction) {
        when(dice.next(anyInt())).thenReturn(direction.value());
    }

    // проверяем что мы можем заходить в узкие проходы,
    // для этого анализиурем что было под героем в прошлом тике
    @Test
    public void should1() {
        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼     1*2    *☼" +
                "☼  1111*1    *☼" +
                "☼  1******☺  *☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "LEFT");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼    ***2    *☼" +
                "☼  111111    *☼" +
                "☼  1*****☺   *☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "LEFT");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼    ***2    *☼" +
                "☼  111111    *☼" +
                "☼  1****☺    *☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "UP");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼    ***2    *☼" +
                "☼  11111☺    *☼" +
                "☼  1****     *☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "LEFT");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼     1*2    *☼" +
                "☼  1111*1    *☼" +
                "☼  1**** ☺   *☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "LEFT");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼     1*2    *☼" +
                "☼  1111*1    *☼" +
                "☼  1****☺    *☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "LEFT");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼     1*21   *☼" +
                "☼  1111*1    *☼" +
                "☼  1***☺     *☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "UP");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼     1*21   *☼" +
                "☼  1111☺1    *☼" +
                "☼  1***      *☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "ACT,UP");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼      ‼11   *☼" +
                "☼  111☺      *☼" +
                "☼  1***      *☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "DOWN");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼      ‼11   *☼" +
                "☼  111       *☼" +
                "☼  1**☺      *☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "LEFT");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼      ‼11   *☼" +
                "☼  111       *☼" +
                "☼  1*☺       *☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "ACT,LEFT");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼      ‼11   *☼" +
                "☼  111       *☼" +
                "☼  1*☺       *☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "ACT,LEFT");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼      ‼11   *☼" +
                "☼            *☼" +
                "☼   ‼☺       *☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "UP");
    }

    // проверяем, что он может построить кратчайший путь в зону, в которой опасно
    // если только мы там собрались ставить флажок
    @Test
    public void should2() {
        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼    1********☼" +
                "☼    1********☼" +
                "☼    12*☺   **☼" +
                "☼‼   ‼      **☼" +
                "☼           **☼" +
                "☼       ‼   **☼" +
                "☼           **☼" +
                "☼           **☼" +
                "☼ ‼         **☼" +
                "☼        ‼  **☼" +
                "☼           **☼" +
                "☼           **☼" +
                "☼   ‼ ‼     **☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "LEFT");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼    1********☼" +
                "☼    1********☼" +
                "☼    12☺1   **☼" +
                "☼‼   ‼      **☼" +
                "☼           **☼" +
                "☼       ‼   **☼" +
                "☼           **☼" +
                "☼           **☼" +
                "☼ ‼         **☼" +
                "☼        ‼  **☼" +
                "☼           **☼" +
                "☼           **☼" +
                "☼   ‼ ‼     **☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "ACT,UP");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼    1********☼" +
                "☼    1*‼******☼" +
                "☼    11☺    **☼" +
                "☼‼   ‼      **☼" +
                "☼           **☼" +
                "☼       ‼   **☼" +
                "☼           **☼" +
                "☼           **☼" +
                "☼ ‼         **☼" +
                "☼        ‼  **☼" +
                "☼           **☼" +
                "☼           **☼" +
                "☼   ‼ ‼     **☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "LEFT");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼    1********☼" +
                "☼    1*‼******☼" +
                "☼    1☺1    **☼" +
                "☼‼   ‼      **☼" +
                "☼           **☼" +
                "☼       ‼   **☼" +
                "☼           **☼" +
                "☼           **☼" +
                "☼ ‼         **☼" +
                "☼        ‼  **☼" +
                "☼           **☼" +
                "☼           **☼" +
                "☼   ‼ ‼     **☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "ACT,UP");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼     ********☼" +
                "☼     ‼‼******☼" +
                "☼     ☺     **☼" +
                "☼‼   ‼      **☼" +
                "☼           **☼" +
                "☼       ‼   **☼" +
                "☼           **☼" +
                "☼           **☼" +
                "☼ ‼         **☼" +
                "☼        ‼  **☼" +
                "☼           **☼" +
                "☼           **☼" +
                "☼   ‼ ‼     **☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "UP");
    }

    // если рядом обезвреженная бомба, то это для героя все равно что там *,
    // т.е. мы не знаем точно сколько бомб вокруг
    @Test
    public void should3() {
        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼      ‼    **☼" +
                "☼           **☼" +
                "☼  ‼ ‼      **☼" +
                "☼           **☼" +
                "☼        ‼  **☼" +
                "☼   ‼    ☺ ***☼" +
                "☼       ‼*****☼" +
                "☼  ‼    ******☼" +
                "☼       ******☼" +
                "☼        *****☼" +
                "☼      ‼  ****☼" +
                "☼         ****☼" +
                "☼   ‼     ****☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "DOWN"); // пока безопасно


        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼      ‼    **☼" +
                "☼           **☼" +
                "☼  ‼ ‼      **☼" +
                "☼           **☼" +
                "☼        ‼  **☼" +
                "☼   ‼      ***☼" +
                "☼       ‼☺****☼" +
                "☼  ‼    ******☼" +
                "☼       ******☼" +
                "☼        *****☼" +
                "☼      ‼  ****☼" +
                "☼         ****☼" +
                "☼   ‼     ****☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "RIGHT"); // а вот дальше опасно идти вниз

        // будет так
//        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
//                "☼      x      ☼" +
//                "☼             ☼" +
//                "☼  x x        ☼" +
//                "☼             ☼" +
//                "☼        x    ☼" +
//                "☼   x         ☼" +
//                "☼       x11   ☼" +
//                "☼  x    1Ѡ1   ☼" +
//                "☼       111   ☼" +
//                "☼             ☼" +
//                "☼      x      ☼" +
//                "☼             ☼" +
//                "☼   x         ☼" +
//                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "STOP");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼      ‼    **☼" +
                "☼           **☼" +
                "☼  ‼ ‼      **☼" +
                "☼           **☼" +
                "☼        ‼  **☼" +
                "☼   ‼      ***☼" +
                "☼       ☺1****☼" +
                "☼  ‼    ******☼" +
                "☼       ******☼" +
                "☼        *****☼" +
                "☼      ‼  ****☼" +
                "☼         ****☼" +
                "☼   ‼     ****☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "DOWN");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼      ‼    **☼" +
                "☼           **☼" +
                "☼  ‼ ‼      **☼" +
                "☼           **☼" +
                "☼        ‼  **☼" +
                "☼   ‼      ***☼" +
                "☼       ‼1****☼" +
                "☼  ‼    ☺*****☼" +
                "☼       ******☼" +
                "☼        *****☼" +
                "☼      ‼  ****☼" +
                "☼         ****☼" +
                "☼   ‼     ****☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "DOWN");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼      ‼    **☼" +
                "☼           **☼" +
                "☼  ‼ ‼      **☼" +
                "☼           **☼" +
                "☼        ‼  **☼" +
                "☼   ‼      ***☼" +
                "☼       ‼1****☼" +
                "☼  ‼    1*****☼" +
                "☼       ☺*****☼" +
                "☼        *****☼" +
                "☼      ‼  ****☼" +
                "☼         ****☼" +
                "☼   ‼     ****☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "RIGHT");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼      ‼    **☼" +
                "☼           **☼" +
                "☼  ‼ ‼      **☼" +
                "☼           **☼" +
                "☼        ‼  **☼" +
                "☼   ‼      ***☼" +
                "☼       ‼1****☼" +
                "☼  ‼    1*****☼" +
                "☼       1☺****☼" +
                "☼        *****☼" +
                "☼      ‼  ****☼" +
                "☼         ****☼" +
                "☼   ‼     ****☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "ACT,UP");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼      ‼    **☼" +
                "☼           **☼" +
                "☼  ‼ ‼      **☼" +
                "☼           **☼" +
                "☼        ‼  **☼" +
                "☼   ‼      ***☼" +
                "☼       ‼ ****☼" +
                "☼  ‼     ‼****☼" +
                "☼        ☺****☼" +
                "☼        *****☼" +
                "☼      ‼  ****☼" +
                "☼         ****☼" +
                "☼   ‼     ****☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "DOWN");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼      ‼    **☼" +
                "☼           **☼" +
                "☼  ‼ ‼      **☼" +
                "☼           **☼" +
                "☼        ‼  **☼" +
                "☼   ‼      ***☼" +
                "☼       ‼ ****☼" +
                "☼  ‼     ‼****☼" +
                "☼         ****☼" +
                "☼        ☺****☼" +
                "☼      ‼  ****☼" +
                "☼         ****☼" +
                "☼   ‼     ****☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "RIGHT");
    }

    // когда не знаем что делать и надо сделать шаг назад
    @Test
    public void should4() {
        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼111‼*********☼" +
                "☼    ☺********☼" +
                "☼    1********☼" +
                "☼    1********☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "UP");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼111‼☺********☼" +
                "☼    1********☼" +
                "☼    1********☼" +
                "☼    1********☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "DOWN");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼111‼ ********☼" +
                "☼    ☺********☼" +
                "☼    1********☼" +
                "☼    1********☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "RIGHT");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼111‼ ********☼" +
                "☼    1☺*******☼" +
                "☼    1********☼" +
                "☼    1********☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "UP");

        asertAI("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼*************☼" +
                "☼111‼ ☺*******☼" +
                "☼    11*******☼" +
                "☼    1********☼" +
                "☼    1********☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "UP");
    }
}

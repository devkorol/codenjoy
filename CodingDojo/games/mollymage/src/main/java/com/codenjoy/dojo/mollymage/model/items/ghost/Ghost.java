package com.codenjoy.dojo.mollymage.model.items.ghost;

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


import com.codenjoy.dojo.games.mollymage.Element;
import com.codenjoy.dojo.mollymage.model.Field;
import com.codenjoy.dojo.mollymage.model.Player;
import com.codenjoy.dojo.mollymage.model.items.blast.Blast;
import com.codenjoy.dojo.services.*;
import com.codenjoy.dojo.services.field.Fieldable;
import com.codenjoy.dojo.services.printer.state.State;

import java.util.HashSet;
import java.util.Set;

import static com.codenjoy.dojo.services.printer.state.StateUtils.filterOne;

public class Ghost extends PointImpl implements State<Element, Player>, Fieldable<Field>, Tickable {

    public static final int MAX = 100;

    protected Dice dice;
    protected Field field;
    protected Direction direction;
    protected boolean stop = false;

    public Ghost(Point pt) {
        super(pt);
    }

    public void init(Field field) {
        this.field = field;
        this.dice = field.dice();
    }

    public void stop() {
        this.stop = true;
    }

    public void start() {
        stop = false;
    }

    public void setDirection(Direction direction) {
        stop = false;
        this.direction = direction;
    }

    @Override
    public Element state(Player player, Object... alsoAtPoint) {
        Blast blast = filterOne(alsoAtPoint, Blast.class);
        if (blast != null) {
            return Element.GHOST_DEAD;
        }

        return Element.GHOST;
    }

    @Override
    public void tick() {
        // неугомонные привидения в тестах только так останавливаются
        if (stop) {
            return;
        }

        Point from = this;
        if (direction == null
            || dice.next(5) == 0
            || barrier(direction.change(from)))
        {
            direction = selectNew(from);
        }

        if (direction != null) {
            move(direction.change(from));
        }
    }

    private Direction selectNew(Point from) {
        int iteration = 0;
        Point to;
        Direction direction;
        Set<Direction> all = new HashSet<>();
        do {
            if (iteration++ >= MAX || all.size() == Direction.getValues().size()) {
                return null;
            }

            int n = dice.next(4);
            direction = Direction.valueOf(n);
            all.add(direction);
            to = direction.change(from);
        } while (barrier(to));

        return direction;
    }

    private boolean barrier(Point to) {
        return field.isGhost(to)
                || field.isWall(to)
                || field.isBox(to)
                || to.isOutOf(field.size());
    }
}
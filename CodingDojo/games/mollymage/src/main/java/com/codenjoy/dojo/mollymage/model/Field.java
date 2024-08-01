package com.codenjoy.dojo.mollymage.model;

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


import com.codenjoy.dojo.mollymage.model.items.Potion;
import com.codenjoy.dojo.mollymage.model.items.Wall;
import com.codenjoy.dojo.mollymage.model.items.blast.Blast;
import com.codenjoy.dojo.mollymage.model.items.blast.Poison;
import com.codenjoy.dojo.mollymage.model.items.box.TreasureBox;
import com.codenjoy.dojo.mollymage.model.items.ghost.Ghost;
import com.codenjoy.dojo.mollymage.model.items.ghost.GhostHunter;
import com.codenjoy.dojo.mollymage.model.items.perks.Perk;
import com.codenjoy.dojo.mollymage.model.items.perks.PerkOnBoard;
import com.codenjoy.dojo.mollymage.services.GameSettings;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.field.Accessor;
import com.codenjoy.dojo.services.round.RoundGameField;

import java.util.List;
import java.util.Optional;

public interface Field extends RoundGameField<Player, Hero> {  // TODO применить тут ISP (все ли методы должны быть паблик?)

    boolean FOR_HERO = true;

    boolean isBarrier(Point pt, boolean isForHero);

    boolean isPerk(Point pt);

    boolean isPotion(Point pt);

    boolean isWall(Point pt);

    boolean isBox(Point pt);

    boolean isBlast(Point pt);

    boolean isGhost(Point pt);

    boolean isHunter(Point pt);

    boolean isActiveAliveHero(Point pt);

    boolean isFree(Point pt);

    Optional<Point> freeRandom(Player player);

    int size();

    void drop(Potion potion);

    void remove(Potion potion);

    void remove(Point anyObject);

    List<PerkOnBoard> pickPerk(Point pt);

    void pickPerkBy(Player player, Perk perk);

    void pickPerkBy(int teamId, Perk perk);

    Dice dice();

    void explodeAllPotions(Hero hero);

    GameSettings settings();

    List<Potion> heroPotions(Hero hero);

    Accessor<Poison> poisons();

    Accessor<Wall> walls();

    Accessor<Potion> potions();

    Accessor<Blast> blasts();

    Accessor<GhostHunter> hunters();

    Accessor<PerkOnBoard> perks();

    Accessor<Ghost> ghosts();

    Accessor<TreasureBox> boxes();

    Accessor<Hero> heroes();
}
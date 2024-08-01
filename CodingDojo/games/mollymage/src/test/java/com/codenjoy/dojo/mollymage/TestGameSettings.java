package com.codenjoy.dojo.mollymage;

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


import com.codenjoy.dojo.mollymage.services.GameSettings;
import org.apache.commons.lang3.StringUtils;

import static com.codenjoy.dojo.mollymage.services.GameSettings.Keys.*;
import static com.codenjoy.dojo.services.round.RoundSettings.Keys.ROUNDS_ENABLED;

public class TestGameSettings extends GameSettings {

    public TestGameSettings() {
        update(this);
    }

    /**
     * Here you can override the settings for all tests.
     */
    public static GameSettings update(GameSettings settings) {
        return settings
                .bool(ROUNDS_ENABLED, false)

                .integer(WIN_ROUND_SCORE, 1000)
                .integer(HERO_DIED_PENALTY, -50)

                .integer(KILL_OTHER_HERO_SCORE, 200)
                .integer(KILL_ENEMY_HERO_SCORE, 500)
                .integer(KILL_GHOST_SCORE, 100)
                .integer(OPEN_TREASURE_BOX_SCORE, 10)
                .integer(CATCH_PERK_SCORE, 5)

                .integer(GHOSTS_COUNT, 0)
                .integer(POTION_POWER, 1)
                .integer(POTIONS_COUNT, 1)

                .integer(TREASURE_BOX_COUNT, 0)

                .bool(BIG_BADABOOM, false)

                .bool(PERK_WHOLE_TEAM_GET, false)
                .string(DEFAULT_PERKS, StringUtils.EMPTY);
    }
}

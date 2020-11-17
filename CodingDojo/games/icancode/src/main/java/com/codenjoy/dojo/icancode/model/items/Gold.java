package com.codenjoy.dojo.icancode.model.items;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
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


import com.codenjoy.dojo.icancode.model.*;
import com.codenjoy.dojo.icancode.model.perks.DeathRayPerk;
import com.codenjoy.dojo.icancode.model.perks.UnstoppableLaserPerk;

import static com.codenjoy.dojo.services.StateUtils.filterOne;

public class Gold extends BaseItem {

    private boolean hidden;

    public Gold(Elements el) {
        super(el);
        hidden = false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public Elements state(Player player, Object... alsoAtPoint) {
        if (filterOne(alsoAtPoint, DeathRayPerk.class) != null) {
            return Elements.DEATH_RAY_PERK;
        }
        if (filterOne(alsoAtPoint, UnstoppableLaserPerk.class) != null) {
            return Elements.UNSTOPPABLE_LASER_PERK;
        }
        if (hidden) {
            return Elements.FLOOR;
        }
        return super.state(player, alsoAtPoint);
    }

    @Override
    public void action(Item item) {
        if (hidden) return; // TODO test me

        HeroItem heroItem = getIf(item, HeroItem.class);
        if (heroItem == null) {
            return;
        }

        Hero hero = heroItem.getHero();
        if (!hero.isFlying()) {
            hero.pickUpGold();
            hidden = true;
        }
    }

    public void reset() {
        hidden = false;
    }

    public boolean getHidden() {
        return hidden;
    }
}

package com.codenjoy.dojo.mollymage.model.items.perks;

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
import com.codenjoy.dojo.services.Tickable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeroPerks implements Tickable {

    private Map<String, Perk> perks = new HashMap<>();

    public void add(Perk perk) {
        // TODO do not use map.containsKey just check that map.get() != null
        if (perks.containsKey(perk.getName())) {
            Perk newPerk = perk.combine(perks.get(perk.getName()));
            perks.put(perk.getName(), newPerk);
        } else {
            perks.put(perk.getName(), perk);
        }
    }

    public Perk getPerk(Element element) {
        return perks.get(element.name());
    }

    public List<Perk> getPerksList() {
        return new ArrayList<>(perks.values());
    }

    @Override
    public void tick() {
        Map<String, Perk> active = new HashMap<>();
        perks.forEach((name, perk) -> {
            perk.tick();
            if (perk.isActive()) {
                active.put(name, perk);
            }
        });

        perks = active;
    }
}
/*
 * This file is part of Odin.
 *
 * Copyright (c) 2011 CraftFire <http://www.craftfire.com/>
 * Odin is licensed under the GNU Lesser General Public License.
 *
 * Odin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Odin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.craftfire.odin.layer.spout;

import java.util.logging.Logger;

import org.spout.api.plugin.CommonPlugin;
import org.spout.api.util.Named;

import com.craftfire.odin.layer.spout.listeners.OdinPlayerListener;

public class Odin extends CommonPlugin implements Named {
    private static final Logger logger = Logger.getLogger("Minecraft.Odin");
    private static Odin instance;

    public static Odin getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        logger.info("Odin " + getDescription().getVersion() + " enabled.");
        getDataFolder().mkdirs();
        registerEvents();
    }

    public void onDisable() {
        getEngine().getScheduler().cancelTasks(this);
    }

    protected void registerEvents() {
        getEngine().getEventManager().registerEvents(new OdinPlayerListener(this), this);
    }
}

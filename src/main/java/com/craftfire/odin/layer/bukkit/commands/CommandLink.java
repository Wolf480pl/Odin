/*
 * This file is part of Odin.
 *
 * Copyright (c) 2011-2012, CraftFire <http://www.craftfire.com/>
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
package com.craftfire.odin.layer.bukkit.commands;

import com.craftfire.odin.layer.bukkit.managers.OdinPlayer;
import com.craftfire.odin.layer.bukkit.util.event.Event;
import com.craftfire.odin.managers.OdinManager;
import com.craftfire.odin.managers.OdinPermission;
import com.craftfire.odin.managers.OdinUser;

public class CommandLink extends OdinBukkitCommand {

    public CommandLink() {
        super("login", OdinPermission.command_login, "TODO");
    }

    @Override
    public void execute(OdinPlayer player, String[] args) {
        if (pre(player, args)) {
            if (!player.isRegistered()) {
                //TODO: Link the player.
            } else {
                player.sendMessage("login.registered");
            }
            OdinManager.getLogger().debug(player.getName() + " link ******** ********");
        }
    }

    private boolean pre(OdinPlayer player, String[] args) {
        if (OdinManager.getConfig().getBoolean("join.restrict") || !OdinManager.getConfig().getBoolean("link.enabled")) {
            return false;
        } else if (args.length != 2) {
            player.sendMessage("login.usage");
            return false;
        } else if (!player.getName().equals(args[0])) {
            player.sendMessage("login.invaliduser");
            return false;
        }
        return true;
    }
}
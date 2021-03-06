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
package com.craftfire.odin.managers.permissions;

import com.craftfire.odin.managers.OdinManager;

public enum OdinPermission {
    command_register (PermissionType.USER, "register"),
    command_delete (PermissionType.USER, "delete"),
    command_login (PermissionType.USER, "login"),
    command_logout (PermissionType.USER, "logout"),
    command_link (PermissionType.USER, "link"),
    command_unlink (PermissionType.USER, "unlink"),
    command_password (PermissionType.USER, "password"),
    command_email (PermissionType.USER, "email"),
    command_admin_users (PermissionType.ADMIN, "users"),
    command_admin_register (PermissionType.ADMIN, "register"),
    command_admin_delete (PermissionType.ADMIN, "delete"),
    command_admin_login (PermissionType.ADMIN, "login"),
    command_admin_logout (PermissionType.ADMIN, "logout"),
    command_admin_password (PermissionType.ADMIN, "password"),
    command_admin_email (PermissionType.ADMIN, "email"),
    command_admin_activate (PermissionType.ADMIN, "activate"),
    command_admin_deactivate (PermissionType.ADMIN, "deactivate"),
    command_admin_reload (PermissionType.ADMIN, "reload"),
    guest_commands (PermissionType.GUEST, "commands"),
    guest_chat (PermissionType.GUEST, "chat"),
    guest_building (PermissionType.GUEST, "building"),
    guest_destruction (PermissionType.GUEST, "destruction"),
    guest_movement (PermissionType.GUEST, "movement"),
    guest_interactions (PermissionType.GUEST, "interactions"),
    guest_inventory (PermissionType.GUEST, "inventory"),
    guest_drop (PermissionType.GUEST, "drop"),
    guest_pickup (PermissionType.GUEST, "pickup"),
    guest_health (PermissionType.GUEST, "health"),
    guest_pvp (PermissionType.GUEST, "pvp"),
    guest_mobtargeting (PermissionType.GUEST, "mobtargeting"),
    guest_mobdamage (PermissionType.GUEST, "mobdamage");

    private PermissionType type;
    private String permission;
    OdinPermission(PermissionType type, String permission) {
        this.type = type;
        this.permission = permission;
    }

    public String getNode() {
        switch (getType()) {
            case USER:
                return getUserNode() + this.permission;
            case ADMIN:
                return getAdminNode() + this.permission;
            default:
                return getGuestNode() + this.permission;
        }
    }

    public boolean isGuestNode() {
        return getType().equals(PermissionType.GUEST);
    }

    public boolean isUserNode() {
        return getType().equals(PermissionType.USER);
    }

    public boolean isAdminNode() {
        return getType().equals(PermissionType.ADMIN);
    }

    public PermissionType getType() {
        return this.type;
    }

    public String getPermission() {
        return this.permission;
    }

    public static String getGuestNode() {
        return OdinManager.getPluginName() + ".guest.";
    }

    public static String getUserNode() {
        return OdinManager.getPluginName() + ".command.user.";
    }

    public static String getAdminNode() {
        return OdinManager.getPluginName() + ".command.admin.";
    }

    public static String getNode(PermissionType type) {
        if (type.equals(PermissionType.GUEST)) {
            return OdinManager.getPluginName() + ".guest.";
        } else {
            return OdinManager.getPluginName() + ".command." + type.name();
        }
    }
}

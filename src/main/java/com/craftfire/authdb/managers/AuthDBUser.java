/*
 * This file is part of AuthDB <http://www.authdb.com/>.
 *
 * AuthDB is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AuthDB is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.craftfire.authdb.managers;

import com.craftfire.authapi.classes.ScriptUser;
import com.craftfire.authdb.managers.configuration.ConfigurationNode;

public class AuthDBUser {
    protected String username;
    private ScriptUser user = null;
    private Status status;
    private String password, ip;

    /**
     * Default constructor for the object.
     *
     * @param username name of the player.
     */
    public AuthDBUser(final String username) {
        this.username = username;
        load();
    }
    
    public void save() {
        AuthDBManager.userStorage.put(this.username, this);
    }

    public void load() {
        if (AuthDBManager.userStorage.containsKey(this.username)) {
            /* TODO */
            AuthDBUser temp = AuthDBManager.userStorage.get(this.username);
            this.username = temp.getUsername();
            this.user = temp.getUser();
            this.status = temp.getStatus();
            this.ip = temp.getIP();
        }
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public ScriptUser getUser() {
        return this.user;
    }
    
    public void setUser() {
        this.user = AuthDBManager.authAPI.getUser(this.username);
    }

    public boolean isLinked() {
        /* TODO */
        return false;
    }

    public String getLinkedName() {
        /* TODO */
        return null;
    }
    
    public void setLinkedName() {
        /* TODO */
    }

    public boolean isAuthenticated() {
        return AuthDBManager.userAuthenticated.contains(this.username);
    }

    public void setAuthenticated(boolean authenticated) {
        AuthDBManager.userAuthenticated.add(this.username);
    }

    public boolean logout() {
        if (AuthDBManager.userAuthenticated.contains(this.username)) {
            AuthDBManager.userAuthenticated.remove(this.username);
            return true;
        }
        return false;
    }

    public boolean login(String password) {
        if (! AuthDBManager.userAuthenticated.contains(this.username)) {
            if (AuthDBManager.authAPI.authenticate(this.username, password)) {
                AuthDBManager.userAuthenticated.add(this.username);
                return true;
            }
        }
        return false;
    }

    public String getIP() {
        /* TODO */
        return null;
    }

    public boolean hasSession() {
        return AuthDBManager.userSessions.contains(this.username);
    }

    public boolean isRegistered() {
        if (this.status != null && this.status.equals(Status.Registered)) {
            return true;
        } else {
            if (this.status.equals(Status.Authenticated)) {
                return true;
            } else if (AuthDBManager.authAPI.getScript().isRegistered(this.username)) {
                this.status = Status.Registered;
                return true;
            } else {
                this.status = Status.Guest;
            }
        }
        return false;
    }

    public boolean isGuest() {
        if (this.status != null) {
            if (this.status.equals(Status.Guest)) {
                return true;
            }
            return false;
        } else {
            if (! AuthDBManager.authAPI.getScript().isRegistered(this.username)) {
                this.status = Status.Guest;
                return true;
            } else {
                this.status = Status.Registered;
            }
        }
        return false;
    }

    public Status getStatus() {
        return this.status;
    }

    public enum Status {
        Guest,
        Registered,
        Authenticated
    }

    public boolean hasMinLength() {
        return this.username.length() < AuthDBManager.cfgMngr.getInteger(ConfigurationNode.username_minimum);
    }

    public boolean hasMaxLength() {
        return this.username.length() > AuthDBManager.cfgMngr.getInteger(ConfigurationNode.username_maximum);
    }
    
    public long getJoinTime() {
        if (AuthDBManager.playerJoin.containsKey(this.username)) {
            return AuthDBManager.playerJoin.get(this.username);
        }
        return 0;
    }
    
    public void setJoinTime() {
        AuthDBManager.playerJoin.put(this.username, System.currentTimeMillis());
    }

    public void setJoinTime(long time) {
        AuthDBManager.playerJoin.put(this.username, time);
    }
    
    public int getPasswordAttempts() {
        if (AuthDBManager.userPasswordAttempts.containsKey(this.username)) {
            return AuthDBManager.userPasswordAttempts.get(this.username);
        }
        return 0;
    }

    public void setPasswordAttempts(int attempts) {
        AuthDBManager.userPasswordAttempts.put(this.username, attempts);
    }

    public void clearPasswordAttempts() {
        AuthDBManager.userPasswordAttempts.put(this.username, 0);
    }

    public void increasePasswordAttempts() {
        if (AuthDBManager.userPasswordAttempts.containsKey(this.username)) {
            AuthDBManager.userPasswordAttempts.put(this.username,
                                                   AuthDBManager.userPasswordAttempts.get(this.username) + 1);
        } else {
            AuthDBManager.userPasswordAttempts.put(this.username, 1);
        }
    }
}
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
package com.craftfire.odin.managers;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.craftfire.commons.util.LoggingManager;

public class LoggingHandler extends LoggingManager {
    public LoggingHandler(String logger, String prefix) {
        super(logger, prefix);
    }

    @Override
    public void stackTrace(final Throwable e) {
        List<String> list = new ArrayList<String>();
        list.add("Odin version: " + OdinManager.getPluginVersion());
        if (OdinManager.getBifrost() != null) {
            list.add("MySQL keep alive: " + OdinManager.getScript().getDataManager().isKeepAlive());
            list.add("MySQL connection: " + OdinManager.getScript().getDataManager().isConnected());
            list.add("MySQL last query: " + OdinManager.getScript().getDataManager().getLastQuery());
        }
        if (OdinManager.getConfig().isInitialized() && OdinManager.getConfig().getBoolean("customdb.enabled")) {
            list.add("Script: Custom");
            list.add("Custom table: " + OdinManager.getConfig().getString("customdb.table"));
            if (OdinManager.getConfig().getBoolean("customdb.emailrequired")) {
                list.add("Custom email field: " + OdinManager.getConfig().getString("customdb.emailfield"));
            }
            list.add("Custom password field: " + OdinManager.getConfig().getString("customdb.passfield"));
            list.add("Custom username field: " + OdinManager.getConfig().getString("customdb.userfield"));
            list.add("Custom encryption: " + OdinManager.getConfig().getString("customdb.encryption"));
            list.add("Custom table schema:");
            ResultSet rs = null;
            try {
                rs = OdinManager.getScript().getDataManager().getResultSet(
                        "SELECT * FROM `" + OdinManager.getConfig().getString("customdb.table") + "` LIMIT 1");
                ResultSetMetaData metaData = rs.getMetaData();
                int rowCount = metaData.getColumnCount();
                list.add("Table Name : " + metaData.getTableName(2));
                list.add("Column\tType(size)");
                for (int i = 0; i < rowCount; i++) {
                    list.add(
                            metaData.getColumnName(i + 1) + "\t" +
                                    metaData.getColumnTypeName(i + 1) + "(" +
                                    metaData.getColumnDisplaySize(i + 1) + ")");
                }
            } catch (SQLException a) {
                error("Failed while getting MySQL table schema.");
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e1) {
                        error("Failed while trying to close ResultSet.");
                    }
                }
            }
        } else {
            if (OdinManager.getBifrost() != null && OdinManager.getScript() != null) {
                list.add("Script chosen: " + OdinManager.getScript().getScriptName());
                list.add("Script version: " + OdinManager.getScript().getVersion());
                list.add("Table prefix: " + OdinManager.getConfig().getString("script.tableprefix"));
            } else if (OdinManager.getConfig().isInitialized()) {
                list.add("Odin will not work because you've set the wrong script name in basic.yml, " +
                        "please correct this node (script.name).");
                list.add("Script in config: " + OdinManager.getConfig().getString("script.name"));
                list.add("Script version in config: " + OdinManager.getConfig().getString("script.version"));
                list.add("Table prefix in config: " + OdinManager.getConfig().getString("script.tableprefix"));
            }
        }
        stackTrace(e, list);
    }
}

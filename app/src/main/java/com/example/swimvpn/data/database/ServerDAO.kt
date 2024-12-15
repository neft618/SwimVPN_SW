package com.example.swimvpn.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.swimvpn.data.model.Server

class ProxyDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "proxy.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE proxy_servers (
                id TEXT PRIMARY KEY,
                country TEXT NOT NULL,
                ip TEXT NOT NULL,
                port INTEGER NOT NULL,
                username TEXT NOT NULL,
                password TEXT NOT NULL
            )
        """
        db.execSQL(createTable)

        // несколько прокси по умолчанию
        db.execSQL("""
            INSERT INTO proxy_servers (id, country, ip, port, username, password) VALUES
            ('1', 'USA', '198.23.239.134', 6540, 'lspfzloa', '0ljlthwd4riq'),
            ('2', 'Germany', '64.137.42.112', 5157, 'lspfzloa', '0ljlthwd4riq'),
            ('3', 'Portugal', '161.123.152.115', 6360, 'lspfzloa', '0ljlthwd4riq');
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS proxy_servers")
        onCreate(db)
    }

    fun getAllServers(): List<Server> {
        val servers = mutableListOf<Server>()
        val cursor = readableDatabase.rawQuery("SELECT * FROM proxy_servers", null)
        cursor.use {
            while (it.moveToNext()) {
                servers.add(
                    Server(
                        id = it.getString(it.getColumnIndexOrThrow("id")),
                        country = it.getString(it.getColumnIndexOrThrow("country")),
                        ip = it.getString(it.getColumnIndexOrThrow("ip")),
                        port = it.getInt(it.getColumnIndexOrThrow("port")),
                        username = it.getString(it.getColumnIndexOrThrow("username")),
                        password = it.getString(it.getColumnIndexOrThrow("password"))
                    )
                )
            }
        }
        return servers
    }
}

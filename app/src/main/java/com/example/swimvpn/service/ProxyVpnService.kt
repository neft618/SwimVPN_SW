package com.example.swimvpn.service

import android.app.Service
import android.content.Intent
import android.net.VpnService
import android.os.IBinder
import android.os.ParcelFileDescriptor
import java.net.InetSocketAddress
import java.net.Socket
import java.nio.ByteBuffer

class ProxyVpnService : VpnService() {

    private var vpnInterface: ParcelFileDescriptor? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val ip = intent?.getStringExtra("ip")
        val port = intent?.getIntExtra("port", 0)
        val password = intent?.getStringExtra("password")

        if (ip != null && port != null && password != null) {
            startVpn(ip, port, password)
        }

        return START_STICKY
    }

    private fun startVpn(ip: String, port: Int, password: String) {
        val builder = Builder()
        builder.addAddress("10.0.0.2", 24) // Виртуальный VPN-адрес
        builder.addRoute("0.0.0.0", 0) // Весь трафик через VPN
        builder.setSession("Secure Waters Proxy")
        builder.setBlocking(true)

        vpnInterface = builder.establish()

        // Настройка сокета для прокси
        Thread {
            try {
                val socket = Socket()
                socket.connect(InetSocketAddress(ip, port))
                val output = socket.getOutputStream()
                val input = socket.getInputStream()

                // Отправляем пароль для подключения
                output.write("CONNECT:$password\n".toByteArray())
                output.flush()

                // Прокидываем трафик
                val vpnInput = ParcelFileDescriptor.AutoCloseInputStream(vpnInterface)
                val vpnOutput = ParcelFileDescriptor.AutoCloseOutputStream(vpnInterface)

                val buffer = ByteBuffer.allocate(32767)
                while (true) {
                    val len = vpnInput.read(buffer.array())
                    if (len > 0) {
                        output.write(buffer.array(), 0, len)
                        output.flush()
                    }
                    val respLen = input.read(buffer.array())
                    if (respLen > 0) {
                        vpnOutput.write(buffer.array(), 0, respLen)
                        vpnOutput.flush()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    override fun onDestroy() {
        vpnInterface?.close()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
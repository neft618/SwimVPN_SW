//package com.example.swimvpn.network
//
//
//import android.app.PendingIntent
//import android.content.Intent
//import android.net.VpnService
//import android.os.ParcelFileDescriptor
//import com.example.swimvpn.MainActivity
//import com.google.androidbrowserhelper.trusted.NotificationUtils
//import java.io.FileInputStream
//import java.io.FileOutputStream
//import java.net.InetSocketAddress
//import java.net.Socket
//import java.util.concurrent.ExecutorService
//import java.util.concurrent.Executors
//
//class MyVpnService : VpnService() {
//
//    private var vpnInterface: ParcelFileDescriptor? = null
//    private lateinit var executorService: ExecutorService
//
//    override fun onCreate() {
//        super.onCreate()
//        executorService = Executors.newSingleThreadExecutor()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        stopVpn()
//        executorService.shutdown()
//    }
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        startVpn()
//        return START_STICKY
//    }
//
//    private fun startVpn() {
//        val builder = Builder()
//
//        // Настройка VPN-интерфейса
//        builder.addAddress("10.0.0.2", 24) // Локальный IP-адрес
//        builder.addRoute("0.0.0.0", 0)    // Направление всего трафика через VPN
//        builder.addDnsServer("8.8.8.8")   // Пример использования DNS Google
//
//        // Установить интерфейс VPN
//        vpnInterface = builder.establish()
//
//        // Создать фоновую задачу для обработки трафика
//        vpnInterface?.let { interfaceDescriptor ->
//            executorService.execute {
//                handleTraffic(interfaceDescriptor)
//            }
//        }
//
//        // Показать уведомление о VPN
//        val notificationIntent = PendingIntent.getActivity(
//            this, 0, Intent(this, MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE
//        )
//        val notification = NotificationUtils.createVpnNotification(this, notificationIntent)
//        startForeground(1, notification)
//    }
//
//    private fun stopVpn() {
//        vpnInterface?.close()
//        vpnInterface = null
//        stopForeground(true)
//    }
//
//    private fun handleTraffic(interfaceDescriptor: ParcelFileDescriptor) {
//        val input = FileInputStream(interfaceDescriptor.fileDescriptor)
//        val output = FileOutputStream(interfaceDescriptor.fileDescriptor)
//
//        val socket = Socket()
//        socket.connect(InetSocketAddress("vpn.server.address", 1194)) // Замените на адрес вашего сервера
//
//        val vpnInput = socket.getInputStream()
//        val vpnOutput = socket.getOutputStream()
//
//        val buffer = ByteArray(32767)
//
//        while (true) {
//            try {
//                // Чтение данных из интерфейса и отправка на сервер
//                val read = input.read(buffer)
//                if (read > 0) {
//                    vpnOutput.write(buffer, 0, read)
//                }
//
//                // Чтение данных с сервера и запись в интерфейс
//                val vpnRead = vpnInput.read(buffer)
//                if (vpnRead > 0) {
//                    output.write(buffer, 0, vpnRead)
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//                break
//            }
//        }
//
//        socket.close()
//    }
//}

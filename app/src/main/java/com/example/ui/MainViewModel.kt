package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.InetSocketAddress
import java.net.Socket

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = ServerRepository(database.serverDao())

    // Language state: true = Arabic, false = English
    private val _isArabic = MutableStateFlow(true)
    val isArabic: StateFlow<Boolean> = _isArabic.asStateFlow()

    // Dark Mode state: true = Dark, false = Light
    private val _isDarkMode = MutableStateFlow(true)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    // Server IP & Port settings (with default values matching prompt)
    private val _serverIp = MutableStateFlow("SEEN")
    val serverIp: StateFlow<String> = _serverIp.asStateFlow()

    private val _serverPort = MutableStateFlow("19132")
    val serverPort: StateFlow<String> = _serverPort.asStateFlow()

    private val _serverVersion = MutableStateFlow("1.21.33")
    val serverVersion: StateFlow<String> = _serverVersion.asStateFlow()

    // Dynamic Server Status states
    private val _isPinging = MutableStateFlow(false)
    val isPinging: StateFlow<Boolean> = _isPinging.asStateFlow()

    private val _isServerOnline = MutableStateFlow(false)
    val isServerOnline: StateFlow<Boolean> = _isServerOnline.asStateFlow()

    private val _playersCount = MutableStateFlow(0)
    val playersCount: StateFlow<Int> = _playersCount.asStateFlow()

    private val _maxPlayers = MutableStateFlow(100)
    val maxPlayers: StateFlow<Int> = _maxPlayers.asStateFlow()

    // Cache Flows
    val newsList: StateFlow<List<NewsEntity>> = repository.allNews
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val rulesList: StateFlow<List<RuleEntity>> = repository.allRules
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val faqsList: StateFlow<List<FaqEntity>> = repository.allFaqs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val staffList: StateFlow<List<StaffEntity>> = repository.allStaff
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val eventsList: StateFlow<List<EventEntity>> = repository.allEvents
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val galleryList: StateFlow<List<GalleryItemEntity>> = repository.allGallery
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            // Load pre-populated data if Room database is empty
            repository.prePopulateIfEmpty()
            // Do initial ping simulation
            refreshServerStatus()
        }
    }

    fun toggleLanguage() {
        _isArabic.value = !_isArabic.value
    }

    fun toggleDarkMode() {
        _isDarkMode.value = !_isDarkMode.value
    }

    fun updateServerSettings(ip: String, port: String, version: String) {
        _serverIp.value = ip
        _serverPort.value = port
        _serverVersion.value = version
        viewModelScope.launch {
            refreshServerStatus()
        }
    }

    suspend fun refreshServerStatus() {
        _isPinging.value = true
        // Simulate a realistic Minecraft network query ping time
        delay(1200)
        
        val ip = _serverIp.value
        val portStr = _serverPort.value
        
        if (ip.lowercase() == "seen" || ip.isBlank()) {
            // "SEEN" is a simulated offline server as per prompt:
            // 📡 Server Status: 🔴 OFFLINE, 👥 Players Online: 0 / 100
            _isServerOnline.value = false
            _playersCount.value = 0
        } else {
            // Try actual socket connect in IO thread to check if server is reachable!
            val online = withContext(Dispatchers.IO) {
                try {
                    val socket = Socket()
                    val port = portStr.toIntOrNull() ?: 19132
                    socket.connect(InetSocketAddress(ip, port), 2500)
                    socket.close()
                    true
                } catch (e: Exception) {
                    false
                }
            }
            _isServerOnline.value = online
            if (online) {
                // If actually reachable, simulate players count elegantly (e.g. 42 / 100) or dynamic
                _playersCount.value = (15..88).random()
            } else {
                _playersCount.value = 0
            }
        }
        _isPinging.value = false
    }

    // Helper functions for Admin or Mock News addition in runtime
    fun addNewsItem(titleAr: String, titleEn: String, contentAr: String, contentEn: String, categoryAr: String, categoryEn: String) {
        viewModelScope.launch {
            val item = NewsEntity(
                titleAr = titleAr,
                titleEn = titleEn,
                contentAr = contentAr,
                contentEn = contentEn,
                date = "2026-07-04",
                categoryAr = categoryAr,
                categoryEn = categoryEn
            )
            repository.insertNews(listOf(item))
        }
    }

    fun addRuleItem(ruleAr: String, ruleEn: String, categoryAr: String, categoryEn: String, index: Int) {
        viewModelScope.launch {
            val item = RuleEntity(
                ruleAr = ruleAr,
                ruleEn = ruleEn,
                categoryAr = categoryAr,
                categoryEn = categoryEn,
                index = index
            )
            repository.insertRules(listOf(item))
        }
    }
}

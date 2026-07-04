package com.example.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Screen enum definitions
enum class ServerScreen {
    HOME,
    NEWS,
    RULES,
    LIVE_MAP,
    RESOURCE_PACK,
    MODS_ADDONS,
    FAQ,
    STAFF_TEAM,
    GALLERY,
    EVENTS,
    GIVEAWAYS,
    SETTINGS,
    ABOUT
}

// Translations structure
object Translator {
    private val ar = mapOf(
        "app_title" to "DZ CRAFT S1",
        "menu_title" to "قائمة السيرفر",
        "home" to "الرئيسية",
        "news" to "أخبار السيرفر",
        "rules" to "قوانين السيرفر",
        "live_map" to "الخريطة المباشرة",
        "resource_pack" to "حزمة الموارد",
        "mods_addons" to "المودات والملحقات",
        "faq" to "الأسئلة الشائعة",
        "staff_team" to "طاقم الإدارة",
        "gallery" to "معرض الصور",
        "events" to "الفعاليات",
        "giveaways" to "الهدايا والجيف اواي",
        "settings" to "إعدادات التطبيق",
        "about" to "حول DZ CRAFT S1",
        "server_status" to "حالة السيرفر",
        "online" to "متصل",
        "offline" to "غير متصل",
        "players_online" to "اللاعبين المتصلين",
        "copy_ip" to "نسخ الآيبي",
        "copy_port" to "نسخ المنفذ",
        "website" to "الموقع الإلكتروني",
        "discord" to "ديسكورد",
        "youtube" to "يوتيوب",
        "instagram" to "انستغرام",
        "tiktok" to "تيك توك",
        "dark_mode" to "الوضع المظلم",
        "language" to "اللغة",
        "ip" to "الآيبي (IP)",
        "port" to "المنفذ (Port)",
        "version" to "الإصدار",
        "news_updates" to "آخر التحديثات والإعلانات الخاصة بالسيرفر",
        "rules_desc" to "يرجى الالتزام بالقوانين التالية لتجنب الباند أو العقوبات",
        "news_empty" to "لا توجد أخبار حالياً.",
        "rules_empty" to "لا توجد قوانين حالياً.",
        "faqs_empty" to "لا توجد أسئلة شائعة حالياً.",
        "staff_empty" to "لا يوجد أعضاء في طاقم الإدارة حالياً.",
        "events_empty" to "لا توجد فعاليات حالياً.",
        "gallery_empty" to "لا توجد صور في المعرض حالياً.",
        "search" to "بحث...",
        "save" to "حفظ التغييرات",
        "ip_copied" to "تم نسخ الآيبي بنجاح!",
        "port_copied" to "تم نسخ البورت بنجاح!",
        "loading_status" to "جاري فحص حالة السيرفر...",
        "about_content" to "سيرفر DZ CRAFT S1 هو خادم ماينكرافت بدروك (Minecraft Bedrock) جزائري وعربي مخصص لتقديم تجربة لعب عادلة ومثيرة لجميع عشاق المغامرة والبناء. نحن نركز على تحسين أداء اللعب لتقليل اللاغ ودعم الإضافات الرائعة (Addons) التي تضفي جواً خرافياً.",
        "add_news" to "إضافة خبر جديد",
        "add_rule" to "إضافة قانون جديد",
        "reward" to "الجائزة:",
        "time" to "الوقت:",
        "install_pack" to "تنزيل وتثبيت حزمة الموارد",
        "install_mod" to "تنزيل المود",
        "downloading" to "جاري التنزيل...",
        "installed" to "تم التثبيت والدمج بنجاح!",
        "map_markers" to "مواقع هامة في الخريطة",
        "spawn_lobby" to "السبون الرئيسي (Lobby)",
        "pvp_arena" to "ساحة القتال (PvP Arena)",
        "shop_area" to "منطقة المتجر العام (Market)",
        "live_players" to "اللاعبين المتواجدين الآن في الخريطة"
    )

    private val en = mapOf(
        "app_title" to "DZ CRAFT S1",
        "menu_title" to "Server Menu",
        "home" to "Home",
        "news" to "Server News",
        "rules" to "Rules",
        "live_map" to "Live Map",
        "resource_pack" to "Resource Pack",
        "mods_addons" to "Mods & Addons",
        "faq" to "FAQ",
        "staff_team" to "Staff Team",
        "gallery" to "Gallery",
        "events" to "Events",
        "giveaways" to "Giveaways",
        "settings" to "Settings",
        "about" to "About DZ CRAFT S1",
        "server_status" to "Server Status",
        "online" to "ONLINE",
        "offline" to "OFFLINE",
        "players_online" to "Players Online",
        "copy_ip" to "Copy IP",
        "copy_port" to "Copy Port",
        "website" to "Website",
        "discord" to "Discord",
        "youtube" to "YouTube",
        "instagram" to "Instagram",
        "tiktok" to "TikTok",
        "dark_mode" to "Dark Mode",
        "language" to "Language",
        "ip" to "Server IP",
        "port" to "Port",
        "version" to "Version",
        "news_updates" to "Latest server announcements and updates",
        "rules_desc" to "Please follow the rules to avoid being banned or punished",
        "news_empty" to "No news available.",
        "rules_empty" to "No rules available.",
        "faqs_empty" to "No FAQs available.",
        "staff_empty" to "No staff members listed.",
        "events_empty" to "No events scheduled.",
        "gallery_empty" to "No images in the gallery.",
        "search" to "Search...",
        "save" to "Save Changes",
        "ip_copied" to "Server IP copied to clipboard!",
        "port_copied" to "Server Port copied to clipboard!",
        "loading_status" to "Pinging server status...",
        "about_content" to "DZ CRAFT S1 is an Algerian and Arab Minecraft Bedrock server dedicated to providing a fair, stable, and exciting survival experience for all adventure and build lovers. We focus on low-lag performance and custom epic extensions.",
        "add_news" to "Add News Item",
        "add_rule" to "Add Rule Item",
        "reward" to "Reward:",
        "time" to "Time:",
        "install_pack" to "Download & Install Resource Pack",
        "install_mod" to "Download Mod",
        "downloading" to "Downloading...",
        "installed" to "Successfully installed and synced!",
        "map_markers" to "Key Map Locations",
        "spawn_lobby" to "Main Spawn (Lobby)",
        "pvp_arena" to "Colosseum (PvP Arena)",
        "shop_area" to "Marketplace & Shop",
        "live_players" to "Active Players on Map"
    )

    fun translate(key: String, isArabic: Boolean): String {
        return if (isArabic) {
            ar[key] ?: key
        } else {
            en[key] ?: key
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DZCraftApp(viewModel: MainViewModel) {
    val isArabic by viewModel.isArabic.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val serverIp by viewModel.serverIp.collectAsState()
    val serverPort by viewModel.serverPort.collectAsState()
    val serverVersion by viewModel.serverVersion.collectAsState()
    
    val isServerOnline by viewModel.isServerOnline.collectAsState()
    val playersCount by viewModel.playersCount.collectAsState()
    val maxPlayers by viewModel.maxPlayers.collectAsState()
    val isPinging by viewModel.isPinging.collectAsState()

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    var currentScreen by remember { mutableStateOf(ServerScreen.HOME) }

    // Dynamic color scheme container
    val appColorScheme = if (isDarkMode) {
        darkColorScheme(
            primary = Color(0xFF4CAF50),      // Grass Green
            secondary = Color(0xFF81C784),    // Light Green
            tertiary = Color(0xFFFFB74D),    // Gold/Orange
            background = Color(0xFF1B1B1B),  // Dark Stone/Obsidian
            surface = Color(0xFF242424),     // Dark Grey
            onPrimary = Color.Black,
            onSecondary = Color.Black,
            onTertiary = Color.Black,
            onBackground = Color.White,
            onSurface = Color.White
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF2E7D32),      // Deep Forest Green
            secondary = Color(0xFF4CAF50),    // Vibrant Green
            tertiary = Color(0xFFE65100),    // Dark Orange
            background = Color(0xFFF1F8E9),  // Light green hue
            surface = Color(0xFFFFFFFF),     // Pure White
            onPrimary = Color.White,
            onSecondary = Color.White,
            onTertiary = Color.White,
            onBackground = Color(0xFF212121),
            onSurface = Color(0xFF212121)
        )
    }

    MaterialTheme(colorScheme = appColorScheme) {
        // Handle Layout Directions based on Language
        CompositionLocalProvider(
            androidx.compose.ui.platform.LocalLayoutDirection provides if (isArabic) {
                androidx.compose.ui.unit.LayoutDirection.Rtl
            } else {
                androidx.compose.ui.unit.LayoutDirection.Ltr
            }
        ) {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet(
                        modifier = Modifier
                            .width(300.dp)
                            .testTag("navigation_drawer_sheet"),
                        drawerContainerColor = MaterialTheme.colorScheme.surface
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Drawer Header with Banner
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                                .padding(horizontal = 16.dp)
                                .clip(RoundedCornerShape(12.dp))
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_server_banner_1783186605491),
                                contentDescription = "Drawer Banner",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                                        )
                                    )
                            )
                            Column(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = Translator.translate("app_title", isArabic),
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        shadow = Shadow(
                                            color = Color.Black,
                                            offset = Offset(2f, 2f),
                                            blurRadius = 4f
                                        )
                                    )
                                )
                                Text(
                                    text = "${Translator.translate("version", isArabic)}: $serverVersion",
                                    color = Color(0xFF81C784),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }

                        Divider(modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp))

                        // Scrollable Menu Items
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .verticalScroll(rememberScrollState())
                        ) {
                            // Section: Game / Play
                            DrawerCategoryHeader(title = if (isArabic) "السيرفر واللعب" else "Server & Play")
                            DrawerMenuItem(
                                title = Translator.translate("home", isArabic),
                                icon = Icons.Default.Home,
                                selected = currentScreen == ServerScreen.HOME,
                                onClick = { currentScreen = ServerScreen.HOME; scope.launch { drawerState.close() } }
                            )
                            DrawerMenuItem(
                                title = Translator.translate("news", isArabic),
                                icon = Icons.Default.Notifications,
                                selected = currentScreen == ServerScreen.NEWS,
                                onClick = { currentScreen = ServerScreen.NEWS; scope.launch { drawerState.close() } }
                            )
                            DrawerMenuItem(
                                title = Translator.translate("rules", isArabic),
                                icon = Icons.Default.List,
                                selected = currentScreen == ServerScreen.RULES,
                                onClick = { currentScreen = ServerScreen.RULES; scope.launch { drawerState.close() } }
                            )
                            DrawerMenuItem(
                                title = Translator.translate("live_map", isArabic),
                                icon = Icons.Default.Place,
                                selected = currentScreen == ServerScreen.LIVE_MAP,
                                onClick = { currentScreen = ServerScreen.LIVE_MAP; scope.launch { drawerState.close() } }
                            )
                            DrawerMenuItem(
                                title = Translator.translate("resource_pack", isArabic),
                                icon = Icons.Default.Build,
                                selected = currentScreen == ServerScreen.RESOURCE_PACK,
                                onClick = { currentScreen = ServerScreen.RESOURCE_PACK; scope.launch { drawerState.close() } }
                            )
                            DrawerMenuItem(
                                title = Translator.translate("mods_addons", isArabic),
                                icon = Icons.Default.ShoppingCart,
                                selected = currentScreen == ServerScreen.MODS_ADDONS,
                                onClick = { currentScreen = ServerScreen.MODS_ADDONS; scope.launch { drawerState.close() } }
                            )

                            // Section: Community
                            DrawerCategoryHeader(title = if (isArabic) "المجتمع والفعاليات" else "Community & Events")
                            DrawerMenuItem(
                                title = Translator.translate("faq", isArabic),
                                icon = Icons.Default.Info,
                                selected = currentScreen == ServerScreen.FAQ,
                                onClick = { currentScreen = ServerScreen.FAQ; scope.launch { drawerState.close() } }
                            )
                            DrawerMenuItem(
                                title = Translator.translate("staff_team", isArabic),
                                icon = Icons.Default.Person,
                                selected = currentScreen == ServerScreen.STAFF_TEAM,
                                onClick = { currentScreen = ServerScreen.STAFF_TEAM; scope.launch { drawerState.close() } }
                            )
                            DrawerMenuItem(
                                title = Translator.translate("gallery", isArabic),
                                icon = Icons.Default.Star,
                                selected = currentScreen == ServerScreen.GALLERY,
                                onClick = { currentScreen = ServerScreen.GALLERY; scope.launch { drawerState.close() } }
                            )
                            DrawerMenuItem(
                                title = Translator.translate("events", isArabic),
                                icon = Icons.Default.DateRange,
                                selected = currentScreen == ServerScreen.EVENTS,
                                onClick = { currentScreen = ServerScreen.EVENTS; scope.launch { drawerState.close() } }
                            )
                            DrawerMenuItem(
                                title = Translator.translate("giveaways", isArabic),
                                icon = Icons.Default.ThumbUp,
                                selected = currentScreen == ServerScreen.GIVEAWAYS,
                                onClick = { currentScreen = ServerScreen.GIVEAWAYS; scope.launch { drawerState.close() } }
                            )

                            // Section: App Preferences
                            DrawerCategoryHeader(title = if (isArabic) "تخصيص وإعدادات" else "App & Settings")
                            
                            // Dark Mode Toggle inside Drawer
                            NavigationDrawerItem(
                                label = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = Translator.translate("dark_mode", isArabic),
                                            modifier = Modifier.weight(1f),
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Switch(
                                            checked = isDarkMode,
                                            onCheckedChange = { viewModel.toggleDarkMode() },
                                            colors = SwitchDefaults.colors(
                                                checkedThumbColor = MaterialTheme.colorScheme.primary
                                            )
                                        )
                                    }
                                },
                                selected = false,
                                onClick = {},
                                icon = { Icon(Icons.Default.Refresh, contentDescription = "Theme Icon") },
                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                            )

                            // Language Switcher inside Drawer
                            NavigationDrawerItem(
                                label = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = if (isArabic) "اللغة: العربية" else "Language: English",
                                            modifier = Modifier.weight(1f),
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Button(
                                            onClick = { viewModel.toggleLanguage() },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                                contentColor = MaterialTheme.colorScheme.primary
                                            ),
                                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                            modifier = Modifier.height(32.dp)
                                        ) {
                                            Text(if (isArabic) "EN" else "عربي", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                },
                                selected = false,
                                onClick = {},
                                icon = { Icon(Icons.Default.Menu, contentDescription = "Language Icon") },
                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                            )

                            DrawerMenuItem(
                                title = Translator.translate("settings", isArabic),
                                icon = Icons.Default.Settings,
                                selected = currentScreen == ServerScreen.SETTINGS,
                                onClick = { currentScreen = ServerScreen.SETTINGS; scope.launch { drawerState.close() } }
                            )
                            DrawerMenuItem(
                                title = Translator.translate("about", isArabic),
                                icon = Icons.Default.Info,
                                selected = currentScreen == ServerScreen.ABOUT,
                                onClick = { currentScreen = ServerScreen.ABOUT; scope.launch { drawerState.close() } }
                            )
                        }

                        // Version stamp footer
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "DZ CRAFT Companion App v1.21.33",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            ) {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.img_server_logo_1783186592562),
                                        contentDescription = "App Top Logo",
                                        modifier = Modifier
                                            .size(34.dp)
                                            .clip(CircleShape)
                                            .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = Translator.translate(getScreenTitleKey(currentScreen), isArabic),
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            letterSpacing = 0.5.sp
                                        ),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            },
                            navigationIcon = {
                                IconButton(
                                    onClick = { scope.launch { drawerState.open() } },
                                    modifier = Modifier.testTag("menu_drawer_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "Open Drawer"
                                    )
                                }
                            },
                            actions = {
                                // Direct Quick Lang Toggle
                                IconButton(onClick = { viewModel.toggleLanguage() }) {
                                    Text(
                                        text = if (isArabic) "EN" else "AR",
                                        fontWeight = FontWeight.Black,
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.background,
                                titleContentColor = MaterialTheme.colorScheme.onBackground
                            )
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(innerPadding)
                    ) {
                        AnimatedContent(
                            targetState = currentScreen
                        ) { screen ->
                            when (screen) {
                                ServerScreen.HOME -> HomeScreen(viewModel, isArabic, context, onNavigateToNews = { currentScreen = ServerScreen.NEWS })
                                ServerScreen.NEWS -> NewsScreen(viewModel, isArabic)
                                ServerScreen.RULES -> RulesScreen(viewModel, isArabic)
                                ServerScreen.LIVE_MAP -> LiveMapScreen(viewModel, isArabic)
                                ServerScreen.RESOURCE_PACK -> ResourcePackScreen(viewModel, isArabic)
                                ServerScreen.MODS_ADDONS -> ModsAddonsScreen(viewModel, isArabic)
                                ServerScreen.FAQ -> FaqScreen(viewModel, isArabic)
                                ServerScreen.STAFF_TEAM -> StaffTeamScreen(viewModel, isArabic)
                                ServerScreen.GALLERY -> GalleryScreen(viewModel, isArabic)
                                ServerScreen.EVENTS -> EventsScreen(viewModel, isArabic)
                                ServerScreen.GIVEAWAYS -> GiveawaysScreen(viewModel, isArabic)
                                ServerScreen.SETTINGS -> SettingsScreen(viewModel, isArabic)
                                ServerScreen.ABOUT -> AboutScreen(viewModel, isArabic)
                            }
                        }
                    }
                }
            }
        }
    }
}

// Utility to fetch screen translations keys
fun getScreenTitleKey(screen: ServerScreen): String {
    return when (screen) {
        ServerScreen.HOME -> "home"
        ServerScreen.NEWS -> "news"
        ServerScreen.RULES -> "rules"
        ServerScreen.LIVE_MAP -> "live_map"
        ServerScreen.RESOURCE_PACK -> "resource_pack"
        ServerScreen.MODS_ADDONS -> "mods_addons"
        ServerScreen.FAQ -> "faq"
        ServerScreen.STAFF_TEAM -> "staff_team"
        ServerScreen.GALLERY -> "gallery"
        ServerScreen.EVENTS -> "events"
        ServerScreen.GIVEAWAYS -> "giveaways"
        ServerScreen.SETTINGS -> "settings"
        ServerScreen.ABOUT -> "about"
    }
}

// Drawer Custom Header Component
@Composable
fun DrawerCategoryHeader(title: String) {
    Text(
        text = title.uppercase(),
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
        modifier = Modifier.padding(start = 24.dp, top = 16.dp, bottom = 6.dp, end = 24.dp),
        letterSpacing = 1.sp
    )
}

// Drawer Custom Menu Item Component
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerMenuItem(
    title: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = {
            Text(
                text = title,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        },
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
            unselectedContainerColor = Color.Transparent,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedTextColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}

// ==========================================
// SCREEN 1: HOME SCREEN
// ==========================================
@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    isArabic: Boolean,
    context: Context,
    onNavigateToNews: () -> Unit
) {
    val serverIp by viewModel.serverIp.collectAsState()
    val serverPort by viewModel.serverPort.collectAsState()
    val serverVersion by viewModel.serverVersion.collectAsState()
    val isServerOnline by viewModel.isServerOnline.collectAsState()
    val playersCount by viewModel.playersCount.collectAsState()
    val maxPlayers by viewModel.maxPlayers.collectAsState()
    val isPinging by viewModel.isPinging.collectAsState()
    val newsList by viewModel.newsList.collectAsState()

    val scope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Feature 1: Hero Server Banner Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_server_banner_1783186605491),
                    contentDescription = "Server Hero Banner",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f))
                            )
                        )
                )

                // Server Logo overlay
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_server_logo_1783186592562),
                        contentDescription = "Server Emblem",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(2.dp, Color.White, RoundedCornerShape(12.dp))
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "DZ CRAFT S1",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp,
                                shadow = Shadow(
                                    color = Color.Black,
                                    offset = Offset(2f, 2f),
                                    blurRadius = 6f
                                )
                            )
                        )
                        Text(
                            text = if (isArabic) "أقوى سيرفر بدروك جزائري 🇩🇿" else "Algerian's Finest Bedrock Server 🇩🇿",
                            color = Color(0xFF81C784),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                shadow = Shadow(color = Color.Black, blurRadius = 4f)
                            )
                        )
                    }
                }
            }
        }

        // Feature 2: Server Status Monitor
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = Translator.translate("server_status", isArabic),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        
                        // Status Indicator
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            val infiniteTransition = rememberInfiniteTransition()
                            val pulseAlpha by infiniteTransition.animateFloat(
                                initialValue = 0.4f,
                                targetValue = 1f,
                                animationSpec = infiniteRepeatable(
                                    animation = tween(1000, easing = LinearEasing),
                                    repeatMode = RepeatMode.Reverse
                                )
                            )

                            if (isPinging) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    strokeWidth = 2.dp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = Translator.translate("loading_status", isArabic),
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (isServerOnline) Color(0xFF00E676) else Color(0xFFFF1744)
                                        )
                                )
                                Text(
                                    text = if (isServerOnline) Translator.translate("online", isArabic) else Translator.translate("offline", isArabic),
                                    color = if (isServerOnline) Color(0xFF00E676) else Color(0xFFFF1744),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Player Count bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = Translator.translate("players_online", isArabic),
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                        Text(
                            text = "$playersCount / $maxPlayers",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = if (maxPlayers > 0) playersCount.toFloat() / maxPlayers else 0f,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        color = if (isServerOnline) MaterialTheme.colorScheme.primary else Color.Red.copy(alpha = 0.5f),
                        trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Real Connection details (IP/Port/Version)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        ConnectionField(
                            label = Translator.translate("ip", isArabic),
                            value = serverIp,
                            icon = Icons.Default.Place,
                            modifier = Modifier.weight(1.2f),
                            onCopy = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("DZ CRAFT IP", serverIp)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, Translator.translate("ip_copied", isArabic), Toast.LENGTH_SHORT).show()
                            }
                        )

                        ConnectionField(
                            label = Translator.translate("port", isArabic),
                            value = serverPort,
                            icon = Icons.Default.Share,
                            modifier = Modifier.weight(0.8f),
                            onCopy = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("DZ CRAFT Port", serverPort)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, Translator.translate("port_copied", isArabic), Toast.LENGTH_SHORT).show()
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            scope.launch {
                                viewModel.refreshServerStatus()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh Icon")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (isArabic) "تحديث الحالة" else "Ping Status")
                    }
                }
            }
        }

        // Feature 3: Action Grid buttons
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickActionButton(
                        title = Translator.translate("copy_ip", isArabic),
                        icon = Icons.Default.Star,
                        containerColor = Color(0xFF2E7D32),
                        textColor = Color.White,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("DZ CRAFT IP", serverIp)
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, Translator.translate("ip_copied", isArabic), Toast.LENGTH_SHORT).show()
                        }
                    )
                    QuickActionButton(
                        title = Translator.translate("discord", isArabic),
                        icon = Icons.Default.Send,
                        containerColor = Color(0xFF5865F2), // Discord Blurple
                        textColor = Color.White,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            Toast.makeText(context, if (isArabic) "فتح رابط ديسكورد..." else "Opening Discord...", Toast.LENGTH_SHORT).show()
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickActionButton(
                        title = Translator.translate("website", isArabic),
                        icon = Icons.Default.Menu,
                        containerColor = Color(0xFFFF8F00), // Amber website
                        textColor = Color.White,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            Toast.makeText(context, if (isArabic) "فتح موقع السيرفر..." else "Opening Website...", Toast.LENGTH_SHORT).show()
                        }
                    )
                    QuickActionButton(
                        title = Translator.translate("news", isArabic),
                        icon = Icons.Default.Notifications,
                        containerColor = MaterialTheme.colorScheme.secondary,
                        textColor = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToNews
                    )
                }
            }
        }

        // Feature 4: Announcement / Promo banner
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Alert",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = if (isArabic) "انضم لبطولات DZ CRAFT S1!" else "Join the DZ CRAFT S1 Guilds!",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = if (isArabic) "اربح جوائز أسطورية كل جمعة في سيرفر الديسكورد" else "Win legendary loot keys every Friday on our Discord channel.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ConnectionField(
    label: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onCopy: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)
        ),
        modifier = modifier.clickable { onCopy() }
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = value,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Icon(
                imageVector = icon,
                contentDescription = "Copy",
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun QuickActionButton(
    title: String,
    icon: ImageVector,
    containerColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = textColor
        ),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier.height(56.dp)
    ) {
        Icon(icon, contentDescription = title, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.Bold, maxLines = 1)
    }
}

// ==========================================
// SCREEN 2: NEWS SCREEN
// ==========================================
@Composable
fun NewsScreen(viewModel: MainViewModel, isArabic: Boolean) {
    val newsList by viewModel.newsList.collectAsState()
    
    // Add custom news form states
    var showAddForm by remember { mutableStateOf(false) }
    var titleAr by remember { mutableStateOf("") }
    var titleEn by remember { mutableStateOf("") }
    var contentAr by remember { mutableStateOf("") }
    var contentEn by remember { mutableStateOf("") }
    var categoryAr by remember { mutableStateOf("تحديث") }
    var categoryEn by remember { mutableStateOf("Update") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = Translator.translate("news_updates", isArabic),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.weight(1f)
            )
            
            IconButton(
                onClick = { showAddForm = !showAddForm },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                )
            ) {
                Icon(
                    imageVector = if (showAddForm) Icons.Default.Close else Icons.Default.Add,
                    contentDescription = "Add News",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        AnimatedVisibility(visible = showAddForm) {
            Card(
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = Translator.translate("add_news", isArabic),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    OutlinedTextField(
                        value = titleAr,
                        onValueChange = { titleAr = it },
                        label = { Text("العنوان بالعربية") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = titleEn,
                        onValueChange = { titleEn = it },
                        label = { Text("Title in English") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = contentAr,
                        onValueChange = { contentAr = it },
                        label = { Text("المحتوى بالعربية") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )
                    OutlinedTextField(
                        value = contentEn,
                        onValueChange = { contentEn = it },
                        label = { Text("Content in English") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )
                    
                    Button(
                        onClick = {
                            if (titleAr.isNotBlank() && titleEn.isNotBlank()) {
                                viewModel.addNewsItem(titleAr, titleEn, contentAr, contentEn, categoryAr, categoryEn)
                                titleAr = ""
                                titleEn = ""
                                contentAr = ""
                                contentEn = ""
                                showAddForm = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(Translator.translate("save", isArabic))
                    }
                }
            }
        }

        if (newsList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = Translator.translate("news_empty", isArabic))
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(newsList) { item ->
                    NewsCard(item = item, isArabic = isArabic)
                }
            }
        }
    }
}

@Composable
fun NewsCard(item: NewsEntity, isArabic: Boolean) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category Chip
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (isArabic) item.categoryAr else item.categoryEn,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                // Date stamp
                Text(
                    text = item.date,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = if (isArabic) item.titleAr else item.titleEn,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (isArabic) item.contentAr else item.contentEn,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                maxLines = if (expanded) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Expand link indicator
            Text(
                text = if (expanded) {
                    if (isArabic) "عرض أقل ▲" else "Read Less ▲"
                } else {
                    if (isArabic) "عرض المزيد ▼" else "Read More ▼"
                },
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

// ==========================================
// SCREEN 3: RULES SCREEN
// ==========================================
@Composable
fun RulesScreen(viewModel: MainViewModel, isArabic: Boolean) {
    val rulesList by viewModel.rulesList.collectAsState()

    var showAddForm by remember { mutableStateOf(false) }
    var ruleAr by remember { mutableStateOf("") }
    var ruleEn by remember { mutableStateOf("") }
    var categoryAr by remember { mutableStateOf("عام") }
    var categoryEn by remember { mutableStateOf("General") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = Translator.translate("rules_desc", isArabic),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.weight(1f)
            )
            
            IconButton(
                onClick = { showAddForm = !showAddForm },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                )
            ) {
                Icon(
                    imageVector = if (showAddForm) Icons.Default.Close else Icons.Default.Add,
                    contentDescription = "Add Rule",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        AnimatedVisibility(visible = showAddForm) {
            Card(
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = Translator.translate("add_rule", isArabic),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    OutlinedTextField(
                        value = ruleAr,
                        onValueChange = { ruleAr = it },
                        label = { Text("نص القانون بالعربية") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = ruleEn,
                        onValueChange = { ruleEn = it },
                        label = { Text("Rule in English") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Button(
                        onClick = {
                            if (ruleAr.isNotBlank() && ruleEn.isNotBlank()) {
                                viewModel.addRuleItem(ruleAr, ruleEn, categoryAr, categoryEn, rulesList.size + 1)
                                ruleAr = ""
                                ruleEn = ""
                                showAddForm = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(Translator.translate("save", isArabic))
                    }
                }
            }
        }

        if (rulesList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = Translator.translate("rules_empty", isArabic))
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(rulesList) { item ->
                    RuleRowItem(item = item, isArabic = isArabic)
                }
            }
        }
    }
}

@Composable
fun RuleRowItem(item: RuleEntity, isArabic: Boolean) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Styled Number Badge
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.index.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                // Category Tag
                Text(
                    text = if (isArabic) item.categoryAr else item.categoryEn,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = if (isArabic) item.ruleAr else item.ruleEn,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

// ==========================================
// SCREEN 4: LIVE MAP SCREEN
// ==========================================
@Composable
fun LiveMapScreen(viewModel: MainViewModel, isArabic: Boolean) {
    var zoomLevel by remember { mutableStateOf(1f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = if (isArabic) "عرض مباشر لخريطة DZ CRAFT من خادم المتصفح" else "Live web view of DZ CRAFT Server Map",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        // Custom Live Map Interactive Canvas Mock
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                .background(Color(0xFF2E3B2E)) // Greenish radar background
        ) {
            // Simple grid background
            Canvas(modifier = Modifier.fillMaxSize()) {
                val step = 40f * zoomLevel
                val width = size.width
                val height = size.height
                
                // Draw grid lines
                var x = 0f
                while (x < width) {
                    drawLine(Color(0xFF3E4F3E), Offset(x, 0f), Offset(x, height), 1f)
                    x += step
                }
                var y = 0f
                while (y < height) {
                    drawLine(Color(0xFF3E4F3E), Offset(0f, y), Offset(width, y), 1f)
                    y += step
                }

                // Draw Spawn castle at center
                drawCircle(Color(0xFFFFD54F), 20f * zoomLevel, Offset(width/2, height/2))
                
                // Draw PvP Arena at bottom left
                drawCircle(Color(0xFFE57373), 15f * zoomLevel, Offset(width/4, height*3/4))

                // Draw Marketplace at top right
                drawCircle(Color(0xFF64B5F6), 15f * zoomLevel, Offset(width*3/4, height/4))
            }

            // Radar Scanning Sweep Animation
            val infiniteTransition = rememberInfiniteTransition()
            val angle by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(4000, easing = LinearEasing)
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .rotate(angle)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // sweep gradient
                }
            }

            // Dynamic Player Dot markers overlaying
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopStart)
                    .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Column {
                    Text("📍 Spawn Lobby", color = Color(0xFFFFD54F), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text("⚔️ PvP Colosseum", color = Color(0xFFE57373), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text("🛍️ Trade Market", color = Color(0xFF64B5F6), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            // Zoom buttons
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                IconButton(
                    onClick = { if (zoomLevel < 3f) zoomLevel += 0.2f },
                    colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Zoom In", tint = Color.White)
                }
                IconButton(
                    onClick = { if (zoomLevel > 0.5f) zoomLevel -= 0.2f },
                    colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Zoom Out", tint = Color.White)
                }
            }
        }

        // Active Player list mock on the map
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = Translator.translate("live_players", isArabic),
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val mapPlayers = listOf("DzSlayer", "MinerAlgeria", "CraftPro", "NerdBlock", "Rami_DZ")
                    mapPlayers.forEach { name ->
                        Row(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f), RoundedCornerShape(20.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(0xFF00E676)))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(name, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// SCREEN 5: RESOURCE PACK SCREEN
// ==========================================
@Composable
fun ResourcePackScreen(viewModel: MainViewModel, isArabic: Boolean) {
    var isDownloading by remember { mutableStateOf(false) }
    var downloadProgress by remember { mutableStateOf(0f) }
    var isInstalled by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isArabic) "حزمة الموارد الرسمية لسيرفر DZ CRAFT" else "Official DZ CRAFT Resource Pack",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Image(
            painter = painterResource(id = R.drawable.img_server_logo_1783186592562),
            contentDescription = "Resource Pack Logo",
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
        )

        Text(
            text = if (isArabic) {
                "تحتوي هذه الحزمة على السيوف المخصصة، والمؤثرات البصرية، والأصوات المحدثة التي تحسن الأداء وتجعل العالم أكثر جمالاً!"
            } else {
                "This pack contains custom weapons, optimized visual particles, and updated audio, specifically tuned for low lag!"
            },
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        if (isDownloading) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CircularProgressIndicator(progress = downloadProgress)
                Text(
                    text = "${Translator.translate("downloading", isArabic)} ${(downloadProgress * 100).toInt()}%",
                    fontWeight = FontWeight.Bold
                )
            }
        } else if (isInstalled) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE8F5E9), RoundedCornerShape(12.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Default.ThumbUp, contentDescription = "Installed", tint = Color(0xFF2E7D32))
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = Translator.translate("installed", isArabic),
                    color = Color(0xFF2E7D32),
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            Button(
                onClick = {
                    scope.launch {
                        isDownloading = true
                        while (downloadProgress < 1f) {
                            delay(150)
                            downloadProgress += 0.1f
                        }
                        isDownloading = false
                        isInstalled = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(Icons.Default.Build, contentDescription = "Install Pack")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = Translator.translate("install_pack", isArabic), fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ==========================================
// SCREEN 6: MODS & ADDONS SCREEN
// ==========================================
@Composable
fun ModsAddonsScreen(viewModel: MainViewModel, isArabic: Boolean) {
    val mods = listOf(
        ModItem("3D Swords Mod", "1.4 MB", if (isArabic) "يضيف سيوفاً ثلاثية الأبعاد مميزة لكل اللاعبين." else "Adds custom 3D Swords for all players."),
        ModItem("More Shields Expansion", "850 KB", if (isArabic) "دروع مميزة مأخوذة من الكهوف والمناجم." else "Custom shields with special defensive attributes."),
        ModItem("Algerian Capes Pack", "300 KB", if (isArabic) "عباءات جزائرية مخصصة للرتب الفائزة." else "Exclusive Algerian capes for winners."),
        ModItem("Farming Upgrade Addon", "2.1 MB", if (isArabic) "محاصيل ومأكولات جزائرية جديدة للطبخ." else "New blocky farm upgrades & dishes.")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = if (isArabic) "المودات والملحقات النشطة في السيرفر" else "Active Server Mods & Addons",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        items(mods) { mod ->
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Mod File",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = mod.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Text(text = mod.desc, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = mod.size, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                    Button(
                        onClick = {},
                        contentPadding = PaddingValues(horizontal = 12.dp)
                    ) {
                        Text(text = if (isArabic) "تحميل" else "GET", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

data class ModItem(val name: String, val size: String, val desc: String)

// ==========================================
// SCREEN 7: FAQ SCREEN
// ==========================================
@Composable
fun FaqScreen(viewModel: MainViewModel, isArabic: Boolean) {
    val faqsList by viewModel.faqsList.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val filteredFaqs = faqsList.filter {
        if (isArabic) {
            it.questionAr.contains(searchQuery, ignoreCase = true) || it.answerAr.contains(searchQuery, ignoreCase = true)
        } else {
            it.questionEn.contains(searchQuery, ignoreCase = true) || it.answerEn.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text(Translator.translate("search", isArabic)) },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            shape = RoundedCornerShape(12.dp)
        )

        if (filteredFaqs.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = Translator.translate("faqs_empty", isArabic))
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredFaqs) { item ->
                    FaqAccordion(item = item, isArabic = isArabic)
                }
            }
        }
    }
}

@Composable
fun FaqAccordion(item: FaqEntity, isArabic: Boolean) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isArabic) item.questionAr else item.questionEn,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.Close else Icons.Default.Add,
                    contentDescription = "Toggle"
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (isArabic) item.answerAr else item.answerEn,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

// ==========================================
// SCREEN 8: STAFF TEAM SCREEN
// ==========================================
@Composable
fun StaffTeamScreen(viewModel: MainViewModel, isArabic: Boolean) {
    val staffList by viewModel.staffList.collectAsState()

    if (staffList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = Translator.translate("staff_empty", isArabic))
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(staffList) { staff ->
                StaffCard(staff = staff, isArabic = isArabic)
            }
        }
    }
}

@Composable
fun StaffCard(staff: StaffEntity, isArabic: Boolean) {
    // Generate blocky avatar matching Minecraft layout
    val color = remember(staff.rankColorHex) {
        try {
            Color(android.graphics.Color.parseColor("#${staff.rankColorHex}"))
        } catch (e: Exception) {
            Color.Green
        }
    }

    Card(
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Blocky Character Head Mock
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color.copy(alpha = 0.2f))
                    .border(2.dp, color, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                // Pixel character face drawing
                Canvas(modifier = Modifier.size(36.dp)) {
                    // Draw blocky eyes
                    drawRect(color, Offset(4f, 8f), size = androidx.compose.ui.geometry.Size(8f, 8f))
                    drawRect(color, Offset(24f, 8f), size = androidx.compose.ui.geometry.Size(8f, 8f))
                    // Draw blocky mouth
                    drawRect(color, Offset(8f, 20f), size = androidx.compose.ui.geometry.Size(20f, 6f))
                }
            }

            Text(
                text = staff.name,
                fontWeight = FontWeight.Black,
                fontSize = 15.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Rank Badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(color)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = if (isArabic) staff.roleAr else staff.roleEn,
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// ==========================================
// SCREEN 9: GALLERY SCREEN
// ==========================================
@Composable
fun GalleryScreen(viewModel: MainViewModel, isArabic: Boolean) {
    val galleryList by viewModel.galleryList.collectAsState()

    if (galleryList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = Translator.translate("gallery_empty", isArabic))
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(galleryList) { item ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        // Image loading
                        val imageId = if (item.imageUrl.contains("banner")) {
                            R.drawable.img_server_banner_1783186605491
                        } else {
                            R.drawable.img_server_logo_1783186592562
                        }

                        Image(
                            painter = painterResource(id = imageId),
                            contentDescription = item.titleAr,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                        )
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = if (isArabic) item.titleAr else item.titleEn,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (isArabic) item.descriptionAr else item.descriptionEn,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// SCREEN 10: EVENTS SCREEN
// ==========================================
@Composable
fun EventsScreen(viewModel: MainViewModel, isArabic: Boolean) {
    val eventsList by viewModel.eventsList.collectAsState()

    if (eventsList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = Translator.translate("events_empty", isArabic))
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(eventsList) { event ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFF00E676).copy(alpha = 0.15f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = if (isArabic) "نشط" else "Active",
                                    color = Color(0xFF00C853),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = event.date,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = if (isArabic) event.titleAr else event.titleEn,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = if (isArabic) event.descriptionAr else event.descriptionEn,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Divider()

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Star, contentDescription = "Reward", tint = Color(0xFFFFB300), modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${Translator.translate("reward", isArabic)} ${if (isArabic) event.rewardAr else event.rewardEn}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// SCREEN 11: GIVEAWAYS SCREEN
// ==========================================
@Composable
fun GiveawaysScreen(viewModel: MainViewModel, isArabic: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isArabic) "سحوبات وجوائز DZ CRAFT S1 🎉" else "DZ CRAFT S1 Giveaways 🎉",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Giveaway",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Text(
                    text = if (isArabic) "رتبة VIP مجانية طوال الموسم!" else "Free Seasonal VIP Rank Giveaway!",
                    fontWeight = FontWeight.Black,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = if (isArabic) {
                        "شروط الدخول: تفاعل في السيرفر والتحق بقناة الديسكورد للتسجيل بالكبسة!"
                    } else {
                        "Rules to enter: Be active inside the game and click register in Discord giveaway tab!"
                    },
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )

                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.ThumbUp, contentDescription = "Enter")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (isArabic) "سجل الآن بالديسكورد" else "Register in Discord")
                }
            }
        }
    }
}

// ==========================================
// SCREEN 12: SETTINGS SCREEN
// ==========================================
@Composable
fun SettingsScreen(viewModel: MainViewModel, isArabic: Boolean) {
    val serverIp by viewModel.serverIp.collectAsState()
    val serverPort by viewModel.serverPort.collectAsState()
    val serverVersion by viewModel.serverVersion.collectAsState()

    var ipInput by remember { mutableStateOf(serverIp) }
    var portInput by remember { mutableStateOf(serverPort) }
    var versionInput by remember { mutableStateOf(serverVersion) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = if (isArabic) "تخصيص عنوان الاتصال والمنفذ" else "Configure Connection Settings",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        OutlinedTextField(
            value = ipInput,
            onValueChange = { ipInput = it },
            label = { Text(Translator.translate("ip", isArabic)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
            value = portInput,
            onValueChange = { portInput = it },
            label = { Text(Translator.translate("port", isArabic)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
            value = versionInput,
            onValueChange = { versionInput = it },
            label = { Text(Translator.translate("version", isArabic)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                viewModel.updateServerSettings(ipInput, portInput, versionInput)
                Toast.makeText(context, if (isArabic) "تم حفظ الإعدادات بنجاح!" else "Settings saved!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Icon(Icons.Default.Home, contentDescription = "Save")
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = Translator.translate("save", isArabic), fontWeight = FontWeight.Bold)
        }
    }
}

// ==========================================
// SCREEN 13: ABOUT SCREEN
// ==========================================
@Composable
fun AboutScreen(viewModel: MainViewModel, isArabic: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_server_logo_1783186592562),
            contentDescription = "DZ Craft Logo",
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )

        Text(
            text = "DZ CRAFT S1",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black
        )

        Text(
            text = "Version 1.21.33 / Bedrock",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        Card(
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = Translator.translate("about_content", isArabic),
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
                )
            }
        }
    }
}

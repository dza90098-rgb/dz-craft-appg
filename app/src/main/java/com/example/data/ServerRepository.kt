package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ServerRepository(private val serverDao: ServerDao) {

    val allNews: Flow<List<NewsEntity>> = serverDao.getAllNews()
    val allRules: Flow<List<RuleEntity>> = serverDao.getAllRules()
    val allFaqs: Flow<List<FaqEntity>> = serverDao.getAllFaqs()
    val allStaff: Flow<List<StaffEntity>> = serverDao.getAllStaff()
    val allEvents: Flow<List<EventEntity>> = serverDao.getAllEvents()
    val allGallery: Flow<List<GalleryItemEntity>> = serverDao.getAllGallery()

    suspend fun insertNews(news: List<NewsEntity>) = withContext(Dispatchers.IO) {
        serverDao.insertNews(news)
    }

    suspend fun insertRules(rules: List<RuleEntity>) = withContext(Dispatchers.IO) {
        serverDao.insertRules(rules)
    }

    suspend fun insertFaqs(faqs: List<FaqEntity>) = withContext(Dispatchers.IO) {
        serverDao.insertFaqs(faqs)
    }

    suspend fun insertStaff(staff: List<StaffEntity>) = withContext(Dispatchers.IO) {
        serverDao.insertStaff(staff)
    }

    suspend fun insertEvents(events: List<EventEntity>) = withContext(Dispatchers.IO) {
        serverDao.insertEvents(events)
    }

    suspend fun insertGallery(gallery: List<GalleryItemEntity>) = withContext(Dispatchers.IO) {
        serverDao.insertGallery(gallery)
    }

    suspend fun prePopulateIfEmpty() = withContext(Dispatchers.IO) {
        val currentRules = serverDao.getAllRules().first()
        if (currentRules.isEmpty()) {
            // Populate Rules
            val initialRules = listOf(
                RuleEntity(ruleAr = "احترام جميع اللاعبين وعدم إزعاجهم.", ruleEn = "Respect all players and do not disturb them.", categoryAr = "عام", categoryEn = "General", index = 1),
                RuleEntity(ruleAr = "يمنع الغش، استخدام الهكات، أو الاستغلال المكرر للثغرات.", ruleEn = "Cheating, hacking, or exploiting glitches is strictly forbidden.", categoryAr = "اللعب العادل", categoryEn = "Fair Play", index = 2),
                RuleEntity(ruleAr = "يمنع السب، الشتم، أو الكلام غير اللائق في الدردشة العامة.", ruleEn = "Swearing, insulting, or toxic chat is not allowed.", categoryAr = "الدردشة", categoryEn = "Chat Rules", index = 3),
                RuleEntity(ruleAr = "يمنع تخريب ممتلكات الآخرين (الـ Griefing) أو سرقتهم.", ruleEn = "Griefing, destroying property, or stealing is forbidden.", categoryAr = "حماية الممتلكات", categoryEn = "Grief Prevention", index = 4),
                RuleEntity(ruleAr = "احترام قرارات الإدارة والتعاون مع المشرفين لحل النزاعات.", ruleEn = "Respect staff decisions and cooperate with administrators.", categoryAr = "عام", categoryEn = "General", index = 5)
            )
            serverDao.insertRules(initialRules)

            // Populate News
            val initialNews = listOf(
                NewsEntity(
                    titleAr = "تحديث السيرفر الضخم 1.21.33 🚀",
                    titleEn = "Huge Server Update 1.21.33 🚀",
                    contentAr = "أهلاً بأبطال DZ CRAFT S1! تم تحديث السيرفر بالكامل ليدعم أحدث إصدار من ماينكرافت بدروك 1.21.33. تم إصلاح المشاكل السابقة، وتحسين جودة الاتصال لتقليل اللاغ بشكل كامل مع زيادة سعة اللاعبين إلى 100 لاعب!",
                    contentEn = "Welcome heroes of DZ CRAFT S1! The server has been fully updated to support the latest Minecraft Bedrock 1.21.33. Fixed previous bugs, improved connection stability, and increased slot limit to 100 players!",
                    date = "2026-07-04",
                    categoryAr = "تحديث",
                    categoryEn = "Update"
                ),
                NewsEntity(
                    titleAr = "إضافة مود الأسلحة والدروع ثلاثية الأبعاد ⚔️",
                    titleEn = "New 3D Weapons & Armor Addon ⚔️",
                    contentAr = "يسرنا أن نعلن عن إضافة ريسورس باك ومود خرافي يضيف أسلحة ثلاثية الأبعاد، وسيوف مطورة، ودروع بتصاميم أسطورية خاصة بسيرفر DZ CRAFT! يمكنكم تحميل الملفات تلقائياً عند دخول السيرفر.",
                    contentEn = "We are pleased to announce the addition of a custom resource pack adding epic 3D weapons, special swords, and customized legendary armor! Files will download automatically upon joining.",
                    date = "2026-07-02",
                    categoryAr = "إضافات",
                    categoryEn = "Addons"
                ),
                NewsEntity(
                    titleAr = "بدء التسجيل لبطولة البناء الكبرى 🏰",
                    titleEn = "Registrations Open for Grand Building Event 🏰",
                    contentAr = "هل أنت باني محترف؟ تم فتح باب التسجيل لبطولة البناء الأسبوعية في سيرفر DZ CRAFT S1. الجوائز تشمل رتبة مميزة داخل السيرفر ومجموعات حزم من الزمرد والدايموند النادر!",
                    contentEn = "Are you a master builder? Registration is open for the weekly building championship. Prizes include exclusive server ranks, stacks of emeralds, and rare diamonds!",
                    date = "2026-06-28",
                    categoryAr = "فعالية",
                    categoryEn = "Event"
                )
            )
            serverDao.insertNews(initialNews)

            // Populate FAQs
            val initialFaqs = listOf(
                FaqEntity(
                    questionAr = "كيف يمكنني الانضمام للسيرفر؟",
                    questionEn = "How can I join the server?",
                    answerAr = "يمكنك الانضمام من خلال إضافة السيرفر في قائمة الأصدقاء أو الخوادم بلعبتك باستخدام IP: SEEN والمنفذ (Port): 19132. تأكد من أن لعبتك محدثة للإصدار 1.21.33 أو 1.21.x.",
                    answerEn = "You can join by adding the server to your server list in Minecraft Bedrock using IP: SEEN and Port: 19132. Ensure your game is on version 1.21.33 or 1.21.x."
                ),
                FaqEntity(
                    questionAr = "ما هي الأجهزة المدعومة للسيرفر؟",
                    questionEn = "What devices are supported?",
                    answerAr = "السيرفر مبني لنسخة ماينكرافت بدروك (Bedrock Edition)، مما يعني أنه يمكنك اللعب من خلال هاتف الأندرويد، الآيفون، الكمبيوتر (Win 10/11)، أجهزة التابلت، وحتى أجهزة الكونسول التي تدعم الاتصال بالخوادم الخارجية.",
                    answerEn = "The server is built for Minecraft Bedrock Edition, meaning you can play on Android, iOS, Windows 10/11, tablets, and consoles that support custom server connections."
                ),
                FaqEntity(
                    questionAr = "كيف أقوم بتحميل مودات وحزم الموارد (Resource Packs)؟",
                    questionEn = "How do I download resource packs?",
                    answerAr = "لا تحتاج لتحميل أي شيء بشكل منفصل! بمجرد ضغطك على 'انضمام' للسيرفر، ستظهر لك رسالة تطلب منك الموافقة على تحميل حزمة الموارد الخاصة بالسيرفر. وافق عليها وسيتم تفعيل المودات تلقائياً.",
                    answerEn = "No manual download is needed! When joining, a prompt will ask you to download the server resource pack. Agree to it, and all mods/addons will load automatically."
                ),
                FaqEntity(
                    questionAr = "كيف أصبح جزءاً من طاقم الإدارة؟",
                    questionEn = "How do I apply for the Staff Team?",
                    answerAr = "نقوم بفتح باب التقديم للإشراف والدعم الفني بشكل دوري في سيرفر الديسكورد الخاص بنا. الشروط تشمل التفاعل المستمر، السلوك الحسن، والالتزام بمساعدة اللاعبين الجدد.",
                    answerEn = "We open recruitment for moderators and support staff periodically on our Discord server. Requirements include activity, good behavior, and helping new players."
                )
            )
            serverDao.insertFaqs(initialFaqs)

            // Populate Staff Team
            val initialStaff = listOf(
                StaffEntity(name = "DZ_CRAFT_OWNER", roleAr = "المالك والمطور الرئيسي", roleEn = "Owner & Lead Developer", rankColorHex = "FF3D00"),
                StaffEntity(name = "ALGERIAN_BOY", roleAr = "نائب المالك والمشرف العام", roleEn = "Co-Owner & Admin", rankColorHex = "00E676"),
                StaffEntity(name = "CRAFT_MASTER", roleAr = "مدير الفعاليات والدعم الفني", roleEn = "Event Manager & Tech Support", rankColorHex = "29B6F6"),
                StaffEntity(name = "MC_MODERATOR", roleAr = "مراقب الدردشة ومكافحة التخريب", roleEn = "Chat & Anti-Grief Mod", rankColorHex = "FFD600")
            )
            serverDao.insertStaff(initialStaff)

            // Populate Events
            val initialEvents = listOf(
                EventEntity(
                    titleAr = "بطولة البي في بي الأسبوعية (PvP Arena) ⚔️",
                    titleEn = "Weekly PvP Tournament ⚔️",
                    descriptionAr = "انضم إلينا يوم الجمعة في ساحة الكولوسيوم الكبرى للمنافسة على لقب بطل الـ PvP في سيرفر DZ CRAFT S1. يُمنع استخدام الدروع المخصصة أو الهكات تماماً.",
                    descriptionEn = "Join us this Friday in the grand Colosseum to compete for the PvP Champion title on DZ CRAFT S1. No custom armor or cheats allowed.",
                    date = "كل جمعة - الساعة 8:00 مساءً بالتوقيت المحلي",
                    rewardAr = "رتبة [PvP King] + 32 حبة تفاح ذهبي مطور!",
                    rewardEn = "[PvP King] Rank + 32 Enchanted Golden Apples!",
                    isActive = true
                ),
                EventEntity(
                    titleAr = "البحث عن الكنز المفقود 🗺️🎁",
                    titleEn = "Lost Treasure Hunt 🗺️🎁",
                    descriptionAr = "تم تخبئة صندوق هدايا أسطوري في مكان ما في خريطة السيرفر بمناسبة التحديث الجديد 1.21.33. سيتم إعطاء تلميحات بالدردشة بشكل تدريجي للوصول للمكان.",
                    descriptionEn = "An epic treasure chest has been hidden somewhere in the server world to celebrate the new 1.21.33 update. Hints will be broadcasted in chat.",
                    date = "يوم الأحد القادم",
                    rewardAr = "سيف نذررايت مطور بأقوى التطويرات [DZ sword]!",
                    rewardEn = "A legendary fully enchanted Netherite Sword [DZ sword]!",
                    isActive = true
                )
            )
            serverDao.insertEvents(initialEvents)

            // Populate Gallery Items
            val initialGallery = listOf(
                GalleryItemEntity(
                    imageUrl = "img_server_banner_1783186605491.jpg",
                    titleAr = "السبون الرئيسي للسيرفر 🏰",
                    titleEn = "Main Server Spawn 🏰",
                    descriptionAr = "لقطة ساحرة للمبنى الرئيسي والسبون عند غروب الشمس.",
                    descriptionEn = "Stunning view of the main lobby and spawn during sunset."
                ),
                GalleryItemEntity(
                    imageUrl = "img_server_logo_1783186592562.jpg",
                    titleAr = "شعار DZ CRAFT الرسمي 🛡️",
                    titleEn = "Official DZ CRAFT Logo 🛡️",
                    descriptionAr = "شعار السيرفر المصمم كرمز للقوة والمغامرة.",
                    descriptionEn = "The official server logo, embodying power and adventure."
                )
            )
            serverDao.insertGallery(initialGallery)
        }
    }
}

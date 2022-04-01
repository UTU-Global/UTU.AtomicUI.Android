package global.ututaxfree.androidui

import android.app.Application
import com.lokalise.sdk.Lokalise

/**
 * Created by Bharath Grandhe on 01/04/2022
 */
class AtomicUIApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Lokalise.init(
            this, "583812487fff520277710df81b2fdfe8dc24d2b0",
            "379618946215ab968dfc94.91153492"
        )
        // Fetch the latest translations from Lokalise (can be called anywhere)
        Lokalise.updateTranslations()
    }
}
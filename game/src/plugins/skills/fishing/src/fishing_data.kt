package org.apollo.game.plugin.skills.fishing

import org.apollo.game.model.Animation
import java.util.*


data class Fish(val id: Int, val level: Int, val exp: Double, val name: String)
data class FishingTool(val id: Int, val animation: Animation, val message: String, val bait: Int, val name: String)
data class FishingSpot(val id: Int, val tools:IntArray, val firstFish: IntArray, val secondFish: IntArray)

val FISH = mutableMapOf<Int, Fish>(
        317 to Fish(317, 1, 10.0, "shrimp"),
        327 to Fish(327, 5, 20.0, "sardine"),
        345 to Fish(354, 10, 30.0, "herring"),
        321 to Fish(321, 15, 40.0, "anchovy"),
        353 to Fish(353, 16, 20.0, "mackerel"),
        335 to Fish(335, 20, 50.0, "trout"),
        341 to Fish(341, 23, 45.0, "cod"),
        349 to Fish(349, 25, 60.0, "pike"),
        331 to Fish(331, 30, 70.0, "salmon"),
        359 to Fish(359, 35, 80.0, "tuna"),
        377 to Fish(377, 40, 90.0, "lobster"),
        363 to Fish(363, 46, 100.0, "bass"),
        371 to Fish(371, 50, 100.0, "swordfish"),
        383 to Fish(383, 76, 110.0, "shark")
)

val FISHING_TOOLS = mutableMapOf<Int, FishingTool>(
        301 to FishingTool(301, Animation(619), "You attempt to catch a lobster...", -1, "lobster cage"),
        303 to FishingTool(303, Animation(620),  "You cast out your net...", -1, "small net"),
        305 to FishingTool(305, Animation(620),  "You cast out your net...", -1, "big net"),
        311 to FishingTool(311, Animation(618),  "You start harpooning fish...", -1, "harpoon"),
        307 to FishingTool(307, Animation(622),  "You attempt to catch a fish...", 313, "fishing rod"),
        309 to FishingTool(309, Animation(622),  "You attempt to catch a fish...", 314, "fishing rod")
)

val FISHING_SPOTS = mutableMapOf<Int, FishingSpot>(
        309 to FishingSpot(309, intArrayOf(307, 309), intArrayOf(335, 331), intArrayOf(349)),
        312 to FishingSpot(312, intArrayOf(301, 311), intArrayOf(377), intArrayOf(359, 371)),
        313 to FishingSpot(313, intArrayOf(305, 311), intArrayOf(353, 341), intArrayOf(363, 383)),
        316 to FishingSpot(316, intArrayOf(303, 307), intArrayOf(317, 321), intArrayOf(327, 345))
)
